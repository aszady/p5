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

    // Temperature delta [Celcuis/hour]
    public static final String CONDITION_TEMPERATURE_DELTA = "temperature_delta";

    // Wind [m/s]
    public static final String CONDITION_WIND = "wind";

    // Air quality: Pollution level based on CAQI value.
    // Possible values: [1 to 6]. 1 - best air, 6 - worst.
    public static final String CONDITION_AIR_CAQL = "air_caql";

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