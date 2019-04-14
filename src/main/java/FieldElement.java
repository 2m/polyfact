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

public abstract class FieldElement {

  /**
   * Addition. Must not change neither this, neither parameter a. Must return result as a new
   * object.
   */
  public abstract FieldElement add(FieldElement a);

  public abstract FieldElement add(Integer a);

  /**
   * Subtraction. Must not change neither this, neither parameter a. Must return result as a new
   * object.
   */
  public abstract FieldElement sub(FieldElement a);

  public abstract FieldElement sub(Integer a);

  /**
   * Multiplication. Must not change neither this, neither parameter a. Must return result as a new
   * object.
   */
  public abstract FieldElement mul(FieldElement a);

  public abstract FieldElement mul(Integer a);

  /** Must return inverse object of this, such that this.mul(this.inv()).isOne() */
  public abstract FieldElement inv();

  /** Return zero element. */
  public abstract FieldElement getZero();

  public abstract boolean isZero();

  /** Return element one. */
  public abstract FieldElement getOne();

  public abstract boolean isOne();

  public abstract Integer getValue();
}
