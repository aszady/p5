package database; /**
 * Created by Mefju on 25.05.2017.
 */
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "crodis")
public class CrodisItem {

    @Id
    public String id;

    String source;
    float[] location = new float[2];
    float radius;
    Map<String, Float> conditions;

    CrodisItem(){}

    CrodisItem(String source, Crodis.Item item){
        this.source = source;
        this.location[0] = item.getLatitude();
        this.location[1] = item.getLongitude();
        this.radius = item.getRadius();
        this.conditions = item.getConditions();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public float[] getLocation() {
        return location;
    }

    public void setLocation(float[] location) {
        this.location = location;
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
