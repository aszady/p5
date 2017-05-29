package p5.obliczajkas.model1;

import com.spatial4j.core.distance.DistanceCalculator;

public class CalculatorFactory {
    private final DistanceCalculatorFactory dcf;
    private Interpolator defaultInterpolator;

    public CalculatorFactory(DistanceCalculatorFactory dcf, Interpolator defaultInterpolator) {
        this.dcf = dcf;
        this.defaultInterpolator = defaultInterpolator;
    }

    public Calculator getCalculator(String modelName, TargetDescription targetDescription) throws GeoprocessingException {
        DistanceCalculator dc = dcf.buildModel(modelName);
        InterpolatorFactory intf = new SimpleInterpolatorFactory(defaultInterpolator);
        return new Calculator(dc, intf, targetDescription);
    }
}
