package p5.obliczajkas.model1;

import com.spatial4j.core.distance.DistanceCalculator;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import p5.obliczajkas.model1.MathUtils.BaseChange;
import p5.obliczajkas.model1.StreamUtils.Cartesian;
import p5.obliczajkas.model1.StreamUtils.CustomCollectors;

import java.util.Collection;

public class LinearInterpolator implements Interpolator {

    DistanceCalculator dc;
    public LinearInterpolator(DistanceCalculator dc) {
        this.dc = dc;
    }

    private double interpolateTwo(FeatureItem item1, FeatureItem item2, TargetDescription targetDescription) {
        RealVector d2 = buildRealVectorDifference(item2, item1);
        RealVector dt = buildRealVectorDifference(targetDescription, item1);

        RealMatrix base = MatrixUtils.createRealMatrix(2, 2);
        base.setColumnVector(0, d2);
        base.setColumnVector(1, BaseChange.rotate90Deg(d2));

        BaseChange changer = new BaseChange(base);

        RealVector transformed = changer.transform(dt);
        System.out.println(transformed);

        double coeff = transformed.getEntry(0);

        return coeff * item2.getCondition() + (1-coeff) * item1.getCondition();

    }

    private static RealVector buildRealVector(LatLonItem item) {
        return MatrixUtils.createRealVector(new double[]{item.getLongitude(), item.getLatitude()});
    }

    private static RealVector buildRealVectorDifference(LatLonItem item, LatLonItem zero) {
        return buildRealVector(item).subtract(buildRealVector(zero));
    }

    @Override
    public Float interpolate(Collection<FeatureItem> items, TargetDescription targetDescription) {
        return (float)(double) // Love java!
                Cartesian.orderedPairs(items.stream()).map(
                pair -> interpolateTwo(pair.first, pair.second, targetDescription)
        ).collect(CustomCollectors.average());
    }
}
