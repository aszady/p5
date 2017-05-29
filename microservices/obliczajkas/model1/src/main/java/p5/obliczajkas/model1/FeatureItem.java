package p5.obliczajkas.model1;

import org.p5.commons.crodis.Item;

public class FeatureItem {
    private float latitude;
    private float longitude;
    private float radius;
    private float condition;

    public FeatureItem(Item item, String featureName) {
        this.latitude = item.getLatitude();
        this.longitude = item.getLongitude();
        this.radius = item.getRadius();
        this.condition = item.getConditions().get(featureName);
    }

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

    public float getCondition() {
        return condition;
    }

    public void setCondition(float condition) {
        this.condition = condition;
    }
}
