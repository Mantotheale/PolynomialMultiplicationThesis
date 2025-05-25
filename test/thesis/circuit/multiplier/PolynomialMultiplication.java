package thesis.circuit.multiplier;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import thesis.circuit.Circuit;
import thesis.signal.Bus;
import thesis.signal.ConstantSignal;
import thesis.signal.Signal;

import java.util.List;

public class PolynomialMultiplication {
    public static void basicMultiplicationTest(@NotNull MultiplicationStrategy strategy) {
        Circuit multiplier = strategy.getCircuit(2);
        fixInput(multiplier, List.of(true, false), List.of(true, false));
        checkOutput(multiplier, List.of(true, false, false));

        multiplier = strategy.getCircuit(3);
        fixInput(multiplier, List.of(false, true, true), List.of(true, true, false));
        checkOutput(multiplier, List.of(false, true, false, true, false));
    }

    public static void zeroMultiplicationTest(@NotNull MultiplicationStrategy strategy) {
        Circuit multiplier = strategy.getCircuit(3);
        fixInput(multiplier, List.of(false, false, false), List.of(true, false, true));
        checkOutput(multiplier, List.of(false, false, false, false, false));

        fixInput(multiplier, List.of(true, false, true), List.of(false, false, false));
        checkOutput(multiplier, List.of(false, false, false, false, false));
    }

    public static void identityMultiplicationTest(@NotNull MultiplicationStrategy strategy) {
        Circuit multiplier = strategy.getCircuit(4);
        fixInput(multiplier, List.of(true, false, false, false), List.of(true, false, true, true));
        checkOutput(multiplier, List.of(true, false, true, true, false, false, false));

        fixInput(multiplier, List.of(true, false, true, true), List.of(true, false, false, false));
        checkOutput(multiplier, List.of(true, false, true, true, false, false, false));
    }

    public static void squareMultiplicationTest(@NotNull MultiplicationStrategy strategy) {
        Circuit multiplier = strategy.getCircuit(7);
        List<Boolean> input = List.of(true, false, false, true, false, true, true);
        fixInput(multiplier, input, input);
        checkOutput(multiplier, List.of(true, false, false, false, false, false, true, false, false, false, true, false, true));
    }

    public static void eightBitMultiplicationTest(@NotNull MultiplicationStrategy strategy) {
        Circuit multiplier = strategy.getCircuit(8);
        fixInput(multiplier,
                List.of(false, true, false, true, false, true, true, true),
                List.of(true, false, false, false, false, false, true, true));
        checkOutput(multiplier,
                List.of(false, true, false, true, false, true, true, false, true, true, true, true, false, false, true));

        fixInput(multiplier,
                List.of(true, false, false, true, false, false, false, true),
                List.of(true, false, false, false, true, true, false, true));
        checkOutput(multiplier,
                List.of(true, false, false, true, true, true, false, true, true, false, true, true, true, false, true));
    }

    public static void sixteenBitMultiplicationTest(@NotNull MultiplicationStrategy strategy) {
        Circuit multiplier = strategy.getCircuit(16);
        fixInput(multiplier,
                List.of(false, true, true, true, true, false, true, true,
                        true, true, true, true, false, false, true, true),
                List.of(true, true, true, true, true, false, false, false,
                        false, true, false, false, true, true, false, true));
        checkOutput(multiplier,
                List.of(false, true, false, true, false, false, false, false,
                        false, false, false, false, true, true, true, false,
                        false, true, true, true, true, true, true, false,
                        true, true, false, false, true, true, true
                ));
    }



    private static void fixInput(@NotNull Circuit multiplier, @NotNull List<Boolean> a, @NotNull List<Boolean> b) {
        Bus busA = new Bus(boolToSignal(a));
        Bus busB = new Bus(boolToSignal(b));
        multiplier.setInput(busA.concat(busB));
    }

    private static void checkOutput(@NotNull Circuit multiplier, @NotNull List<Boolean> expected) {
        Assertions.assertEquals(expected, evaluateCircuit(multiplier));
    }

    private static @NotNull List<@NotNull Signal> boolToSignal(@NotNull List<@NotNull Boolean> bools) {
        return bools.stream().map(x -> x ? ConstantSignal.TRUE : ConstantSignal.FALSE).toList();
    }

    private static @NotNull List<@NotNull Boolean> evaluateCircuit(@NotNull Circuit multiplier) {
        return multiplier.outputBus().stream().map(Signal::evaluate).toList();
    }
}
