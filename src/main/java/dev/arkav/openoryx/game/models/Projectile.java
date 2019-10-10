package dev.arkav.openoryx.game.models;

import dev.arkav.openoryx.game.info.GameInfoMapper;
import dev.arkav.openoryx.game.info.ObjectInfo;
import dev.arkav.openoryx.game.info.ProjectileInfo;

@SuppressWarnings("ALL")
public class Projectile {
    public int parrentType;
    public ObjectInfo parrentProperties;
    public int parrent;

    public byte bulletType;
    public int bulletId;

    public float startAngle;
    public int startTime;

    public Vector2 startPos;
    public Vector2 currentPos;

    public boolean damagePlayers;
    public boolean damageEnemies;
    public int damage;

    public ProjectileInfo proporties;

    public Projectile(int parrentType, int parrent, byte bulletType, int bulletId, float startAngle, int startTime, Vector2 startPos) {
        this.parrentType = parrentType;
        this.parrentProperties = GameInfoMapper.objectById(parrentType);
        this.parrent = parrent;

        this.bulletType = bulletType;
        this.bulletId = bulletId;

        this.startAngle = startAngle;
        this.startTime = startTime;
        this.startPos = startPos;

        this.proporties = this.parrentProperties.getProjectiles()[this.bulletType];

        this.damage = 0;
    }

    public boolean update(int currentTime) {
        int elapsed = currentTime - this.startTime;
        if (elapsed > this.proporties.getLifetime()) return false;
        this.currentPos = this.getPositionAt(elapsed);
        return true;
    }

    // taken from nrelay
    private Vector2 getPositionAt(int time) {
        Vector2 point = this.startPos.clone();

        float distanceTravelled = time * (this.proporties.getSpeed() / 10000);
        double phase = this.bulletId % 2 == 0 ? 0 : Math.PI;

        if (this.proporties.isWavy()) {
            double newAngle = this.startAngle + (Math.PI / 64) * Math.sin(phase + (6 * Math.PI) * time / 1000);
            point.x += distanceTravelled * Math.cos(newAngle);
            point.y += distanceTravelled * Math.sin(newAngle);
        } else if (this.proporties.isParametric()) {
            double offset1 = time / this.proporties.getLifetime() * 2 * Math.PI;
            double offset2 = Math.sin(offset1) * (this.bulletId % 2 > 0 ? 1 : -1);
            double offset3 = Math.sin(2 * offset1) * (this.bulletId % 4 < 2 ? 1 : -1);
            double angleX = Math.cos(this.startAngle);
            double angleY = Math.cos(this.startAngle);

            point.x += (offset2 * angleY - offset3 * angleX) * this.proporties.getMagnitude();
            point.y += (offset2 * angleX - offset3 * angleY) * this.proporties.getMagnitude();
        } else {
            if (this.proporties.isBoomerang()) {
                float halfwayPoint = this.proporties.getLifetime() * (this.proporties.getSpeed() / 10000) / 2;
                if (distanceTravelled > halfwayPoint) {
                    distanceTravelled = halfwayPoint - (distanceTravelled - halfwayPoint);
                }
            }
            point.x += distanceTravelled * Math.cos(this.startAngle);
            point.y += distanceTravelled * Math.sin(this.startAngle);

            if (this.proporties.getAmplitude() != 0) {
                double deflection = this.proporties.getAmplitude() * Math.sin(phase + time / this.proporties.getLifetime() * this.proporties.getFrequency() * 2 * Math.PI);
                point.x += deflection * Math.cos(this.startAngle + Math.PI / 2);
                point.y += deflection * Math.sin(this.startAngle + Math.PI / 2);
            }
        }
        return point;
    }
}
