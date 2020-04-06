package unit1;

/* 2020.02.19
 * James N
 * RandomWord
 * create a random word 6 letters long with 1 or 2 vowels
 * 
 */

public class RandomWordsSecondAttempt {

	public static int vowelLimit = 2;
	public static int vowelMinimum = 1;

	public static int wordLength = 6;

	public static String vowels = "aeiou";
	public static String consonants = "qwrtypsdfghjklzxcvbnm";

	public static void main(String[] args) {

		// print 10 words 
		for (int j = 0; j < 10; j++) {

			// print word
			System.out.println(makeRandWord());

		}
		
	}

	public static int rand(int min, int max) {
		return (int) (Math.floor(Math.random() * (max - min + 1)) + min);
	}
	
	public static String makeRandWord() {
		// initialize word of correct length 
		char[] word = new char[wordLength];

		// fill word will consonants
		for (int i = 0; i < wordLength; i++) {
			word[i] = consonants.charAt(rand(0, consonants.length() - 1));
		}

		// determine amount of vowels to add
		int vowelCount = rand(vowelMinimum, vowelLimit);	
		// add vowels at random place
		for (int i = 0; i < vowelCount; i++) {
			word[rand(0, wordLength - 1)] = vowels.charAt(rand(0, vowels.length() - 1));
		}
		
		return new String(word);
	}
}
