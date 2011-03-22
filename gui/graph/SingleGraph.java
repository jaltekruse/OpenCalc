package gui.graph;

import java.awt.Graphics;

public abstract class SingleGraph extends GraphComponent {
	
	public SingleGraph(Graph g){
		super(g);
	}

	@Override
	public abstract void draw(Graphics g);

}
