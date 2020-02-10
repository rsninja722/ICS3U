package graphics;

/* 2020.02.10
 * James N
 * ShowDialog
 * shows dialog and gets input from dialog
 * 
 */

import hsa2.GraphicsConsole;
import java.awt.Color;

public class ShowDialog {

	public static void main(String[] args) {
		new ShowDialog();
	}
	
	GraphicsConsole gc = new GraphicsConsole (1000,800, "HSA2 Graphics");

	ShowDialog(){		
		gc.showDialog("task failed succesfully","error");
		System.out.println(gc.showInputDialog("type something to log","stuff"));
		gc.setColor(Color.RED);
		gc.drawRect(10, 6, 6, 6);
	}
}