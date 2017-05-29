package p5.obliczajkas.model1;

import org.p5.commons.crodis.Item;

import java.util.Collection;

public interface Interpolator {
    Float interpolate(Collection<FeatureItem> items, TargetDescription targetDescription);
}
