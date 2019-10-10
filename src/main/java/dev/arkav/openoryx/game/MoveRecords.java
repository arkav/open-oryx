package dev.arkav.openoryx.game;

import dev.arkav.openoryx.game.models.Vector2;
import dev.arkav.openoryx.net.data.MoveRecord;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class MoveRecords {
    public int lastClearTime;
    public ArrayList<MoveRecord> records;

    public MoveRecords() {
        this.lastClearTime = -1;
        this.records = new ArrayList<>();
    }

    public void addRecord(int time, Vector2 pos) {
        this.addRecord(time, pos.x, pos.y);
    }

    public void addRecord(int time, float x, float y) {
        if (this.lastClearTime < 0) return;
        int id = this.getId(time);
        if (id < 1 || id > 10) return;

        if (this.records.size() == 0) {
            MoveRecord record = new MoveRecord(time, x, y);
            this.records.add(record);
        }

        MoveRecord current = this.records.get(this.records.size() - 1);
        int currentId = this.getId(current.time);

        if (id != currentId) {
            MoveRecord record = new MoveRecord(time, x, y);
            this.records.add(record);
        }

        int score = this.getScore(id, time);
        int currentScore = this.getScore(currentId, current.time);

        if (score < currentScore) {
            current.time = time;
            current.x = x;
            current.y = y;
        }
    }

    public void clear(int time) {
        this.records.clear();
        this.lastClearTime = time;
    }

    private int getId(int time) {
        return (time - this.lastClearTime + 50) / 100;
    }

    private int getScore(int id, int time) {
        return Math.round(Math.abs(time - this.lastClearTime - id * 100));
    }
}
