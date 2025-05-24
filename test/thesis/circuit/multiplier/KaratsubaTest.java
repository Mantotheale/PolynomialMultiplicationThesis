package thesis.circuit.multiplier;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

public class KaratsubaTest {
    private final @NotNull MultiplicationStrategy strategy = MultiplicationStrategy.KARATSUBA;

    @Test
    public void karatsubaBasicMultiplicationTest() {
        PolynomialMultiplication.basicMultiplicationTest(strategy);
    }

    @Test
    public void karatsubaZeroMultiplicationTest() {
        PolynomialMultiplication.zeroMultiplicationTest(strategy);
    }

    @Test
    public void karatsubaIdentityMultiplicationTest() {
        PolynomialMultiplication.identityMultiplicationTest(strategy);
    }

    @Test
    public void karatsubaSquareMultiplicationTest() {
        PolynomialMultiplication.squareMultiplicationTest(strategy);
    }
}
