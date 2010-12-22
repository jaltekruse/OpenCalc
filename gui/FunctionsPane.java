package gui;



import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JTabbedPane;

import tree.EvalException;
import tree.ParseException;
import tree.ValueNotStoredException;

public class FunctionsPane extends SubPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7861065934708847322L;
	
	private Color[] graphColors = { Color.GREEN.darker(), Color.BLUE.darker(),
			Color.MAGENTA.darker(), Color.ORANGE.darker(), Color.cyan.darker(),
			Color.RED.darker()};
	private String[] graphTypes = {"cart", "pol"};
	private Graph graph;
	private Function[] functions;
	private MainApplet mainApp;
	
	public FunctionsPane(MainApplet currmainApp){
		mainApp = currmainApp;
		graph = mainApp.getGraphObj();
		functions = graph.getFunctions();
		
		GridBagConstraints pCon = new GridBagConstraints();
		pCon.gridx = 0;
		pCon.gridy = 0;
		pCon.gridheight = 1;
		pCon.gridwidth = 6;
		pCon.fill = GridBagConstraints.BOTH;
		pCon.weightx = 1;
		pCon.weighty = 1;
		
		SubPanel funcVars = new SubPanel();
		funcVars.setBorder(BorderFactory.createTitledBorder("Function Vars"));
		this.add(funcVars, pCon);
		OCButton x = new OCButton("x", 1, 1, 0, 0, funcVars, mainApp);
		OCButton theta = new OCButton("theta", 1, 1, 1, 0, funcVars, mainApp);
		
		for (int i = 0; i < functions.length; i++) {
			pCon.gridy = i + 1;
			pCon.insets = new Insets(6, 2, 6, 2);
			this.add(new GraphAttributesPanel(mainApp, this, graphColors[i], functions[i]), pCon);
		}
		OCButton graph = new OCButton("Graph", 1, 1, 0, functions.length + 1, this, mainApp){
			public void associatedAction() throws ParseException, ValueNotStoredException, EvalException{
				mainApp.getCurrTextField().primaryAction();
			}
		};
	}
	
	public String[] getGraphTypes(){
		return graphTypes;
	}
}
