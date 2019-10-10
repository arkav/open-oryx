package dev.arkav.openoryx.game.models;

public class Account {
    private String guid;
    private String password;
    public Account(String guid, String password) {
        this.guid = guid;
        this.password = password;
    }

    public String getGuid() {
        return guid;
    }

    public String getPassword() {
        return password;
    }
}
