package p5.obliczajkas.model1;

import org.p5.commons.crodis.Item;

public class FeatureItem implements LatLonItem {
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

    public float getLongitude() {
        return longitude;
    }

    public float getRadius() {
        return radius;
    }

    public float getCondition() {
        return condition;
    }
}
