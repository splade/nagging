package util;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class OptionalDemo {

    public static void main(String[] args) {


        Optional<Double> d = Optional.empty();
        d = Optional.of(3.14);
        d = Optional.ofNullable(null);

        d.isPresent();
        if (d.isPresent())
            d.get(); // 如果没有值,会抛出 NoSuchElementException

        d.orElse(0.0);
        d.orElseGet(() -> 3.14);
        try {
            d.orElseThrow(() -> new NoSuchElementException("Exception"));
        } catch (NoSuchElementException e) {
            System.out.println(e);
        }


        d.ifPresent((value) -> System.out.println(value));
        d.map(new Function<Double, String>() {
            @Override
            public String apply(Double value) {
                return "{value: " + value + "}";
            }
        });

        d.filter(new Predicate<Double>() {
            @Override
            public boolean test(Double value) {
                if (value > 0.5)
                    return false;
                else
                    return true;
            }
        });

        d.flatMap(new Function<Double, Optional<String>>() {
            @Override
            public Optional<String> apply(Double value) {
                return Optional.ofNullable(String.valueOf(value));
            }
        });

        System.out.println(d);


    }
}
