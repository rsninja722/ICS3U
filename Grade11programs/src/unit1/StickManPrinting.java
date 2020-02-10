package unit1;

import java.util.concurrent.TimeUnit;

/* 2020.02.06
 * James N
 * Stick person program
 * prints out an animated ascii stick man to the console
 * 
 */
public class StickManPrinting {

	public static void main(String[] args) throws InterruptedException {
		
		// printing with seperate lines
//		System.out.println(" _____");
//		System.out.println("( o o )");
//		System.out.println(" ▀▀┬▀▀");
//		System.out.println(" ╗ | ╔");
//		System.out.println(" ╚═╬═╝");
//		System.out.println("   █");
//		System.out.println("   █");
//		System.out.println("  / \\");
//		System.out.println(" /   \\");
//		System.out.println("█     █");
		
		// Same thing condensed to one line
		System.out.println(" _____\n( o o )\n ▀▀┬▀▀\n ╗ | ╔\n ╚═╬═╝\n   █\n   █\n  / \\\n /   \\\n█     █\n");
		
		
		// animation
		
		// integer to keep track of what loop we are on
		int i = 0;
		
		// loop 100 times so it will eventually stop on its own
		while(i < 100) {
			
			// pause for a quarter of a second each time
			TimeUnit.MILLISECONDS.sleep(250);
			// move previous picture up a bit
			System.out.println("\n\n\n\n\n");
			
			// depending on weather the  number is even or odd, draw a different frame
			if(i % 2 == 0) {
				System.out.println(" _____\n( o o )\n ▀▀┬▀▀\n ╗ |  \n ╚═╬═╗\n   █ ╚\n   █\n  / \\\n /   \\\n█     █\n");
			} else {
				System.out.println(" _____\n( o o )\n ▀▀┬▀▀\n   | ╔\n ╔═╬═╝\n ╝ █\n   █\n  / \\\n /   \\\n█     █\n");
			}
			
			// increase loop count
			i++;
		}
	}
}
