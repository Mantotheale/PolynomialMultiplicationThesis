package thesis.circuit.multiplier;

import org.junit.jupiter.api.Test;

public class MultiplicationTest {
    @Test
    public void multiplicationTest() {
        for (MultiplicationStrategy strategy: MultiplicationStrategy.values()) {
            PolynomialMultiplication.basicMultiplicationTest(strategy);
            PolynomialMultiplication.zeroMultiplicationTest(strategy);
            PolynomialMultiplication.identityMultiplicationTest(strategy);
            PolynomialMultiplication.squareMultiplicationTest(strategy);
            PolynomialMultiplication.eightBitMultiplicationTest(strategy);
            PolynomialMultiplication.sixteenBitMultiplicationTest(strategy);
        }
    }
}
