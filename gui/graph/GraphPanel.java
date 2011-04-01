package gui.graph;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.JSplitPane;

import gui.FunctionsPane;
import gui.MainApplet;
import gui.OCTextField;
import gui.SubPanel;
import gui.TopLevelContainer;

public class GraphPanel extends SubPanel{

	private MainApplet mainApp;
	private GraphWindow graphWindow;
	private GraphToolbar toolbar;
	private GraphAndSelectionPanel graphAndSelection;
	private GraphNavigationPanel navigation;
	private OCTextField mousePt;
	private SelectionPanel selection;
	private SingleGraphsPanel graphsPanel;
	
	public GraphPanel(MainApplet mainApp, TopLevelContainer topLevelComp, int width, int height){
		super(topLevelComp);
		this.mainApp = mainApp;
		
		graphWindow = new GraphWindow(mainApp, getTopLevelContainer(), this, width, height);
		graphWindow.setMinimumSize(new Dimension(400,400));

		toolbar = new GraphToolbar(mainApp, getTopLevelContainer(), graphWindow);
		graphAndSelection = new GraphAndSelectionPanel(mainApp, getTopLevelContainer(), graphWindow);
		graphsPanel = new SingleGraphsPanel(mainApp, getTopLevelContainer(), graphWindow);
		graphsPanel.setMinimumSize(new Dimension(300,400));
		navigation = new GraphNavigationPanel(mainApp, topLevelComp, graphWindow);
		
		SubPanel functionsAndNavigation = new SubPanel(getTopLevelContainer());
		
		GridBagConstraints bCon = new GridBagConstraints();
		bCon.fill = GridBagConstraints.BOTH;
		bCon.weightx = .1;
		bCon.weighty = .1;
		bCon.gridheight = 1;
		bCon.gridwidth = 1;
		bCon.gridx = 0;
		bCon.gridy = 0;
		functionsAndNavigation.add(graphsPanel, bCon);
		
		bCon.fill = GridBagConstraints.BOTH;
		bCon.gridy = 1;
		functionsAndNavigation.add(navigation, bCon);
		
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                functionsAndNavigation, graphAndSelection);
        
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                splitPane, toolbar);
        splitPane2.setDividerLocation(850);
        
		bCon.fill = GridBagConstraints.BOTH;
		bCon.weightx = 1;
		bCon.weighty = 1;
		bCon.gridheight = 1;
		bCon.gridwidth = 1;
		bCon.gridx = 0;
		bCon.gridy = 0;
		this.add(splitPane2, bCon);
		
		
//		bCon.fill = GridBagConstraints.VERTICAL;
//		bCon.weightx = .1;
//		bCon.weighty = .1;
//		bCon.gridheight = 1;
//		bCon.gridwidth = 1;
//		bCon.gridx = 2;
//		bCon.gridy = 0;
//		this.add(toolbar, bCon);
		
//		bCon.fill = GridBagConstraints.BOTH;
//		bCon.weightx = .1;
//		bCon.weighty = .1;
//		bCon.gridheight = 1;
//		bCon.gridwidth = 2;
//		bCon.gridx = 0;
//		bCon.gridy = 1;
//		this.add(graphAndSelection, bCon);
		
	}
	
	public GraphAndSelectionPanel getBottomToolbar(){
		return graphAndSelection;
	}
}
