package p5.obliczajkas.model1;

import com.spatial4j.core.distance.DistanceCalculator;
import com.spatial4j.core.distance.GeodesicSphereDistCalc;

import java.util.Objects;

public class DistanceCalculatorFactory {
    private String DEFAULT_MODEL_NAME = "haversine";

    public DistanceCalculator buildModel(String distanceModelName) throws GeoprocessingException {
        if (Objects.equals(distanceModelName, "haversine")) {
            return new GeodesicSphereDistCalc.Haversine();
        } else {
            throw new GeoprocessingException("Unknown distance calculation model name");
        }
    }

    public DistanceCalculator buildDefault() throws  GeoprocessingException {
        return buildModel(DEFAULT_MODEL_NAME);
    }
}
