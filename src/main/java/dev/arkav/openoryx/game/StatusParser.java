package dev.arkav.openoryx.game;

import dev.arkav.openoryx.game.models.ObjectStatus;
import dev.arkav.openoryx.net.data.StatData;
import dev.arkav.openoryx.net.packets.StatType;

public class StatusParser {
    public static ObjectStatus parseObject(StatData[] data, ObjectStatus current) {
        return parse(data, current);
    }

    public static ObjectStatus parseObject(StatData[] data) {
        return parse(data, new ObjectStatus());
    }

    private static ObjectStatus parse(StatData[] data, ObjectStatus current) {
        for (StatData stat : data) {
            switch (stat.statType) {
                case StatType.NAME_STAT:
                    current.name = stat.stringStatValue;
                    break;
                case StatType.LEVEL_STAT:
                    current.level = stat.statValue;
                    break;
                case StatType.EXP_STAT:
                    current.exp = stat.statValue;
                    break;
                case StatType.CURR_FAME_STAT:
                    current.currentFame = stat.statValue;
                    break;
                case StatType.NUM_STARS_STAT:
                    current.numStars = stat.statValue;
                    break;
                case StatType.ACCOUNT_ID_STAT:
                    current.accountId = stat.stringStatValue;
                    break;
                case StatType.FAME_STAT:
                    current.fame = stat.statValue;
                    break;
                case StatType.CREDITS_STAT:
                    current.credits = stat.statValue;
                    break;
                case StatType.MAX_HP_STAT:
                    current.maxHp = stat.statValue;
                    break;
                case StatType.MAX_MP_STAT:
                    current.maxMp = stat.statValue;
                    break;
                case StatType.HP_STAT:
                    current.hp = stat.statValue;
                    break;
                case StatType.MP_STAT:
                    current.mp = stat.statValue;
                    break;
                case StatType.ATTACK_STAT:
                    current.atk = stat.statValue;
                    break;
                case StatType.DEFENSE_STAT:
                    current.def = stat.statValue;
                    break;
                case StatType.SPEED_STAT:
                    current.spd = stat.statValue;
                    break;
                case StatType.DEXTERITY_STAT:
                    current.dex = stat.statValue;
                    break;
                case StatType.VITALITY_STAT:
                    current.vit = stat.statValue;
                    break;
                case StatType.CONDITION_STAT:
                    current.condtion = stat.statValue;
                    break;
                case StatType.WISDOM_STAT:
                    current.wis = stat.statValue;
                    break;
                case StatType.HEALTH_POTION_STACK_STAT:
                    current.hpPots = stat.statValue;
                    break;
                case StatType.MAGIC_POTION_STACK_STAT:
                    current.mpPots = stat.statValue;
                    break;
                case StatType.HASBACKPACK_STAT:
                    current.hasBackpack = stat.statValue == 1;
                    break;
                case StatType.NAME_CHOSEN_STAT:
                    current.nameChosen = stat.statValue != 0;
                    break;
                case StatType.GUILD_NAME_STAT:
                    current.guildName = stat.stringStatValue;
                    break;
                case StatType.GUILD_RANK_STAT:
                    current.guildRank = stat.stringStatValue;
                    break;
                case StatType.INVENTORY_0_STAT:
                    current.inventory[0] = stat.statValue;
                    break;
                case StatType.INVENTORY_1_STAT:
                    current.inventory[1] = stat.statValue;
                    break;
                case StatType.INVENTORY_2_STAT:
                    current.inventory[2] = stat.statValue;
                    break;
                case StatType.INVENTORY_3_STAT:
                    current.inventory[3] = stat.statValue;
                    break;
                case StatType.INVENTORY_4_STAT:
                    current.inventory[4] = stat.statValue;
                    break;
                case StatType.INVENTORY_5_STAT:
                    current.inventory[5] = stat.statValue;
                    break;
                case StatType.INVENTORY_6_STAT:
                    current.inventory[6] = stat.statValue;
                    break;
                case StatType.INVENTORY_7_STAT:
                    current.inventory[7] = stat.statValue;
                    break;
                case StatType.INVENTORY_8_STAT:
                    current.inventory[8] = stat.statValue;
                    break;
                case StatType.INVENTORY_9_STAT:
                    current.inventory[9] = stat.statValue;
                    break;
                case StatType.INVENTORY_10_STAT:
                    current.inventory[10] = stat.statValue;
                    break;
                case StatType.INVENTORY_11_STAT:
                    current.inventory[11] = stat.statValue;
                    break;
            }
        }
        return current;
    }
}
