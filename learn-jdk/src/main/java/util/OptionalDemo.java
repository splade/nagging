package util;

import java.util.Optional;
import java.util.function.Function;

public class OptionalDemo {

    public static void main(String[] args) {


        Optional<Double> d = Optional.empty();
        d = Optional.of(3.14);

        System.out.println(d);


    }
}
