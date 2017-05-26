package org.p5.commons.crodis;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by krzysiek on 26.05.17.
 */
public class Item implements Serializable {
    private float latitude;
    private float longitude;
    private float radius;
    private Map<String, Float> conditions;

    // Temperature [Celsius]
    public static final String CONDITION_TEMPERATURE = "temperature";

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public Map<String, Float> getConditions() {
        return conditions;
    }

    public void setConditions(Map<String, Float> conditions) {
        this.conditions = conditions;
    }
}