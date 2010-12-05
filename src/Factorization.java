import java.util.TreeSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.ConcurrentModificationException;

/**
 * Implements all the algorithms needed
 * for polynomial factorization.
 */
public class Factorization {

    // polynomial to be factorized
    private Polynomial<FiniteFieldElement> f;

    // cyclotomic cosets
    private TreeSet<Coset> cosets;

    // basis for G
    private ArrayList<Polynomial<FiniteFieldElement>> gBasis;

    // factors of f
    private HashSet<Polynomial<FiniteFieldElement>> factors;

    // modulus
    public int q;

    // highest degree of the polynomial
    public int n;

    /**
     * Prepares for the factorization of polynomial x^n - 1
     * above the field Fq.
     */
    public Factorization(int n, int q) {
        this.q = q;
        this.n = n;

        FiniteFieldElement.MOD = q;

        cosets = new TreeSet<Coset>();
        gBasis = new ArrayList<Polynomial<FiniteFieldElement>>();
        factors = new HashSet<Polynomial<FiniteFieldElement>>();

        constructPolynomial();
    }

    /**
     * Constructs the polynomial to be factorized.
     */
    public void constructPolynomial() {
        ArrayList<FiniteFieldElement> al = new ArrayList<FiniteFieldElement>();

        // the zero coefficiet is -1 or q - 1
        al.add(new FiniteFieldElement(q - 1));

        // all the other coefficients are zero
        for (int i = 1; i < n; i++) {
            al.add(new FiniteFieldElement(0));
        }

        // the n-th coefficient is one.
        al.add(new FiniteFieldElement(1));

        f = new Polynomial<FiniteFieldElement>(al);
        factors.add(f);
    }

    /**
     * Constructs cyclotomic cosets for every i from [0, n).
     */
    public void constructCosets() {
        for (int i = 0; i < n; i++) {
            cosets.add(new Coset(i, q, n));
        }
    }

    /**
     * Construct basis for G.
     * Which is a mapping between cyclotomic cosets and polynomials.
     */
    public void construcyGBasis() {
        for (Coset cos: cosets) {
            ArrayList<FiniteFieldElement> v = new ArrayList<FiniteFieldElement>();
            for (int i = 0; i <= cos.getValues().last(); i++) {

                // for every degree number check if this number is in
                // cyclotomic coset. If it is, add coeffcient 1, if not -- 0
                if (cos.getValues().contains(i)) {
                    v.add(new FiniteFieldElement(1));
                }
                else {
                    v.add(new FiniteFieldElement(0));
                }

            }
            gBasis.add(new Polynomial<FiniteFieldElement>(v));
        }
    }

    /**
     * Does the factorization.
     */
    public void factorize() {
        // We know that there will be the same ammount of factors
        // as there are polynomials in the basis of G.
        while (factors.size() != gBasis.size()) {

            // reset the factors iterator to the beginning
            Iterator<Polynomial<FiniteFieldElement>> iter = factors.iterator();

            try {
                while (iter.hasNext()) {
                    // get the next factor.
                    Polynomial<FiniteFieldElement> factor = iter.next();

                    // this will be set to true, whenever we get new factor
                    // if that happens, we need to start factorization from the
                    // beginning, because there is one new factor to be checked.
                    boolean factorised = false;

                    for (Polynomial<FiniteFieldElement> g: gBasis) {

                        if (factorised) {
                            // do not factorize if there is one new factor
                            continue;
                        }

                        // skip the trivial cases
                        if (g.deg() == 0) {
                            continue;
                        }

                        for (int i = 0; i < q; i++) {
                            Polynomial<FiniteFieldElement> gcd = factor.gcd(g.sub(i));
                            gcd.normalize();
                            if (gcd.deg() != 0 && !factors.contains(gcd)) {
                                factors.remove(factor);
                                factors.add(gcd);
                                factorised = true;
                            }
                        }
                    }
                }
            }
            catch (ConcurrentModificationException ex) {
                // if we get here, it means that new factor has been added
                // to the factors list.
                // Continue factorization with the new factor.
            }
        }
    }

    public HashSet<Polynomial<FiniteFieldElement>> getFactors() {
        return factors;
    }

    /**
     * Format cyclotomic cosets as a HTML string.
     * Used for GUI.
     */
    public String cosetToHTMLString() {
        String output = "<html>";

        // for every coset
        for (Coset c: cosets) {
            String values = "";

            // get coset values
            Iterator<Integer> iter = c.getValuesIterator();
            while (iter.hasNext()) {
                values += iter.next();

                // if not last value, add comma
                if (iter.hasNext()) {
                    values += ", ";
                }
            }

            output += String.format("C<sub>%d</sub> = {%s}<br />", c.getIndex(), values);
        }
        return output;
    }

    /**
     * Format basis for G as a HTML string.
     * Used for GUI.
     */
    public String gBasisToHTMLString() {
        String output = "<html>";
        int index = 1;

        // for every poly
        for (Polynomial<FiniteFieldElement> p: gBasis) {
            String poly = "";

            // get all coefficients
            Iterator<FiniteFieldElement> iter = p.getCoefficientsIterator();
            int degree = -1;
            while (iter.hasNext()) {
                degree++;

                FiniteFieldElement c = iter.next();

                // skip zero coefficients
                if (c.isZero()) {
                    continue;
                }

                if (c.isOne()) {
                    if (degree == 0) {
                        poly += String.format("%s", c);
                    }
                    else {
                        poly += String.format("x<sup>%d</sup>", degree);
                    }
                }
                else {
                    poly += String.format("%sx<sup>%d</sup>", c, degree);
                }

                if (iter.hasNext()) {
                    poly += " + ";
                }
            }

            output += String.format("g<sub>%d</sub>(x) = %s<br />", index, poly);
            index++;
        }
        return output;
    }

    /**
     * Format factors as a HTML string.
     * Used for GUI.
     */
    public String getFactorsToHTMLString() {
        String output = "<html>f(x) = ";

        // for every factor
        for (Polynomial<FiniteFieldElement> p: factors) {
            String poly = "";
            p.normalize();

            // get all coefficients
            ListIterator<FiniteFieldElement> iter = p.getCoefficientsBackwardsIterator();
            int degree = p.deg() + 1;
            while (iter.hasPrevious()) {
                degree--;

                FiniteFieldElement c = iter.previous();

                // skip zero coefficients
                if (c.isZero()) {
                    continue;
                }

                if (degree == 0) {
                    // if degree is zero print only coefficient
                    poly += String.format("%s", c);
                }
                else if (degree == 1) {
                    // if degree is 1, do not print degree
                    if (c.isOne()) {
                        // if coefficient is one, print only x
                        poly += String.format("x");
                    }
                    else {
                        poly += String.format("%sx", c);
                    }
                }
                else {
                    if (c.isOne()) {
                        // if coefficient is one, print only x
                        poly += String.format("x<sup>%d</sup>", degree);
                    }
                    else {
                        poly += String.format("%sx<sup>%d</sup>", c, degree);
                    }
                }

                if (iter.hasPrevious()) {
                    poly += " + ";
                }
            }

            output += String.format("(%s)", poly);
        }
        return output;
    }
}