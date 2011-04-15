package gui.graph;

import java.util.Vector;

import tree.EvalException;

public class Integral extends GraphCalculation {

	public Integral(Graph g, Vector<SingleGraph> graphs, Selection s) {
		super(g, graphs, s);
		double result;
		try {
			result = ((GraphWithExpression)graphs.get(0)).getExpression().integrate(
					getSelection().getStart(), getSelection().getEnd(),"x", "y").toDec().getValue();
			setResult(result);
		} catch (EvalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}

}
