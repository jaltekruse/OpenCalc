package gui.graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class GraphComponent {

	protected Graph graph;
	
	public GraphComponent(Graph g){
		graph = g;
	}
	
	protected int gridxToScreen(double x){
		return (int) Math.round(((x - graph.X_MIN) / graph.X_PIXEL));
	}
	
	protected double screenxToGrid(int x){
		return x * graph.X_PIXEL + graph.X_MIN;
	}
	
	protected double screenyToGrid(int y){
		return y * graph.Y_PIXEL + graph.Y_MIN;
	}
	protected int gridyToScreen(double y){
		return (graph.Y_SIZE - graph.LINE_SIZE) - (int)Math.round((y - graph.Y_MIN) / graph.Y_PIXEL);
	}
	
	protected void setLineSize(int sizeInPixels) {
		graph.LINE_SIZE = sizeInPixels;
	}
	
	protected void drawTracer(double x, double y, Graphics g) {
		g.drawOval(gridxToScreen(x) - 5, gridyToScreen(y) - 5, 10, 10);
		g.fillOval(gridxToScreen(x) - 3, gridyToScreen(y) - 3, 6, 6);
	}
	
	protected void ptOn(double a, double b, Graphics g) {
		if (a <= graph.X_MAX && a >= graph.X_MIN && b <= graph.Y_MAX && b >= graph.Y_MIN) {
			if (graph.LINE_SIZE == 2){
				Color c = g.getColor();
//				if (g.getColor().equals(Color.black))
//				{
//					g.setColor(Color.gray.brighter());
//				}
//				else
//				{
					g.setColor(g.getColor().brighter());
//				}
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
	
	protected void drawTangent(double x, double y, double m, Color c, Graphics g){
		int length = 15;
		int screenX = gridxToScreen(x);
		int screenY = gridyToScreen(y);
		double xChange = 0, yChange = 0;
		
		double angle = Math.atan( Math.abs(m));
		
		System.out.println();
		System.out.println("class graphComponent (drawTan):");
		System.out.println("angle (radians): " + angle);
		
		xChange = (Math.cos(angle) * length);
		yChange = (Math.sin(angle) * length);
		
		graph.LINE_SIZE = 3;
		
		if (m > 0){
			drawLineSeg(x, y, x + xChange * graph.X_PIXEL, y + yChange * graph.Y_PIXEL, c , g);
			drawLineSeg(x, y, x - xChange * graph.X_PIXEL, y - yChange * graph.Y_PIXEL, c , g);
		}
		else{
			drawLineSeg(x, y, x + xChange * graph.X_PIXEL, y - yChange * graph.Y_PIXEL, c , g);
			drawLineSeg(x, y, x - xChange * graph.X_PIXEL, y + yChange * graph.Y_PIXEL, c , g);
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
		if (graph.LINE_SIZE > 1){
//			if (color.equals(Color.black)){
//				g.setColor(Color.gray.brighter());
//			}
//			else{
				g.setColor(color.brighter());
//			}

			g.drawLine(gridxToScreen(x1), gridyToScreen(y1)-1, gridxToScreen(x2), gridyToScreen(y2)-1);
			g.drawLine(gridxToScreen(x1), gridyToScreen(y1)+1, gridxToScreen(x2), gridyToScreen(y2)+1);
			
			g.drawLine(gridxToScreen(x1) - 1, gridyToScreen(y1), gridxToScreen(x2) - 1, gridyToScreen(y2));
			g.drawLine(gridxToScreen(x1) + 1, gridyToScreen(y1), gridxToScreen(x2) + 1, gridyToScreen(y2));
			
			if (graph.LINE_SIZE > 2){
				g.drawLine(gridxToScreen(x1), gridyToScreen(y1)-2, gridxToScreen(x2), gridyToScreen(y2)-2);
				g.drawLine(gridxToScreen(x1), gridyToScreen(y1)+2, gridxToScreen(x2), gridyToScreen(y2)+2);
				
				g.drawLine(gridxToScreen(x1) - 2, gridyToScreen(y1), gridxToScreen(x2) - 2, gridyToScreen(y2));
				g.drawLine(gridxToScreen(x1) + 2, gridyToScreen(y1), gridxToScreen(x2) + 2, gridyToScreen(y2));
			}
		}
		g.setColor(color);
		g.drawLine(gridxToScreen(x1), gridyToScreen(y1), gridxToScreen(x2), gridyToScreen(y2));
	}
	
	public abstract void draw(Graphics g);
	
	public Graph getGraph(){
		return graph;
	}
}
