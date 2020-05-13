package dev.arkav.openoryx.game.info;

import dev.arkav.openoryx.game.models.ConditionEffect;
import dev.arkav.openoryx.util.OptionalXml;
import org.jdom2.Element;

import java.util.ArrayList;

public class ProjectileInfo {
    public static final ProjectileInfo DEFAULT = new ProjectileInfo();
    private final byte id;

    private final int damage;
    private final float speed;
    private final int size;
    private final float lifetime;

    private final int minDamage;
    private final int maxDamage;

    private final float magnitude;
    private final float amplitude;
    private final float frequency;

    private final boolean wavy;
    private final boolean parametric;
    private final boolean boomerang;
    private final boolean armorPierce;
    private final boolean multiHit;
    private final boolean passesCover;

    private ConditionEffect[] effects;

    private final TextureInfo texture;

    private ProjectileInfo() {
        this.id = 0;
        this.damage = 0;
        this.speed = 1;
        this.size = 1;
        this.lifetime = 10000;
        this.minDamage = 0;
        this.maxDamage = 0;

        this.magnitude = 0;
        this.amplitude = 0;
        this.frequency = 0;

        this.wavy = false;
        this.parametric = false;
        this.boomerang = false;
        this.armorPierce = false;
        this.multiHit = true;
        this.passesCover = false;

        this.effects = new ConditionEffect[0];
        this.texture = TextureInfo.DEFAULT;
    }

    public ProjectileInfo(Element e) {
        this.id = Byte.parseByte(OptionalXml.attribute(e, "id", "0"));

        this.damage = OptionalXml.childInt(e,"Damage", 0);
        this.speed = OptionalXml.childFloat(e, "Speed", 0);
        this.size = OptionalXml.childInt(e, "Size", 0);
        this.lifetime = OptionalXml.childFloat(e, "LifetimeMS", 0);

        this.minDamage = OptionalXml.childInt(e, "MinDamage", 0);
        this.maxDamage = OptionalXml.childInt(e, "MaxDamage", 0);

        this.magnitude = OptionalXml.childFloat(e, "Magnitude", 0);
        this.amplitude = OptionalXml.childFloat(e, "Amplitude", 0);
        this.frequency = OptionalXml.childFloat(e, "Frequency", 0);

        this.wavy = OptionalXml.child(e, "Wavy");
        this.parametric = OptionalXml.child(e, "Parametric");
        this.boomerang = OptionalXml.child(e,"Boomerang");
        this.armorPierce = OptionalXml.child(e, "ArmorPiercing");
        this.multiHit = OptionalXml.child(e, "MultiHit");
        this.passesCover = OptionalXml.child(e, "PassesCover");

        ArrayList<ConditionEffect> effects = new ArrayList<>();
        for (Element elm : e.getChildren("ConditionEffectType")) {
            effects.add(ConditionEffect.fromObject(
                    elm.getValue(),
                    Float.parseFloat(elm.getAttributeValue("duration"))
            ));
        }

        this.effects = new ConditionEffect[effects.size()];
        this.effects = effects.toArray(this.effects);

        this.texture = OptionalXml.child(e, "Texture") ? new TextureInfo(e) : TextureInfo.DEFAULT;
    }

    public byte getId() {
        return id;
    }

    public int getDamage() {
        return damage;
    }

    public float getSpeed() {
        return speed;
    }

    public int getSize() {
        return size;
    }

    public float getLifetime() {
        return lifetime;
    }

    public int getMinDamage() {
        return minDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public float getMagnitude() {
        return magnitude;
    }

    public float getAmplitude() {
        return amplitude;
    }

    public float getFrequency() {
        return frequency;
    }

    public boolean isWavy() {
        return wavy;
    }

    public boolean isParametric() {
        return parametric;
    }

    public boolean isBoomerang() {
        return boomerang;
    }

    public boolean isArmorPierce() {
        return armorPierce;
    }

    public boolean isMultiHit() {
        return multiHit;
    }

    public boolean isPassesCover() {
        return passesCover;
    }

    public ConditionEffect[] getEffects() {
        return effects;
    }

    public TextureInfo getTexture() {
        return texture;
    }
}
