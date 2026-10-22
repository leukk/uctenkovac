Účtenkovač
Konzolový nástroj, který vytiskne účtenku, jakou dostanete v samoobsluze, supermarketu nebo trafice.

Aktuální verze: 1.0.1 (sémantické verzování MAJOR.MINOR.PATCH, viz Jak vytvořit novou verzi).

Projekt vznikl jako týmové cvičení na VŠE.

Autoři:
Martin Šilar,
Kristian Kalmar,
Dominik Wells,
Matej Eibner,

Repozitář: https://github.com/leukk/uctenkovac

Požadavky
JDK 17 nebo novější (projekt je nastavený na Javu 17)
JUnit 5 (5.14.0) pro spuštění testů
Spuštění
java -jar uctenkovac-1.0.1.jar data/company.csv
Program po startu vypíše svůj název a verzi, načte údaje obchodu ze zadaného CSV souboru, zeptá se na položky nákupu a nakonec vytiskne účtenku.

Vstupní data
Údaje obchodu (CSV soubor)
Údaje, které se na účtence opakují (název obchodu, adresa, měna), jsou v souboru data/company.csv. Formát: kódování UTF-8, oddělovač ;, první řádek je hlavička, druhý řádek obsahuje data.

name;address;currency
Potraviny Blanice;Hlavní 1, Praha;CZK
Údaje o nákupu (příkazová řádka)
Položky nákupu zadává uživatel za běhu programu. Zadávání končí prázdným názvem položky.

Název položky (prázdný řádek = konec): Rohlík
Počet kusů: 4
Cena za kus: 3.50
Název položky (prázdný řádek = konec):
Ukázka výstupu
====================================
Potraviny Blanice
Hlavní 1, Praha
====================================
Účtenka č. 1
------------------------------------
Rohlík         4 x    3,50     14,00
Mléko 1 l      1 x   24,90     24,90
Máslo          2 x   49,90     99,80
------------------------------------
CELKEM                    138,70 CZK
====================================

Struktura projektu
uctenkovac/
├── data/
│   └── company.csv           údaje obchodu (vzorový soubor)
├── src/
│   ├── Main.java             vstupní bod, název a verze programu
│   ├── CsvLoader.java        načítání údajů obchodu ze souboru
│   ├── model/
│   │   ├── Receipt.java      účtenka (hlavička, položky, celková cena)
│   │   └── ReceiptItem.java  jedna položka účtenky
│   └── tests/
│       └── ReceiptTest.java  unit testy
└── README.md

Sestavení
Z kořenové složky projektu:

mkdir out
javac -d out src/*.java src/model/*.java
jar --create --file uctenkovac-1.0.1.jar --main-class Main -C out .
Výsledný uctenkovac-1.0.1.jar jde spustit samostatně bez vývojového prostředí (viz Spuštění). Testy se do JARu nepřidávají.

Testy
Aktuálně testy pokrývají výpočet celkové ceny účtenky (Receipt.Total()).

V IntelliJ IDEA: pravý klik na složku src/tests → Run 'All Tests'.

Z příkazové řádky: stáhněte junit-platform-console-standalone-1.14.0.jar do kořene projektu a spusťte:

javac -d out -cp junit-platform-console-standalone-1.14.0.jar src/*.java src/model/*.java src/tests/*.java
java -jar junit-platform-console-standalone-1.14.0.jar execute --class-path out --scan-class-path
Jak vytvořit novou verzi
V souboru src/Main.java změňte konstantu VERSION (např. 1.0.1 → 1.1.0). Pravidla sémantického verzování:
PATCH (1.0.1 → 1.0.2): oprava chyby,
MINOR (1.0.1 → 1.1.0): nová funkce, která nic nerozbije,
MAJOR (1.0.1 → 2.0.0): nekompatibilní změna (např. nový formát CSV).
Spusťte všechny testy (viz Testy), musí projít.
Sestavte JAR s novým číslem verze v názvu:
rm -rf out && mkdir out
javac -d out src/*.java src/model/*.java
jar --create --file uctenkovac-1.1.0.jar --main-class Main -C out .
Ověřte, že program běží: java -jar uctenkovac-1.1.0.jar data/company.csv.
Změny commitněte a označte verzi tagem:
git commit -am "Release 1.1.0"
git tag v1.1.0
git push && git push --tags

Volitelně nahrajte JAR do sekce Releases na GitHubu.
