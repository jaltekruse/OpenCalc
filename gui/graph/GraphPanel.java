package gui.graph;

import java.awt.Dimension;
import java.awt.GridBagConstraints;

import gui.MainApplet;
import gui.SubPanel;

public class GraphPanel extends SubPanel{

	private MainApplet mainApp;
	private GraphWindow graphWindow;
	private GraphToolbar toolbar;
	
	public GraphPanel(int width, int height, MainApplet mainApp){
		this.mainApp = mainApp;
		
		graphWindow = new GraphWindow(width, height, mainApp);
		toolbar = new GraphToolbar(mainApp);
		
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
