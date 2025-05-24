package thesis.circuit.multiplier;

import org.jetbrains.annotations.NotNull;
import thesis.circuit.component.Adder;
import thesis.circuit.Circuit;
import thesis.circuit.component.ShiftAdder;
import thesis.circuit.component.UnevenAdder;
import thesis.signal.AndGate;
import thesis.signal.Bus;

import java.util.List;

public class RefinedKaratsuba extends Circuit {
    private final @NotNull Bus outputs;

    public RefinedKaratsuba(int bits) {
        super(bits * 2);

        if (bits == 1) {
            outputs = new Bus(List.of(new AndGate(inputs.get(0), inputs.get(1))));
        } else {
            int k = bits / 2;
            int n = bits - k;

            Bus a0 = new Bus(inputs.subList(0, n));
            Bus a1 = new Bus(inputs.subList(n, bits));
            Bus b0 = new Bus(inputs.subList(bits, bits + n));
            Bus b1 = new Bus(inputs.subList(bits + n, 2 * bits));

            RefinedKaratsuba a0b0 = new RefinedKaratsuba(a0, b0);
            RefinedKaratsuba a1b1 = new RefinedKaratsuba(a1, b1);
            UnevenAdder a0Plusa1 = new UnevenAdder(a0, a1);
            UnevenAdder b0Plusb1 = new UnevenAdder(b0, b1);
            RefinedKaratsuba a0Plusa1b0Plusb1 = new RefinedKaratsuba(a0Plusa1.outputBus(), b0Plusb1.outputBus());
            Adder a0b0PlusTna1b1 = new Adder(
                    a0b0.outputBus().subBus(n, n - 1),
                    a1b1.outputBus().subBus(0, n - 1)
            );
            Bus a0b0PlusTna1b1Bus = a0b0.outputBus().subBus(0, n)
                    .concat(a0b0PlusTna1b1.outputBus())
                    .concat(a1b1.outputBus().subBus(n - 1, 2 * k - n));
            ShiftAdder onePlusTna0b0PlusTna1b1 = new ShiftAdder(a0b0PlusTna1b1Bus, n);
            Adder total = new Adder(
                    onePlusTna0b0PlusTna1b1.outputBus().subBus(n, 2 * n - 1),
                    a0Plusa1b0Plusb1.outputBus()
            );

            outputs = onePlusTna0b0PlusTna1b1.outputBus().subBus(0, n)
                    .concat(total.outputBus())
                    .concat(onePlusTna0b0PlusTna1b1.outputBus().subBus(3 * n - 1, 2 * k - n));


        }
    }

    public RefinedKaratsuba(@NotNull Bus a, @NotNull Bus b) {
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
