package thesis.circuit.multiplier;

import org.jetbrains.annotations.NotNull;
import thesis.circuit.component.Adder;
import thesis.circuit.Circuit;
import thesis.circuit.component.ScalarMultiplier;
import thesis.signal.AndGate;
import thesis.signal.Bus;
import thesis.signal.Signal;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SchoolBook extends Circuit {
    private final @NotNull Bus outputs;

    public SchoolBook(int bits) {
        super(bits * 2);

        if (bits == 1) {
            outputs = new Bus(List.of(new AndGate(inputs.get(0), inputs.get(1))));
        } else {
            Bus a0 = new Bus(inputs.subList(0, bits - 1));
            Bus b0 = new Bus(inputs.subList(bits, 2 * bits - 1));
            Signal aLast = inputs.get(bits - 1);
            Signal bLast = inputs.get(2 * bits - 1);

            SchoolBook a0b0 = new SchoolBook(a0, b0);
            ScalarMultiplier a0bLast = new ScalarMultiplier(bLast, a0);
            ScalarMultiplier aLastb0 = new ScalarMultiplier(aLast, b0);
            Adder a0bLastplusaLastb0 = new Adder(a0bLast.outputBus(), aLastb0.outputBus());
            AndGate aLastbLast = new AndGate(aLast, bLast);

            Adder a0b0plusA0bLastplusaLastb0 = new Adder(
                    a0b0.outputBus().subBus(bits - 1, bits - 2),
                    a0bLastplusaLastb0.outputBus().subBus(0, bits - 2)
            );

            var x0 = IntStream.range(0, bits - 1).mapToObj(a0b0::output);
            var x1 = IntStream.range(0, bits - 2).mapToObj(a0b0plusA0bLastplusaLastb0::output);
            var x2 = Stream.of(a0bLastplusaLastb0.output(bits - 2));
            var x3 = Stream.of(aLastbLast);

            outputs = new Bus(Stream.concat(Stream.concat(Stream.concat(x0, x1), x2), x3));
        }
    }

    public SchoolBook(@NotNull Bus a, @NotNull Bus b) {
        if (a.size() != b.size()) {
            throw new IllegalArgumentException("Inputs should be the same size");
        }

        this(a.size());
        setInput(0, a);
        setInput(a.size(), b);
    }

    @Override
    public @NotNull Bus outputBus() {
        return outputs;
    }
}
