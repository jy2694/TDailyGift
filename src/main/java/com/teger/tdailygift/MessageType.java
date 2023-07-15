package com.teger.tdailygift;

public enum MessageType {
    ALREADY_GET_TODAY("already-get-today"),
    ALREADY_GET_MONTH("already-get-month"),
    INVENTORY_FULL("inventory-full"),
    GET_DAILY_GIFT("get-daily-gift"),
    DATE_DISPLAY_ITEM("date-display-item"),
    DATE_DISPLAY_ITEM_SEPARATOR("date-display-item-separator"),
    RECEIVED_DISPLAY_ITEM("received-display-item"),
    NO_DAILY_GIFT("no-daily-gift");

    private final String key;

    MessageType(String key){
        this.key = key;
    }

    public String getKey(){
        return key;
    }
}
