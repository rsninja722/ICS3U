import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Draw4Ovals extends JPanel {

	Color color;

	public Draw4Ovals(Color color) {    
		this.color = color;
		this.setBackground(Color.BLACK);
		this.setOpaque(true);    //needed to ensure panel background is set
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);    //needed to ensure that panel background is set
		int width = getWidth();
		int height = getHeight();
		g.setColor(color);
		g.fillOval(0, 0, width, height);
	}

	public static void main(String args[]) {
		JFrame frame = new JFrame("Oval Sample");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container content = frame.getContentPane();
		content.setBackground(new Color(180,230,255));    
		content.setLayout(new GridLayout(100, 100, 5 ,5));
		// RGB = 200,0,200 <-- purple???
		// brown is dark yellow:  RGB=100,90,0
		Color colors[] = { Color.RED, new Color(200,0,200), 
		new Color(130,100,20), Color.YELLOW };
		for(Color c: colors) {
			Draw4Ovals panel = new Draw4Ovals(c);     
			content.add(panel);
		}
		//validate must be done on the component that has the .add() and the layoutManager
		content.validate();    
		frame.setSize(500, 400);
		frame.setLocation(50,100);
		frame.setVisible(true);    
		
	}
} 
