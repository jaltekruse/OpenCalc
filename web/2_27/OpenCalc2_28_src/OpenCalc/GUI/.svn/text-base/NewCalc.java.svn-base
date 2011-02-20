package GUI;

/*
 OpenCalc is a Graphing Calculator for the web.
 Copyright (C) 2009, 2010 Jason Altekruse

 This file is part of OpenCalc.

 OpenCalc is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 OpenCalc is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with OpenCalc  If not, see <http://www.gnu.org/licenses/>.
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyListener;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import calc.VarStorage;
import calc.calc;


public class NewCalc extends JApplet{

	private static final long serialVersionUID = 1L;
	private calc CALC1;
	private JScrollPane varScrollPane, constScrollPane;
	private OCTextField textWithFocus;
	private JPanel graph;
	private CalcPanel text;
	private ElmStoragePanel varPanel, constPanel;
	private JTabbedPane graphCalcDraw, graphProps, mathFunc;
	private GridPropsPanel gridProps;
	private Graph g;
	private KeyListener keys;
	private static int textWithFocusCaretPos;
	private static JFrame frame;
	private int xSize;
	private int ySize;


	/**
	 * 
	 */

	public NewCalc() {
		
		super.setLayout(new GridBagLayout());
		textWithFocus = new OCTextField();
		
		Dimension halfApplet = new Dimension(360, 360);
		graph = new JPanel();
		CALC1 = new calc(this);
		
		g = new Graph(360, 360, this);
		g.setPreferredSize(halfApplet);
		graph.setPreferredSize(halfApplet);
		graph.add(g);
		
		graphCalcDraw = new JTabbedPane(JTabbedPane.BOTTOM);

		NumsAndOppsPanel Nums = new NumsAndOppsPanel(this);

		mathFunc = new JTabbedPane();
		mathFunc.setPreferredSize(halfApplet);
		varPanel = new ElmStoragePanel(this, CALC1.getVarList());
		varScrollPane = new JScrollPane(varPanel);
		constPanel = new ElmStoragePanel(this, CALC1.getConstantList());
		constScrollPane = new JScrollPane(constPanel);

		mathFunc.add(varScrollPane, "Vars");
		mathFunc.add(new TrigPanel(this), "Trig");
		mathFunc.add(constScrollPane, "Const");

		text = new CalcPanel(this);

		graphProps = new FunctionsPane(this);
		/*
		graphProps = new JTabbedPane();
		graphProps.add("Graphs", new JScrollPane(new GraphFuncPanel(this)));

		JPanel gridProps = new GridPropsPanel(this);
		graphProps.add("Grid", gridProps);
		*/

		graphCalcDraw.add("Graph", graph);
		graphCalcDraw.add("Calculator", text);
		graphCalcDraw.add("Draw", new DrawPad(360, 360,this));
		gridProps = new GridPropsPanel(this);
		graphCalcDraw.add("Grid", gridProps);
		graphCalcDraw.addChangeListener(new ChangeListener(){
			
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				int selected = 	graphCalcDraw.getSelectedIndex();
				String nameSelected = graphCalcDraw.getTitleAt(selected);
				if (nameSelected.equals("Calculator")){
					setCurrTextField(text.getTextTerminal());
				}
				else if(nameSelected.equals("Grid")){
					getGridProps().refreshAttributes();
				}
			}
		});

		GridBagConstraints pCon = new GridBagConstraints();
		pCon.gridheight = 6;
		pCon.gridwidth = 4;
		pCon.gridx = 0;
		pCon.gridy = 0;
		pCon.fill = GridBagConstraints.BOTH;
		pCon.weightx = .9;
		pCon.weighty = .8;

		this.add(graphCalcDraw, pCon);

		pCon.gridheight = 2;
		pCon.gridwidth = 4;
		pCon.gridx = 4;
		pCon.gridy = 0;
		pCon.weightx = .7;
		pCon.weighty = .4;
		this.add(mathFunc, pCon);

		pCon.gridheight = 4;
		pCon.gridwidth = 4;
		pCon.gridx = 4;
		pCon.gridy = 2;
		pCon.weighty = .2;
		this.add(Nums, pCon);

		pCon.gridheight = 3;
		pCon.gridwidth = 8;
		pCon.gridx = 0;
		pCon.gridy = 6;
		pCon.weighty = .8;
		this.add(graphProps, pCon);
		this.repaint();
	}

	public int getFocusedComp() {
		int currPos = graphCalcDraw.getSelectedIndex();
		return currPos;
	}

	public void updateGraph() {
		g.repaint();
	}

	public void updateGraph(String func) {
		g.repaint();
	}

	public calc getBasicCalc() {
		return CALC1;
	}

	public OCTextField getCurrTextField() {
		return textWithFocus;
	}
	
	public ElmStoragePanel getVarsPanel(){
		return varPanel;
	}

	public void setCurrTextField(OCTextField focused) {
		if (!textWithFocus.equals(focused)) {
			textWithFocus.associatedAction();
			textWithFocus = focused;
			textWithFocus.requestFocus();
		}
	}

	public int getCurrCaretPos() {
		return textWithFocusCaretPos;
	}

	public void setCurrCaretPos(int pos) {
		textWithFocusCaretPos = pos;
	}

	public void addToCaretPos(int i) {
		textWithFocusCaretPos += i;
		updateCaretPos();
	}

	public void updateCaretPos() {
		textWithFocus.setCaretPosition(textWithFocusCaretPos);
	}

	public calc getCalcObj() {
		return CALC1;
	}

	public VarStorage getVarsObj() {
		// TODO Auto-generated method stub
		return CALC1.getVarList();
	}

	public Graph getGraphObj() {
		return g;
	}
	
	public GridPropsPanel getGridProps(){
		return gridProps;
	}

	public String evalCalc(String eqtn) {
		String tempString = new String();
		double ans;
		CALC1.parse(eqtn);
		ans = CALC1.solve();
		if (ans == Double.MAX_VALUE)
			return "error";
		else
			tempString += ans;
		return tempString;
	}

	private static void createAndShowGUI() {

		frame = new JFrame("OpenCalc");
		Dimension frameDim = new Dimension(640, 480);
		frame.setPreferredSize(frameDim);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		NewCalc currCalc = new NewCalc();
		frame.add(currCalc);

		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
