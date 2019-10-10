package dev.arkav.openoryx.net.packets;

import dev.arkav.openoryx.net.data.Packet;
import dev.arkav.openoryx.net.packets.s2c.*;
import dev.arkav.openoryx.net.packets.c2s.*;

import java.util.HashMap;
import java.util.Map;

public enum PacketType {
    FAILURE(FailurePacket.class),
    ACCEPTARENADEATH(null),
    LOAD(LoadPacket.class),
    QUESTREDEEMRESPONSE(QuestRedeemResponsePacket.class),
    GOTOACK(GotoAckPacket.class),
    PETCHANGEFORMMSG(null),
    TRADEDONE(TradeDonePacket.class),
    HELLO(HelloPacket.class),
    MOVE(MovePacket.class),
    SETCONDITION(SetConditionPacket.class),
    ACTIVEPETUPDATE(null),
    PONG(PongPacket.class),
    CANCELTRADE(CancelTradePacket.class),
    OTHERHIT(OtherHitPacket.class),
    IMMINENTARENAWAVE(null),
    GLOBALNOTIFICATION(GlobalNotificationPacket.class),
    TRADECHANGED(TradeChangedPacket.class),
    PETYARDUPDATE(null),
    CREATESUCCESS(CreateSuccessPacket.class),
    QUESTFETCHASK(null),
    TELEPORT(TeleportPacket.class),
    EVOLVEPET(null),
    UPDATEACK(UpdateAckPacket.class),
    UPDATE(UpdatePacket.class),
    INVITEDTOGUILD(InvitedToGuildPacket.class),
    USEITEM(UseItemPacket.class),
    TRADESTART(TradeStartPacket.class),
    CLAIMLOGINREWARDMSG(ClaimDailyRewardMessagePacket.class),
    SHOWEFFECT(ShowEffectPacket.class),
    DEATH(DeathPacket.class),
    RESKIN(ReskinPacket.class),
    PLAYERTEXT(PlayerTextPacket.class),
    DELETEPET(null),
    QUESTREDEEM(QuestRedeemResponsePacket.class),
    USEPORTAL(UsePortalPacket.class),
    KEYINFORESPONSE(KeyInfoResponsePacket.class),
    ACCEPTTRADE(AcceptTradePacket.class),
    RECONNECT(ReconnectPacket.class),
    BUYRESULT(BuyResultPacket.class),
    REQUESTTRADE(RequestTradePacket.class),
    PETUPGRADEREQUEST(null),
    SHOOTACK(ShootAckPacket.class),
    PLAYERHIT(PlayerHitPacket.class),
    ACTIVEPETUPDATEREQUEST(null),
    PLAYERSHOOT(PlayerShootPacket.class),
    PLAYSOUND(null),
    ESCAPE(EscapePacket.class),
    GUILDRESULT(GuildResultPacket.class),
    NOTIFICATION(NotificationPacket.class),
    VERIFYEMAIL(VerifyEmailPacket.class),
    GOTO(GotoPacket.class),
    MAPINFO(MapInfoPacket.class),
    INVDROP(InvDropPacket.class),
    ARENADEATH(null),
    ALLYSHOOT(AllyShootPacket.class),
    SERVERPLAYERSHOOT(ServerPlayerShootPacket.class),
    PASSWORDPROMPT(PasswordPromptPacket.class),
    FILE(null),
    KEYINFOREQUEST(KeyInfoRequestPacket.class),
    QUESTROOMMSG(QuestRedeemResponsePacket.class),
    CHECKCREDITS(CheckCreditsPacket.class),
    ENEMYHIT(EnemyHitPacket.class),
    CREATE(CreatePacket.class),
    GUILDINVITE(GuildInvitePacket.class),
    ENTERARENA(null),
    PING(PingPacket.class),
    EDITACCOUNTLIST(EditAccountListPacket.class),
    AOE(AoePacket.class),
    ACCOUNTLIST(AccountListPacket.class),
    BUY(BuyPacket.class),
    INVSWAP(InvSwapPacket.class),
    AOEACK(AoeAckPacket.class),
    PIC(null),
    INVRESULT(InvResultPacket.class),
    LOGINREWARDMSG(ClaimDailyRewardMessagePacket.class),
    CHANGETRADE(ChangeTradePacket.class),
    TEXT(TextPacket.class),
    QUESTFETCHRESPONSE(null),
    TRADEREQUESTED(TradeRequestedPacket.class),
    HATCHPET(null),
    GROUNDDAMAGE(GroundDamagePacket.class),
    ENEMYSHOOT(EnemyShootPacket.class),
    CHOOSENAME(ChooseNamePacket.class),
    CLIENTSTAT(ClientStatPacket.class),
    RESKINUNLOCK(ReskinUnlockPacket.class),
    NAMERESULT(NameResultPacket.class),
    JOINGUILD(JoinGuildPacket.class),
    NEWTICK(NewTickPacket.class),
    SQUAREHIT(SquareHitPacket.class),
    CHANGEGUILDRANK(ChangeGuildRankPacket.class),
    NEWABILITY(NewAbilityMessagePacket.class),
    CREATEGUILD(CreateGuildPacket.class),
    PETCHANGESKINMSG(null),
    DAMAGE(DamagePacket.class),
    QUESTOBJID(QuestObjectIdPacket.class),
    TRADEACCEPTED(TradeAcceptedPacket.class),
    GUILDREMOVE(GuildRemovePacket.class),
    CHATHELLOMSG(ChatHelloPacket.class),
    CHATTOKENMSG(ChatTokenPacket.class),
    CHATLOGOUTMSG(null),
    REALMHEROLEFTMSG(RealmHeroLeftMsgPacket.class),
    UNKNOWN(null);

    private final Class<? extends Packet> impl;

    PacketType(final Class<? extends Packet> impl) {
        this.impl = impl;
    }

    public static PacketType parseFromString(String s) {
        for (PacketType t : PacketType.values()) {
            if (t.name().equals(s)) {
                return t;
            }
        }
        return PacketType.UNKNOWN;
    }

    public PacketType fromPacketClass(Class<? extends Packet> c) {
        for (PacketType t : PacketType.values()) {
            if (t.impl == c) {
                return t;
            }
        }
        return PacketType.UNKNOWN;
    }

    public Packet newInstance() throws InstantiationException, IllegalAccessException {
        return impl.newInstance();
    }

    private static Map<Class<? extends Packet>, PacketType> packets;
    static {
        packets = new HashMap<>();
        for (PacketType pt : PacketType.values()) {
            packets.put(pt.impl, pt);
        }
    }
    public static PacketType typeOf(Packet p) {
        return packets.get(p.getClass());
    }
}
