package com.company;

import java.io.Serializable;

public class Recipe implements Serializable {
	public String title;
	public String indgredients;
	public String text;

	public Recipe(String title, String ingredients, String text)
	{
		this.indgredients = ingredients;
		this.text = text;
		this.title = title;
	}
}
