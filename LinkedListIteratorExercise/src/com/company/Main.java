package com.company;

public class Main {

    public static void main(String[] args) {
	    LinkedListSimple<String> strings = new LinkedListSimple<String>();
        strings.add("Ebbe");
        strings.add("Liv");
        strings.add("Mikkel");

        Iterator iterator = strings.getIterator();
        while (iterator.hasNext())
        {
            System.out.println(iterator.next());
        }

    }
}
