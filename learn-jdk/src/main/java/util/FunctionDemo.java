package util;

import java.util.function.Function;

public class FunctionDemo {

    public static void main(String[] args) {
        /**
         * 函数接口:
         *  Output apply(Input)
         *  Function andThen(Function)
         *  Function compose(Function before)
         *  Function identity()  返回一个特殊的函数
         */
//        Function<Input, Output>

        // 1. lambda
        // 2. function reference

        Function<Double, String> f = (input) -> Double.toString(input);

        String result = f.apply(10.0);
        System.out.println(result);

//        f = Double::toHexString;
//        System.out.println(f.apply(10.2));

        Function<String, Double> before = (input) -> {
            Double value = Double.parseDouble(input);
            return value * 10;
        };

        Function<String, String> after = (input) -> ":::" + input + ":::";

        result = f.compose(before).andThen(after).apply("3.14");
        System.out.println(result);

        result = f.compose(before).compose(f).andThen(after).apply(3.1415);
        System.out.println(result);
    }
}
