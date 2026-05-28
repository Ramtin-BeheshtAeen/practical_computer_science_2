package de.uni_bremen.pi2;

/**
 * Implementierung verschiedener Sortierverfahren.
 * @author Thomas Röfer
 */
public class Sort
{
    /**
     * Erzeugt die nächste Permutation einer Folge nach dem Algorithmus von
     * Dijkstra.
     * @param a Die Folge, die permutiert wird.
     * @param <T> Der Typ der Elemente der Folge.
     */
    public static <T extends Comparable<T>> void nextPerm(final T[] a)
    {
        int i, j;

        // Finde das letzte Element, das kleiner als sein Nachfolger ist.
        for (i = a.length - 2; i >= 0 && a[i].compareTo(a[i + 1]) >= 0; --i);
        if (i >= 0) {
            // Finde das letzte Element, das größer als a[i] ist.
            for (j = a.length - 1; a[j].compareTo(a[i]) <= 0; --j);

            // Beide austauschen.
            swap(a, i, j);
        }

        // Elemente ab a[i + 1] umdrehen.
        reverse(a, i + 1, a.length - 1);
    }

    /**
     * Invertiert die Reihenfolge aller Elemente in einem Teilbereich eines
     * Arrays.
     * @param a Das Array, das verändert wird.
     * @param l Die Untergrenze des Indexbereichs des Arrays, der invertiert
     *          wird.
     * @param r Die Obergrenze des Indexbereichs des Arrays, der invertiert
     *          wird.
     */
    private static void reverse(final Object[] a, int l, int r)
    {
        while (l < r) {
            swap(a, l++, r--);
        }
    }

    /**
     * Tauscht zwei Elemente eines Arrays aus.
     * @param a Das Array, in dem die beiden Elemente getauscht werden.
     * @param i Der Index des einen Elements.
     * @param j Der Index des anderen Elements.
     */
    private static void swap(final Object[] a, final int i, final int j)
    {
        final Object temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    /**
     * Naives Sortieren. Die Elemente des Arrays werden so lange permutiert,
     * bis sie nach ihrer natürlichen Ordnung aufsteigend sortiert sind.
     * @param a Das Array, das sortiert wird.
     * @param <T> Der Typ der Elemente des Arrays.
     */
    public static <T extends Comparable<T>> void naiveSort(final T[] a)
    {
        while (!sorted(a)) {
            nextPerm(a);
        }
    }

    /**
     * Testet, ob ein Array nach der natürlichen Ordnung seiner Elemente
     * aufsteigend sortiert ist.
     * @param a Das Array, dessen Ordnung getestet wird.
     * @param <T> Der Typ der Elemente des Arrays.
     * @return Ist das Array sortiert?
     */
    private static <T extends Comparable<T>> boolean sorted(final T[] a)
    {
        int i;
        for (i = 1; i < a.length && a[i - 1].compareTo(a[i]) <= 0; ++i);
        return i >= a.length;
    }

    /**
     * Sortieren durch Einfügen entsprechend der natürlichen Ordnung der
     * Elemente eines Arrays.
     * @param a Das Array, das sortiert wird.
     * @param <T> Der Typ der Elemente des Arrays.
     */
    public static <T extends Comparable<T>> void insertionSort(final T[] a)
    {
        for (int i = 1; i < a.length; ++i) {
            // Einzufügendes Element sichern.
            final T value = a[i];

            // Rückwärts nach Einfügestelle suchen und dabei Platz schaffen.
            int j;
            for (j = i; j > 0 && a[j - 1].compareTo(value) > 0; --j) {
                a[j] = a[j - 1];
            }

            // Gesichertes Element an Einfügestelle schreiben.
            a[j] = value;
        }
    }

    /**
     * Sortieren durch Auswählen entsprechend der natürlichen Ordnung der
     * Elemente eines Arrays.
     * @param a Das Array, das sortiert wird.
     * @param <T> Der Typ der Elemente des Arrays.
     */
    public static <T extends Comparable<T>> void selectionSort(final T[] a)
    {
        // Für alle Positionen im Array (außer der letzten).
        for (int i = 0; i < a.length - 1; ++i) {
            // Tausche das kleinste Element des Restarrays an diese Stelle.
            for (int j = i + 1; j < a.length; ++j) {
                if (a[i].compareTo(a[j]) > 0) {
                    swap(a, i, j);
                }
            }
        }
    }

    /**
     * BubbleSort entsprechend der natürlichen Ordnung der Elemente eines
     * Arrays.
     * @param a Das Array, das sortiert wird.
     * @param <T> Der Typ der Elemente des Arrays.
     */
    public static <T extends Comparable<T>> void bubbleSort(final T[] a)
    {
        boolean swapped = true;

        // Laufe rückwärts durch das Array. Alles oberhalb von i ist bereits
        // sortiert. Der Bereich bis i ist auch sortiert, wenn im vorherigen
        // Durchgang nicht mehr getauscht wurde (→ Abbruch).
        for (int i = a.length - 1; i > 0 && swapped; --i) {
            // In diesem Durchlauf wurde noch nicht getauscht.
            swapped = false;

            // Laufe bis zur Obergrenze und tausche jeweils aufeinander
            // folgende Elemente, wenn sie falsch angeordnet sind.
            for (int j = 0; j < i; ++j) {
                if (a[j].compareTo(a[j + 1]) > 0) {
                    swap(a, j + 1, j);
                    swapped = true;
                }
            }
        }
    }

    /**
     * Sortieren durch Mischen entsprechend der natürlichen Ordnung der
     * Elemente in einem Array.
     * @param a Das Array, das sortiert wird.
     * @param <T> Der Typ der Elemente des Arrays.
     */
    public static <T extends Comparable<T>> void mergeSort(final T[] a)
    {
        // Zwischenspeicher anlegen.
        @SuppressWarnings("unchecked")
        final T[] temp = (T[]) new Comparable[a.length];

        // Sortiere ganzes Array.
        mergeSort(a, 0, a.length, temp);
    }

    /**
     * Sortieren durch Mischen eines Teilbereichs eines Arrays.
     * @param a Das Array, in dem sortiert wird.
     * @param bottom Die Untergrenze des Teilbereichs (inklusiv).
     * @param top Die Obergrenze des Teilbereichs (exklusiv).
     * @param temp Ein Zwischenspeicher, der mindestens top - bottom
     *         Elemente groß ist.
     * @param <T> Der Typ der Elemente des Arrays.
     */
    private static <T extends Comparable<T>> void mergeSort(final T[] a,
            final int bottom, final int top, final T[] temp)
    {
        if (top - bottom > 1) {
            final int middle = bottom + (top - bottom) / 2;
            mergeSort(a, bottom, middle, temp);
            mergeSort(a, middle, top, temp);
            merge(a, bottom, middle, top, temp);
        }
    }

    /**
     * Mischen zweier aufeinander folgender, bereits sortierter
     * Teilbereiche eines Arrays zu einem gemeinsamen, sortierten Bereich.
     * @param a Das Array, in dem sortiert wird.
     * @param bottom Die Untergrenze des ersten Teilbereichs (inklusiv).
     * @param middle Die Obergrenze des ersten Teilbereichs (exklusiv) und
     *         gleichzeitig die Untergrenze des zweiten Teilbereichs
     *         (inklusiv).
     * @param top Die Obergrenze des zweiten Teilbereichs (exklusiv).
     * @param temp Ein Zwischenspeicher, der mindestens top - bottom
     *         Elemente groß ist.
     * @param <T> Der Typ der Elemente des Arrays.
     */
    private static <T extends Comparable<T>> void merge(final T[] a,
            final int bottom, final int middle, final int top, final T[] temp)
    {
        // i durchläuft den ersten Teilbereich, j den zweiten und k den
        // zusammengemischten Bereich.
        int i = bottom;
        int j = middle;
        int k = 0;

        // Solange beide Teilarrays noch nicht leer, kopiere kleineres Element.
        while (i < middle && j < top) {
            temp[k++] = a[i].compareTo(a[j]) <= 0 ? a[i++] : a[j++];
        }

        // Reste kopieren. Eines der Teilarrays ist leer, weshalb nur ein
        // Kopiervorgang tatsächlich ausgeführt wird.
        System.arraycopy(a, i, temp, k, middle - i);
        System.arraycopy(a, j, temp, k, top - j);

        // Zurück in Originalarray kopieren.
        System.arraycopy(temp, 0, a, bottom, top - bottom);
    }

    /**
     * QuickSort entsprechend der natürlichen Ordnung der Elemente eines
     * Arrays.
     * @param a Das Array, das sortiert wird.
     * @param <T> Der Typ der Elemente des Arrays.
     */
    public static <T extends Comparable<T>> void quickSort(final T[] a)
    {
        // Das ganze Array sortieren.
        quickSort(a, 0, a.length);
    }

    /**
     * QuickSort eines Teilbereichs eines Arrays entsprechend der
     * natürlichen Ordnung der Elemente des Arrays.
     * @param a Das Array, das sortiert wird.
     * @param bottom Die Untergrenze des zu sortierenden Teilbereichs (inklusiv)
     * @param top Die Obergrenze des zu sortierenden Teilbereichs (exklusiv)
     * @param <T> Der Typ der Elemente des Arrays.
     */
    private static <T extends Comparable<T>> void quickSort(
            final T[] a, final int bottom, final int top)
    {
        if (bottom + 1 < top) {
            final T pivot = a[bottom];
            int i = bottom + 1;
            int j = top - 1;
            while (true) {
                // Von unten Element suchen, das größer-gleich Pivot.
                for (; i < top && a[i].compareTo(pivot) < 0; ++i);

                // Von oben Element suchen, das kleiner als Pivot.
                for (; j > bottom && a[j].compareTo(pivot) >= 0; --j);

                // Fertig, wenn Indizes aneinander vorbeigelaufen sind.
                if (i >= j) {
                    break;
                }

                // Ansonsten vertauschen und weitersuchen.
                swap(a, i, j);
            }

            // Pivot an Nahtstelle tauschen (steht nun richtig).
            swap(a, i - 1, bottom);

            // Rekursiv beide Teilbereiche sortieren.
            quickSort(a, bottom, i - 1);
            quickSort(a, i, top);
        }
    }

    /**
     * Glattes QuickSort entsprechend der natürlichen Ordnung der Elemente eines
     * Arrays.
     * @param a Das Array, das sortiert wird.
     * @param <T> Der Typ der Elemente des Arrays.
     */
    public static <T extends Comparable<T>> void smoothQuickSort(final T[] a)
    {
        smoothQuickSort(a, 0, a.length);
    }

    /**
     * Glattes QuickSort eines Teilbereichs eines Arrays entsprechend der
     * natürlichen Ordnung der Elemente des Arrays.
     * @param a Das Array, das sortiert wird.
     * @param bottom Die Untergrenze des zu sortierenden Teilbereichs (inklusiv)
     * @param top Die Obergrenze des zu sortierenden Teilbereichs (exklusiv)
     * @param <T> Der Typ der Elemente des Arrays.
     */
    private static <T extends Comparable<T>> void smoothQuickSort(T[] a, int bottom, int top)
    {
        if (bottom + 1 < top) {
            final T pivot = a[bottom];
            int i = bottom + 1, j = top - 1, p = i, q = j;
            do {
                // Von unten Element suchen, das größer-gleich Pivot.
                for (; i < top && a[i].compareTo(pivot) < 0; ++i);

                // Von oben Element suchen, das kleiner-gleich Pivot.
                for (; j > bottom && a[j].compareTo(pivot) > 0; --j);
                if (i < j) {
                    // Vertauschen, wenn Indizes nicht aneinander vorbeigelaufen sind.
                    swap(a, i, j);
                    // Unteres Element gleich Pivot → ans Ende des unteren Pivot-Bereichs tauschen.
                    if (a[i].compareTo(pivot) == 0) {
                        swap(a, i, p++);
                    }

                    // Oberes Element gleich Pivot → an Anfang des oberen Pivot-Bereichs tauschen.
                    if (a[j].compareTo(pivot) == 0) {
                        swap(a, j, q--);
                    }
                }
            } while (i < j);

            // Pivot-Bereiche in die Mitte tauschen.
            j = i;
            while (p > bottom) {
                swap(a, --i, --p);
            }
            while (q < top - 1) {
                swap(a, j++, ++q);
            }

            // Rekursiv beide Teilbereiche sortieren.
            smoothQuickSort(a, bottom, i);
            smoothQuickSort(a, j, top);
        }
    }

    /**
     * Funktionale Schnittstelle, die eine Funktion definiert, die ein Bit
     * aus einem Schlüsselwert liefert. Diese wird von BinaryRadixSort verwendet.
     * @param <T> Der Typ des Schlüsselwerts.
     */
    public interface GetBit<T>
    {
        /**
         * Liefert ein Bit eines Schlüsselwerts.
         * @param key Der Schlüsselwert
         * @param bit Die Nummer des Bits, das zurückgeliefert wird.
         * @return Das Bit, d.h. 0 oder 1.
         */
        int get(T key, int bit);
    }

    /**
     * Binärer RadixSort entsprechend der durch die Bits der Schlüsselwerte
     * definierten Ordnung. Werden jeweils alle Bits eines Schlüsselwerts als
     * Zahl betrachtet, werden diese Zahlen in aufsteigender Reihenfolge sortiert.
     * @param a Das Array, das sortiert wird.
     * @param bits Die maximale Anzahl der benutzten Bits in den Schlüsselwerten.
     * @param getBit Eine Funktion, die jeweils ein bestimmtes Bit eines
     *         Schlüsselwerts zurückliefert.
     * @param <T> Der Typ der Elemente des Arrays.
     */
    public static <T> void binaryRadixSort(final T[] a, final int bits, final GetBit<T> getBit)
    {
        // Das ganze Array sortieren. Es wird mit dem obersten Bit der Schlüssel begonnen.
        binaryRadixSort(a, bits - 1, getBit, 0, a.length);
    }

    /**
     * Binärer RadixSort eines Teilbereichs eines Arrays entsprechend der durch
     * einen unteren Teilbereich der Bits der Schlüsselwerte definierten Ordnung.
     * @param a Das Array, das sortiert wird.
     * @param bit Das aktuell betrachtete Bit der Schlüsselwerte.
     * @param getBit Eine Funktion, die jeweils ein bestimmtes Bit eines
     *         Schlüsselwerts zurückliefert.
     * @param bottom Die Untergrenze des zu sortierenden Teilbereichs (inklusiv)
     * @param top Die Obergrenze des zu sortierenden Teilbereichs (exklusiv)
     * @param <T> Der Typ der Elemente des Arrays.
     */
    private static <T> void binaryRadixSort(final T[] a,
            final int bit, final GetBit<T> getBit, final int bottom, final int top)
    {
        // Wenn es noch etwas zu sortieren gibt
        if (bit >= 0 && bottom + 1 < top) {
            int i = bottom;
            int j = top - 1;
            while (true) {
                // Von unten Element suchen, dessen Bit 1 ist.
                for (; i < top && getBit.get(a[i], bit) == 0; ++i);

                // Von oben Element suchen, dessen Bit 0 ist.
                for (; j > bottom && getBit.get(a[j], bit) == 1; --j);

                // Fertig, wenn Indizes aneinander vorbeigelaufen sind.
                if (i >= j) {
                    break;
                }

                // Ansonsten vertauschen und weitersuchen.
                swap(a, i, j);
            }

            // Rekursiv beide Teilbereiche für das nächst niedrigere Bit sortieren.
            binaryRadixSort(a, bit - 1, getBit, bottom, i);
            binaryRadixSort(a, bit - 1, getBit, i, top);
        }
    }

    /**
     * HeapSort entsprechend der natürlichen Ordnung der Elemente eines
     * Arrays.
     * @param a Das Array, das sortiert wird.
     * @param <T> Der Typ der Elemente des Arrays.
     */
    public static <T extends Comparable<T>> void heapSort(final T[] a)
    {
        // Heap aufbauen.
        for (int i = a.length / 2 - 1; i >= 0; --i) {
            siftDown(a, i, a.length);
        }

        // Heap abbauen und dabei Sortierung vom Ende des Arrays aufbauen.
        for (int i = a.length - 1; i > 0; --i) {
            swap(a, 0, i);
            siftDown(a, 0, i);
        }
    }

    /**
     * Versickern eines Elements in einem Array, das hinter diesem
     * Element bis zu einer Obergrenze eine Heap-Struktur besitzt.
     * @param a Das Array, das die Elemente des Heaps enthält.
     * @param i Der Index des Elements, das versickern soll.
     * @param n Der Index des Endes des Heaps (exklusiv).
     * @param <T> Der Typ der Elemente des Arrays.
     */
    private static <T extends Comparable<T>> void siftDown(
            final T[] a, int i, final int n)
    {
        while (i < n / 2) { // 2 × i + 1 < n
            // j ist Index des größeren Nachfolgers.
            int j = 2 * i + 1;
            if (j + 1 < n && a[j].compareTo(a[j + 1]) < 0) {
                ++j;
            }

            // Element ist kleiner als größerer Nachfolger?
            if (a[i].compareTo(a[j]) < 0) {
                //  Vertauschen und dort weiter.
                swap(a, i, j);
                i = j;
            }
            else {
                // Sonst fertig.
                break;
            }
        }
    }
}
