package de.uni_bremen.pi2;

public class Ex1<T extends Number> {
    private T content;
    /*
    Exercise 1: Create a Generic "Box" with Typebounds
    Goal: Create a class that can hold any object, but restrict it to only hold objects that are subclasses of Number.

    Task:

    Define a class MathBox<T>.

    Add a constraint so T must extend Number.

    Add a method double getValueAsDouble(T item) that returns the double value of the item.

    Try to instantiate it with String to see the compiler error.
     */
    public Ex1(T content){
        this.content = content;
    }
    public double getValueAsDouble(){
        return content.doubleValue();
    }
}
