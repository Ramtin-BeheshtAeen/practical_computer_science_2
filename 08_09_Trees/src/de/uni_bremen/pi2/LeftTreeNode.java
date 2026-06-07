package de.uni_bremen.pi2;

/**
 * Ein Knoten eines Linksbaums.
 * @param <E> Der Typ der in dem Knoten gespeicherten Daten.
 */
class LeftTreeNode<E extends Comparable<E>> extends Node<E>
{
    /** Die Distanz zum dichtesten Blatt. */
    private int distance = 1;

    /**
     * Erzeugt einen neuen Knoten.
     * @param data Die in dem Knoten gespeicherten Daten.
     */
    LeftTreeNode(final E data)
    {
        super(data);
    }

    /**
     * Aktualisiert die Distanz zum dichtesten Blatt unter der Annahme,
     * dass diese für die Kinder bereits bestimmt wurde. Es wird zudem
     * dafür gesorgt, dass die Distanz des rechten Kinds kürzer ist.
     */
    void updateDistance()
    {
        if (distance(RIGHT) > distance(LEFT)) {
            final Node<E> temp = children[LEFT];
            children[LEFT] = children[RIGHT];
            children[RIGHT] = temp;
        }
        distance = distance(RIGHT) + 1;
    }

    /**
     * Stellt den Knoten als Zeichenkette dar.
     * @return Die in dem Knoten gespeicherten Daten und die Distanz zum
     *         dichtesten Blatt.
     */
    @Override
    public String toString()
    {
        return data + " (" + distance + ")";
    }

    /**
     * Die Distanz zum dichtesten Blatt ausgehend von einem Kind des aktuellen Knotens.
     * @param direction Die Richtung, in der das Kind an diesem Knoten hängt (LEFT, RIGHT).
     * @return Die Distanz.
     */
    private int distance(final int direction)
    {
        return children[direction] == null ? 0 : ((LeftTreeNode<E>) children[direction]).distance;
    }
}
