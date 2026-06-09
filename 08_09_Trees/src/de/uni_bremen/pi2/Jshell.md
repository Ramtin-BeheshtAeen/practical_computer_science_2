import de.uni_bremen.pi2.*;

    final Tree<Integer> tree = new Tree<>(
            new Node<>(1,
                    new Node<>(2,
                            new Node<>(4),
                            new Node<>(5,
                                    new Node<>(6),
                                    new Node<>(7)
                            )
                    ),
                    new Node<>(3)
            )
    );

    System.setOut(new java.io.PrintStream(System.out, true, java.nio.charset.StandardCharsets.UTF_8));
    System.out.println(tree.toString());


    final myTree<Integer> tree2 = new myTree<>(
            new myNode<>(1,
                    new myNode<>(2,
                            new myNode<>(4),
                            new myNode<>(5,
                                    new myNode<>(6),
                                    new myNode<>(7)
                            )
                    ),
                    new myNode<>(3)
            )
    );

    tree2.printMyTree();
    tree2.printMyTreeWithLines();