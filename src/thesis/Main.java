package thesis;

import org.jetbrains.annotations.NotNull;
import thesis.circuit.Circuit;
import thesis.circuit.multiplier.MultiplicationStrategy;
import thesis.poly.Polynomial;
import thesis.signal.Bus;
import thesis.signal.ConstantSignal;
import thesis.signal.Signal;

import java.util.List;

public class Main {
    public static void main() {
        List<Boolean> aList = List.of(false, true, false, true, false, true, true, true);
        List<Boolean> bList = List.of(true, false, false, false, false, false, true, true);
        List<Boolean> expList = List.of(false, true, false, true, false, true, true, false, true, true, true, true, false, false, true);

        Polynomial a = new Polynomial(aList);
        Polynomial b = new Polynomial(bList);
        Polynomial res = a.mul(b);
        Polynomial exp = new Polynomial(expList);

        System.out.println("Uguali? " + res.equals(exp));


        /*for (int i = 1; i < 32; i++) {
            System.out.println("--- " + i + " bits ---");
            Circuit _ = new TwoLevelSevenWay(i);
            System.out.println("--- ---");
        }*/
        /*
        List<Boolean> a = padList(IntToBinaryList.convert(new BigInteger(200, r)), 200);
        List<Boolean> b = padList(IntToBinaryList.convert(new BigInteger(200, r)), 200);

        System.out.println("Grado 100");
        System.out.println();
         */

        /*
        Random r = new Random();
        List<Boolean> a = padList(IntToBinaryList.convert(new BigInteger(1000, r)), 1000);;
        List<Boolean> b = padList(IntToBinaryList.convert(new BigInteger(1000, r)), 1000);

        for (MultiplicationStrategy strategy: MultiplicationStrategy.values()) {
            System.out.println("--- " + strategy + " ---");
            multiply(strategy, a, b);
            System.out.println("--- ---");
        }*/

        /*List<Boolean> a = List.of(true, false, true);
        List<Boolean> b = List.of(true, false, true);

        for (MultiplicationStrategy strategy: MultiplicationStrategy.values()) {
            System.out.println("--- " + strategy + " ---");
            multiply(strategy, a, b);
            System.out.println("--- ---");
        }*/
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
        evaluateCircuit(multiplier);
        /*Polynomial res = new Polynomial(evaluateCircuit(multiplier));
        Polynomial exp = expectedResult(a, b);
        if (!res.equals(exp)) {
            throw new RuntimeException("ERRORE circuito");
        }*/

        long middle = System.currentTimeMillis();
        evaluateCircuit(multiplier);
        /*res = new Polynomial(evaluateCircuit(multiplier));
        if (!res.equals(exp)) {
            throw new RuntimeException("ERRORE circuito");
        }*/

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