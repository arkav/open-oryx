package dev.arkav.openoryx.game.models;

import dev.arkav.openoryx.game.info.ObjectInfo;
import dev.arkav.openoryx.net.data.ObjectStatusData;

@SuppressWarnings("ALL")
public class Entity {
    protected final int objectId;
    protected Vector2 pos;
    protected final ObjectInfo info;

    public Entity(ObjectStatusData data, ObjectInfo info) {
        this.objectId = data.objectId;
        this.pos = data.pos.clone();
        this.info = info;
    }

    public Entity(int objectId, Vector2 pos, ObjectInfo info) {
        this.objectId = objectId;
        this.pos = pos;
        this.info = info;
    }

    public int getObjectId() {
        return this.objectId;
    }

    public Vector2 getPos() {
        return this.pos;
    }

    public ObjectInfo getInfo() {
        return this.info;
    }

    public void setPos(float x, float y) {
        this.pos.x = x;
        this.pos.y = y;
    }
}
