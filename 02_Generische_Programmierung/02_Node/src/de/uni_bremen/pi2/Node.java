package de.uni_bremen.pi2;

/**
 * Der Knoten eines Binärbaums
 * @param <E> Der Typ des Werts, der im Knoten gespeichert werden kann.
 */
public class Node<E>
{
    /** Der im Knoten gespeicherte Wert. */
    private E info;

    /** Der linke Kindknoten. Ist null, wenn es keinen gibt. */
    private final Node<E> left;

    /** Der rechte Kindknoten. Ist null, wenn es keinen gibt. */
    private final Node<E> right;

    /**
     * Konstruktor für einen Knoten ohne Kinder.
     * @param info Der im Knoten gespeicherte Wert.
     */
    public Node(final E info)
    {
        this(info, null, null);
    }

    /**
     * Konstruktor für einen Knoten.
     * @param info Der im Knoten gespeicherte Wert.
     * @param left Der linke Kindknoten. Ist null, wenn es keinen gibt.
     * @param right Der rechte Kindknoten. Ist null, wenn es keinen gibt.
     */
    public Node(final E info, final Node<E> left, final Node<E> right)
    {
        this.info = info;
        this.left = left;
        this.right = right;
    }

    /**
     * Liefert die im Knoten gespeicherte Information.
     * @return Der im Knoten gespeicherte Wert.
     */
    public E getInfo()
    {
        return info;
    }

    /**
     * Liefert den linken Kindknoten.
     * @return Der linke Kindknoten oder null, wenn es keinen gibt.
     */
    public Node<E> getLeft()
    {
        return left;
    }

    /**
     * Liefert den rechten Kindknoten.
     * @return Der rechte Kindknoten oder null, wenn es keinen gibt.
     */
    public Node<E> getRight()
    {
        return right;
    }

    /**
     * Setzt den im Knoten gespeicherten Wert.
     * @param info Der neue im Knoten gespeicherte Wert.
     */
    public void setInfo(final E info)
    {
        this.info = info;
    }

    /**
     * Liefert eine mehrzeilige Darstellung des Baums.
     * @return Ein mehrzeiliger String mit den Knoten des Baums.
     */
    @Override
    public String toString()
    {
        return "\n" + toString(this, "", "");
    }

    /**
     * Erzeugt eine mehrzeilige Darstellung eines Knotens und seiner Kinder.
     * @param node Der Knoten.
     * @param horizontal Die horizontale Kante, die vor dem Knoten stehen soll.
     * @param vertical Die vertikale Kante, die vor allen Kindknoten stehen soll.
     * @return Ein mehrzeiliger String mit den Knoten des Teilbaums.
     */
    private String toString(final Node<E> node, final String horizontal, final String vertical)
    {
        return horizontal
                + (node == null
                        ? "[]"
                        : node.info + "\n"
                                + toString(node.right, vertical + "├── ", vertical + "│   ") + "\n"
                                + toString(node.left, vertical + "└── ", vertical + "    "));
    }
}
