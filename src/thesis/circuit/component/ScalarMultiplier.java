package thesis.circuit.component;

import org.jetbrains.annotations.NotNull;
import thesis.circuit.Circuit;
import thesis.signal.AndGate;
import thesis.signal.Bus;
import thesis.signal.Signal;

import java.util.stream.IntStream;

public class ScalarMultiplier extends Circuit {
    private final @NotNull Bus outputs;

    public ScalarMultiplier(int bits) {
        super(bits + 1);

        outputs = new Bus(
                IntStream.range(0, bits).mapToObj(i ->
                        new AndGate(inputs.get(i), inputs.get(bits))
                )
        );
    }

    public ScalarMultiplier(Signal scalar, Bus vector) {
        this(vector.size());

        setInput(vector);
        setInput(vector.size(), scalar);
    }

    @Override
    public @NotNull Bus outputBus() {
        return outputs;
    }
}
