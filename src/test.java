import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import domain.Food;
import domain.Ingredients;
import domain.Recipe;

public class test {

	public static void main(String[] args) throws NumberFormatException, IOException {
		String s = "0123-45-67";
		System.out.println(s.substring(0, 4));
		System.out.println(s.substring(5, 7));
		System.out.println(s.substring(8, 10));
	}
}