package p5.obliczajkas.model1;

import com.spatial4j.core.context.SpatialContext;
import com.spatial4j.core.shape.Point;
import com.spatial4j.core.shape.Rectangle;
import com.spatial4j.core.shape.Shape;
import com.spatial4j.core.shape.SpatialRelation;
import com.spatial4j.core.shape.impl.PointImpl;
import org.p5.commons.crodis.Item;

public class SimplePointFactory implements PointFactory {
    @Override
    public Point build(float latitude, float longitude) {
        return new PointImpl(latitude, longitude, SpatialContext.GEO);
    }

    @Override
    public Point build(Item crodisItem) throws GeoprocessingException {
        return build(crodisItem.getLatitude(), crodisItem.getLongitude());
    }
}
