package com.example.appxtube.event;

import com.example.appxtube.entity.Item;

public class EventVideoOptions {

    public Item item;

    public String action;

    public EventVideoOptions(Item item,String action) {
        this.item = item;
        this.action = action;
    }
}
