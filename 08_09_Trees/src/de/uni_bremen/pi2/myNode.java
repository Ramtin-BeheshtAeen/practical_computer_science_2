package de.uni_bremen.pi2;
import de.uni_bremen.pi2.myTree;
public class myNode<E>
{
    static final int LEFT  = 0;
    static final int RIGHT = 1;

    final myNode<E>[] children = (myNode<E>[]) new myNode[2];
    myNode<E>         parent;
    E                 data;

    public myNode(final E data){
        this(data, null, null );
    }

    public myNode(final E data, final myNode<E> left, final myNode<E> right ){
        children[LEFT]  = left;
        children[RIGHT] = right;

        if(left != null){
            left.parent = this; // it is safe to touch it when it is actually exist!
        }
        if (right != null){
            right.parent = this;
        }

        this.data = data;
    }

    public void printSelfAndChildren(int depth){
        //1. Right Side Go deep
      if (children[RIGHT] != null) {
          children[RIGHT].printSelfAndChildren(depth + 1);
      } else {
          printSpaces(depth + 1);
          System.out.println("[]");
      }
        // 2. Print Current Node Data
        printSpaces(depth);
        System.out.println(this.data);

        // 3. Left Side Deeper
        if (children[LEFT] != null) {
            children[LEFT].printSelfAndChildren(depth + 1);
        } else {
            printSpaces(depth + 1);
            System.out.println("[]");
        }
    }

    public void printSelfAndChildrenWithLine(String prefix, boolean isLeft){
        //
        //    ┌── 70
        //── 50
        //    └── 30
        if(children[RIGHT] != null){
            children[RIGHT].printSelfAndChildrenWithLine(prefix + (isLeft ? "│   " : "    "), false);
        }
        // 2. Print Current Node Data with its connectors
        System.out.print(prefix);
        if (this.parent == null) {
            System.out.print("── "); // Root node indicator
        } else {
            System.out.print(isLeft ? "└── " : "┌── ");
        }
        System.out.println(this.data);

        // 3. Left Side (printed last so it appears at the bottom left)
        if (children[LEFT] != null) {
            children[LEFT].printSelfAndChildrenWithLine(prefix + (isLeft ? "    " : "│   "), true);
        }

    }

    @Override
    public String toString() {
        return data.toString();
    }


    public void printSpaces(int depth){
        for (int i = 0; i < depth; i++) {
            System.out.print("    ");
        }
    }
}