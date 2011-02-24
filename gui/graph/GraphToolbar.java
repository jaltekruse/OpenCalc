package gui.graph;

import java.awt.Container;

import gui.MainApplet;
import gui.SubPanel;
import gui.TopLevelContainer;

public class GraphToolbar extends SubPanel{

	private MainApplet mainApp;
	
	public GraphToolbar(MainApplet mainApp, TopLevelContainer topLevelComp){
		super(topLevelComp);
		this.mainApp = mainApp;
	}
}
