package dev.arkav.openoryx.impl;

import dev.arkav.openoryx.game.models.*;
import dev.arkav.openoryx.util.logging.Logger;
import dev.arkav.openoryx.game.info.GameInfoMapper;
import dev.arkav.openoryx.game.info.ObjectInfo;
import dev.arkav.openoryx.game.info.TileInfo;
import dev.arkav.openoryx.net.data.GroundTileData;
import dev.arkav.openoryx.net.data.ObjectData;
import dev.arkav.openoryx.net.data.ObjectStatusData;
import dev.arkav.openoryx.net.data.WorldPosData;
import dev.arkav.openoryx.net.listeners.ListenerType;
import dev.arkav.openoryx.net.packets.PacketType;
import dev.arkav.openoryx.net.packets.c2s.EnemyHitPacket;
import dev.arkav.openoryx.net.packets.c2s.PlayerHitPacket;
import dev.arkav.openoryx.net.packets.c2s.ShootAckPacket;
import dev.arkav.openoryx.net.packets.s2c.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("WeakerAccess")
public class CombatClient extends Client implements Runnable {
    //protected Map<Integer, ObjectStatus> loadedPlayers;
    //protected Map<Integer, Enemy> enemies;
    /**
     * Mostly unimportant game entities
     */
    protected final ConcurrentHashMap<Integer, Entity> entities;
    /**
     * Living game entities that are important
     */
    protected final ConcurrentHashMap<Integer, LivingEntity> livingEntities;
    /**
     * Projectiles, must me synchronized!
     */
    protected final ArrayList<Projectile> projectiles;
    /**
     * Tiles the client is unable to walk on
     */
    protected TileMap<Boolean> nowalk;
    /**
     * The point the client should move towards
     */
    protected Vector2 target;

    protected int chp;
    protected int lastNewTick;
    protected int lastTickId;

    public WorldPosData getPos() {
        return this.pos;
    }

    public CombatClient(Account account) {
        super(account);
        new Thread(this).start();

        this.entities = new ConcurrentHashMap<>();
        this.livingEntities = new ConcurrentHashMap<>();
        this.projectiles = new ArrayList<>();

        this.ls.hook(ListenerType.CONNECT, () -> {
            this.entities.clear();
            this.livingEntities.clear();
            this.projectiles.clear();
            this.target = null;
        });

        this.ls.hook(PacketType.MAPINFO, p -> {
            MapInfoPacket mapInfo = (MapInfoPacket)p;
            this.nowalk = new TileMap<>(mapInfo.width, mapInfo.height);
            this.nowalk.fill(true);
            this.lastNewTick = this.getTime();
        });

        this.ls.hook(PacketType.UPDATE, p -> {
            UpdatePacket update = (UpdatePacket)p;

            for (ObjectData obj : update.newObjects) {
                ObjectInfo go = GameInfoMapper.objectById(obj.objectType);
                if (obj.status.objectId == this.objectId) {
                    this.chp = this.data.hp;
                }
                if (go.isEnemy() || go.isPlayer()) {
                    this.livingEntities.put(obj.status.objectId, new LivingEntity(obj.status, go));
                } else {
                    this.entities.put(obj.status.objectId, new Entity(obj.status, go));
                }
                // Add wall entities as unwalkable tiles
                if (go.isFullOccupy() || go.isEnemyOccupySquare() || go.isOccupySquare()) this.nowalk.set(obj.status.pos, false);
            }

            for (GroundTileData tile : update.tiles) {
                TileInfo to = GameInfoMapper.tileById(tile.type);
                if (to.isNoWalk()) this.nowalk.set(tile.x, tile.y, false);
            }

            for (int drop : update.drops) {
                this.entities.remove(drop);
                this.livingEntities.remove(drop);
            }
        });

        this.ls.hook(PacketType.NEWTICK, p -> {
            NewTickPacket newTick = (NewTickPacket)p;
            int delta = this.getTime() - lastNewTick;
            lastNewTick = this.getTime();
            // tick entities/livingEntities
            for (ObjectStatusData obj : newTick.statuses) {
                if (this.entities.containsKey(obj.objectId)) {
                    this.entities.get(obj.objectId).setPos(obj.pos.x, obj.pos.y);
                }

                if (this.livingEntities.containsKey(obj.objectId)) {
                    this.livingEntities.get(obj.objectId).tick(delta, newTick.tickId, obj.pos, obj.stats);
                }
            }
            // vit
            this.chp += delta / 1000 * (1 + 0.12 * this.data.vit);
            if (this.chp > this.data.maxHp) {
                this.chp = this.data.maxHp;
            }
            this.lastTickId = newTick.tickId;
        });

        this.ls.hook(PacketType.ENEMYSHOOT, p -> {
            EnemyShootPacket enemyShoot = (EnemyShootPacket)p;

            ShootAckPacket ack = new ShootAckPacket();
            ack.time = this.getTime();

            this.io.send(ack);

            if (this.livingEntities.containsKey(enemyShoot.ownerId)) {
                LivingEntity e = this.livingEntities.get(enemyShoot.ownerId);
                if (e.getInfo().isEnemy() && e.getObjectId() == enemyShoot.ownerId) {
                    for (int i = 0; i < enemyShoot.numShots; i++) {
                        synchronized (this.projectiles) {
                            this.projectiles.add(new Projectile(
                                    e.getInfo().getId(),
                                    enemyShoot.ownerId,
                                    enemyShoot.bulletType,
                                    (enemyShoot.bulletId + i) % 256,
                                    enemyShoot.angle + i * enemyShoot.angleInc,
                                    this.getTime(),
                                    enemyShoot.startingPos.clone()
                            ));
                        }
                    }
                }
            }
        });

        this.ls.hook(PacketType.SERVERPLAYERSHOOT, p -> {
            ServerPlayerShootPacket serverPlayerShoot = (ServerPlayerShootPacket)p;
            if (serverPlayerShoot.ownerId == this.objectId) {
                ShootAckPacket ack = new ShootAckPacket();
                ack.time = this.getTime();
                this.io.send(ack);
            }
        });

        this.ls.hook(PacketType.RECONNECT, p -> {
            ReconnectPacket rp = (ReconnectPacket)p;
            Logger.log(this.account.getGuid(), "Reconnect to: " + rp.name);
            this.gameState.gameId = rp.gameId;
            this.gameState.keyTime = rp.keyTime;
            this.gameState.key = rp.key;

            this.currentServer.setName(rp.name);
            this.currentServer.setPort(rp.port);
            this.currentServer.setHost(rp.host);
            this.connect(this.currentServer, this.gameState);
        });
    }

    @Override
    public void run() {
        int lastFrame = this.getTime();
        while (!this.destroyed) {
            if (this.connected) {
                int delta = this.getTime() - lastFrame;
                lastFrame = this.getTime();
                try {
                    // Update enemies
                    for (LivingEntity e : this.livingEntities.values()) {
                        e.update(delta, this.lastTickId);
                    }
                    this.checkProjectiles();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (this.target != null && this.pos != null && this.pos.distanceTo(this.target) > 0) {
                    double angle = Math.atan2(this.target.y - this.pos.y, this.target.x - this.pos.x);
                    delta = delta > 200 ? 200 : delta;
                    double speed = MIN_MOVE_SPEED + this.data.spd / 75f * (MAX_MOVE_SPEED - MIN_MOVE_SPEED) / 2;
                    double step = speed * delta;

                    if (step > this.pos.distanceTo(this.target)) step = this.pos.distanceTo(this.target);

                    float x = (float)(this.pos.x + step * Math.cos(angle));
                    float y = (float)(this.pos.y + step * Math.sin(angle));
                    if (!this.nowalk.get(x, this.pos.y)) this.pos.x = x;
                    if (!this.nowalk.get(this.pos.x, y)) this.pos.y = y;
                }
            }
            try {
                Thread.sleep(1000 / 30);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void damage(int damage, int bulletId, int objectId) throws IOException {
        int min = damage * 3 / 20;
        int actual = Math.max(min, damage - this.data.def);
        this.chp -= actual;
        Logger.log(this.account.getGuid(), "Hit for " + actual + " now at " + this.chp + "hp server hp: " + this.data.hp);
        if (this.data.hp < this.data.maxHp / 5 || this.chp < this.data.hp / 5) this.disconnect();
        if (this.chp < this.data.hp / 2 || this.chp > this.data.hp) {
            this.chp = this.data.hp;
            Logger.log(this.account.getGuid(), "Hp desync, resync with server!");
        }
        PlayerHitPacket playerHit = new PlayerHitPacket();
        playerHit.bulletId = (byte)bulletId;
        playerHit.objectId = objectId;

        this.io.send(playerHit);
    }

    private void checkProjectiles() throws IOException {
        synchronized (this.projectiles) {
            for (Iterator<Projectile> i = this.projectiles.iterator(); i.hasNext();) {
                Projectile p = i.next();
                if (!p.update(this.getTime())) {
                    this.projectiles.remove(p.bulletId);
                    continue;
                }

                if (p.damagePlayers) {
                    if (this.pos.distanceTo(p.currentPos) < 0.25f) {
                        this.damage(p.proporties.getDamage(), p.bulletId, p.parrent);
                        i.remove();
                    }
                } else {
                    LivingEntity closest = null;
                    double closestDistance = 0.25f;

                    for (LivingEntity e : this.livingEntities.values()) {
                        if (e.getInfo().isEnemy()) {
                            double dist = e.getPos().distanceTo(p.currentPos);
                            if (dist < closestDistance) {
                                closestDistance = dist;
                                closest = e;
                            }
                        }
                    }

                    if (closest != null) {
                        EnemyHitPacket enemyHit = new EnemyHitPacket();
                        int damage = closest.damage(p.damage);

                        enemyHit.bulletId = (byte) p.bulletId;
                        enemyHit.targetId = closest.getObjectId();
                        enemyHit.time = this.getTime();
                        enemyHit.kill = closest.getStats().hp <= damage;

                        this.io.send(enemyHit);
                        i.remove();
                    }
                }
            }
        }
    }

    public TileMap getWalkableTiles() {
        return nowalk;
    }

    public ConcurrentHashMap<Integer, Entity> getEntities() {
        return entities;
    }

    public ConcurrentHashMap<Integer, LivingEntity> getLivingEntities() {
        return livingEntities;
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public Vector2 getTarget() {
        return target;
    }

    public void setTarget(float x, float y) {
        if (this.target == null) this.target = new Vector2(x, y);
        this.target.x = x;
        this.target.y = y;
    }

    public boolean isConnected() {
        return this.connected;
    }

    private static final double MIN_MOVE_SPEED = 0.004;
    private static final double MAX_MOVE_SPEED = 0.0096;

    private static final int[] CLASSES = new int[] {
            768,
            775,
            782,
            784,
            797,
            798,
            799,
            800,
            801,
            802,
            803,
            804,
            805,
            806,
            785
    };
}
