package unit1;

import java.awt.Color;

/* 2020.02.25
 * James N
 * Sort
 * sorts an array from smallest to largest
 */

import java.util.Arrays;
import java.util.Random;

import hsa2.GraphicsConsole;

public class Sort {
	
	static int arrLength = 50;
	static int barWidth = 16;
	
	static int[] arr = new int[arrLength];
	
	public static final int sw = arrLength * barWidth;
	public static final int sh = 800;

	GraphicsConsole gc = new GraphicsConsole(sw, sh);

	public static void main(String[] args) {
		new Sort();
	}
	
	public Sort() {
		setup();
		// fill array
		Random r = new Random();
		for(int i=0;i<arr.length;i++) {
			arr[i] = r.nextInt(801);
		}
		
		System.out.println(Arrays.toString(arr));
		
		int i=1; // position in array
		int pos=arr.length; // length of unsorted portion
		int memory = 0;
		
		while(pos>1) {
			// going through unsorted, find largest number
			if(arr[i] > arr[memory]) {
				memory = i;
			}
			i++;
			
			// when at end of unsorted 
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
			draw(i);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println(Arrays.toString(arr));
	}
	
	public void setup() {
		gc.setAntiAlias(true);
		gc.setLocationRelativeTo(null);
		gc.setTitle("sort");
		gc.setBackgroundColor(new Color(50,50,50));
	}
	
	public void draw(int index) {
		synchronized (gc) {
			gc.clear();
			Color mainColor = Color.getHSBColor(0.5f, 1.0f, 1.0f);
			gc.setColor(mainColor);
			for(int j=0;j<arr.length;j++) {
				if(j==index) {
					gc.setColor(Color.getHSBColor(0.5f, 1.0f, 0.5f));
				} else {
					gc.setColor(mainColor);
				}
				gc.fillRect(j*barWidth, arr[j], barWidth, sh-arr[j]);
			}
		}
	}

}
