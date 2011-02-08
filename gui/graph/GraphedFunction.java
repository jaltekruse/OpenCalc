package gui.graph;

import java.awt.Graphics;

public abstract class GraphedFunction extends GraphComponent {
	
	public GraphedFunction(Graph g){
		super(g);
	}

	@Override
	public abstract void draw(Graphics g);

}
