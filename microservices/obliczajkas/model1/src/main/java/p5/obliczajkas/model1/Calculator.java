package p5.obliczajkas.model1;

import com.spatial4j.core.distance.DistanceCalculator;
import com.spatial4j.core.shape.Point;
import org.p5.commons.crodis.Crodis;
import org.p5.commons.crodis.Item;
import p5.obliczajkas.model1.StreamUtils.CustomCollectors;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Calculator {
    private final PointFactory pointFactory;
    private final InterpolatorFactory interpolatorFactory;
    private final DistanceCalculator distanceCalculator;
    private final List<Item> items = new ArrayList<>();
    private final TargetDescription targetDescription;

    public Calculator(DistanceCalculator distanceCalculator,
                      InterpolatorFactory interpolatorFactory,
                      TargetDescription targetDescription) throws GeoprocessingException {
        this.pointFactory = new SimplePointFactory();
        this.distanceCalculator = distanceCalculator;
        this.interpolatorFactory = interpolatorFactory;
        this.targetDescription = targetDescription;
    }

    private double getDistance(Item crodisItem, TargetDescription target) throws GeoprocessingException {
        Point p1 = this.pointFactory.build(crodisItem);
        Point p2 = this.pointFactory.build(target.getLatitude(), target.getLongitude());

        return this.distanceCalculator.distance(p1, p2);
    }

    public void feedCrodis(Crodis crodis) {
        items.addAll(crodis.getItems());
    }

    private Collection<Item> getRelevantCrodises() {
        return items.stream().filter(
                crit -> {
                    try {
                        return getDistance(crit, this.targetDescription) <= crit.getRadius();
                    } catch (GeoprocessingException e) {
                        return false;
                    }
                }
        ).collect(Collectors.toList());
    }

    private Collection<String> extractFeatures(Collection<Item> items) {
        return items.stream().flatMap(
                crit -> crit.getConditions().keySet().stream()
        ).sorted().distinct().collect(Collectors.toList());
    }

    private Comparator<Item> buildDistanceComparator() {
        return Comparator.comparingDouble(o -> {
            try {
                return Calculator.this.getDistance(o, this.targetDescription);
            } catch (GeoprocessingException e) {
                return Double.POSITIVE_INFINITY;
            }
        });
    }

    private Stream<FeatureItem> getNearestItems(Collection<Item> items, String feature) {
        return items.stream().filter(
                crit -> crit.getConditions().containsKey(feature)
        ).sorted(
            this.buildDistanceComparator()
        ).map(crit -> new FeatureItem(crit, feature));
    }

    private Map<String, Collection<FeatureItem>> getNearestItems(Collection<Item> items,
                                                               Collection<String> features,
                                                               long count) {
        return features.stream().map(
                feature ->
                    new AbstractMap.SimpleImmutableEntry<String, Collection<FeatureItem>>(
                            feature,
                            getNearestItems(items, feature).
                            limit(count).collect(Collectors.toList()))


        ).collect(CustomCollectors.toMap());
    }

    private Map<String, Float> calculateValues(Map<String, Collection<FeatureItem>> items,
                                               InterpolatorFactory interpolatorFactory) {
        return items.entrySet().stream().map(
                entry -> new AbstractMap.SimpleImmutableEntry<String, Float>(
                        entry.getKey(),
                        interpolatorFactory.getInterpolator(entry.getKey()).
                                interpolate(entry.getValue(), this.targetDescription))
        ).collect(CustomCollectors.toMap());
    }

    private Crodis constructResult(Map<String, Float> computedValues) {
        Crodis crodis = new Crodis("model1"); // Todo: more accurate data
        Map<String, Float> conditions = new HashMap<>();

        for (Map.Entry<String, Float> computed : computedValues.entrySet()) {
            conditions.put(computed.getKey(), computed.getValue());
        }
        crodis.addItem(this.targetDescription.getLatitude(), this.targetDescription.getLongitude(),
                1f, conditions);

        return crodis;
    }

    public Crodis interpolate() {
        Collection<Item> relevant = getRelevantCrodises();
        Collection<String> features = extractFeatures(relevant);
        Map<String, Collection<FeatureItem>> topSources = getNearestItems(
                relevant, features, 5);
        Map<String, Float> computed = calculateValues(topSources, this.interpolatorFactory);

        return this.constructResult(computed);
    }
}
