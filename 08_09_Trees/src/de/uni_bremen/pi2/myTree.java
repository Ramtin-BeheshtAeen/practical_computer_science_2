package de.uni_bremen.pi2;

public class myTree<E> {
    myNode<E> root;
    public myTree(){ this(null); }
    public myTree(final myNode<E> root) {this.root = root;}

    public void printMyTree(){
        if (root == null) {
            System.out.println("Empty Tree []");
        }else {
            root.printSelfAndChildren(0);
        }
    }

    public void printMyTreeWithLines() {
        if (root == null) {
            System.out.println("Empty Tree []");
        } else {
            // Start with an empty prefix, and treat root as a "right/top" branch visually
            root.printSelfAndChildrenWithLine("", false);
        }
    }

}
