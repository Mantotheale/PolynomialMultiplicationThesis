package thesis;

import org.jetbrains.annotations.NotNull;
import thesis.circuit.Circuit;
import thesis.circuit.multiplier.MultiplicationStrategy;
import thesis.poly.Polynomial;
import thesis.signal.Bus;
import thesis.signal.ConstantSignal;
import thesis.signal.Signal;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main() {
        Random r = new Random();
        List<Boolean> a = padList(IntToBinaryList.convert(new BigInteger(200, r)), 200);
        List<Boolean> b = padList(IntToBinaryList.convert(new BigInteger(200, r)), 200);

        System.out.println("Grado 100");
        System.out.println();

        for (MultiplicationStrategy strategy: MultiplicationStrategy.values()) {
            System.out.println("--- " + strategy + " ---");
            multiply(strategy, a, b);
            System.out.println("--- ---");
        }
    }

    private static @NotNull List<@NotNull Boolean> padList(@NotNull List<@NotNull Boolean> l, int len) {
        for (int i = l.size(); i < len; i++) {
            l.add(Boolean.FALSE);
        }

        return l;
    }

    private static void multiply(@NotNull MultiplicationStrategy strategy, @NotNull List<@NotNull Boolean> a, @NotNull List<@NotNull Boolean> b) {
        long startCreation = (System.currentTimeMillis());
        Circuit multiplier = strategy.getCircuit(a.size());
        long endCreation = (System.currentTimeMillis());

        fixInput(multiplier, a, b);

        long start = System.currentTimeMillis();

        Polynomial res = new Polynomial(evaluateCircuit(multiplier));
        Polynomial exp = expectedResult(a, b);
        if (!res.equals(exp)) {
            throw new RuntimeException("ERRORE circuito");
        }

        long middle = System.currentTimeMillis();

        res = new Polynomial(evaluateCircuit(multiplier));
        if (!res.equals(exp)) {
            throw new RuntimeException("ERRORE circuito");
        }

        long end = System.currentTimeMillis();

        System.out.println("Gates: " + multiplier.gateCount());
        System.out.println("Tempo creazione: " + (endCreation - startCreation));
        System.out.println("Tempo primo calcolo: " + (middle - start));
        System.out.println("Tempo secondo calcolo: " + (end - middle));
        System.out.println("Tempo totale: " + (end - start));
    }

    private static void fixInput(@NotNull Circuit multiplier, @NotNull List<Boolean> a, @NotNull List<Boolean> b) {
        Bus busA = new Bus(boolToSignal(a));
        Bus busB = new Bus(boolToSignal(b));
        multiplier.setInput(busA.concat(busB));
    }

    private static @NotNull List<@NotNull Signal> boolToSignal(@NotNull List<@NotNull Boolean> bools) {
        return bools.stream().map(x -> x ? ConstantSignal.TRUE : ConstantSignal.FALSE).toList();
    }

    private static @NotNull List<@NotNull Boolean> evaluateCircuit(@NotNull Circuit multiplier) {
        return multiplier.outputBus().stream().map(Signal::evaluate).toList();
    }

    private static @NotNull Polynomial expectedResult(@NotNull List<Boolean> a, @NotNull List<Boolean> b) {
        return new Polynomial(a).mul(new Polynomial(b));
    }
}