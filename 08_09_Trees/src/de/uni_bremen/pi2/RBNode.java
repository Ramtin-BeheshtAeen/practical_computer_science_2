package de.uni_bremen.pi2;

import static de.uni_bremen.pi2.RBNode.Color.*;

/**
 * Ein Knoten eines Rot-Schwarz-balancierten Binärbaums.
 * @param <E> Der Typ der in dem Knoten gespeicherten Daten.
 */
class RBNode<E> extends Node<E>
{
    /** Die möglichen Farben von Knoten. */
    enum Color
    {
        BLACK,
        RED
    }

    /** Die Farbe dieses Knotens. */
    Color color = RED;

    /**
     * Erzeugt einen neuen Knoten.
     * @param data Die in dem Knoten gespeicherten Daten.
     */
    RBNode(final E data)
    {
        super(data);
    }

    /**
     * Liefert eine Zeichenkette, die diesen Knoten darstellt. Die
     * Kinder sind nicht repräsentiert.
     * @return Eine Zeichenkette aus den gespeicherten Daten und
     *         der Farbe.
     */
    @Override
    public String toString()
    {
        return super.toString() + (color == RED ? " (R)" : " (B)");
    }
}
