package de.uni_bremen.pi2;

import static de.uni_bremen.pi2.Node.*;

/**
 * Ein Linksbaum. Der Baum implementiert eine Vorrangwarteschlange,
 * die nach der natürlichen Ordnung der in ihr gespeicherten Daten
 * geordnet ist.
 * @param <E> Der Typ der Daten, die im Baum gespeichert werden.
 */
public class LeftTree<E extends Comparable<E>> extends Tree<E>
{
    /**
     * Füge einen Wert in den Baum ein.
     * @param data Der einzufügende Wert.
     */
    public void insert(final E data)
    {
        setChild(null, merge(root, new LeftTreeNode<>(data)), LEFT);
    }

    /**
     * Liefert den kleinsten Wert im Baum.
     * @return Der kleinste Wert.
     */
    public E accessMin()
    {
        return root.data;
    }

    /**
     * Löscht den kleinsten Wert aus dem Baum und liefert ihn zurück.
     * @return Der kleinste Wert.
     */
    public E deleteMin()
    {
        final E data = accessMin();
        setChild(null, merge(root.children[LEFT], root.children[RIGHT]), LEFT);
        return data;
    }

    /**
     * Verschmelze zwei Linksbäume zu einem.
     * @param a Der eine Linksbaum.
     * @param b Der zweite Linksbaum.
     * @return Das Ergebnis der Verschmelzung.
     */
    private Node<E> merge(final Node<E> a, final Node<E> b)
    {
        if (a == null) {
            return b;
        }
        else if (b == null) {
            return a;
        }
        else if (a.data.compareTo(b.data) > 0) {
            return merge(b, a);
        }
        else {
            setChild(a, merge(a.children[RIGHT], b), RIGHT);
            ((LeftTreeNode<E>) a).updateDistance();
            return a;
        }
    }
}
