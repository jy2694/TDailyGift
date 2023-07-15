package com.teger.tdailygift.configuration;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class PlayerData {
    private final UUID uniqueId;
    private Date lastReceived;
    private int count;
    private String nowOpen;

    public PlayerData(PlayerDataBuilder builder) {
        this.uniqueId = builder.getUniqueId();
        this.lastReceived = builder.getLastReceived();
        this.count = builder.getCount();
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
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        Calendar last = Calendar.getInstance();
        last.setTime(lastReceived);
        if(now.get(Calendar.YEAR) != last.get(Calendar.YEAR)) count = 0;
        else if(now.get(Calendar.MONTH) != last.get(Calendar.MONTH)) count = 0;
        return count;
    }

    public String getNowOpen() {
        return nowOpen;
    }

    public void setNowOpen(String nowOpen) {
        this.nowOpen = nowOpen;
    }

    public boolean todayCanReceive(){
        Date today = new Date();
        long betweenTime = today.getTime() - lastReceived.getTime();
        long days = (betweenTime / (24 * 60 * 60 * 1000L)) % 365;
        System.out.println("day between : "+ days);
        return days > 0;
    }

    public static PlayerDataBuilder builder(){
        return new PlayerDataBuilder();
    }

    public static PlayerDataBuilder emptyBuilder(UUID uniqueId){
        return new PlayerDataBuilder().uniqueId(uniqueId).count(0);
    }
}
