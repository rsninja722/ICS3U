package unit1;

/* 2020.02.11
 * James N
 * Scanning
 * use scanning to make a fast, powerful, lightweight, user friendly calculator
 * 
 */

import java.util.Scanner;

public class Scanning {

	public static void main(String[] args) throws InterruptedException {
		Scanner s = new Scanner(System.in);

		// name
		System.out.println("Welcome to calculator. enter name: ");
		String name = s.next();
		
		// password
		System.out.println("Please enter a password " + name + ": ");
		String pass = s.next();
		
		Thread.sleep(1000);
		System.out.println("new account registered for calculator");
		Thread.sleep(1000);
		
		// number 1
		System.out.println("enter the first number to add: ");
		float num1 = s.nextFloat();
		
		System.out.println("storing number...");
		Thread.sleep(1000);
		
		// number 2
		System.out.println("enter the second number to add: ");
		float num2 = s.nextFloat();
		
		System.out.println("calculating...");
		Thread.sleep(2500);
		
		// result
		System.out.println("here is the result " + name + ": " + (num1 + num2));
		
		s.close();
		
	}

}
