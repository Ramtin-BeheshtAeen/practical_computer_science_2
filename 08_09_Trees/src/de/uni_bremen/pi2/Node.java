package de.uni_bremen.pi2;

/**
 * Ein Knoten eines Binärbaums.
 * @param <E> Der Typ der in dem Knoten gespeicherten Daten.
 */
public class Node<E>
{
    /** Index für linke Knoten. */
    static final int LEFT = 0;

    /** Index für rechte Knoten. */
    static final int RIGHT = 1;

    /** Die zwei Kinder des Knotens. */
    @SuppressWarnings("unchecked")
    final Node<E>[] children = (Node<E>[]) new Node[2];

    /** Der Elternknoten. Ist null, wenn dies die Wurzel ist. */
    Node<E> parent;

    /** Die in dem Knoten gespeicherten Daten. */
    E data;

    /**
     * Erzeugt einen neuen Knoten ohne Kinder.
     * @param data Die in dem Knoten gespeicherten Daten.
     */
    public Node(final E data)
    {
        this(data, null, null);
    }

    /**
     * Erzeugt einen neuen Knoten.
     * @param data Die in dem Knoten gespeicherten Daten.
     * @param left Linker Kindknoten oder null, falls es diesen nicht gibt.
     * @param right Rechter Kindknoten oder null, falls es diesen nicht gibt.
     */
    public Node(final E data, final Node<E> left, final Node<E> right)
    {
        children[LEFT] = left;
        children[RIGHT] = right;
        if (left != null) {
            left.parent = this;
        }
        if (right != null) {
            right.parent = this;
        }
        this.data = data;
    }

    /**
     * Liefert eine Zeichenkette, die diesen Knoten darstellt. Die
     * Kinder sind nicht repräsentiert.
     * @return Eine Zeichenkette aus den gespeicherten Daten.
     */
    @Override
    public String toString()
    {
        return data.toString();
    }
}