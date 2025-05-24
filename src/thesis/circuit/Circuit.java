package thesis.circuit;

import org.jetbrains.annotations.NotNull;
import thesis.signal.Bus;
import thesis.signal.CachedPin;
import thesis.signal.Pin;
import thesis.signal.Signal;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public abstract class Circuit {
    protected final @NotNull List<@NotNull Pin> inputs;

    protected Circuit(int inputSlots) {
        //this.inputs = IntStream.range(0, inputSlots).mapToObj(_ -> new Pin()).toList();
        this.inputs = IntStream.range(0, inputSlots).mapToObj(_ -> (Pin) new CachedPin()).toList();
    }

    public final void setInput(int slot, @NotNull Signal signal) {
        reset();
        inputs.get(slot).set(signal);
    }

    public final void setInput(int offset, @NotNull Bus bus) {
        reset();
        for (int i = 0; i < bus.size(); i++) {
            inputs.get(offset + i).set(bus.getSignal(i));
        }
    }

    public final void setInput(@NotNull Bus bus) {
        setInput(0, bus);
    }

    public abstract @NotNull Bus outputBus();

    public final @NotNull Signal output(int slot) {
        return outputBus().getSignal(slot);
    }

    public final int gateCount() {
        Set<Signal> visited = new HashSet<>();

        int count = 0;
        for (Signal o: outputBus()) {
            count += o.gateCount(visited);
        }

        return count;
    }

    public final void reset() {
        for (Signal s: outputBus())
            s.reset();
    }
}
