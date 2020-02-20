package unit1;

/* 2020.02.19
 * James N
 * Triplets
 * finds and prints words with 3 letters in a row
 * 
 */

import java.util.Scanner;

public class Triplets {
	
	static String sentanceDefault = "Thiiis is a sentttance with words wiiith extrrra  tesst letteeetrs.";

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		
		System.out.println("enter a sentance, if nothing is entered, the default will be used ");
		String sentance = s.nextLine();
		
		// if nothing entered, set to default
		if(sentance.equals("")) {
			sentance = sentanceDefault;
		}
		
		String repeatedWords = "Repeated words: ";
		
		char lastChar = 0;
		int repeatCount = 0;
		// go through sentence
		for(int i = 0;i<sentance.length();i++) {
			// cache word
			char currentChar = sentance.charAt(i);
			// increment count if same letter
			if(currentChar == lastChar) {
				repeatCount++;
			} else {
				repeatCount = 0;
			}
			
			// if a letter repeats twice (3 in a row) find and add word to repeated words
			if(repeatCount == 2) {
				// find start
				int wordStart = i-1;
				while(wordStart >= 0 && sentance.charAt(wordStart) != ' ') { wordStart--; }
				// find end
				int wordEnd = i;
				while(wordEnd < sentance.length()-1 && sentance.charAt(wordEnd) != ' ') { wordEnd++; }
				
				// add word
				repeatedWords += sentance.substring(wordStart+1,wordEnd) + " ";

				// reset count
				repeatCount = 0;
				
			}
			
			// set last word to current word
			lastChar = currentChar;
		}
		
		System.out.println(repeatedWords);
	}

}