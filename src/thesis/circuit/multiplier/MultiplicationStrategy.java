package thesis.circuit.multiplier;

import org.jetbrains.annotations.NotNull;
import thesis.circuit.Circuit;

public enum MultiplicationStrategy {
    SCHOOL_BOOK {
        @Override
        public @NotNull Circuit getCircuit(int bits) {
            return new SchoolBook(bits);
        }
    },
    KARATSUBA {
        @Override
        public @NotNull Circuit getCircuit(int bits) {
            return new Karatsuba(bits);
        }
    },
    REFINED_KARATSUBA {
        @Override
        public @NotNull Circuit getCircuit(int bits) {
            return new RefinedKaratsuba(bits);
        }
    };

    public abstract @NotNull Circuit getCircuit(int bits);
}
