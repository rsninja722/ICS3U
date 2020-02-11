package unit1;

/* 2020.02.11
 * James N
 * VowelCount
 * takes in a string and outputs the amount of vowels (excluding y)
 * 
 */

import java.util.Scanner;

public class vowelCount {

	public static void main(String[] args) {
		
		char[] vowels = {'a','e','i','o','u'};
		
		Scanner s = new Scanner(System.in);

		System.out.print("enter text to count vowels of: ");
		String sentance = s.nextLine();
		
		short vowelCount = 0;
		
		for(int i=0; i<sentance.length(); i++) {
			
			boolean isVowel = false;
			
			for(int j=0; j<vowels.length; j++) {
				
				if(sentance.charAt(i) == vowels[j]) {
					isVowel = true;
					break;
				}
				
			}
			
			if(isVowel) {
				vowelCount++;
			}
			
		}
		
		System.out.println("Amount of vowels: " + vowelCount);
		
		s.close();
		
	}

}
