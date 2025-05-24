package thesis.signal;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;

public class XorGate implements Signal {
    private final @NotNull Signal a;
    private final @NotNull Signal b;

    public XorGate(@NotNull Signal a, @NotNull Signal b) {
        this.a = Objects.requireNonNull(a);
        this.b = Objects.requireNonNull(b);
    }

    @Override
    public boolean evaluate() {
        return a.evaluate() ^ b.evaluate();
    }

    @Override
    public int gateCount(@NotNull Set<@NotNull Signal> visited) {
        if (visited.contains(this)) {
            return 0;
        }

        visited.add(this);
        return 1 + a.gateCount(visited) + b.gateCount(visited);
    }

    @Override
    public @NotNull String formattedToString(int level) {
        return new StringBuilder()
                .repeat('\t', level)
                .append("XOR [\n")
                .append(a.formattedToString(level + 1))
                .append(",\n")
                .append(b.formattedToString(level + 1))
                .append("\n")
                .repeat('\t', level)
                .append("]")
                .toString();
    }

    @Override
    public void reset() {
        a.reset();
        b.reset();
    }

    @Override
    public String toString() {
        return formattedToString(0);
    }
}