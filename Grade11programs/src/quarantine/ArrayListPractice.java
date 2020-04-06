package quarantine;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import unit1.RandomWordsSecondAttempt;

/* 2020.04.06
 * James N
 * ArrayListPractice
 * Practice using array lists
 */

// random object for testing
class Box {
	int size;
	Color color;
	String whatsInside;

	Box(int size, Color color, String whatsInside) {
		this.size = size;
		this.color = color;
		this.whatsInside = whatsInside;
	}

	double returnANumber() {
		return Math.pow((double) this.size, 3.0);
	}
}

public class ArrayListPractice {

	public static void main(String[] args) {
		ArrayList<Box> boxList = new ArrayList<Box>();

		Box box = new Box(9, Color.BLUE, "ASDAASDSAD");
		boxList.add(box);

		boxList.add(new Box(4, Color.black, "cashews"));

		// add some random boxes
		int i = 0;
		do {
			boxList.add(new Box(RandomWordsSecondAttempt.rand(0, 5024),
					Color.getHSBColor((float) Math.random(), 0.5f, 0.5f), RandomWordsSecondAttempt.makeRandWord()));
			++i;
		} while (i < 5);

		// print size
		System.out.println(boxList.size());

		// same values, but not the same object
		System.out.println(boxList.contains(new Box(9, Color.BLUE, "ASDAASDSAD")));
		// same object
		System.out.println(boxList.contains(boxList.get(0)));

		// print what's in each
		for (Box b : boxList) {
			System.out.println(b.whatsInside);
		}

		// print size of each
		Iterator<Box> iter = boxList.iterator();

		while (iter.hasNext()) {
			System.out.println(iter.next().size);
		}

		// remove some boxes
		boxList.remove(3);
		boxList.remove(boxList.get(1));

		// list to array
		Box[] boxArray = new Box[boxList.size()];
		boxArray = boxList.toArray(boxArray);

		for (Box b : boxArray) {
			System.out.println(b);
		}

		// array to list
		System.out.println(Arrays.asList(boxArray));
	}

}
