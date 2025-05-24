package thesis.signal;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface Signal {
    boolean evaluate();
    int gateCount(@NotNull Set<@NotNull Signal> visited);
    @NotNull String formattedToString(int level);
    void reset();
}