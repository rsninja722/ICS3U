package unit1;

/* 2020.02.12
 * James N
 * Variables
 * using variables
 * 
 */

public class Variables {

	public static void main(String[] args) {
		
		int minutes,hours;
		
		double decimalHours;
		
		hours = 4;
		minutes = 30;

		decimalHours = hours + (minutes/60.0);
		
		System.out.println(hours + ":" + minutes + " in deciaml: " + decimalHours);
		
	}

}
