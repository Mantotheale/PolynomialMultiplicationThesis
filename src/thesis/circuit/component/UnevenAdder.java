package thesis.circuit.component;

import org.jetbrains.annotations.NotNull;
import thesis.circuit.Circuit;
import thesis.signal.Bus;
import thesis.signal.Signal;
import thesis.signal.XorGate;

import java.util.ArrayList;
import java.util.List;

public class UnevenAdder extends Circuit {
    private final @NotNull Bus outputs;

    public UnevenAdder(int bitsA, int bitsB) {
        super(bitsA + bitsB);

        List<Signal> outList = new ArrayList<>();
        int i;
        for (i = 0; i < bitsA && i < bitsB; i++) {
            outList.add(new XorGate(inputs.get(i), inputs.get(bitsA + i)));
        }

        for ( ; i < bitsA; i++) {
            outList.add(inputs.get(i));
        }

        for ( ; i < bitsB; i++) {
            outList.add(inputs.get(i + bitsA));
        }

        outputs = new Bus(outList);
    }

    public UnevenAdder(@NotNull Bus a, @NotNull Bus b) {
        this(a.size(), b.size());

        setInput(0, a);
        setInput(a.size(), b);
    }

    @Override
    public @NotNull Bus outputBus() {
        return outputs;
    }
}