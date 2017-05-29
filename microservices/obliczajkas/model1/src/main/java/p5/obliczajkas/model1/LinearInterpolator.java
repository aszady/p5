package p5.obliczajkas.model1;

import com.spatial4j.core.distance.DistanceCalculator;
import p5.obliczajkas.model1.StreamUtils.Cartesian;
import p5.obliczajkas.model1.StreamUtils.CustomCollectors;

import java.util.Collection;

public class LinearInterpolator implements Interpolator {

    DistanceCalculator dc;
    public LinearInterpolator(DistanceCalculator dc) {
        this.dc = dc;
    }

    private double interpolateTwo(FeatureItem item1, FeatureItem item2, TargetDescription targetDescription) {
        float dlat = item2.getLatitude() - item1.getLatitude();
        float dlon = item2.getLongitude() - item1.getLongitude();
        double angle = Math.atan2(dlat, dlon);

        double p1 = Math.cos(angle) * item1.getLongitude() + Math.sin(angle) * item1.getLatitude();
        //double q1 = -Math.sin(angle) * item1.getLongitude() + Math.cos(angle) * item1.getLatitude();

        double p2 = Math.cos(angle) * item2.getLongitude() + Math.sin(angle) * item2.getLatitude();
        //double q2 = -Math.sin(angle) * item2.getLongitude() + Math.cos(angle) * item2.getLatitude();

        double pt = Math.cos(angle) * targetDescription.getLongitude() + Math.sin(angle) * targetDescription.getLatitude();
        //double qt = -Math.sin(angle) * targetDescription.getLongitude() + Math.cos(angle) * targetDescription.getLatitude();

        double d2p = p2 - p1;
        //double d2q = q2 - q1;

        double dtp = pt - p1;
        //double dtq = qt - q1;

        double dp = dtp/d2p;
        //double dq = dtq/d2q;

        double coeff = dp;
        /*System.out.printf("%f,%f - %f,%f angle: %f (t = %f,%f) -> i1(p=%f, q=%f)," +
                        "t(p=%f, q=%f), d2(%f, %f), dt(%f, %f), d(%f, %f)\n",
                item1.getLatitude(), item1.getLongitude(),
                item2.getLatitude(), item2.getLongitude(),
                angle,
                targetDescription.getLatitude(), targetDescription.getLongitude(),
                p1, q1,
                pt, qt,
                d2p, d2q,
                dtp, dtq,
                dp, dq);

        System.out.printf("coeff = %f, i2=%f, i1=%f\n", coeff, item2.getCondition(), item1.getCondition());*/


        return coeff * item2.getCondition() + (1-coeff) * item1.getCondition();

    }

    @Override
    public Float interpolate(Collection<FeatureItem> items, TargetDescription targetDescription) {
        return (float)(double) // Love java!
                Cartesian.orderedPairs(items.stream()).map(
                pair -> interpolateTwo(pair.first, pair.second, targetDescription)
        ).collect(CustomCollectors.average());
    }
}
