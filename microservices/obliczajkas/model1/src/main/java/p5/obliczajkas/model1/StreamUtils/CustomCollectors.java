package p5.obliczajkas.model1.StreamUtils;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CustomCollectors {
    public static <K, V>
    Collector<
        AbstractMap.SimpleImmutableEntry<K, V>,
        ?,
        Map<K, V>
    > toMap() {
        return Collectors.toMap(AbstractMap.SimpleImmutableEntry::getKey,
                AbstractMap.SimpleImmutableEntry::getValue);
    }

    public static <V>
    Collector<V, ?, Double>
    average() {
        return Collectors.averagingDouble(x -> Double.parseDouble(x.toString())  // Java hate!!!1
        );
    }
}
