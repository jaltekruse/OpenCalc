package gui;

import java.util.ArrayList;

public class Stroke extends DrawObj {
	private ArrayList<Integer> xPoints, yPoints;
	private int numPoints;
	
	public Stroke(){
		numPoints = 0;
		xPoints = new ArrayList<Integer>();
		yPoints = new ArrayList();
	}
	
	public ArrayList<Integer> getXPoints(){
		return xPoints;
	}
	
	public ArrayList<Integer> getYPoints(){
		return yPoints;
	}
	
	public void addPoint(int x, int y){
		xPoints.add(x);
		yPoints.add(y);
		numPoints++;
	}
	
	public int getNumPoints(){
		return numPoints;
	}
}
