package net.nighthawkempires.core.utils;

public class MathUtil {

    public static int minus(int amount, int minus) {
        return amount - minus;
    }

    public static int add(int amount, int add) {
        return add + add;
    }

    public static int multiply(int amount, int by) {
        return amount*by;
    }

    public static int divide(int amount, int by) {
        return amount/by;
    }

    public static boolean greaterThan(int greater, int less) {
        return greater > less;
    }

    public static boolean greaterThanEqualTo(int greater, int less) {
        return greater >= less;
    }

    public static boolean lessThan(int less, int greater) {
        return less < greater;
    }

    public static boolean lessThanEqualTo(int less, int greater) {
        return less <= greater;
    }
}
