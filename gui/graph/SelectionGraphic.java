package gui.graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class SelectionGraphic extends GraphComponent {

	private Color color;
	private Selection selection;
	
	public SelectionGraphic(Graph g, Color c) {
		
		super(g);
		color = c;
		selection = new Selection();
		
	}
	
	public SelectionGraphic(Graph g, Color c, double a) {
		
		super(g);
		color = c;
		selection = new Selection();
		selection.setStart(a);
		
	}
	
	public SelectionGraphic(Graph g, Color c, double a, double b) {
		
		super(g);
		color = c;
		selection = new Selection();
		selection.setStart(a);
		selection.setEnd(a);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 100));
		if (selection.getStart() != Selection.EMPTY){
			if (selection.getEnd() != Selection.EMPTY){
				g2d.fillRect(gridxToScreen(selection.getStart()) + 3, 0, gridxToScreen(selection.getEnd()) - gridxToScreen(selection.getStart()) - 6, graph.Y_SIZE);
				g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 180));
				g2d.fillRect(gridxToScreen(selection.getStart()), 0, 3, graph.Y_SIZE);
				g2d.fillRect(gridxToScreen(selection.getEnd()) - 3, 0, 3, graph.Y_SIZE);
			}
			else{
				g2d.fillRect(gridxToScreen(selection.getStart()) - 3, 0, 5, graph.Y_SIZE);
			}
		}
		
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	public Selection getSelection(){
		return selection;
	}
	public void setStart(double d){
		selection.setStart(d);
	}

	public void setEnd(double d){
		selection.setEnd(d);
	}
	
	public double getStart(){
		return selection.getStart();
	}
	
	public double getEnd(){
		return selection.getEnd();
	}
}
