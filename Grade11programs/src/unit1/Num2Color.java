package unit1;

/* 2020.02.12
 * James N
 * Num2Color
 * practice using if else
 * 
 */

public class Num2Color {

	public static void main(String[] args) {

		int a = -1;
		int b = -2;
		
		String finalMessage = new String();
		
		if (a < 0 || b < 0) {
			finalMessage += "green, ";
		}
		
		if (a + b < 0) {
			finalMessage += "navy, ";
		}
		
		if (a % 2 == 0) {
			if (b > a ) {
				finalMessage += "crimson, ";
			} else {
				finalMessage += "pink, ";
			}
		}
		
		int sum = a + b;
		
		if (sum < 10 || sum > 100) {
			finalMessage += "yellow, ";
		}
		
		if(finalMessage.length() > 0) {
			finalMessage = finalMessage.substring(0, finalMessage.length()-2);
		}
		
		System.out.println(finalMessage);
	}

}
