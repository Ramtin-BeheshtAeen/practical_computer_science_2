package de.uni_bremen.pi2;

import static de.uni_bremen.pi2.Node.*;
import static de.uni_bremen.pi2.RBNode.Color.*;

/**
 * Ein Rot-Schwarz-balancierter Binärbaum. Der Baum ist
 * nach der natürlichen Ordnung der in ihm gespeicherten
 * Daten geordnet.
 * @param <E> Der Typ der Daten, die im Baum gespeichert werden.
 */
 public class RBTree<E extends Comparable<E>> extends BalancedTree<E>
{
    /**
     * Erzeugt einen neuen, Rot-Schwarz-balancierten Knoten.
     * @param data Die in dem Knoten gespeicherten Daten.
     * @return Ein neuer Knoten.
     */
    @Override
    RBNode<E> newNode(final E data)
    {
        return new RBNode<>(data);
    }

    /**
     * Balanciert den Baum nach dem Einfügen eines neuen Knotens wieder aus. Dabei
     * werden aufeinander folgende rote Knoten behoben und die Wurzel schwarz
     * gefärbt.
     * @param current Der neue Knoten.
     */
    void rebalanceAfterInsert(Node<E> current)
    {
        // Neue Knoten sind rot. Ist es der Elternknoten auch, muss balanciert
        // werden.
        while (isRed(current.parent)) {
            // Wo hängt der Elternknoten am (schwarzen) Großelternknoten?
            final int parentDirection = whichChild(current.parent);

            // Die Tante hängt auf der anderen Seite.
            final Node<E> aunt = current.parent.parent.children[1 - parentDirection];
            if (isRed(aunt)) {
                // Fall 1: Die Tante ist rot → Umfärben.
                setColor(current.parent, BLACK);
                setColor(aunt, BLACK);
                current = current.parent.parent;
                setColor(current, RED);
            }
            else {
                // Wo hängt der aktuelle Knoten an seinem Elternknoten?
                final int direction = whichChild(current);
                if (direction != parentDirection) {
                    // Fall 2: Der aktuelle Knoten ist das innere Kind, weshalb
                    // nach außen rotiert werden muss. Nach der Rotation ist der
                    // bisherige Elternknoten das aktuelle "Problemkind".
                    current = current.parent;
                    rotate(current, parentDirection);
                }

                // Fall 3: Großelternknoten in Richtung Tante rotieren.
                setColor(current.parent, BLACK);
                setColor(current.parent.parent, RED);
                rotate(current.parent.parent, 1 - parentDirection);
            }
        }
        setColor(root, BLACK);
    }

    /**
     * Methode zum Rebalancieren des Baums nach dem Löschen.
     * @param deletedNode Der Knoten, der gelöscht wurde.
     * @param direction Die Richtung, in der er an seinem Elternknoten hing (LEFT, RIGHT).
     */
    void rebalanceAfterDelete(final Node<E> deletedNode, int direction)
    {
        if (!isRed(deletedNode)) {
            Node<E> parent = deletedNode.parent;
            while (parent != null && !isRed(parent.children[direction])) {
                Node<E> sibling = parent.children[1 - direction];
                if (isRed(sibling)) {
                    // Fall 1: Schwester ist rot → Rotiere sie hoch. Danach ist ein
                    // anderer Knoten die neue Schwester.
                    setColor(sibling, BLACK);
                    setColor(parent, RED);
                    rotate(parent, direction);
                    sibling = parent.children[1 - direction];
                }

                if (!isRed(sibling.children[LEFT]) && !isRed(sibling.children[RIGHT])) {
                    // Fall 2: Beide Nichten sind schwarz → Färbe Schwester rot.
                    setColor(sibling, RED);

                    // Beim Elternknoten geht es weiter.
                    direction = whichChild(parent);
                    parent = parent.parent;
                }
                else {
                    if (isRed(sibling.children[direction])) {
                        // Fall 3: Innere Nichte ist rot → Rotiere Schwester nach außen.
                        // Keine Umfärbungen, da diese in Fall 4 überschrieben werden.
                        rotate(sibling, 1 - direction);
                        sibling = parent.children[1 - direction];
                    }

                    // Fall 4: Äußere Nichte ist rot → Rotiere Elternknoten und sie
                    // dabei nach oben.
                    setColor(sibling, ((RBNode<E>) parent).color);
                    setColor(parent, BLACK);
                    setColor(sibling.children[1 - direction], BLACK);
                    rotate(parent, direction);
                    parent = null;
                }
            }

            // Färbe aktuellen Knoten schwarz, wenn er rot ist. Wenn parent null ist,
            // ist der aktuelle Knoten die Wurzel.
            if (parent != null && isRed(parent.children[direction])) {
                setColor(parent.children[direction], BLACK);
            }
            else if (parent == null && isRed(root)) {
                setColor(root, BLACK);
            }
        }
    }

    /**
     * Testet, ob ein Knoten ein rot ist.
     * @param node Der Knoten. Darf auch ein Blatt (null) sein.
     * @return Ist der Knoten rot?
     */
    private boolean isRed(final Node<E> node)
    {
        return node != null && ((RBNode<E>) node).color == RED;
    }

    /**
     * Setzt die Farbe eines Knotens.
     * @param node Der Knoten, dessen Farbe geändert wird.
     * @param color Die neue Farbe des Knotens.
     */
    private void setColor(final Node<E> node, final RBNode.Color color)
    {
        ((RBNode<E>) node).color = color;
    }
}
