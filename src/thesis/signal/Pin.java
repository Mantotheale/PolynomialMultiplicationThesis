package thesis.signal;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class Pin implements Signal {
    private @NotNull Signal signal = ConstantSignal.FALSE;

    public void set(Signal signal) {
        this.signal = signal;
    }

    @Override
    public boolean evaluate() {
        return signal.evaluate();
    }

    @Override
    public int gateCount(@NotNull Set<Signal> visited) {
        return signal.gateCount(visited);
    }

    @Override
    public @NotNull String formattedToString(int level) {
        return signal.formattedToString(level);
    }

    @Override
    public void reset() {
        signal.reset();
    }
}
