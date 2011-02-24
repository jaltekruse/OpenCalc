package gui.graph;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import gui.MainApplet;
import gui.SubPanel;
import gui.TopLevelContainer;

public class GraphPanel extends SubPanel{

	private MainApplet mainApp;
	private GraphWindow graphWindow;
	private GraphToolbar toolbar;
	
	public GraphPanel(MainApplet mainApp, TopLevelContainer topLevelComp, int width, int height){
		super(topLevelComp);
		this.mainApp = mainApp;
		
		graphWindow = new GraphWindow(mainApp, getTopLevelContainer(), width, height);
		toolbar = new GraphToolbar(mainApp, getTopLevelContainer());
		
		GridBagConstraints bCon = new GridBagConstraints();
		bCon.fill = GridBagConstraints.BOTH;
		bCon.weightx = 1;
		bCon.weighty = 1;
		bCon.gridheight = 1;
		bCon.gridwidth = 1;
		bCon.gridx = 0;
		bCon.gridy = 0;
		this.add(graphWindow, bCon);
		
		
		bCon.fill = GridBagConstraints.BOTH;
		bCon.weightx = .1;
		bCon.weighty = .1;
		bCon.gridheight = 1;
		bCon.gridwidth = 2;
		bCon.gridx = 0;
		bCon.gridy = 1;
		this.add(toolbar, bCon);
		
	}
}
