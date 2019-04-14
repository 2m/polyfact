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

import org.junit.jupiter.api.Test;

class PolyFactTest {

  @Test
  void testArithmetic() {
    FiniteFieldElement.MOD = 3;

    assert new Polynomial(FiniteFieldElement.class, "2021")
            .mul(new Polynomial(FiniteFieldElement.class, "102111"))
            .equals(new Polynomial(FiniteFieldElement.class, "200000001"))
        : "Multiplication test. MOD = 3";

    assert new Polynomial(FiniteFieldElement.class, "200000001")
            .div(new Polynomial(FiniteFieldElement.class, "2021"))
            .equals(new Polynomial(FiniteFieldElement.class, "102111"))
        : "Division test. MOD = 3";

    assert new Polynomial(FiniteFieldElement.class, "200000001")
            .rem(new Polynomial(FiniteFieldElement.class, "2021"))
            .equals(new Polynomial(FiniteFieldElement.class, "0"))
        : "Remainder test. MOD = 3";

    FiniteFieldElement.MOD = 2;

    assert new Polynomial(FiniteFieldElement.class, "101")
            .mul(new Polynomial(FiniteFieldElement.class, "1101"))
            .equals(new Polynomial(FiniteFieldElement.class, "111001"))
        : "Multiplication test. MOD = 2";

    assert new Polynomial(FiniteFieldElement.class, "101")
            .mul(new Polynomial(FiniteFieldElement.class, "1"))
            .equals(new Polynomial(FiniteFieldElement.class, "101"))
        : "Multiplication by 1 test. MOD = 2";

    assert new Polynomial(FiniteFieldElement.class, "101")
            .mul(new Polynomial(FiniteFieldElement.class, "0"))
            .equals(new Polynomial(FiniteFieldElement.class, "0"))
        : "Multiplication by 0 test. MOD = 2";

    assert new Polynomial(FiniteFieldElement.class, "101101101")
            .rem(new Polynomial(FiniteFieldElement.class, "100001"))
            .equals(new Polynomial(FiniteFieldElement.class, "011"))
        : "Remainder test. MOD = 2";

    assert new Polynomial(FiniteFieldElement.class, "101101101")
            .rem(new Polynomial(FiniteFieldElement.class, "101101101"))
            .equals(new Polynomial(FiniteFieldElement.class, "0"))
        : "Remainder by self test. MOD = 2";

    assert new Polynomial(FiniteFieldElement.class, "101101101")
            .rem(new Polynomial(FiniteFieldElement.class, "1"))
            .equals(new Polynomial(FiniteFieldElement.class, "0"))
        : "Remainder by 1 test. MOD = 2";

    assert new Polynomial(FiniteFieldElement.class, "0101101")
            .div(new Polynomial(FiniteFieldElement.class, "1"))
            .equals(new Polynomial(FiniteFieldElement.class, "0101101"))
        : "Division by 1 test. MOD = 2";

    try {
      assert new Polynomial(FiniteFieldElement.class, "0101101")
              .div(new Polynomial(FiniteFieldElement.class, "0"))
              .equals(null)
          : "Division by 0 test. MOD = 2";
    } catch (ArithmeticException ex) {
    }

    assert new Polynomial(FiniteFieldElement.class, "0101101")
            .div(new Polynomial(FiniteFieldElement.class, "0101101"))
            .equals(new Polynomial(FiniteFieldElement.class, "1"))
        : "Division by self test. MOD = 2";

    assert new Polynomial(FiniteFieldElement.class, "10000001")
            .div(new Polynomial(FiniteFieldElement.class, "1111111"))
            .equals(new Polynomial(FiniteFieldElement.class, "11"))
        : "Division test. MOD = 2";

    assert new Polynomial(FiniteFieldElement.class, "1000000000000001")
            .gcd(new Polynomial(FiniteFieldElement.class, "011010001"))
            .equals(new Polynomial(FiniteFieldElement.class, "11010001"))
        : "GCD test #1. MOD = 2";

    assert new Polynomial(FiniteFieldElement.class, "1000000000000001")
            .gcd(
                new Polynomial(FiniteFieldElement.class, "011010001")
                    .sub(new Polynomial(FiniteFieldElement.class, "1")))
            .equals(new Polynomial(FiniteFieldElement.class, "111010001"))
        : "GCD test #2. MOD = 2";

    assert new Polynomial(FiniteFieldElement.class, "11010001")
            .gcd(new Polynomial(FiniteFieldElement.class, "0001001001001"))
            .equals(new Polynomial(FiniteFieldElement.class, "1001"))
        : "GCD test #3. MOD = 2";

    assert new Polynomial(FiniteFieldElement.class, "11010001")
            .gcd(
                new Polynomial(FiniteFieldElement.class, "0001001001001")
                    .sub(new Polynomial(FiniteFieldElement.class, "1")))
            .equals(new Polynomial(FiniteFieldElement.class, "11001"))
        : "GCD test #4. MOD = 2";

    assert new Polynomial(FiniteFieldElement.class, "111010001")
            .gcd(new Polynomial(FiniteFieldElement.class, "0001001001001"))
            .equals(new Polynomial(FiniteFieldElement.class, "1"))
        : "GCD test #5. MOD = 2";

    assert new Polynomial(FiniteFieldElement.class, "111010001")
            .gcd(
                new Polynomial(FiniteFieldElement.class, "0001001001001")
                    .sub(new Polynomial(FiniteFieldElement.class, "1")))
            .equals(new Polynomial(FiniteFieldElement.class, "111010001"))
        : "GCD test #6. MOD = 2";

    assert new Polynomial(FiniteFieldElement.class, "111010001")
            .gcd(new Polynomial(FiniteFieldElement.class, "000000010001011"))
            .equals(new Polynomial(FiniteFieldElement.class, "10011"))
        : "GCD test #7. MOD = 2";

    assert new Polynomial(FiniteFieldElement.class, "111010001")
            .gcd(
                new Polynomial(FiniteFieldElement.class, "000000010001011")
                    .sub(new Polynomial(FiniteFieldElement.class, "1")))
            .equals(new Polynomial(FiniteFieldElement.class, "11111"))
        : "GCD test #8. MOD = 2";

    assert new Polynomial(FiniteFieldElement.class, "11")
            .mul(
                new Polynomial(FiniteFieldElement.class, "111")
                    .mul(
                        new Polynomial(FiniteFieldElement.class, "10011")
                            .mul(
                                new Polynomial(FiniteFieldElement.class, "11001")
                                    .mul(new Polynomial(FiniteFieldElement.class, "11111")))))
            .equals(new Polynomial(FiniteFieldElement.class, "1000000000000001"))
        : "Multiplication by many test. MOD = 2";

    FiniteFieldElement.MOD = 5;

    assert new Polynomial(FiniteFieldElement.class, "11")
            .mul(
                new Polynomial(FiniteFieldElement.class, "21")
                    .mul(
                        new Polynomial(FiniteFieldElement.class, "31")
                            .mul(
                                new Polynomial(FiniteFieldElement.class, "41")
                                    .mul(
                                        new Polynomial(FiniteFieldElement.class, "201")
                                            .mul(
                                                new Polynomial(FiniteFieldElement.class, "301"))))))
            .equals(new Polynomial(FiniteFieldElement.class, "400000001"))
        : "Multiplication by many test. MOD = 5";
  }

  @Test
  void testFactorization() {
    FiniteFieldElement.MOD = 2;

    Factorization factorization = new Factorization(7, 2);
    factorization.constructCosets();
    factorization.construcyGBasis();
    factorization.factorize();
    assert factorization.getFactors().size() == 3 : "Fact. n = 7, q = 2, wrong num of factors";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "11"))
        : "Fact. n = 7, q = 2, factor #1.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "1101"))
        : "Fact. n = 7, q = 2, factor #2.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "1011"))
        : "Fact. n = 7, q = 2, factor #3.";

    factorization = new Factorization(9, 2);
    factorization.constructCosets();
    factorization.construcyGBasis();
    factorization.factorize();
    assert factorization.getFactors().size() == 3 : "Fact. n = 9, q = 2, wrong num of factors";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "11"))
        : "Fact. n = 9, q = 2, factor #1.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "111"))
        : "Fact. n = 9, q = 2, factor #2.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "1001001"))
        : "Fact. n = 9, q = 2, factor #3.";

    factorization = new Factorization(15, 2);
    factorization.constructCosets();
    factorization.construcyGBasis();
    factorization.factorize();
    assert factorization.getFactors().size() == 5 : "Fact. n = 15, q = 2, wrong num of factors";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "11"))
        : "Fact. n = 15, q = 2, factor #1.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "111"))
        : "Fact. n = 15, q = 2, factor #2.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "11001"))
        : "Fact. n = 15, q = 2, factor #3.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "10011"))
        : "Fact. n = 15, q = 2, factor #4.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "11111"))
        : "Fact. n = 15, q = 2, factor #5.";

    factorization = new Factorization(17, 2);
    factorization.constructCosets();
    factorization.construcyGBasis();
    factorization.factorize();
    assert factorization.getFactors().size() == 3 : "Fact. n = 17, q = 2, wrong num of factors";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "11"))
        : "Fact. n = 17, q = 2, factor #1.";
    assert factorization
            .getFactors()
            .contains(new Polynomial(FiniteFieldElement.class, "100111001"))
        : "Fact. n = 17, q = 2, factor #2.";
    assert factorization
            .getFactors()
            .contains(new Polynomial(FiniteFieldElement.class, "111010111"))
        : "Fact. n = 17, q = 2, factor #3.";

    FiniteFieldElement.MOD = 3;

    factorization = new Factorization(4, 3);
    factorization.constructCosets();
    factorization.construcyGBasis();
    factorization.factorize();
    assert factorization.getFactors().size() == 3 : "Fact. n = 4, q = 3, wrong num of factors";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "21"))
        : "Fact. n = 4 q = 3, factor #1.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "11"))
        : "Fact. n = 4, q = 3, factor #2.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "101"))
        : "Fact. n = 4, q = 3, factor #3.";

    factorization = new Factorization(8, 3);
    factorization.constructCosets();
    factorization.construcyGBasis();
    factorization.factorize();
    assert factorization.getFactors().size() == 5 : "Fact. n = 8, q = 3, wrong num of factors";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "21"))
        : "Fact. n = 8 q = 3, factor #1.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "11"))
        : "Fact. n = 8, q = 3, factor #2.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "101"))
        : "Fact. n = 8, q = 3, factor #3.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "211"))
        : "Fact. n = 8, q = 3, factor #4.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "221"))
        : "Fact. n = 8, q = 3, factor #5.";

    factorization = new Factorization(10, 3);
    factorization.constructCosets();
    factorization.construcyGBasis();
    factorization.factorize();
    assert factorization.getFactors().size() == 4 : "Fact. n = 10, q = 3, wrong num of factors";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "21"))
        : "Fact. n = 10 q = 3, factor #1.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "11"))
        : "Fact. n = 10, q = 3, factor #2.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "11111"))
        : "Fact. n = 10, q = 3, factor #3.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "12121"))
        : "Fact. n = 10, q = 3, factor #4.";

    factorization = new Factorization(11, 3);
    factorization.constructCosets();
    factorization.construcyGBasis();
    factorization.factorize();
    assert factorization.getFactors().size() == 3 : "Fact. n = 11, q = 3, wrong num of factors";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "21"))
        : "Fact. n = 11 q = 3, factor #1.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "221201"))
        : "Fact. n = 11, q = 3, factor #2.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "201211"))
        : "Fact. n = 11, q = 3, factor #3.";

    factorization = new Factorization(13, 3);
    factorization.constructCosets();
    factorization.construcyGBasis();
    factorization.factorize();
    assert factorization.getFactors().size() == 5 : "Fact. n = 13, q = 3, wrong num of factors";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "21"))
        : "Fact. n = 13 q = 3, factor #1.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "2201"))
        : "Fact. n = 13, q = 3, factor #2.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "2011"))
        : "Fact. n = 13, q = 3, factor #3.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "2111"))
        : "Fact. n = 13, q = 3, factor #4.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "2221"))
        : "Fact. n = 13, q = 3, factor #5.";

    FiniteFieldElement.MOD = 5;

    factorization = new Factorization(8, 5);
    factorization.constructCosets();
    factorization.construcyGBasis();
    factorization.factorize();
    assert factorization.getFactors().size() == 6 : "Fact. n = 8, q = 5, wrong num of factors";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "11"))
        : "Fact. n = 8 q = 5, factor #1.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "21"))
        : "Fact. n = 8, q = 5, factor #2.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "31"))
        : "Fact. n = 8, q = 5, factor #3.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "41"))
        : "Fact. n = 8, q = 5, factor #4.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "201"))
        : "Fact. n = 8, q = 5, factor #5.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "301"))
        : "Fact. n = 8, q = 5, factor #6.";

    factorization = new Factorization(13, 5);
    factorization.constructCosets();
    factorization.construcyGBasis();
    factorization.factorize();
    assert factorization.getFactors().size() == 4 : "Fact. n = 13, q = 5, wrong num of factors";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "41"))
        : "Fact. n = 13 q = 5, factor #1.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "11411"))
        : "Fact. n = 13, q = 5, factor #2.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "12121"))
        : "Fact. n = 13, q = 5, factor #3.";
    assert factorization.getFactors().contains(new Polynomial(FiniteFieldElement.class, "13031"))
        : "Fact. n = 13, q = 5, factor #4.";
  }
}
