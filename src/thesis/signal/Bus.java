package thesis.signal;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Bus implements Iterable<Signal> {
    private final @NotNull List<@NotNull Signal> signals;

    public <T extends Signal> Bus(@NotNull List<@NotNull T> signals) {
        this.signals = Objects.requireNonNull(signals).stream().map(s -> (Signal) s).toList();
    }

    public <T extends Signal> Bus(@NotNull Stream<@NotNull T> signals) {
        this.signals = Objects.requireNonNull(signals).map(s -> (Signal) s).toList();
    }

    @SafeVarargs
    public <T extends Signal> Bus(@NotNull T @NotNull ...signals) {
        this.signals = Arrays.stream(Objects.requireNonNull(signals)).map(s -> (Signal) s).toList();
    }

    public @NotNull Signal getSignal(int slot) {
        return signals.get(slot);
    }

    public int size() {
        return signals.size();
    }

    public @NotNull Bus subBus(int offset, int size) {
        return new Bus(signals.subList(offset, offset + size));
    }

    public @NotNull Bus concat(@NotNull Bus other) {
        return new Bus(Stream.concat(this.stream(), other.stream()));
    }

    @Override
    public @NotNull Iterator<Signal> iterator() {
        return signals.iterator();
    }

    public @NotNull Stream<Signal> stream() {
        return signals.stream();
    }

    @Override
    public String toString() {
        return signals.toString();
    }
}
