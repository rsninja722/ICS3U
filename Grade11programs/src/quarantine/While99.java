package quarantine;

import java.util.Scanner;

public class While99 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		int total = 0;
		
		while(true) {
			int nextInt = scanner.nextInt();
			
			if(nextInt == -99) {
				break;
            }
            
            if(nextInt % 3 == 0 || nextInt % 5 == 0) {
				continue;
			}
			
            total += nextInt;
		}
		
		System.out.println("total: " + total);
		
		scanner.close();
	}

}