# ZHAW-thin-turingmachine

## Inhaltsverzeichnis
<!-- TOC -->
* [Aufgabe 1](#aufgabe-1)
    * [Ausgabe](#ausgabe)
    * [Modi](#modi)
    * [Austesten](#austesten)
* [Aufgabe 2](#aufgabe-2)
<!-- TOC -->

## Aufgabe 1

Schreiben Sie (in einer beliebigen Programmiersprache) ein Programm, das eine Universelle
TM mit einem Band emuliert (Hinweis: Sie sollen keine **Universelle TM entwerfen**, nur einen Emulator dafür).

Das Programm soll die kodierte Funktion einer TM und eine Eingabe dazu einlesen und
verarbeiten. Als Kodierung verwenden Sie die in der Vorlesung eingeführte Codierung über
eine Binärzeichenreihe _(siehe Slides Teil-6: Universelle Turingmaschine (HO))_.

Die Eingabe (Programm und Eingabeparameter) kann als Binärzeichenreihe eingegeben oder
_(vgl. Teil-6: Universelle Turingmaschine (HO))_ oder aus einer Datei eingelesen werden. Es kann
auch ein Parser für die Eingabeparameter mit einer Dezimalzahl verwendet werden, der die
Dezimalzahl vor der Verarbeitung im Emulator in eine Binärzeichenreihe umwandelt.

### Ausgabe
Als Ausgabe (Bildschirm) wird folgendes erwartet:
* das korrekte Ergebnis, 
* die Angabe des aktuellen Zustandes der TM, 
* das Band mit mind. 15 Elementen vor und nach dem Lese-/Schreibkopf, 
* die aktuelle Position des Lese-/Schreibkopfes und 
* ein Zähler, der die Anzahl der bisher durchgeführten Berechnungsschritte angibt. 
* **Optional**: Graphische Ausgabe des Übergangsdiagramms im Step-Modus, Grafisches Interface, usw. Bitte nur angehen, wenn Sie wirklich Zeit dazu haben

### Modi
Es sind zwei Modi zu realisieren:
* ein „Step-Modus“: Ausgabe (b bis e) nach jedem einzelnen Berechnungsschritt 
* ein „Laufmodus“: einmalige Ausgabe nach Abschluss der gesamten Berechnung (alle Berechnungsschritte ohne Halt durchführen und dann das Ergebnis einmalig ausgeben)

### Austesten

Testen Sie Ihr Programm z. B. mit den bekannten Codierungen für Turing-Maschinen (aus der
Vorlesung):

* T1 = {010010001010011000101010010110001001001010011000100010001010}
* T2 = {1010010100100110101000101001100010010100100110001010010100}

## Aufgabe 2

Entwerfen Sie eine Turingmaschine Tquad für die Berechnung einer Quadratzahl (Eingabe unär
codiert). Kodieren Sie das Programm über eine Binärzeichenreihe und führen Sie mit dem in
Aufgabe 1 entwickelten Emulator Berechnungen durch (für kleine Zahlen).

### Präsentation
Im Unterricht in der Kalenderwoche 18 (bzw. wie mit Ihrem Dozenten vereinbart) präsentieren
Sie Ihre TM in maximal 5 Minuten (pro Gruppe): Zeigen Sie kurz auf,

* wie Sie die TM emulieren, 
* wie Sie die Berechung einer Quadratzahl umgesetzt haben und 
* berechnen Sie vier vorgegebene Quadratzahlen (z.B. für 2, für 10, für 11 und für 25); je nach Grösse der Zahlen im „Step-“ oder „Lauf-Modus“.

Hinweis: Ihr könnt https://universal-turing-machine.ch benutzen, um eure TM zu validieren.
Die Anwendung befindet sich noch in der Beta-Phase und die Korrektheit kann nicht garantiert
werden. Feedback zur App ist jederzeit willkommen.