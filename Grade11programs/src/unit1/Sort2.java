package unit1;

/* 2020.02.25
 * James N
 * Sort
 * sorts an array from smallest to largest
 */


import java.util.Arrays;
import java.util.Random;

public class Sort2 {

	public static void main(String[] args) {
		int[] arr = new int[15];
		
		// fill array
		Random r = new Random();
		for(int i=0;i<arr.length;i++) {
			arr[i] = r.nextInt(1001);
		}
		
		System.out.println(Arrays.toString(arr));
		
		
		int pos=arr.length; // length of unsorted portion
		int memory = 0;
		for (int i = 1; pos > 1; i++) {
			
			if(i==pos) {
				// swap largest with end of unsorted
				int endElem = arr[i-1];
				arr[i-1] = arr[memory];
				arr[memory] = endElem;
				// reset indexes
				memory=0;
				i=1;
				// decrease size of unsorted array
				pos--;
			}
			
			// going through unsorted, find largest number
			if(arr[i] > arr[memory]) {
				memory = i;
			}
			
			// when at end of unsorted 
			
			System.out.println(Arrays.toString(arr));
		}

		System.out.println(Arrays.toString(arr));
	}

}
