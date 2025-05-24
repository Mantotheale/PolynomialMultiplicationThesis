package thesis;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class IntToBinaryList {
    private IntToBinaryList() { }

    public static @NotNull List<@NotNull Boolean> convert(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Can't convert a negative number to binary");
        }

        List<Boolean> l = new ArrayList<>();
        while (n > 0) {
            l.add(n % 2 == 1);
            n /= 2;
        }

        if (l.isEmpty()) {
            l.add(false);
        }

        return l;
    }

    public static @NotNull List<@NotNull Boolean> convert(BigInteger n) {
        if (n.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("Can't convert a negative number to binary");
        }

        List<Boolean> l = new ArrayList<>();
        while (n.compareTo(BigInteger.ZERO) > 0) {
            l.add(n.mod(BigInteger.TWO).equals(BigInteger.ONE));
            n = n.divide(BigInteger.TWO);
        }

        if (l.isEmpty()) {
            l.add(false);
        }

        return l;
    }
}
