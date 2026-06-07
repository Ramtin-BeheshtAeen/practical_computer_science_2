package de.uni_bremen.pi2;

/**
 * Abstrakte Basisklasse für balancierte Bäume. Definiert mit der Rotation
 * die zentrale Operation des Balancierens.
 * @param <E> Der Typ der Daten, die im Baum gespeichert werden.
 */
abstract class BalancedTree<E extends Comparable<E>> extends SearchTree<E>
{
    /**
     * Rotiert einen Teilbaum in die angegebene Richtung.
     * @param oldRoot Die bisherige Wurzel des Teilbaums. Wird Kind der neuen
     *         Wurzel in der angegebenen Richtung.
     * @param direction Die Richtung, in die rotiert wird (LEFT, RIGHT).
     */
    void rotate(final Node<E> oldRoot, final int direction)
    {
        final Node<E> newRoot = oldRoot.children[1 - direction];
        final Node<E> innerGrandChild = newRoot.children[direction];
        setChild(oldRoot.parent, newRoot, whichChild(oldRoot));
        setChild(newRoot, oldRoot, direction);
        setChild(oldRoot, innerGrandChild, 1 - direction);
    }
}
