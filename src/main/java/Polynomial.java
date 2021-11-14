/*
 * Copyright 2010 Martynas Mickevičius
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package lt.dvim.polyfact;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Polynomial class is generic. It can use any class derived from FieldElement class as
 * coefficients.
 */
public class Polynomial<T extends FieldElement> {

  /**
   * Holds all coefficients of the Polynomial. ArrayList is zero indexed, so coefficient at index 0
   * is coefficent at x to the power of 0, coefficient at index 1 is coefficent at x to the power of
   * 1. and so on.
   */
  private ArrayList<T> p;

  /** Constructor for empty polynomial. */
  public Polynomial() {
    p = new ArrayList<T>();
  }

  /** Constructor with specified initial values as ArrayList. */
  public Polynomial(ArrayList<T> p) {
    this.p = p;
  }

  /**
   * Special constructor used for easy polynomial creation. Works only for coefficients which can be
   * specified as one character. Usage: p = new Polynomial(FiniteFieldElement.class, "001101"); will
   * create polynomial: x^2 + x^3 + x^5.
   *
   * <p>This constructor is used extensively in the Test class.
   */
  @SuppressWarnings("unchecked")
  public Polynomial(Class c, String s) {
    p = new ArrayList<T>();

    for (int i = 0; i < s.length(); i++) {
      try {
        p.add(
            (T)
                ((T) c.getDeclaredConstructor().newInstance())
                    .add(Integer.valueOf(s.substring(i, i + 1))));
      } catch (Exception ex) {
      }
    }
  }

  /**
   * Returns degree of the polynomial which is the highest degree where coefficient is not zero.
   * Degree of empty polynomial is -1.
   */
  public int deg() {
    return p.size() - 1;
  }

  /**
   * Adds coeffient to another coefficient.
   *
   * @param i index where coefficient must be added.
   * @param value the coefficient which will be added.
   */
  @SuppressWarnings("unchecked")
  public void addCoef(int i, T value) {
    T oldCoef = getCoef(i);

    if (oldCoef == null) {
      // if polynomial is shorter than specified index,
      // create zero coefficient
      oldCoef = (T) value.getZero();
    }

    setCoef(i, (T) oldCoef.add(value));
  }

  /**
   * Sets coefficient at specifiend index. If polynomial is shorter than index, extends with zero
   * coefficients.
   *
   * @param i index where coefficient must be set.
   * @param value the coefficient which will be set.
   */
  @SuppressWarnings("unchecked")
  public void setCoef(int i, T value) {
    while (this.deg() < i) {
      this.p.add((T) value.getZero());
    }

    this.p.set(i, value);
  }

  /**
   * Returns coefficient.
   *
   * @param i index of the coefficient to be returned.
   * @return coefficient at specified index, or null if polynomial is shorter then index
   */
  public T getCoef(int i) {
    if (this.deg() < i) {
      return null;
    } else {
      return this.p.get(i);
    }
  }

  /**
   * Adds one polynomial to another, and returns new polynomial as a result. Does not modify any
   * polynomials.
   */
  public Polynomial<T> add(Polynomial<T> b) {
    Polynomial<T> result, shorterOne;

    // figure out which is longer, and set that as a result
    if (this.deg() > b.deg()) {
      result = this.clone();
      shorterOne = b;
    } else {
      result = b.clone();
      shorterOne = this;
    }

    // add all coefficients of the shorter one to the longer one
    for (int i = 0; i <= shorterOne.deg(); i++) {
      result.addCoef(i, shorterOne.getCoef(i));
    }

    trim(result);
    return result;
  }

  /** Subtract one polynomial from another. */
  public Polynomial<T> sub(Polynomial<T> b) {
    Polynomial<T> result = this.add(b.mul(-1));

    trim(result);
    return result;
  }

  /** Subtract integer from polynomial, which means, subtract integer from the zero coefficient. */
  @SuppressWarnings("unchecked")
  public Polynomial<T> sub(Integer i) {
    Polynomial<T> result = this.clone();

    result.setCoef(0, (T) result.getCoef(0).sub(i));

    trim(result);
    return result;
  }

  /**
   * Multiplies one polynomial by another, and returns new polynomial as a result. Does not modify
   * any polynomials.
   */
  @SuppressWarnings("unchecked")
  public Polynomial<T> mul(Polynomial<T> b) {
    Polynomial<T> result = new Polynomial<T>();

    int degA = this.deg();
    int degB = b.deg();

    for (int i = 0; i <= degA; i++) {
      for (int j = 0; j <= degB; j++) {
        T termA = this.getCoef(i);
        T termB = b.getCoef(j);

        result.addCoef(i + j, (T) termA.mul(termB));
      }
    }

    trim(result);
    return result;
  }

  /** Multiply polynomial by integer, which is multiply all coefficients by integer. */
  @SuppressWarnings("unchecked")
  public Polynomial<T> mul(Integer a) {
    Polynomial<T> result = this.clone();

    for (int i = 0; i <= result.deg(); i++) {
      result.setCoef(i, (T) result.getCoef(i).mul(a));
    }

    trim(result);
    return result;
  }

  /**
   * Divides one polynomial by another and returns integer and remainder parts.
   *
   * @return ArrayList where polynomial at index 0 is integer part and polynomial at index 1 is
   *     remainder.
   */
  @SuppressWarnings("unchecked")
  public ArrayList<Polynomial<T>> divisionWithRemainder(Polynomial<T> b) {

    // check for division by zero
    if (b.isZero()) {
      throw (new ArithmeticException("/ by zero"));
    }

    // result will be ArrayList with to polynomials
    ArrayList<Polynomial<T>> result = new ArrayList<Polynomial<T>>();

    // remainders will be stored here
    Polynomial<T> a = this;

    // integer part will be stored here
    Polynomial<T> integer = new Polynomial<T>();

    int deg = a.deg() - b.deg();

    while (deg >= 0 && !a.isZero()) {
      T coefA = a.getCoef(a.deg());
      T coefB = b.getCoef(b.deg());
      T coefI = (T) coefA.mul(coefB.inv());

      integer.setCoef(deg, coefI);

      // temporary Polynomial for multiplying b
      Polynomial<T> temp = new Polynomial<T>();
      temp.setCoef(deg, coefI);

      a = a.sub(b.mul(temp));

      deg = a.deg() - b.deg();
    }

    result.add(integer);
    result.add(a);
    return result;
  }

  /** Returns the integer part of division. */
  public Polynomial<T> div(Polynomial<T> b) {
    return this.divisionWithRemainder(b).get(0);
  }

  /** Returns the remainder part of division. */
  public Polynomial<T> rem(Polynomial<T> b) {
    return this.divisionWithRemainder(b).get(1);
  }

  /** Calculates Greatest Common Divisor */
  public Polynomial<T> gcd(Polynomial<T> b) {
    Polynomial<T> f = this;
    Polynomial<T> g = b;

    // implementation of euclidean algorithm
    while (true) {
      Polynomial<T> remainder = f.rem(g);

      if (remainder.isZero()) {
        return g;
      }

      f = g;
      g = remainder;
    }
  }

  /**
   * Remove trailing zeros from the coefficient ArrayList. Since the degree of polynomial is the
   * size of coefficient ArrayList, trailing zeros at the and of this list, would mean wrong degree.
   */
  public void trim(Polynomial<T> a) {
    int i = a.deg();
    while (i > 0 && a.getCoef(i).isZero()) {
      a.p.remove(i);
      i--;
    }
  }

  /** Make coefficient at the highest degree to one. */
  @SuppressWarnings("unchecked")
  public void normalize() {
    // do not normalize empty polynomials
    if (this.deg() < 0) {
      return;
    }

    // do not normalize zero polynomials
    if (this.deg() == 0 && this.getCoef(0).isZero()) {
      return;
    }

    if (this.getCoef(this.deg()).isOne()) {
      return;
    }

    T c = (T) this.getCoef(this.deg()).inv();
    for (int i = 0; i <= this.deg(); i++) {
      this.setCoef(i, (T) this.getCoef(i).mul(c));
    }
  }

  /**
   * Return true if polynomial is zero. Which is degree of polynomial is zero and the only
   * coefficient is also zero.
   */
  public boolean isZero() {
    return (this.deg() == 0 && this.getCoef(0).isZero());
  }

  public String toString() {
    return p.toString();
  }

  @SuppressWarnings("unchecked")
  public Polynomial<T> clone() {
    return new Polynomial<T>((ArrayList<T>) this.p.clone());
  }

  /**
   * Equality of two polynomials is defined as 1. Equality of the degrees. 2. And equality of the
   * coefficients.
   */
  @SuppressWarnings("unchecked")
  public boolean equals(Object o) {
    if (!(o instanceof Polynomial)) {
      return false;
    }

    Polynomial<T> a = (Polynomial<T>) o;

    if (this.deg() != a.deg()) {
      return false;
    }

    for (int i = 0; i <= this.deg(); i++) {
      if (!this.getCoef(i).equals(a.getCoef(i))) {
        return false;
      }
    }

    return true;
  }

  public int hashCode() {
    return p.hashCode();
  }

  /** Iterator to iterate from the start to the end of coefficients list. */
  public Iterator<T> getCoefficientsIterator() {
    return p.iterator();
  }

  /** Iterator to iterate from the end to the start of coefficients list. */
  public ListIterator<T> getCoefficientsBackwardsIterator() {
    return p.listIterator(p.size());
  }
}
