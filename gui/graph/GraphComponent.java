package gui.graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class GraphComponent {

	protected Graph graph;
	
	public GraphComponent(Graph g){
		graph = g;
	}
	
	protected int gridXPtToScreen(double x){
		return (int) Math.round(((x - graph.X_MIN) / graph.X_PIXEL));
	}
	
	protected int gridYPtToScreen(double y){
		return (graph.Y_SIZE - graph.LINE_SIZE) - (int)Math.round((y - graph.Y_MIN) / graph.Y_PIXEL);
	}
	
	protected void setLineSize(int sizeInPixels) {
		graph.LINE_SIZE = sizeInPixels;
	}
	
	protected void ptOn(double a, double b, Graphics g) {
		if (a <= graph.X_MAX && a >= graph.X_MIN && b <= graph.Y_MAX && b >= graph.Y_MIN) {
			if (graph.LINE_SIZE == 2){
				Color c = g.getColor();
				if (g.getColor().equals(Color.black))
				{
					g.setColor(Color.gray.brighter());
				}
				else
				{
					g.setColor(g.getColor().brighter().brighter().brighter());
				}
				g.fillRect((int)Math.round((a - graph.X_MIN) / graph.X_PIXEL - graph.LINE_SIZE/2.0) - 1,
						(int)Math.round((graph.Y_SIZE - graph.LINE_SIZE/2.0) - (b - graph.Y_MIN) / graph.Y_PIXEL) - 1,
						3, 3);
				g.setColor(c);
			}
			g.fillRect((int)Math.round((a - graph.X_MIN) / graph.X_PIXEL - graph.LINE_SIZE/2.0),
					(int)Math.round((graph.Y_SIZE - graph.LINE_SIZE/2.0) - (b - graph.Y_MIN) / graph.Y_PIXEL),
					1, 1);
		}
	}

	protected void drawLineSeg(double x1, double y1, double x2, double y2,
			Color color, Graphics g) {
		//right now this ignores the LINE_SIZE currently set, but it draws much faster than before
		//I'll modify it to handle line thickness soon
		g.setColor(color);
		if (x1 > graph.X_MAX && x2 > graph.X_MAX){
			return;
		}
		if (x1 < graph.X_MIN && x2 < graph.X_MIN){
			return;
		}
		if (y1 > graph.Y_MAX && y2 > graph.Y_MAX){
			return;
		}
		if (y1 < graph.Y_MIN && y2 < graph.Y_MIN){
			return;
		}
		if (graph.LINE_SIZE == 2){
			if (color.equals(Color.black)){
				g.setColor(Color.gray.brighter());
			}
			else{
				g.setColor(color.brighter());
			}
			
			if (x1 == x2){//the line is horizontal
				g.drawLine(gridXPtToScreen(x1) - 1, gridYPtToScreen(y1), gridXPtToScreen(x2) - 1, gridYPtToScreen(y2));
				g.drawLine(gridXPtToScreen(x1) + 1, gridYPtToScreen(y1), gridXPtToScreen(x2) + 1, gridYPtToScreen(y2));
			}
			else if (y1 == y2){
				g.drawLine(gridXPtToScreen(x1), gridYPtToScreen(y1) - 1, gridXPtToScreen(x2), gridYPtToScreen(y2) - 1);
				g.drawLine(gridXPtToScreen(x1), gridYPtToScreen(y1) + 1, gridXPtToScreen(x2), gridYPtToScreen(y2) + 1);
			}
			else{
				g.drawLine(gridXPtToScreen(x1), gridYPtToScreen(y1)-1, gridXPtToScreen(x2), gridYPtToScreen(y2)-1);
				g.drawLine(gridXPtToScreen(x1), gridYPtToScreen(y1)+1, gridXPtToScreen(x2), gridYPtToScreen(y2)+1);
				
				g.drawLine(gridXPtToScreen(x1) - 1, gridYPtToScreen(y1), gridXPtToScreen(x2) - 1, gridYPtToScreen(y2));
				g.drawLine(gridXPtToScreen(x1) + 1, gridYPtToScreen(y1), gridXPtToScreen(x2) + 1, gridYPtToScreen(y2));
			}
		}
		g.setColor(color);
		g.drawLine(gridXPtToScreen(x1), gridYPtToScreen(y1), gridXPtToScreen(x2), gridYPtToScreen(y2));
	}
	
	public abstract void draw(Graphics g);
	
	public Graph getGraph(){
		return graph;
	}
}
