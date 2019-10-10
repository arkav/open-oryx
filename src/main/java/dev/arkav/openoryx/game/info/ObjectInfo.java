package dev.arkav.openoryx.game.info;


import org.jdom2.Element;

import static dev.arkav.openoryx.util.OptionalXml.*;

import java.util.ArrayList;

@SuppressWarnings("WeakerAccess")
public class ObjectInfo {
    public static final ObjectInfo DEFAULT = new ObjectInfo();
    /**
     * Name of the object.
     */
    private final String name;
    /**
     * Id of the object.
     */
    private final int id;

    /**
     * What type of object.
     */
    private final String objectClass;

    /**
     * Maximum hitpoints of the object.
     */
    private final int maxHp;

    /**
     * The amount of exp gained when the object dies.
     */
    private final float xpMultiplier;

    /**
     * Weather or not the object can be walked over.
     */
    private final boolean isStatic;
    private final boolean occupySquare;
    private final boolean enemyOccupySquare;
    private final boolean fullOccupy;
    private final boolean blocksSight;

    /**
     * Weather or not the object protects the player from ground damage.
     */
    private final boolean protectsFromGroundDammage;

    /**
     * Weather or not the object protects the player from sinking.
     */
    private final boolean protectsFromSink;

    /**
     * Weather or not the object is an enemy.
     */
    private final boolean enemy;

    /**
     * Weather or not the object is a player.
     */
    private final boolean player;

    /**
     * Weather or not the object is a pet.
     */
    private final boolean pet;

    private final int size;

    private int shadowSize;

    private final boolean drawOnGround;
    /**
     * How much defense the object child.
     */
    private final int defence;

    /**
     * Weather the object is flying.
     */
    private final boolean flying;

    /**
     * Weather or not the object counts towards god kills.
     */
    private final boolean god;

    /**
     * Weather or not the object is a quest target.
     */
    private final boolean quest;

    /**
     * Weather or not the object is an item.
     */
    private final boolean item;

    /**
     * Weather or not the item can be traded.
     */
    private final boolean soulbound;

    /**
     * Weather or not the object is usable.
     */
    private final boolean usable;

    /**
     * MP cost of using to object.
     */
    private final int mpCost;

    /**
     * Projectiles
     */
    private final ProjectileInfo[] projectiles;

    private TextureInfo texture;
    private TextureInfo animatedTexture;

    public ObjectInfo(Element e) {
        this.id = attributeIntDecode(e, "type", -1);
        this.name = attribute(e, "id", "unknown");
        this.objectClass = child(e, "Class", "unknown");

        this.maxHp = childIntHex(e, "MaxHitPoints", 0);
        this.xpMultiplier = childFloat(e,"XpMult", 0);

        this.isStatic = child(e, "Static");
        this.occupySquare = child(e, "OccupySquare");
        this.enemyOccupySquare = child(e, "EnemyOccupySquare");
        this.fullOccupy = child(e, "FullOccupy");
        this.blocksSight = child(e, "BlocksSight");
        this.protectsFromGroundDammage = child(e, "ProtectFromGroundDamage");
        this.protectsFromSink = child(e, "ProtectFromSink");
        this.enemy = child(e, "Enemy");
        this.player = child(e, "Player");
        this.pet = child(e, "Pet");
        this.drawOnGround = child(e, "DrawOnGround");

        this.size = childInt(e, "Size", 0);
        this.shadowSize = childInt(e, "ShadowSize", 0);
        this.defence = childInt(e, "Defence", 0);
        this.flying = child(e, "Flying");
        this.god = child(e, "God");
        this.quest = child(e, "Quest");

        this.item = child(e, "Item");
        this.usable = child(e, "Usable");
        this.soulbound = child(e, "Soulbound");

        this.mpCost = childInt(e,"MpCost", 0);
        ArrayList<ProjectileInfo> projectiles = new ArrayList<>();
        if (child(e, "Projectile")) {
            for (Element elm : e.getChildren("Projectile")) {
                projectiles.add(new ProjectileInfo(elm));
            }
        }
        this.projectiles = (ProjectileInfo[])projectiles.toArray();

        this.texture = child(e, "Texture") ? new TextureInfo(e.getChild("Texture")) : TextureInfo.DEFAULT;
        this.animatedTexture = child(e, "AnimatedTexture") ? new TextureInfo(e.getChild("AnimatedTexture")) : TextureInfo.DEFAULT;
    }

    private ObjectInfo() {
        this.id = -1;
        this.name = "unknown";
        this.objectClass = "unknown";
        this.maxHp = 0;
        this.xpMultiplier = 0;
        this.isStatic = false;
        this.occupySquare = false;
        this.enemyOccupySquare = false;
        this.fullOccupy = false;
        this.blocksSight = false;
        this.protectsFromGroundDammage = false;
        this.protectsFromSink = false;
        this.enemy = false;
        this.player = false;
        this.pet = false;
        this.drawOnGround = false;
        this.size = 0;
        this.defence = 0;
        this.flying = false;
        this.god = false;
        this.quest = false;
        this.item = false;
        this.usable = false;
        this.soulbound = false;
        this.mpCost = 0;
        this.projectiles = new ProjectileInfo[0];
        this.texture = TextureInfo.DEFAULT;
        this.animatedTexture = TextureInfo.DEFAULT;
    }


    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getObjectClass() {
        return objectClass;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public float getXpMultiplier() {
        return xpMultiplier;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public boolean isOccupySquare() {
        return occupySquare;
    }

    public boolean isEnemyOccupySquare() {
        return enemyOccupySquare;
    }

    public boolean isFullOccupy() {
        return fullOccupy;
    }

    public boolean isBlocksSight() {
        return blocksSight;
    }

    public boolean isProtectsFromGroundDammage() {
        return protectsFromGroundDammage;
    }

    public boolean isProtectsFromSink() {
        return protectsFromSink;
    }

    public boolean isEnemy() {
        return enemy;
    }

    public boolean isPlayer() {
        return player;
    }

    public boolean isPet() {
        return pet;
    }

    public int getSize() {
        return size;
    }

    public int getShadowSize() {
        return shadowSize;
    }

    public boolean isDrawOnGround() {
        return drawOnGround;
    }

    public int getDefence() {
        return defence;
    }

    public boolean isFlying() {
        return flying;
    }

    public boolean isGod() {
        return god;
    }

    public boolean isQuest() {
        return quest;
    }

    public boolean isItem() {
        return item;
    }

    public boolean isSoulbound() {
        return soulbound;
    }

    public boolean isUsable() {
        return usable;
    }

    public int getMpCost() {
        return mpCost;
    }

    public TextureInfo getTexture() {
        return texture;
    }

    public ProjectileInfo[] getProjectiles() {
        return projectiles;
    }

    public TextureInfo getAnimatedTexture() {
        return animatedTexture;
    }
}
