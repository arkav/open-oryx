package dev.arkav.openoryx.game.models;

public class ConditionEffect {
    public enum ConditionEffectType {
        NOTHING("Nothing", 0),
        DEAD("Dead", 1),
        QUIET("Quiet", 2),
        WEAK("Weak", 3),
        SLOWED("Slowed", 4),
        SICK("Sick", 5),
        DAZED("Dazed", 6),
        STUNNED("Stunned", 7),
        BLIND("Blind", 8),
        HALLUCINATING("Hallucinating", 9),
        DRUNK("Drunk", 10),
        CONFUSED("Confused", 11),
        STUN_IMMUNE("Stun Immune", 12),
        INVISIBLE("Invisible", 13),
        PARALYZED("Paralyzed", 14),
        SPEEDY("Speedy", 15),
        BLEEDING("Bleeding", 16),
        NOT_USED("Not Used", 17),
        HEALING("Healing", 18),
        DAMAGING("Damaging", 19),
        BERSERK("Berserk", 20),
        PAUSED("Paused", 21),
        STASIS("Stasis", 22),
        STASIS_IMMUNE("Stasis Immune", 23),
        INVINCIBLE("Invincible", 24),
        INVULNERABLE("Invulnerable", 25),
        ARMORED("Armored", 26),
        ARMORBROKEN("Armor Broken", 27),
        HEXED("Hexed", 28),
        NINJA_SPEEDY("Ninja Speedy", 29);

        final String name;
        final int id;

        ConditionEffectType(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public static ConditionEffectType fromName(String name) {
            for (ConditionEffectType c : ConditionEffectType.values()) {
                if (c.name.equals(name)) return c;
            }
            return ConditionEffectType.NOTHING;
        }
    }

    private final ConditionEffectType type;
    private final float duration;

    public ConditionEffect(ConditionEffectType type, float duration) {
        this.type = type;
        this.duration = duration;
    }

    public ConditionEffectType getType() {
        return type;
    }

    public float getDuration() {
        return duration;
    }

    public static ConditionEffect fromObject(String name, float duration) {

        return new ConditionEffect(ConditionEffectType.fromName(name), duration);
    }

    public static boolean has(int condition, ConditionEffectType effect) {
        int effbit = 1 << effect.id - 1;
        return (condition & effbit) == 1;
    }
}
