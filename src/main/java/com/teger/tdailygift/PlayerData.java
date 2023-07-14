package com.teger.tdailygift;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

class PlayerData {
    private final UUID uniqueId;
    private Date lastReceived;
    private int count;

    public PlayerData(PlayerDataBuilder builder) {
        this.uniqueId = builder.uniqueId;
        this.lastReceived = builder.lastReceived;
        this.count = builder.count;
    }

    public void setLastReceived(Date lastReceived) {
        this.lastReceived = lastReceived;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public Date getLastReceived() {
        return lastReceived;
    }

    public int getCount() {
        return count;
    }

    public boolean todayCanReceive(){
        Date today = new Date();
        long betweenTime = today.getTime() - lastReceived.getTime();
        long days = (betweenTime / (24 * 60 * 60 * 1000L)) % 365;
        return days > 0;
    }

    public static PlayerDataBuilder builder(){
        return new PlayerDataBuilder();
    }

    static class PlayerDataBuilder{
        private UUID uniqueId;
        private Date lastReceived = new Date(1970, Calendar.JANUARY, 1);
        private int count;

        public PlayerDataBuilder uniqueId(UUID uniqueId){
            this.uniqueId = uniqueId;
            return this;
        }

        public PlayerDataBuilder lastReceived(Date lastReceived){
            this.lastReceived = lastReceived;
            return this;
        }

        public PlayerDataBuilder count(int count){
            this.count = count;
            return this;
        }

        public PlayerData build(){
            return new PlayerData(this);
        }
    }
}
