package thesis.poly;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Polynomial {
    private final boolean @NotNull [] coeffs;
    private final int degree;

    public Polynomial() {
        coeffs = new boolean[1];
        degree = 0;
    }

    public Polynomial(boolean ...coeffs) {
        this.coeffs = new boolean[coeffs.length];
        System.arraycopy(coeffs, 0, this.coeffs, 0, coeffs.length);

        int d = 0;
        for (int i = 0; i < coeffs.length; i++) {
            if (coeffs[i]) {
                d = i;
            }
        }
        degree = d;
    }

    public Polynomial(@NotNull List<@NotNull Boolean> coeffs) {
        this.coeffs = new boolean[coeffs.size()];
        for (int i = 0; i < coeffs.size(); i++) {
            this.coeffs[i] = coeffs.get(i);
        }

        int d = 0;
        for (int i = 0; i < coeffs.size(); i++) {
            if (coeffs.get(i)) {
                d = i;
            }
        }
        degree = d;
    }

    public boolean coeff(int degree) {
        if (degree < 0)
            throw new IllegalArgumentException("A polynomial has only non-negative degree terms");

        return coeffs.length > degree && coeffs[degree];
    }

    public int degree() {
        return degree;
    }

    public @NotNull Polynomial mul(@NotNull Polynomial other) {
        boolean[] coeffs = new boolean[this.degree() + other.degree() + 1];

        for (int i = 0; i <= this.degree(); i++) {
            for (int j = 0; j <= other.degree(); j++) {
                coeffs[i + j] ^= this.coeff(i) & other.coeff(j);
            }
        }

        return new Polynomial(coeffs);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj instanceof Polynomial other) {
            int max = Math.max(this.coeffs.length, other.coeffs.length);
            for (int i = 0; i < max; i++) {
                if (this.coeff(i) != other.coeff(i)) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (coeffs[0]) {
            sb.append(1).append(" + ");
        }

        if (coeff(1)) {
            sb.append('x').append(" + ");
        }

        for (int i = 2; i < coeffs.length; i++) {
            if (coeff(i)) {
                sb.append('x').append('^').append(i).append(" + ");
            }
        }

        if (sb.isEmpty()) {
            return "0";
        }

        sb.delete(sb.length() - 3, sb.length());
        return sb.toString();
    }
}
