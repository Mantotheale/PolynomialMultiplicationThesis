package thesis.circuit.component;

import org.jetbrains.annotations.NotNull;
import thesis.circuit.Circuit;
import thesis.signal.Bus;
import thesis.signal.XorGate;

import java.util.stream.IntStream;

public class Adder extends Circuit {
    private final @NotNull Bus outputs;

    public Adder(int bits) {
        super(bits * 2);

        outputs = new Bus(
                IntStream.range(0, bits)
                        .mapToObj(i -> new XorGate(inputs.get(i), inputs.get(bits + i))).toList()
        );
    }

    public Adder(@NotNull Bus a, @NotNull Bus b) {
        if (a.size() != b.size())
            throw new IllegalArgumentException("The inputs should have the same size");

        int bits = a.size();
        this(bits);

        setInput(0, a);
        setInput(bits, b);
    }

    @Override
    public @NotNull Bus outputBus() {
        return outputs;
    }
}
