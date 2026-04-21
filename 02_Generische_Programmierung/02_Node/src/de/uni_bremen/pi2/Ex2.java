package de.uni_bremen.pi2;
/*


When you write public class ArrayUtils,
        you are making a standard, non-generic class.
However, you can still have Generic Methods inside it.
 */
public class Ex2{
    /*
    Exercise 2: Implementing a Generic Method
Goal: Write a standalone method that works with different types of arrays.

Task:

Write a static method called swap.

It should take an array of any type T[] and two integers (indices).

The method should swap the elements at those two indices.

Test it with an Integer[] array and a String[] array.
 */

    // The <T> here makes ONLY this method generic
    //When a method is static, you don't need to create an instance of the class (using new) to use it.
    public static <T> void swap(T[] array, int i, int j){
        T temp   = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
