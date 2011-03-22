package gui.graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Selection extends GraphComponent {

	double start, end;
	public static final double EMPTY = Double.MAX_VALUE;
	private Color color;
	
	public Selection(Graph g, Color c) {
		
		super(g);
		color = c;
		start = end = EMPTY;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 100));
		if (start != EMPTY){
			if (end != EMPTY){
				g2d.fillRect(gridXPtToScreen(start) + 3, 0, gridXPtToScreen(end) - gridXPtToScreen(start) - 6, graph.Y_SIZE);
				g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 180));
				g2d.fillRect(gridXPtToScreen(start), 0, 3, graph.Y_SIZE);
				g2d.fillRect(gridXPtToScreen(end) - 3, 0, 3, graph.Y_SIZE);
			}
			else{
				g2d.fillRect(gridXPtToScreen(start) - 3, 0, 5, graph.Y_SIZE);
			}
		}
		
	}
	
	public void setStart(double d){
		start = d;
	}

	public void setEnd(double d){
		end = d;
	}
	
	public double getStart(){
		return start;
	}
	
	public double getEnd(){
		return end;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
}
