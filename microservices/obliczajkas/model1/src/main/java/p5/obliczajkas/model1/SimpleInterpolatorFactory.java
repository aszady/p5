package p5.obliczajkas.model1;

public class SimpleInterpolatorFactory implements InterpolatorFactory {
    private Interpolator interpolator;

    public SimpleInterpolatorFactory(Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    @Override
    public Interpolator getInterpolator(String feature) {
        return this.interpolator;
    }
}
