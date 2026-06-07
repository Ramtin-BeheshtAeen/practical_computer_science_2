package de.uni_bremen.pi2;

/**
 * Ein Knoten eines AVL-balancierten Binärbaums.
 * @param <E> Der Typ der in dem Knoten gespeicherten Daten.
 */
class AVLNode<E> extends Node<E>
{
    /** Die Höhe des Teilbaums, dessen Wurzel dieser Knoten ist. */
    private int height = 1;

    /**
     * Erzeugt einen neuen Knoten.
     * @param data Die in dem Knoten gespeicherten Daten.
     */
    AVLNode(final E data)
    {
        super(data);
    }

    /**
     * Berechnet die Höhe unter der Annahme neu, dass sie für die Kinder bereits
     * korrekt ist.
     * @return Hat sich die Höhe tatsächlich geändert?
     */
    boolean updateHeight()
    {
        final int oldHeight = height;
        height = 1 + Math.max(height(LEFT), height(RIGHT));
        return oldHeight != height;
    }

    /**
     * Bestimmt die Neigung dieses Knotens. Linksneigungen sind negativ,
     * Rechtsneigungen positiv.
     * @return Die Neigung.
     */
    int slope()
    {
        return height(RIGHT) - height(LEFT);
    }

    /**
     * Liefert die Höhe des Teilbaums eines Kindes. Diese ist 0, wenn es das Kind
     * nicht gibt.
     * @param direction Das Kind in welcher Richtung wird betrachtet (LEFT, RIGHT)?.
     * @return Die Höhe des Teilbaums.
     */
    private int height(final int direction)
    {
        return children[direction] == null ? 0 : ((AVLNode<E>) children[direction]).height;
    }

    /**
     * Stellt den Knoten als Zeichenkette dar.
     * @return Die in dem Knoten gespeicherten Daten und die Neigung als
     *         Zeichenkette.
     */
    @Override
    public String toString()
    {
        return super.toString() + " (" + slope() + ")";
    }
}