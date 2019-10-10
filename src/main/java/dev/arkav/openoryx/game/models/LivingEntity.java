package dev.arkav.openoryx.game.models;

import dev.arkav.openoryx.game.StatusParser;
import dev.arkav.openoryx.game.info.ObjectInfo;
import dev.arkav.openoryx.net.data.ObjectStatusData;
import dev.arkav.openoryx.net.data.StatData;

public class LivingEntity extends Entity implements IUpdating {
    private Vector2 tickPos;
    private Vector2 lastTickPos;
    private Vector2 moveVector;
    private int lastTickId;
    private ObjectStatus stats;

    public LivingEntity(ObjectStatusData data, ObjectInfo go) {
        super(data, go);
        this.tickPos = data.pos.clone();
        this.lastTickPos = data.pos.clone();
        this.moveVector = new Vector2(0f, 0f);
        this.stats = StatusParser.parseObject(data.stats);
    }

    public LivingEntity(int objectId, Vector2 pos, ObjectInfo info, StatData[] data) {
        super(objectId, pos, info);
        this.tickPos = pos.clone();
        this.lastTickPos = pos.clone();
        this.moveVector = new Vector2(0f, 0f);
        this.stats = StatusParser.parseObject(data);
    }

    public void tick(int delta, int tickId, Vector2 pos, StatData[] data) {
        //this.pos = pos.clone();
        this.tickPos.clone(this.lastTickPos);
        pos.clone(this.tickPos);
        this.lastTickId = tickId;

        this.moveVector = this.tickPos.clone()
                .subtract(this.lastTickPos)
                .divide(delta);
        StatusParser.parseObject(data, this.stats);
    }

    public void update(int delta, int lastTick) {
        if (!(this.moveVector.x == 0 && this.moveVector.y == 0)) {
            if (this.lastTickId < lastTick) {
                this.moveVector.x = 0;
                this.moveVector.y = 0;
                this.tickPos.clone(this.pos);
            } else {
                this.pos.x += this.moveVector.x * delta;
                this.pos.y += this.moveVector.y * delta;
            }
        }
    }

    public int damage(int dmg) {
        int min = dmg * 3 / 20;
        return Math.max(min, dmg - this.stats.def);
    }

    public ObjectStatus getStats() {
        return stats;
    }
}
