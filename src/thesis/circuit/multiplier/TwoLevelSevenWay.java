package thesis.circuit.multiplier;

import org.jetbrains.annotations.NotNull;
import thesis.circuit.Circuit;
import thesis.circuit.component.Adder;
import thesis.circuit.component.ShiftAdder;
import thesis.circuit.component.UnevenAdder;
import thesis.signal.AndGate;
import thesis.signal.Bus;

import java.util.List;

public class TwoLevelSevenWay extends Circuit {
    private final @NotNull Bus outputs;

    public TwoLevelSevenWay(int bits) {
        super(bits * 2);

        int n = Math.ceilDiv(bits, 4);
        if (bits == 1) {
            outputs = new Bus(List.of(new AndGate(inputs.get(0), inputs.get(1))));
        } else if (2 * bits < 7 * n) {
            RefinedKaratsuba replacement = new RefinedKaratsuba(bits);
            replacement.setInput(new Bus(inputs));
            outputs = replacement.outputBus();
        } else {
            int k = bits - 3 * n;

            Bus a0 = new Bus(inputs.subList(0, n));
            Bus a1 = new Bus(inputs.subList(n, 2 * n));
            Bus a2 = new Bus(inputs.subList(2 * n, 3 * n));
            Bus a3 = new Bus(inputs.subList(3 * n, bits));
            Bus b0 = new Bus(inputs.subList(bits, bits + n));
            Bus b1 = new Bus(inputs.subList(bits + n, bits + 2 * n));
            Bus b2 = new Bus(inputs.subList(bits + 2 * n, bits + 3 * n));
            Bus b3 = new Bus(inputs.subList(bits + 3 * n, 2 * bits));

            TwoLevelSevenWay a0b0 = new TwoLevelSevenWay(a0, b0);
            TwoLevelSevenWay a1b1 = new TwoLevelSevenWay(a1, b1);
            TwoLevelSevenWay a2b2 = new TwoLevelSevenWay(a2, b2);
            TwoLevelSevenWay a3b3 = new TwoLevelSevenWay(a3, b3);
            Adder a0b0plusTna1b1 = new Adder(
                    a0b0.outputBus().subBus(n, n - 1),
                    a1b1.outputs.subBus(0, n -1)
            );
            Adder a1b1plusTna2b2 = new Adder(
                    a1b1.outputBus().subBus(n, n - 1),
                    a2b2.outputs.subBus(0, n -1)
            );
            Adder a2b2plusTna3b3 = new Adder(
                    a2b2.outputBus().subBus(n, n - 1),
                    a3b3.outputs.subBus(0, n -1)
            );
            Bus firstSum = a0b0.outputBus().subBus(0, n)
                    .concat(a0b0plusTna1b1.outputBus())
                    .concat(a1b1.output(n - 1))
                    .concat(a1b1plusTna2b2.outputBus())
                    .concat(a2b2.output(n - 1))
                    .concat(a2b2plusTna3b3.outputBus())
                    .concat(a3b3.outputBus().subBus(n - 1, 2 * k - n));
            ShiftAdder A = new ShiftAdder(firstSum, n);

            Adder a0plusa1 = new Adder(a0, a1);
            Adder b0plusb1 = new Adder(b0, b1);
            TwoLevelSevenWay a0plusa1b0plusb1 = new TwoLevelSevenWay(a0plusa1.outputBus(), b0plusb1.outputBus());
            UnevenAdder a2plusa3 = new UnevenAdder(a2, a3);
            UnevenAdder b2plusb3 = new UnevenAdder(b2, b3);
            TwoLevelSevenWay a2plusa3b2plusb3 = new TwoLevelSevenWay(a2plusa3.outputBus(), b2plusb3.outputBus());
            Adder AplusB = new Adder(A.outputBus().subBus(n, 2 * n - 1), a0plusa1b0plusb1.outputBus());
            Adder AplusC = new Adder(A.outputBus().subBus(3 * n, 2 * n - 1), a2plusa3b2plusb3.outputBus());
            Bus AplusBplusC = A.outputBus().subBus(0, n)
                    .concat(AplusB.outputBus())
                    .concat(A.output(3 * n - 1))
                    .concat(AplusC.outputBus())
                    .concat(A.outputBus().subBus(5 * n - 1, 2 * k - n));
            ShiftAdder D = new ShiftAdder(AplusBplusC, 2 * n);

            Adder a0plusa2 = new Adder(a0, a2);
            UnevenAdder a1plusa3 = new UnevenAdder(a1, a3);
            Adder b0plusb2 = new Adder(b0, b2);
            UnevenAdder b1plusb3 = new UnevenAdder(b1, b3);
            TwoLevelSevenWay E = new TwoLevelSevenWay(
                    a0plusa2.outputBus().concat(a1plusa3.outputBus()),
                    b0plusb2.outputBus().concat(b1plusb3.outputBus())
            );
            Adder DplusE = new Adder(D.outputBus().subBus(2 * n, 4 * n - 1), E.outputBus());

            outputs = D.outputBus().subBus(0, 2 * n)
                    .concat(DplusE.outputBus())
                    .concat(D.outputBus().subBus(6 * n - 1, 2 * k));
        }
    }

    public TwoLevelSevenWay(@NotNull Bus a, @NotNull Bus b) {
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
