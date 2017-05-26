package org.p5.commons.crodis;

/**
 * Created by krzysiek on 26.05.17.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Crodis implements Serializable {

    private String source;
    private List<Item> items;

    public Crodis(){
        this.items = new ArrayList<>();
    }

    public Crodis(String source) {
        this.source = source;
        this.items = new ArrayList<>();
    }

    public void addItem(float lat, float lon, float radius, Map<String, Float> conditions) {
        Item item = new Item();
        item.setLatitude(lat);
        item.setLongitude(lon);
        item.setRadius(radius);
        Map<String, Float> clonedConditions = new HashMap<String, Float>();
        clonedConditions.putAll(conditions);
        item.setConditions(clonedConditions);
        this.items.add(item);
    }

    public String getSource() {
        return this.source;
    }
    public List<Item> getItems() { return this.items; }
}