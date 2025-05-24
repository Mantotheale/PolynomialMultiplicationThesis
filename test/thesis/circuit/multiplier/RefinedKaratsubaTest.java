package thesis.circuit.multiplier;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

public class RefinedKaratsubaTest {
    private final @NotNull MultiplicationStrategy strategy = MultiplicationStrategy.REFINED_KARATSUBA;

    @Test
    public void refinedKaratsubaBasicMultiplicationTest() {
        PolynomialMultiplication.basicMultiplicationTest(strategy);
    }

    @Test
    public void refinedKaratsubaZeroMultiplicationTest() {
        PolynomialMultiplication.zeroMultiplicationTest(strategy);
    }

    @Test
    public void refinedKaratsubaIdentityMultiplicationTest() {
        PolynomialMultiplication.identityMultiplicationTest(strategy);
    }

    @Test
    public void refinedKaratsubaSquareMultiplicationTest() {
        PolynomialMultiplication.squareMultiplicationTest(strategy);
    }
}
