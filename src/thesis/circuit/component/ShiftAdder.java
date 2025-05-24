package thesis.circuit.component;

import org.jetbrains.annotations.NotNull;
import thesis.circuit.Circuit;
import thesis.signal.Bus;
import thesis.signal.ConstantSignal;
import thesis.signal.XorGate;

import java.util.stream.IntStream;

public class ShiftAdder extends Circuit {
    private final @NotNull Bus outputs;

    public ShiftAdder(int bits, int shift) {
        super(bits);

        outputs = new Bus(IntStream.range(0, bits + shift).mapToObj(i -> {
            if (i < shift) {
                return i < bits ? inputs.get(i) : ConstantSignal.FALSE;
            } else {
                return i < bits ? new XorGate(inputs.get(i), inputs.get(i - shift)) : inputs.get(i - shift);
            }
        }).toList());
    }

    public ShiftAdder(@NotNull Bus x, int shift) {
        this(x.size(), shift);
        setInput(x);
    }

    @Override
    public @NotNull Bus outputBus() {
        return outputs;
    }
}
