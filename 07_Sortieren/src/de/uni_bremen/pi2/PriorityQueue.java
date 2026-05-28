package de.uni_bremen.pi2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementiert eine Vorrangwarteschlange, die entsprechend der
 * natürlichen Ordnung der Elemente Werte in absteigender
 * Reihenfolge ausliefert.
 * @param <T> Der Typ der Elemente.
 */
public class PriorityQueue<T extends Comparable<T>>
{
    /** Der Max-Heap. */
    private final List<T> heap = new ArrayList<T>();

    /**
     * Ist die Warteschlange leer?
     * @return Ist sie leer?
     */
    public boolean empty()
    {
        return heap.isEmpty();
    }

    /**
     * Liefert das nächste Element der Warteschlange, ohne es zu
     * entfernen.
     * @return Das der Ordnung entsprechend nächste Element.
     * @throws ArrayIndexOutOfBoundsException Die Schlange war leer.
     */
    public T top()
    {
        return heap.getFirst();
    }

    /**
     * Liefert das nächste Element der Warteschlange und entfernt es.
     * @return Das der Ordnung entsprechend nächste Element.
     * @throws ArrayIndexOutOfBoundsException Die Schlange war leer.
     */
    public T pop()
    {
        Collections.swap(heap, 0, heap.size() - 1);
        int i = 0;
        while (i < heap.size() / 2 - 1) {
            int j = 2 * i + 1;
            if (j + 1 < heap.size() - 1 && heap.get(j).compareTo(heap.get(j + 1)) < 0) {
                ++j;
            }
            if (heap.get(i).compareTo(heap.get(j)) < 0) {
                Collections.swap(heap, i, j);
                i = j;
            }
            else {
                break;
            }
        }
        return heap.removeLast();
    }

    /**
     * Fügt ein neues Element in die Warteschlange ein.
     * @param entry Das neue Element.
     */
    public void push(final T entry)
    {
        heap.add(entry);
        for (int i = heap.size() - 1, j = (i - 1) / 2;
                i > 0 && heap.get(j).compareTo(heap.get(i)) < 0;
                i = j, j = (i - 1) / 2) {
            Collections.swap(heap, i, j);
        }
    }

    /**
     * Liefert eine Baumdarstellung der Einträge der Warteschlange.
     * @return Ein mehrzeiliger String mit den Knoten des Heaps.
     */
    @Override
    public String toString()
    {
        return empty() ? "" : "\n" + toString(0, "", "");
    }

    /**
     * Erzeugt eine Baumdarstellung eines Heap-Knotens und seiner Kinder.
     * @param i Der Index des Knotens im Heap.
     * @param horizontal Die horizontale Kante, die vor dem Knoten stehen soll.
     * @param vertical Die vertikale Kante, die vor allen Kindknoten stehen soll.
     * @return Ein mehrzeiliger String mit den Knoten des Teilbaums.
     */
    private String toString(final int i, final String horizontal, final String vertical)
    {
        final StringBuilder s = new StringBuilder(horizontal + heap.get(i) + "\n");
        if (i < (heap.size() - 1) / 2) {
            s.append(toString(2 * i + 2, vertical + "├── ", vertical + "│   "));
        }
        if (i < heap.size() / 2) {
            s.append(toString(2 * i + 1, vertical + "└── ", vertical + "    "));
        }
        return s.toString();
    }
}
