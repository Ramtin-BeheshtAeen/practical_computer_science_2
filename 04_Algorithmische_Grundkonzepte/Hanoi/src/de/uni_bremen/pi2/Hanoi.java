package de.uni_bremen.pi2;

import java.util.Stack;

/**
 * Zwei Implementierungen der Türme von Hanoi.
 * @author Thomas Röfer
 */
public class Hanoi
{
    /** Die Anzahl der Scheiben. */
    private final int anzahlScheiben;

    /**
     * Die Stapel der drei Säulen. Jede gespeicherte Zahl
     * repräsentiert eine Scheibe einer bestimmten Größe.
     */
    @SuppressWarnings("unchecked")
    private final Stack<Integer>[] stapel = new Stack[3];

    /**
     * Initialisiert die drei Stapel. Alle Scheiben sind zu
     * Beginn auf dem rechten Stapel.
     * @param anzahlScheiben Die Anzahl der Scheiben.
     */
    public Hanoi(final int anzahlScheiben)
    {
        assert anzahlScheiben > 0;
        this.anzahlScheiben = anzahlScheiben;

        // Die drei Stapel anlegen.
        for (int i = 0; i < stapel.length; ++i) {
            stapel[i] = new Stack<>();
        }

        // Den rechten Stapel füllen, von groß nach klein.
        for (int i = anzahlScheiben; i > 0; --i) {
            stapel[2].add(i);
        }
    }

    /**
     * Greedy-Implementierung der Türme von Hanoi. Bewegt den
     * Stapel von rechts zur Mitte.
     * Achtung: Kann nur unmittelbar nach der Konstruktion
     * des Objekts aufgerufen werden.
     */
    public void gierig()
    {
        anzeigen();

        // Abhängig von der Anzahl der Scheiben (gerade/ungerade) muss die
        // kleinste Scheibe immer nach rechts oder links bewegt werden.
        final int schritt = anzahlScheiben % 2 == 0 ? 1 : 2;

        // Die kleinste Scheibe befindet sich auf dem rechten Stapel.
        int k = 2;

        while (true) {
            // Kleinste Scheibe verschieben.
            final int i = k;
            k = (k + schritt) % stapel.length;
            stapel[k].push(stapel[i].pop());
            anzeigen();

            // Fertig, wenn kleinste Scheibe wieder auf vollem Stapel.
            if (stapel[k].size() == anzahlScheiben) {
                break;
            }

            // Ansonsten kleinere Scheibe zwischen anderen beiden Stapeln bewegen.
            final int j = 3 - k - i;
            if (stapel[i].empty() || (!stapel[j].empty() && stapel[j].peek() < stapel[i].peek())) {
                stapel[i].push(stapel[j].pop());
            }
            else {
                stapel[j].push(stapel[i].pop());
            }

            anzeigen();
        }
    }

    /**
     * Divide-and-Conquer-Implementierung der Türme von Hanoi.
     * Bewegt den Stapel von rechts zur Mitte.
     * Achtung: Kann nur unmittelbar nach der Konstruktion
     * des Objekts aufgerufen werden.
     */
    public void teileUndHerrsche()
    {
        anzeigen();
        teileUndHerrsche(2, 1, anzahlScheiben);
    }

    /**
     * Bewegen der obersten "höhe" Scheiben von einem Stapel zu einem anderen.
     * @param i Der Index des Stapels, von dem die Scheiben entnommen werden.
     * @param j Der Index des Stapels, auf den die Scheiben gelegt werden.
     * @param höhe Die Anzahl der oberen Scheiben, die bewegt werden.
     */
    private void teileUndHerrsche(final int i, final int j, final int höhe)
    {
        // Um etwas zu verschieben, muss ein Stapel mindestens Höhe 1 haben.
        if (höhe > 0) {
            // Die höhe-1 Scheiben auf einen Ausweichplatz bewegen.
            final int t = 3 - i - j;
            teileUndHerrsche(i, t, höhe - 1);

            // Die nun oberste Scheibe verschieben
            stapel[j].push(stapel[i].pop());
            anzeigen();

            // Die Scheiben vom Ausweichplatz zum Ziel bewegen.
            teileUndHerrsche(t, j, höhe - 1);
        }
    }

    /**
     * Anzeigen der Stapel.
     */
    private void anzeigen()
    {
        // Stangen und Scheiben darstellen.
        for (int y = anzahlScheiben; y >= 0; --y) {
            for (final var s : stapel) {
                if (y < s.size()) {
                    final int scheibe = s.elementAt(y);
                    System.out.print(" ".repeat(anzahlScheiben - scheibe) + "=".repeat(2 * scheibe - 1)
                            + " ".repeat(anzahlScheiben - scheibe + 1));
                }
                else {
                    System.out.print(" ".repeat(anzahlScheiben - 1) + "|" + " ".repeat(anzahlScheiben));
                }
            }
            System.out.println();
        }

        // Sockel darstellen.
        for (final var s : stapel) {
            System.out.print("-".repeat(anzahlScheiben * 2 - 1) + " ");
        }
        System.out.println("\n");
    }
}
