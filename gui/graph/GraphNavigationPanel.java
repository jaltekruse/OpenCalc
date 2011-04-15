package gui.graph;

import java.awt.GridBagConstraints;

import javax.swing.JPanel;

import tree.EvalException;
import gui.MainApplet;
import gui.OCButton;
import gui.TopLevelContainer;
import gui.SubPanel;

public class GraphNavigationPanel extends SubPanel {

	GraphWindow graphWindow;
	private MainApplet mainApp;
	
	public GraphNavigationPanel(MainApplet mainApp, TopLevelContainer topLevelComp, GraphWindow gw) {
		super(topLevelComp);
		graphWindow = gw;
		this.mainApp = mainApp;

		OCButton up = new OCButton("^", 2, 1, 1, 0, this, mainApp){
			public void associatedAction(){
				graphWindow.getGraph().shiftGraph(0, 30);
			}
		};
		OCButton left = new OCButton("<", 1, 2, 0, 1, this, mainApp){
			public void associatedAction(){
				graphWindow.getGraph().shiftGraph(-30, 0);
			}
		};
		
		OCButton right = new OCButton(">", 1, 2, 3, 1, this, mainApp){
			public void associatedAction(){
				graphWindow.getGraph().shiftGraph(30, 0);
			}
		};
		
		OCButton down = new OCButton("v", 2, 1, 1, 3, this, mainApp){
			public void associatedAction(){
				graphWindow.getGraph().shiftGraph(0, -30);
			}
		};
		
		OCButton zoomPlus = new OCButton("+", 1, 2, 1, 1, this, mainApp){
			public void associatedAction(){
				try {
					graphWindow.getGraph().zoom(120);
				} catch (EvalException e) {
					// TODO Auto-generated catch block
					//think of something to do here
				}
			}
		};
		
		OCButton zoomMinus = new OCButton("-", 1, 2, 2, 1, this, mainApp){
			public void associatedAction(){
				try {
					graphWindow.getGraph().zoom(80);
				} catch (EvalException e) {
					// TODO Auto-generated catch block
					//need something here too
				}
			}
		};
		
		OCButton properties = new OCButton("Properties", 1, 4, 4, 0, this, mainApp){
			public void associatedAction(){
				graphWindow.showGridPropsWindow();
			}
		};
		
		
	}

}
