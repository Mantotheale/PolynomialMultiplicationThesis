package thesis.signal;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ConstantSignal implements Signal {
    private final boolean value;

    private ConstantSignal(boolean value) {
        this.value = value;
    }

    @Override
    public boolean evaluate() {
        return value;
    }

    @Override
    public int gateCount(@NotNull Set<Signal> visited) {
        return 0;
    }

    @Override
    public @NotNull String formattedToString(int level) {
        return new StringBuilder()
                .repeat('\t', level)
                .append(value)
                .toString();
    }

    @Override
    public void reset() { }

    @NotNull public static Signal TRUE = new ConstantSignal(true);

    @NotNull public static Signal FALSE = new ConstantSignal(false);
}