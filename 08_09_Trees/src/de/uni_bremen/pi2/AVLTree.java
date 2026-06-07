package de.uni_bremen.pi2;

import static de.uni_bremen.pi2.Node.*;

/**
 * Ein AVL-balancierter Binärbaum. Der Baum ist nach der natürlichen
 * Ordnung der in ihm gespeicherten Daten geordnet.
 * @param <E> Der Typ der Daten, die im Baum gespeichert werden.
 */
public class AVLTree<E extends Comparable<E>> extends BalancedTree<E>
{
    /**
     * Erzeugt einen neuen, AVL-balancierten Knoten.
     * @param data Die in dem Knoten gespeicherten Daten.
     * @return Ein neuer Knoten.
     */
    @Override
    AVLNode<E> newNode(final E data)
    {
        return new AVLNode<>(data);
    }

    /**
     * Methode zum Rebalancieren des Baums nach dem Einfügen.
     * Es wird von der Einfügestelle den Baum hoch gelaufen und
     * unterwegs jeder Knoten balanciert. Dabei werden auch die Höhen
     * neu berechnet. Es wird abgebrochen, sobald sich die Höhe in einem
     * Knoten dabei nicht ändert, da sie sich dann darüber auch nicht
     * mehr ändern kann.
     * @param newNode Der Knoten, der hinzugefügt wurde.
     */
    @Override
    void rebalanceAfterInsert(final Node<E> newNode)
    {
        for (Node<E> current = newNode.parent; current != null
                && rebal((AVLNode<E>) current); current = current.parent);
    }

    /**
     * Methode zum Rebalancieren des Baums nach dem Löschen.
     * Ist in dieser Implementierung identisch zum Rebalancieren
     * nach dem Einfügen.
     * @param deletedNode Der Knoten, der gelöscht wurde.
     * @param direction Die Richtung, in der er an seinem Elternknoten
     *         hing (LEFT, RIGHT). Hier unbenutzt.
     */
    @Override
    void rebalanceAfterDelete(final Node<E> deletedNode, final int direction)
    {
        rebalanceAfterInsert(deletedNode);
    }

    /**
     * Rebalanciert einen Knoten bei Bedarf, unter der Annahme, dass
     * die Kindknoten bereits AVL-balanciert sind.
     * @param node Der Knoten, der balanciert wird.
     * @return Ist weiteres Rebalancieren notwendig, da sich die Höhe
     *         geändert hat?
     */
    private boolean rebal(final AVLNode<E> node)
    {
        // Wenn die Neigung 2 ist, muss entgegen der Neigungsrichtung
        // rotiert werden.
        if (Math.abs(node.slope()) == 2) {
            shift(node, node.slope() > 0 ? LEFT : RIGHT);
        }

        // Höhe des Knotens aktualisieren. Wenn rotiert wurde, steht der
        // Knoten nun eine Ebene tiefer im Baum, sodass die Höhe seines
        // Elternknotens beim nächsten Aufruf dieser Methode korrigiert
        // wird.
        return node.updateHeight();
    }

    /**
     * (Doppel-)Rotiert einen Teilbaum in die angegebene Richtung.
     * @param oldRoot Die bisherige Wurzel des Teilbaums. Wird Kind der neuen
     *         Wurzel in der angegebenen Richtung.
     * @param direction Die Richtung, in die rotiert wird (LEFT, RIGHT).
     */
    private void shift(final Node<E> oldRoot, final int direction)
    {
        final int otherDirection = 1 - direction;
        final int inwardsSlope = direction == LEFT ? -1 : 1;

        // Wenn Tochterbaum nach innen geneigt, muss er nach außen rotiert werden.
        if (((AVLNode<E>) oldRoot.children[otherDirection]).slope() == inwardsSlope) {
            rotate(oldRoot.children[otherDirection], otherDirection);

            // Die Höhe der ehemaligen Tochter würde nicht automatisch aktualisiert.
            ((AVLNode<E>) oldRoot.children[otherDirection].children[otherDirection]).updateHeight();
        }
        rotate(oldRoot, direction);
    }
}
