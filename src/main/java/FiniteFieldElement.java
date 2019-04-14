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

public class FiniteFieldElement extends FieldElement {

  public static int MOD = 2;
  private Integer value;

  /** Class constructor. Default value is zero. */
  public FiniteFieldElement() {
    this.value = 0;
  }

  /** Class constructor with initial value. */
  public FiniteFieldElement(Integer value) {
    this.value = (value % MOD + MOD) % MOD;
  }

  public FieldElement add(FieldElement a) {
    return new FiniteFieldElement(this.getValue() + a.getValue());
  }

  public FieldElement add(Integer i) {
    return this.add(new FiniteFieldElement(i));
  }

  public FieldElement sub(FieldElement a) {
    return new FiniteFieldElement(this.getValue() - a.getValue());
  }

  public FieldElement sub(Integer i) {
    return this.sub(new FiniteFieldElement(i));
  }

  public FieldElement mul(FieldElement a) {
    return new FiniteFieldElement(this.getValue() * a.getValue());
  }

  public FieldElement mul(Integer i) {
    return this.mul(new FiniteFieldElement(i));
  }

  public FieldElement inv() {
    for (int i = 1; i < MOD; i++) {
      if (this.mul(i).isOne()) {
        return new FiniteFieldElement(i);
      }
    }
    assert false : String.format("No inv of %s found. Probable mod is no prime.", this);
    return null;
  }

  public FieldElement getZero() {
    return new FiniteFieldElement(0);
  }

  public boolean isZero() {
    return value % MOD == 0;
  }

  public FieldElement getOne() {
    return new FiniteFieldElement(1);
  }

  public boolean isOne() {
    return value % MOD == 1;
  }

  public Integer getValue() {
    return value;
  }

  public String toString() {
    return value.toString();
  }

  /**
   * Checks if values are equal of two objects. Special arithemtics must be used because, e.g. -3
   * mod 2 == 5 mod 2 and so on.
   */
  public boolean equals(Object o) {
    if (!(o instanceof FiniteFieldElement)) {
      return false;
    }

    Integer valueA = this.getValue();
    Integer valueB = ((FiniteFieldElement) o).getValue();

    return ((valueA % MOD + MOD) % MOD == (valueB % MOD + MOD) % MOD);
  }

  /** Internal value is returned as hashcode. */
  public int hashCode() {
    return (this.getValue() % MOD + MOD) % MOD;
  }
}
