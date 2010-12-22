package gui.graph;

import java.awt.Dimension;

import gui.MainApplet;
import gui.SubPanel;

public class GraphWindow extends SubPanel{
	
	private MainApplet mainApp;
	
	public GraphWindow(int xSize, int ySize, MainApplet mainApp){
		this.setPreferredSize(new Dimension(xSize, ySize));
		this.mainApp = mainApp;
	}
	
	public int getWidth(){
		return this.getWidth();
	}
	
	public int getHeight(){
		return this.getHeight();
	}
	
}
