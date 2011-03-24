package gui.graph;

import java.awt.Container;
import java.awt.Dimension;

import gui.FuncCalcPanel;
import gui.MainApplet;
import gui.OCButton;
import gui.OCFrame;
import gui.SubPanel;
import gui.TopLevelContainer;

public class GraphToolbar extends SubPanel{

	private MainApplet mainApp;
	private GraphWindow graphWindow;
	
	public GraphToolbar(MainApplet mainApp, TopLevelContainer topLevelComp, GraphWindow g){
		super(topLevelComp);
		this.mainApp = mainApp;
		graphWindow = g;
		OCButton integral = new OCButton("Int", 1, 1, 0, 0, this, mainApp){
			public void associatedAction() {
				for (SingleGraph s : graphWindow.getGraph().getGraphs()){
					if (s.hasFocus()){
						if (graphWindow.hasValidSelection()){
							if (s instanceof GraphedCartFunction){
								((GraphedCartFunction)s).setIntegral(graphWindow.getGraph().getSelection().getStart()
										, graphWindow.getGraph().getSelection().getEnd());
								graphWindow.repaint();
							}
						}
					}
				}
			}
		};
		
		OCButton derivative = new OCButton("Derive", 1, 1, 0, 1, this, mainApp){
			public void associatedAction() {
				for (SingleGraph s : graphWindow.getGraph().getGraphs()){
					if (s.hasFocus()){
						if (graphWindow.getGraph().getSelection().getStart() != Selection.EMPTY &&
								graphWindow.getGraph().getSelection().getEnd() == Selection.EMPTY ){
							if (s instanceof GraphedCartFunction){
								((GraphedCartFunction)s).setDerivative(graphWindow.getGraph().getSelection().getStart());
								((GraphedCartFunction)s).setDeriving(true);
								graphWindow.repaint();
							}
						}
					}
				}
			}
		};
		
		OCButton trace = new OCButton("Trace", 1, 1, 0, 2, this, mainApp){
			public void associatedAction() {
				for (SingleGraph s : graphWindow.getGraph().getGraphs()){
					if (s.hasFocus()){
						if (graphWindow.getGraph().getSelection().getStart() != Selection.EMPTY &&
								graphWindow.getGraph().getSelection().getEnd() == Selection.EMPTY ){
							if (s instanceof GraphedCartFunction){
								((GraphedCartFunction)s).setTrace(graphWindow.getGraph().getSelection().getStart());
								((GraphedCartFunction)s).setTracingPt(true);
								graphWindow.repaint();
							}
						}
					}
				}
			}
		};
		
	}
}
