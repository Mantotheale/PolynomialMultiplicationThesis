package thesis.signal;

import org.jetbrains.annotations.NotNull;

public class CachedPin extends Pin {
    private boolean isCached = false;
    private boolean cachedValue = false;

    public void set(@NotNull Signal signal) {
        super.set(signal);
        reset();
    }

    @Override
    public boolean evaluate() {
        if (isCached)
            return cachedValue;

        cachedValue = super.evaluate();
        isCached = true;
        return cachedValue;
    }

    @Override
    public void reset() {
        super.reset();
        isCached = false;
    }
}