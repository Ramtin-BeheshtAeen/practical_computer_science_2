package de.uni_bremen.pi2;

import java.util.*;
import java.util.function.Consumer;

import static de.uni_bremen.pi2.Node.*;

/**
 * Ein Baum.
 * @param <E> Der Typ der Daten, die im Baum gespeichert werden.
 */
public class Tree<E> implements Iterable<E>
{
    /** Die Wurzel des Baums. */
    Node<E> root;

    /**
     * Konstruktor für einen leeren Baum.
     */
    public Tree()
    {
        this(null);
    }

    /**
     * Konstruktor für einen Baum mit Knoten.
     * @param root Die Wurzel des Baums.
     */
    public Tree(final Node<E> root)
    {
        this.root = root;
    }

    /**
     * Hängt einen Kindknoten an einen Elternknoten in einer bestimmten Richtung
     * an.
     * @param parent Der Elternknoten, an den angehängt wird. Ist er null, wird
     *         das Kind die neue Wurzel des Baums.
     * @param child Der Kindknoten, der angehängt wird. Darf ein Blatt (null)
     *         sein.
     * @param direction Die Richtung, in die angehängt wird (LEFT, RIGHT). Ist
     *         parent null, hat dieser Parameter keine Relevanz.
     */
    void setChild(final Node<E> parent, final Node<E> child, final int direction)
    {
        if (parent == null) {
            root = child;
        }
        else {
            parent.children[direction] = child;
        }
        if (child != null) {
            child.parent = parent;
        }
    }

    /**
     * Bestimmt die Richtung, in der ein Knoten an seinem Elternknoten hängt.
     * @param node Der Knoten. Darf kein Blatt (null) sein.
     * @return Die Richtung, in der Knoten an seinem Elternknoten hängt (LEFT,
     *         RIGHT). Ist der Knoten die Wurzel, ist das Ergebnis LEFT und damit
     *         unsinnig.
     */
    int whichChild(final Node<E> node)
    {
        return node.parent == null || node == node.parent.children[LEFT]
                ? LEFT : RIGHT;
    }

    /**
     * Bestimmt die Höhe des Baums.
     * @return Die Höhe des Baums.
     */
    public int height()
    {
        return height(root);
    }

    /**
     * Bestimmt die Höhe eines Teilbaums.
     * @param node Die Wurzel des Teilbaums.
     * @return Die Höhe des Teilbaums.
     */
    private int height(final Node<E> node)
    {
        return node == null ? 0 : 1 + Math.max(height(node.children[LEFT]),
                height(node.children[RIGHT]));
    }

    /**
     * Führe eine Aktion in Präorder-Reihenfolge für alle Knoten
     * des Baums aus.
     * @param action Die Aktion, die für jeden Knoten durchgeführt wird.
     */
    public void preorder(final Consumer<E> action)
    {
        preorder(root, action);
    }

    /**
     * Führe eine Aktion in Präorder-Reihenfolge für alle Knoten
     * eines Teilbaums aus.
     * @param node Die Wurzel des Teilbaums.
     * @param action Die Aktion, die für jeden Knoten durchgeführt wird.
     */
    private void preorder(final Node<E> node, final Consumer<E> action)
    {
        if (node != null) {
            action.accept(node.data);
            preorder(node.children[LEFT], action);
            preorder(node.children[RIGHT], action);
        }
    }

    /**
     * Führe eine Aktion in Postorder-Reihenfolge für alle Knoten
     * des Baums aus.
     * @param action Die Aktion, die für jeden Knoten durchgeführt wird.
     */
    public void postorder(final Consumer<E> action)
    {
        postorder(root, action);
    }

    /**
     * Führe eine Aktion in Postorder-Reihenfolge für alle Knoten
     * eines Teilbaums aus.
     * @param node Die Wurzel des Teilbaums.
     * @param action Die Aktion, die für jeden Knoten durchgeführt wird.
     */
    private void postorder(final Node<E> node, final Consumer<E> action)
    {
        if (node != null) {
            postorder(node.children[LEFT], action);
            postorder(node.children[RIGHT], action);
            action.accept(node.data);
        }
    }

    /**
     * Führe eine Aktion in Inorder-Reihenfolge für alle Knoten
     * des Baums aus.
     * @param action Die Aktion, die für jeden Knoten durchgeführt wird.
     */
    public void inorder(final Consumer<E> action)
    {
        inorder(root, action);
    }

    /**
     * Führe eine Aktion in Inorder-Reihenfolge für alle Knoten
     * eines Teilbaums aus.
     * @param node Die Wurzel des Teilbaums.
     * @param action Die Aktion, die für jeden Knoten durchgeführt wird.
     */
    private void inorder(final Node<E> node, final Consumer<E> action)
    {
        if (node != null) {
            inorder(node.children[LEFT], action);
            action.accept(node.data);
            inorder(node.children[RIGHT], action);
        }
    }

    /**
     * Führe eine Aktion in Präorder-Reihenfolge für alle Knoten
     * des Baums aus.
     * @param action Die Aktion, die für jeden Knoten durchgeführt wird.
     */
    public void preorder2(final Consumer<E> action)
    {
        final Stack<Node<E>> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            final Node<E> node = stack.pop();
            if (node != null) {
                action.accept(node.data);
                stack.push(node.children[RIGHT]);
                stack.push(node.children[LEFT]);
            }
        }
    }

    /**
     * Führe eine Aktion in Level-Order-Reihenfolge für alle Knoten
     * des Baums aus. Javas Schnittstelle "Queue" nutzt "add" statt
     * "push" und "poll" statt "pop".
     * @param action Die Aktion, die für jeden Knoten durchgeführt wird.
     */
    public void levelOrder(final Consumer<E> action)
    {
        final Queue<Node<E>> stack = new LinkedList<>();
        stack.add(root);
        while (!stack.isEmpty()) {
            final Node<E> node = stack.poll();
            if (node != null) {
                action.accept(node.data);
                stack.add(node.children[LEFT]);
                stack.add(node.children[RIGHT]);
            }
        }
    }

    /**
     * Zählt die Elemente im Baum in Präorder-Reihenfolge auf.
     * @return Ein Iterator zum Aufzählen der Elemente im Baum.
     */
    @Override
    public Iterator<E> iterator()
    {
        final Stack<Node<E>> stack = new Stack<>();
        stack.push(root);

        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                while (!stack.isEmpty() && stack.peek() == null) {
                    stack.pop();
                }
                return !stack.isEmpty();
            }

            @Override
            public E next() {
                if (hasNext()) {
                    final Node<E> node = stack.pop();
                    stack.push(node.children[RIGHT]);
                    stack.push(node.children[LEFT]);
                    return node.data;
                }
                else {
                    throw new NoSuchElementException();
                }
            }
        };
    }

    /**
     * Liefert eine mehrzeilige Darstellung des Baums.
     * @return Ein mehrzeiliger String mit den Knoten des Baums.
     */
    @Override
    public String toString()
    {
        return "\n" + toString(root, "", "");
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
                : node + "\n"
                + toString(node.children[RIGHT], vertical + "├── ", vertical + "│   ") + "\n"
                + toString(node.children[LEFT], vertical + "└── ", vertical + "    "));
    }
}