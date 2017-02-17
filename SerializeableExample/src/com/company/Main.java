package com.company;

import java.io.*;

public class Main {

	public static void main(String[] args) {
		Recipe recipe = new Recipe("Recipe2", "Something and...", "don't bake it");
		serialize(recipe);

		Recipe deserializedRecipe  = deserialize();
		System.out.println(deserializedRecipe.title);
		System.out.println(deserializedRecipe.indgredients);
		System.out.println(deserializedRecipe.text);

	}

	public static void serialize(Recipe recipe) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream("myObj.ser");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(recipe);
		} catch (FileNotFoundException e) {
			System.err.println("The file wasn't found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("An IO exception occurred");
			e.printStackTrace();
		}
	}

	public static Recipe deserialize() {
		Recipe recipe = null;
		try {
			FileInputStream fileInputStream = new FileInputStream("myObj.ser");
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			recipe = (Recipe) objectInputStream.readObject();
		} catch (FileNotFoundException e) {
			System.err.println("File was not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("File failed to load");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("The corresponding class could not be found");
			e.printStackTrace();
		}
		return recipe;
	}
}