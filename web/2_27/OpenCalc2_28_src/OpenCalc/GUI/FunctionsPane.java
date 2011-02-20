package GUI;

import java.awt.Color;
import java.awt.GridBagConstraints;

import javax.swing.JTabbedPane;

import calc.Function;

public class FunctionsPane extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7861065934708847322L;
	
	private Color[] graphColors = { Color.GREEN, Color.BLUE, Color.MAGENTA,
			Color.YELLOW, Color.ORANGE };
	private String[] graphTypes = {"cartesian", "polar"};
	private Graph graph;
	private Function[] functions;
	private NewCalc calcObj;
	
	public FunctionsPane(NewCalc currCalcObj){
		calcObj = currCalcObj;
		graph = calcObj.getGraphObj();
		functions = graph.getFunctions();
		this.setTabPlacement(JTabbedPane.LEFT);
		
		GridBagConstraints tCon = new GridBagConstraints();
		for (int i = 0; i < functions.length; i++) {
			this.add("fn" + (i + 1), new FuncPane(calcObj, this, graphColors[i],
					functions[i]));
		}
	}
	
	public String[] getGraphTypes(){
		return graphTypes;
	}
}
