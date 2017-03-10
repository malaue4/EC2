package com.company;

public class Main {

    public static void main(String[] args) {
	    LinkedListSimple<String> names = new LinkedListSimple<String>();
        names.add("Ebbe");
        names.add("Liv");
        names.add("Mikkel");

        Iterator iterator = names.getIterator();
        while (iterator.hasNext())
        {
            System.out.println(iterator.next());
        }

        String[] strings = {"kayak", "volvo", "den laks skal ned", "A man, a plan, a canal: Panama.", "Rom er ovre bakken; er vore mor?"};

		for(String string : strings) {
			String cleanString = string.toUpperCase();
			cleanString = cleanString.replaceAll("[^A-Z]", "");
			System.out.printf("%s is a%s palindrome(%s)%n", string, isPalindrome(cleanString)?"":" not", cleanString);
		}
	}

    private static boolean isPalindrome(String string) {
		char first = string.charAt(0);
		char last = string.charAt(string.length() - 1);
		if (first == last)
			if (string.length() <= 2) return true;
			else if (isPalindrome(string.substring(1, string.length() - 1))) return true;
		return false;
	}
}
