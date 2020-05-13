package dev.arkav.openoryx.game.models;

public class GameState {

    public String buildVersion;

    public int characterId;

    public int gameId;

    public byte[] key;

    public int keyTime;

    public String connectionGuid;

    public GameState(String buildVersion) {
        this.buildVersion = buildVersion;
        this.characterId = 0;
        this.gameId = -2;
        this.key = new byte[0];
        this.keyTime = -1;
        this.connectionGuid = "";
    }
}
