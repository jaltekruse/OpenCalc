package gui.graph;

import gui.SubPanel;

import java.awt.Color;
import java.awt.Graphics;

public class Graph {

	private SubPanel graph;
	private int X_SIZE, Y_SIZE;
	private GraphWindow graphWindow;
	
	public Graph(GraphWindow window){
		
		graphWindow = window;
		graph = new SubPanel(){
			
			public void paint(Graphics g) {
				g.setColor(Color.WHITE);
				X_SIZE = graphWindow.getWidth() * 5;
				Y_SIZE = graphWindow.getHeight() * 5;
				g.fillRect(0, 0, X_SIZE, Y_SIZE);
			}
		};
	}
}
