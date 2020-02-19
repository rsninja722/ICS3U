package unit1;

/* 2020.02.19
 * James N
 * RandomWord
 * create a random word 6 letters long with 1 or 2 vowels
 * 
 */

public class RandomWordsFirstAttempt {

	public static int vowelLimit = 2;
	public static int vowelMinimum = 1;

	public static int wordLength = 6;

	public static String vowels = "aeiou";
	public static String consonants = "qwrtypsdfghjklzxcvbnm";

	public static void main(String[] args) {

		for (int j = 0; j < 10; j++) {

			// characters added to this at the start
			String unscrambledString = "";

			// chars pulled from unscrambledString into this
			String finalString = "";

			// amount of vowels to add
			int vowelAmount = rand(vowelMinimum, vowelLimit);

			// add vowels
			for (int i = 0; i < vowelAmount; i++) {
				unscrambledString += vowels.charAt(rand(0, vowels.length() - 1));
			}

			// add characters
			for (int i = vowelAmount; i < wordLength; i++) {
				unscrambledString += consonants.charAt(rand(0, consonants.length() - 1));
			}

			while (unscrambledString.length() > 0) {
				if (rand(0, 1) == 1) {
					// chop off first letter
					finalString += unscrambledString.charAt(0);
					unscrambledString = unscrambledString.substring(1);
				} else {
					// chop of last letter
					int end = unscrambledString.length() - 1;
					finalString += unscrambledString.charAt(end);
					unscrambledString = unscrambledString.substring(0, end);
				}
			}

			System.out.println(finalString);

		}

	}

	public static int rand(int min, int max) {
		return (int) (Math.floor(Math.random() * (max - min + 1)) + min);
	}

}
