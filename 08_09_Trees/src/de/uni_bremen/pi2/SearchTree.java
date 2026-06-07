package de.uni_bremen.pi2;

import static de.uni_bremen.pi2.Node.*;

/**
 * Ein Suchbaum. Der Baum ist nach der natürlichen Ordnung der
 * in ihm gespeicherten Daten geordnet.
 * @param <E> Der Typ der Daten, die im Baum gespeichert werden.
 */
public class SearchTree<E extends Comparable<E>> extends Tree<E>
{
    /**
     * Erzeugt einen neuen Knoten.
     * @param data Die in dem Knoten gespeicherten Daten.
     * @return Ein neuer Knoten.
     */
    Node<E> newNode(final E data)
    {
        return new Node<>(data);
    }

    /**
     * Suche nach einem Wert im Baum.
     * @param data Der gesuchte Wert.
     * @return Der gefundene Wert oder null, wenn er nicht gefunden wurde.
     */
    public E search(final E data)
    {
        final Node<E> node = searchNode(data);
        return node == null ? null : node.data;
    }

    /**
     * Sucht nach Daten im Baum.
     * @param data Die Daten, nach denen gesucht wird. Beachtet wird dabei
     *         lediglich der Teil, auf den sich das Ordnungskriterium des Baums
     *         bezieht.
     * @return Der Knoten mit den gefundenen Daten oder null, wenn sie nicht im
     *         Baum enthalten sind.
     */
    private Node<E> searchNode(final E data)
    {
        // Die Suche beginnt mit der Wurzel.
        Node<E> current = root;

        // Die Suche geht maximal bis zu einem Blatt.
        while (current != null) {
            // Daten im Knoten mit den gesuchten vergleichen.
            final int comparison = data.compareTo(current.data);
            if (comparison == 0) {
                // Gefunden → Knoten zurückgeben.
                return current;
            }
            else {
                // Sonst weitersuchen entsprechend der Ordnung im Baum.
                current = current.children[comparison < 0 ? LEFT : RIGHT];
            }
        }

        // Wurde ein Blatt erreicht, wurden die Daten nicht gefunden.
        return null;
    }

    /**
     * Fügt neue Daten in den Baum ein.
     * @param data Die Daten, die eingefügt werden.
     */
    public void insert(final E data)
    {
        // Elternknoten des aktuellen Knotens (null für die Wurzel). Wird benötigt,
        // weil beim Einfügen ein Blatt ersetzt wird und diese keine Elternreferenz
        // haben.
        Node<E> parent = null;

        // Die Suche beginnt mit der Wurzel
        Node<E> current = root;

        // Die Richtung, über die der aktuelle Knoten erreicht wurde.
        // Der Startwert ist egal, da er nicht verwendet wird.
        int direction = LEFT;

        // Die Suche geht bis zu einem Blatt.
        while (current != null) {
            // Aktueller Knoten ist Elternknoten des nächsten Knotens.
            parent = current;

            // Suchrichtung aus Vergleich bestimmen und dorthin wechseln.
            direction = data.compareTo(current.data) < 0 ? LEFT : RIGHT;
            current = current.children[direction];
        }

        // Neuen Knoten erzeugen und an letzten Elternknoten in der
        // letzten Verzweigungsrichtung anhängen.
        final Node<E> child = newNode(data);
        setChild(parent, child, direction);

        // Baum wieder ausbalancieren.
        rebalanceAfterInsert(child);
    }

    /**
     * Löscht ein Vorkommen der übergebenen Daten aus dem Baum, wenn sie vorhanden
     * sind.
     * @param data Die Daten, nach denen zum Löschen gesucht wird. Beachtet wird
     *         dabei lediglich der Teil, auf den sich das Ordnungskriterium des
     *         Baums bezieht.
     */
    public void delete(final E data)
    {
        Node<E> toDelete = searchNode(data);
        if (toDelete != null) {
            // Auf welcher Seite seines Elternknotens hängt der zu löschende?
            int direction = whichChild(toDelete);
            if (toDelete.children[LEFT] == null) {
                // Linkes Kind ist leer → Diesen Knoten durch rechtes Kind ersetzen.
                setChild(toDelete.parent, toDelete.children[RIGHT], direction);
            }
            else if (toDelete.children[RIGHT] == null) {
                // Rechtes Kind ist leer → Diesen Knoten durch linkes Kind ersetzen.
                setChild(toDelete.parent, toDelete.children[LEFT], direction);
            }
            else {
                // Ersatzknoten ist der linkeste im rechten Teilbaum, d.h. er hat
                // kein linkes Kind mehr.
                final Node<E> replacement = findExtremum(toDelete.children[RIGHT], LEFT);

                // Zu löschende Daten durch die aus Ersatzknoten überschreiben.
                toDelete.data = replacement.data;

                // Ersatzknoten ist nun der tatsächlich zu löschende Knoten.
                toDelete = replacement;
                direction = whichChild(toDelete);

                // Lösche Knoten, indem er durch sein rechtes Kind ersetzt wird.
                setChild(toDelete.parent, toDelete.children[RIGHT], direction);
            }

            // Baum wieder ausbalancieren.
            rebalanceAfterDelete(toDelete, direction);
        }
    }

    /**
     * Liefert den inneren Knoten im Teilbaum, der am weitesten in die angegebene
     * Richtung liegt.
     * @param node Die Wurzel des Teilbaums, in dem gesucht wird. Darf kein Blatt
     *         (null) sein.
     * @param direction Die Richtung, in die gesucht wird.
     * @return Der gesuchte innere Knoten (d.h. ungleich null).
     */
    private Node<E> findExtremum(Node<E> node, final int direction)
    {
        while (node.children[direction] != null) {
            node = node.children[direction];
        }
        return node;
    }

    /**
     * Methode zum Rebalancieren des Baums nach dem Einfügen.
     * Diese Implementierung tut das nicht.
     * @param newNode Der Knoten, der hinzugefügt wurde.
     */
    void rebalanceAfterInsert(final Node<E> newNode) {}

    /**
     * Methode zum Rebalancieren des Baums nach dem Löschen.
     * Diese Implementierung tut das nicht.
     * @param deletedNode Der Knoten, der gelöscht wurde.
     * @param direction Die Richtung, in der er an seinem Elternknoten hing (LEFT, RIGHT).
     */
    void rebalanceAfterDelete(final Node<E> deletedNode, final int direction) {}
}