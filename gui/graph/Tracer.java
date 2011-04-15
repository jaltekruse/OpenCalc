package gui.graph;

import java.util.Vector;

public class Tracer extends GraphCalculation {

	public Tracer(Graph g, Vector<SingleGraph> graphs, Selection s) 
	{//trying to think of a good way to restrict the selection used to be correct
		//a single point is required, instead of a range
		super(g, graphs, s);
		// TODO Auto-generated constructor stub
	}

}
