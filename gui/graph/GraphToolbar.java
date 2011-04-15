package gui.graph;

import java.awt.Container;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.BorderFactory;

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
		
		this.setBorder(BorderFactory.createTitledBorder("Calc"));
		
		OCButton integral = new OCButton("Integral", 1, 1, 0, 0, this, mainApp){
			public void associatedAction() {
				for (SingleGraph sg : graphWindow.getGraph().getGraphs()){
					if (sg.hasFocus()){
						if (graphWindow.hasRangeSelection()){
							if (sg instanceof GraphedCartFunction){
								Vector<SingleGraph> tempList = new Vector<SingleGraph>();
								tempList.add(sg);
								graphWindow.getGraph().getGraphCalcGraphics().addIntegral(
										new Integral(graphWindow.getGraph(), tempList, new Selection(
										graphWindow.getGraph().getSelection().getStart(),
										graphWindow.getGraph().getSelection().getEnd())));
								
//								((GraphedCartFunction)s).setIntegral(graphWindow.getGraph().getSelection().getStart()
//										, graphWindow.getGraph().getSelection().getEnd());
								graphWindow.repaint();
							}
						}
					}
				}
			}
		};
		
		OCButton derivative = new OCButton("Derive", 1, 1, 0, 1, this, mainApp){
			public void associatedAction() {
				for (SingleGraph sg : graphWindow.getGraph().getGraphs()){
					if (sg.hasFocus()){
						if (graphWindow.getGraph().getSelection().getStart() != Selection.EMPTY &&
								graphWindow.getGraph().getSelection().getEnd() == Selection.EMPTY ){
							if (sg instanceof GraphedCartFunction){
//								System.out.println();
//								System.out.println("--------------------------------------");
//								System.out.println("Been having problems with derivatives");
//								System.out.println("drawing horizontal, going to be printing data from");
//								System.out.println("various involved classes, staritng with GraphToolbar:");
//								System.out.println("graph: " + ((GraphedCartFunction)sg).getExpression().toString());
//								System.out.println("selectionStart: " + graphWindow.getGraph().getSelection().getStart());
								Vector<SingleGraph> tempList = new Vector<SingleGraph>();
								tempList.add(sg);
								graphWindow.getGraph().getGraphCalcGraphics().addDerivative(
										new Derivative(graphWindow.getGraph(), tempList, new Selection(
										graphWindow.getGraph().getSelection().getStart())));
							}
//							((GraphedCartFunction)s).setDerivative(graphWindow.getGraph().getSelection().getStart());
//							((GraphedCartFunction)s).setDeriving(true);
							graphWindow.repaint();
						}
					}
				}
			}
		};
		
		OCButton trace = new OCButton("Trace", 1, 1, 0, 2, this, mainApp){
			public void associatedAction() {
				for (SingleGraph sg : graphWindow.getGraph().getGraphs()){
					if (sg.hasFocus()){
						if (graphWindow.getGraph().getSelection().getStart() != Selection.EMPTY &&
								graphWindow.getGraph().getSelection().getEnd() == Selection.EMPTY ){
							if (sg instanceof GraphedCartFunction){
								if (sg instanceof GraphedCartFunction){
									Vector<SingleGraph> tempList = new Vector<SingleGraph>();
									tempList.add(sg);
									graphWindow.getGraph().getGraphCalcGraphics().addTracer(
											new Tracer(graphWindow.getGraph(), tempList, new Selection(
											graphWindow.getGraph().getSelection().getStart())));
								}
//								((GraphedCartFunction)s).setTrace(graphWindow.getGraph().getSelection().getStart());
//								((GraphedCartFunction)s).setTracingPt(true);
								graphWindow.repaint();
							}
						}
					}
				}
			}
		};
		
		OCButton clear = new OCButton("Clear", 1, 1, 0, 3, this, mainApp){
			public void associatedAction() {
				graphWindow.getGraph().getGraphCalcGraphics().clearAll();
				graphWindow.repaint();
			}
			
		};
	}
}
