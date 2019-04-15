# Plolinomų Faktorizacija [![maven-central-badge][]][maven-central] [![travis-badge][]][travis]

[maven-central]:         https://search.maven.org/#search%7Cga%7C1%7Cpolyfact
[maven-central-badge]:   https://maven-badges.herokuapp.com/maven-central/lt.dvim.polyfact/polyfact/badge.svg
[travis]:                https://travis-ci.com/2m/polyfact
[travis-badge]:          https://travis-ci.com/2m/polyfact.svg?branch=master

## Programos aprašymas

Ši programa faktorizuoja polinomus pavidalo f(x) = x<sup>n</sup> - 1 virš kūno F<sub>q</sub>.
Taigi programoje reikia įvesti **n** ir **q** (turi būti pirminis) parametrus.
Tada programa išves visus polinomo f(x) pirminius daugiklius kartu su tarpniais rezultatais.

## Programos kompiliavimas ir paleidimas

Programos kompiliavimui ir paleidimui yra naudojamas [sbt](https://www.scala-sbt.org/) įrankis. Naudingos sbt komandos:

* **sbt package**

  Sukompiliuos išeities tekstus ir sukurs paleidimo failą: `target/polyfact-<...>.jar`.
  Programą galima paleisti du kartus spustelėjus ant sukurto failo.

* **sbt run**

  Paleidžia programą, kartu su konsolės langu.

* **sbt test**

  Sukompiliuos programą, sukurs paleidimo failą ir paleis programą testavimo režime.
  Visi testai yra aprašyti [`PolyFactTest.java`](src/test/java/PolyFactTest.java) faile.

## Programos naudojimas kituose projektuose

Programa yra patalpinta Maven Central repozitorijoje iš kurios kiti projektai gali šitą programą naudoti kaip biblioteką. Taip pat naudojant [coursier](https://get-coursier.io/) galima šią programą paleisti tiesiai iš repositorijos naudojant šią komandą:

    coursier launch lt.dvim.polyfact:polyfact:1.0

## Programos testavimas.

[`PolyFactTest.java`](src/test/java/PolyFactTest.java) faile yra aprašyta daug predikatų, kurie tikrina ar teisingai veikia aritmetikos operacijos bei faktorizavimas.
Predikatas atlieka tam tikrus veiksmus, ir patikrina rezultatą su žinomu rezultatu.
Visi aprašyti predikatai turi grąžinti reikšmę `true`. Jeigu bent vienas grąžina `false`, tai testavimo programa yra stabdoma.
Išspausdintas pranešimas nurodo, kuri operacija grąžino kitokį rezultatą negu tikėtasi.

Testavimo programa bando įvarias aritmetines operacijas (sudėtis, atimtis, daugyba, dalyba, dbd), bei faktorizavimo algoritmą.
Štai tokie faktorizavimo algoritmo parametrai yra išbandomi.

  * **q** = 2, **n** = 7, 9, 15, 17
  * **q** = 3, **n** = 4, 8, 10, 11, 13
  * **q** = 5, **n** = 8, 13

Atsakymai naudojant šiuos pavyzdžius yra tikrinami su atsakymais pateiktais [čia](http://uosis.mif.vu.lt/~skersys/10r/ktkt/uzduotys/gen_pol_pvz.htm).

## Naudota literatūra.

Pats faktorizavimo algoritmas yra paimtas iš:

*Scott A. Vanstone, Paul C. Van Oorschot*. **An introduction to error correcting codes with applications**, 1989, psl [187-192](http://uosis.mif.vu.lt/~skersys/doc/ktkt/literatura24.pdf).

Apie baigtinius kūnus bei ciklinius kodus skaičiau:

*Gintaras Skersys*. **Klaidas taisančių kodų teorija**, 2008. Galima rasti [čia](http://uosis.mif.vu.lt/~skersys/10r/ktkt/pagr.htm).
