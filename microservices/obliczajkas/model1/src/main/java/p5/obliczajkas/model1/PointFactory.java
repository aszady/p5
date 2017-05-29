package p5.obliczajkas.model1;

import com.spatial4j.core.shape.Point;
import org.p5.commons.crodis.Item;

public interface PointFactory {
    Point build(float latitude, float longitude);
    Point build(Item crodis) throws GeoprocessingException;
}
