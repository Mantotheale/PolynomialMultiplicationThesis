package thesis.circuit.multiplier;

import org.jetbrains.annotations.NotNull;
import thesis.circuit.component.Adder;
import thesis.circuit.Circuit;
import thesis.circuit.component.ShiftAdder;
import thesis.signal.AndGate;
import thesis.signal.Bus;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Karatsuba extends Circuit {
    private final @NotNull Bus outputs;

    public Karatsuba(int bits) {
        super(bits * 2);

        if (bits == 1) {
            outputs = new Bus(List.of(new AndGate(inputs.get(0), inputs.get(1))));
        } else if (bits % 2 != 0) {
            Karatsuba nextKaratsuba = new Karatsuba(bits + 1);
            nextKaratsuba.setInput(0, new Bus(inputs.subList(0, bits)));
            nextKaratsuba.setInput(bits + 1, new Bus(inputs.subList(bits, 2 * bits)));
            outputs = nextKaratsuba.outputBus().subBus(0, 2 * bits - 1);
        } else {
            int n = bits / 2;
            Bus a0 = new Bus(inputs.subList(0, n));
            Bus a1 = new Bus(inputs.subList(n, bits));
            Bus b0 = new Bus(inputs.subList(bits, bits + n));
            Bus b1 = new Bus(inputs.subList(bits + n, 2 * bits));

            Karatsuba a0b0 = new Karatsuba(a0, b0);
            Karatsuba a1b1 = new Karatsuba(a1, b1);
            Adder a0Plusa1 = new Adder(a0, a1);
            Adder b0Plusb1 = new Adder(b0, b1);
            Karatsuba a0Plusa1b0Plusb1 = new Karatsuba(a0Plusa1.outputBus(), b0Plusb1.outputBus());
            ShiftAdder onePlusTna0b0 = new ShiftAdder(a0b0.outputBus(), n);
            ShiftAdder onePlusTna1b1 = new ShiftAdder(a1b1.outputBus(), n);
            Adder firstAddMiddlePart = new Adder(
                    onePlusTna0b0.outputBus().subBus(n, 2 * n - 1),
                    a0Plusa1b0Plusb1.outputBus()
            );
            Adder secondAddMiddlePart = new Adder(
                    firstAddMiddlePart.outputBus(),
                    onePlusTna1b1.outputBus().subBus(0, 2 * n - 1)
            );

            var x0 = IntStream.range(0, n).mapToObj(onePlusTna0b0::output);
            var x1 = secondAddMiddlePart.outputBus().stream();
            var x2 = IntStream.range(0, n).mapToObj(i -> onePlusTna1b1.output(2 * n - 1 + i));
            outputs = new Bus(Stream.concat(Stream.concat(x0, x1), x2));
        }
    }

    public Karatsuba(@NotNull Bus a, @NotNull Bus b) {
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

