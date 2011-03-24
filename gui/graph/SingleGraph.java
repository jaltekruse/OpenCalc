package gui.graph;

import java.awt.Graphics;
import java.util.Vector;

public abstract class SingleGraph extends GraphComponent {
	
	private Vector<Integer> xVals;
	private Vector<Integer> yVals;
	boolean hasFocus;
	
	public SingleGraph(Graph g){
		super(g);
		xVals = new Vector<Integer>();
		yVals = new Vector<Integer>();
	}

	@Override
	public abstract void draw(Graphics g);

	public void setxVals(Vector<Integer> xVals) {
		this.xVals = xVals;
	}

	public Vector<Integer> getxVals() {
		return xVals;
	}

	public void setyVals(Vector<Integer> yVals) {
		this.yVals = yVals;
	}

	public Vector<Integer> getyVals() {
		return yVals;
	}
	
	protected void addPt(Integer x, Integer y){
		xVals.add(x);
		yVals.add(y);
	}
	
	protected void clearPts(){
		xVals = new Vector<Integer>();
		yVals = new Vector<Integer>();
	}
	
	public boolean hasFocus(){
		return hasFocus;
	}
	
	public void setfocus(boolean b){
		hasFocus = b;
	}
}
