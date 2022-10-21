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

import java.util.Iterator;
import java.util.TreeSet;

/** Cyclotomic coset class. */
public class Coset implements Comparable<Coset> {

  // index of the coset
  private int index;

  // coset values
  private TreeSet<Integer> values;

  /** Build cyclotomic coset when given index, modulus and highest degree. */
  public Coset(int index, int q, int n) {
    this.index = index % n;
    this.values = new TreeSet<Integer>();

    int newValue = this.index;
    while (!values.contains(newValue)) {
      values.add(newValue);
      newValue = (newValue * q) % n;
    }
  }

  /**
   * Needed for Comparable interface. Cosets will be stored in the TreeSet, so we need to know how
   * to order them.
   */
  public int compareTo(Coset c) {
    final int BEFORE = -1;
    final int EQUAL = 0;
    final int AFTER = 1;

    if (this == c) {
      return EQUAL;
    }

    // equal if values are the same
    if (this.values.equals(c.values)) {
      return EQUAL;
    }

    // if values differ, sort by first value in the set
    return this.values.first().compareTo(c.values.first());
  }

  /** Two cosets are equal if their indexes are equal. */
  public boolean equals(Object c) {
    if (this == c) {
      return true;
    }

    if (!(c instanceof Coset)) {
      return false;
    }

    Coset cc = (Coset) c;

    // equal if values are the same
    if (this.values.equals(cc.values)) {
      return true;
    }

    // by definition of coset, same indexes => same values
    assert this.index != cc.index : String.format("Coset.equals() problem %s %s", this, cc);

    return false;
  }

  public int hashCode() {
    return values.hashCode();
  }

  public TreeSet<Integer> getValues() {
    return values;
  }

  public Iterator<Integer> getValuesIterator() {
    return values.iterator();
  }

  public int getIndex() {
    return index;
  }

  public String toString() {
    return String.format("Coset[index: %d, values: %s]", index, values);
  }
}
