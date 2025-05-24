package thesis.circuit.multiplier;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

public class SchoolBookTest {
    private final @NotNull MultiplicationStrategy strategy = MultiplicationStrategy.SCHOOL_BOOK;

    @Test
    public void schoolBookBasicMultiplicationTest() {
        PolynomialMultiplication.basicMultiplicationTest(strategy);
    }

    @Test
    public void schoolBookZeroMultiplicationTest() {
        PolynomialMultiplication.zeroMultiplicationTest(strategy);
    }

    @Test
    public void schoolBookIdentityMultiplicationTest() {
        PolynomialMultiplication.identityMultiplicationTest(strategy);
    }

    @Test
    public void schoolBookSquareMultiplicationTest() {
        PolynomialMultiplication.squareMultiplicationTest(strategy);
    }
}
