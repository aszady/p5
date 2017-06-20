package p5.obliczajkas.model1.StreamUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Cartesian {

    // Java hates pair, but I hate java.
    public static class Pair<T> {
        public T first, second;
        public Pair(T first, T second) {
            this.first = first;
            this.second = second;
        }
    }

    public static <T> Stream<Pair<T>> orderedPairs(Stream<T> input) {
        final List<T> items = new ArrayList<>();
        return input.sequential().flatMap(second -> {
            Stream<Pair<T>> stream = items.stream().limit(items.size()).map(first -> new Pair<T>(first, second));
            items.add(second);
            return stream;
        });
    }

    public static void main(String[] args) {
        orderedPairs(Stream.of(1, 2, 3, 4, 5)).forEachOrdered(p -> System.out.printf("%d, %d\n", p.first, p.second));
    }
}
