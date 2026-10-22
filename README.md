# Účtenkovač

Konzolový nástroj v Javě, který vytiskne účtenku podobnou té, jakou dostanete v samoobsluze, supermarketu nebo trafice. Účtenku lze zadat ručně z příkazové řádky, uložit do CSV souboru a později zase načíst a vytisknout.

**Aktuální verze: 1.0.1** (sémantické verzování `MAJOR.MINOR.PATCH`, postup vydání nové verze viz [Jak vytvořit novou verzi](#jak-vytvořit-novou-verzi)).

Projekt vznikl jako týmové cvičení na VŠE.

## Autoři

- Martin Šilar
- Kristian Kalmar
- Dominik Wells
- Matej Eibner

Repozitář: <https://github.com/leukk/uctenkovac>

## Požadavky

- **JDK 17 nebo novější** (projekt je nastavený na Javu 17) – pro sestavení.
- **JRE 17 nebo novější** – stačí pro spuštění hotového JARu.
- **JUnit 5** (5.14.0) – pouze pro spuštění testů.

## Spuštění

Hotový program se spouští bez vývojového prostředí, přímo z příkazové řádky:

```
java -jar uctenkovac-1.0.1.jar
```

Program vypíše svůj název a verzi a čeká na příkazy. Ovládá se interaktivně, jeden příkaz na řádek:

| Příkaz         | Popis                                                                 |
|----------------|-----------------------------------------------------------------------|
| `create`       | Interaktivně zadá novou účtenku (údaje obchodu i položky nákupu).     |
| `load <soubor>`| Načte účtenku z CSV souboru.                                          |
| `save <soubor>`| Uloží aktuální účtenku do CSV souboru.                                |
| `print`        | Vytiskne aktuální účtenku.                                            |
| `exit`         | Ukončí program.                                                       |

Názvy příkazů nerozlišují velká a malá písmena. Příkazy `save` a `print` vyžadují, aby byla účtenka předtím vytvořena (`create`) nebo načtena (`load`).

### Příklad práce s programem

```
Uctenkovac v1.0.1
Zadejte prikaz (load, save, print, create, exit):
> load receipt.csv
Nacteno
> print
...
> exit
```

## Vstupní data

### Údaje, které se opakují (soubor)

Údaje o obchodu a samotné položky lze načíst ze souboru ve formátu CSV (`load`). Ukázkový soubor `receipt.csv` je v kořeni projektu.

Formát souboru:

- kódování UTF-8, oddělovač polí `;`,
- **první řádek** – hlavička účtenky: `číslo účtenky;název obchodu;adresa obchodu;měna`,
- **každý další řádek** – jedna položka: `název;počet kusů;cena za kus`,
- desetinná čárka se zapisuje jako **tečka** (`3.5`),
- hodnoty nesmí obsahovat znak `;` ani konec řádku,
- prázdné řádky se ignorují.

```
1;Albert;Vaclavske namesti 15, Praha 1;CZK
Rohlik;4;3.5
Mleko polotucne 1l;2;24.9
Maslo 250g;1;59.9
Chleb kmetsky;1;45.0
Banany;3;12.5
```

Stejný formát vytvoří příkaz `save`, takže si můžete jednou zadanou účtenku (nebo její šablonu) uložit a použít znovu.

### Údaje, které se mění podle nákupu (příkazová řádka)

Příkaz `create` se postupně zeptá na všechny údaje. Zadávání položek končí **prázdným názvem položky**. Žádné z polí kromě názvu položky nesmí zůstat prázdné.

```
> create
Id: 7
Nazev firmy: Potraviny Blanice
Adresa firmy: Hlavni 1, Praha
Mena: CZK
Zadejte polozky (prazdny nazev ukonci zadavani):
Nazev: Rohlik
Mnozstvi: 4
Cena: 3.5
Nazev: Mleko
Mnozstvi: 1
Cena: 24.9
Nazev:
Vytvoreno
```

Počet kusů musí být celé číslo, cena se zadává s desetinnou tečkou.

## Ukázka výstupu

Příkaz `print` vytiskne účtenku o pevné šířce 42 znaků. Dlouhé názvy se zkrátí a zakončí třemi tečkami, aby se nerozbil formát.

```
+----------------------------------------+
|                 Albert                 |
|     Vaclavske namesti 15, Praha 1      |
|               Receipt #1               |
|----------------------------------------|
| 4x Rohlik                        14.00 |
| 2x Mleko polotucne 1l            49.80 |
| 1x Maslo 250g                    59.90 |
| 1x Chleb kmetsky                 45.00 |
| 3x Banany                        37.50 |
|========================================|
| TOTAL                       206.20 CZK |
+----------------------------------------+
|               Thank you!               |
+----------------------------------------+
```

> **Poznámka:** Texty programu jsou záměrně bez diakritiky, aby se správně zobrazovaly v každé konzoli (zejména ve Windows).

## Struktura projektu

```
uctenkovac/
├── src/
│   ├── Main.java              vstupní bod, název a verze programu, zpracování příkazů
│   ├── CsvLoader.java         načítání a ukládání účtenky do CSV
│   ├── ReceiptCreator.java    interaktivní zadání nové účtenky
│   ├── ReceiptPrinter.java    formátovaný tisk účtenky
│   ├── model/
│   │   ├── Receipt.java       účtenka (hlavička, položky, celková cena)
│   │   ├── ReceiptItem.java   jedna položka účtenky
│   │   └── CommandType.java   výčet příkazů programu
│   └── tests/
│       ├── ReceiptTest.java       testy výpočtu celkové ceny
│       └── CommandTypeTest.java   testy rozpoznávání příkazů
├── receipt.csv                ukázková účtenka
└── README.md
```

## Sestavení

Projekt nepoužívá Maven, sestavuje se ručně pomocí `javac` a `jar`. Z kořenové složky projektu:

**macOS / Linux**

```
mkdir -p out
javac -encoding UTF-8 -d out src/*.java src/model/*.java
jar --create --file uctenkovac-1.0.1.jar --main-class Main -C out .
```

**Windows (cmd)**

```
mkdir out
javac -encoding UTF-8 -d out src\*.java src\model\*.java
jar --create --file uctenkovac-1.0.1.jar --main-class Main -C out .
```

Výsledný `uctenkovac-1.0.1.jar` jde spustit samostatně bez vývojového prostředí (viz [Spuštění](#spuštění)). Testy se do JARu nepřidávají.

## Testy

Unit testy jsou ve složce `src/tests` a používají JUnit 5. Pokrývají:

- výpočet celkové ceny účtenky (`Receipt.Total()`),
- rozpoznávání příkazů (`CommandType.fromString()`) včetně nezávislosti na velikosti písmen.

**V IntelliJ IDEA:** pravý klik na složku `src/tests` → *Run 'All Tests'*.

**Z příkazové řádky:** stáhněte [`junit-platform-console-standalone`](https://central.sonatype.com/artifact/org.junit.platform/junit-platform-console-standalone) do kořene projektu a spusťte (u ostatních verzí upravte název souboru):

```
javac -encoding UTF-8 -d out -cp junit-platform-console-standalone-1.14.0.jar src/*.java src/model/*.java src/tests/*.java
java -jar junit-platform-console-standalone-1.14.0.jar execute --class-path out --scan-class-path
```

## Jak vytvořit novou verzi

1. **Zvyšte číslo verze** v souboru `src/Main.java` (konstanta `VERSION`, např. `1.0.1` → `1.1.0`). Pravidla sémantického verzování:
   - `PATCH` (1.0.1 → 1.0.2): oprava chyby,
   - `MINOR` (1.0.1 → 1.1.0): nová funkce, která nic nerozbije,
   - `MAJOR` (1.0.1 → 2.0.0): nekompatibilní změna (např. nový formát CSV).
2. **Spusťte všechny testy** (viz [Testy](#testy)) – musí projít.
3. **Sestavte JAR** s novým číslem verze v názvu:
   ```
   rm -rf out && mkdir out
   javac -encoding UTF-8 -d out src/*.java src/model/*.java
   jar --create --file uctenkovac-1.1.0.jar --main-class Main -C out .
   ```
4. **Ověřte, že program běží:** `java -jar uctenkovac-1.1.0.jar` (zkuste `load receipt.csv` a `print`).
5. **Commitněte změny a označte verzi tagem:**
   ```
   git commit -am "Release 1.1.0"
   git tag v1.1.0
   git push && git push --tags
   ```
6. **Volitelně** nahrajte JAR do sekce *Releases* na GitHubu.
