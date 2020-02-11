package unit1;

/* 2020.02.11
 * James N
 * ThridWord
 * takes in a string and outputs every third word
 * 
 */

import java.util.Scanner;

public class ThridWord {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		System.out.println("enter sentance to split");
		String sentance = s.nextLine();
		
		String[] words = sentance.split(" ");
		
		String finalMessage = new String();
		for(int i=0; i<words.length; i++) {
			if(i%3 == 2) {
				finalMessage += words[i] + " ";
			}
		}

		System.out.println("every third word: " + finalMessage);
		
		s.close();

	}

}
