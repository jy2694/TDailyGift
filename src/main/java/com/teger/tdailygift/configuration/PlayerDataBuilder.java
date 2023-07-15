package com.teger.tdailygift.configuration;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class PlayerDataBuilder{
    private UUID uniqueId;
    private Date lastReceived = new GregorianCalendar(1970, Calendar.JANUARY, 1, 0, 0, 0).getTime();
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

    public UUID getUniqueId() {
        return uniqueId;
    }

    public Date getLastReceived() {
        return lastReceived;
    }

    public int getCount() {
        return count;
    }
}
