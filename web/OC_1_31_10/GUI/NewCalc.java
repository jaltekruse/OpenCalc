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
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.BorderFactory; 

import calc.Var2;
import calc.calc;

public class NewCalc extends JApplet implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private calc CALC1;
	private JTextArea terminal;
    private JScrollPane termScrollPane, varScrollPane;
	private JTextField textWithFocus2, entryLine, graphEntry, startInt, endInt, point2Trace, yVal,intVal;
	private OCTextField textWithFocus;
	private JPanel text, graph, graphs, graphCalcs, gridProps; 
	private JTabbedPane graphCalcDraw, graphProps, mathFunc;
	private Graph g;
	private boolean degRad;
	private static int textWithFocusCaretPos;
	private static JFrame frame;
	private Component focusedComp;
	
	/**
	 * 
	 */

	public NewCalc(){
		//constraints must be used for all objects, they set placement and size of all objects
		//all constraints are a part of the standard GridBagLayout in java swing
		//bCon == button constraints
		//pCon == panel constraints
		//tCon == text constraints

		super.setLayout(new GridBagLayout());
		textWithFocus = new OCTextField();
		
		graph = new JPanel();
		CALC1 = new calc(this);
		g = new Graph(320,320, this);
		termScrollPane = new JScrollPane(terminal);
		graphCalcDraw = new JTabbedPane(JTabbedPane.BOTTOM);
		GridBagConstraints bCon = new GridBagConstraints();

		NumsAndOppsPanel Nums = new NumsAndOppsPanel(this);
		
		mathFunc = new JTabbedPane();
		varScrollPane = new JScrollPane(new VarsPanel(this));
		
		mathFunc.add(varScrollPane, "Vars");
		mathFunc.add(new TrigPanel(this), "Trig");
		
		text = new CalcPanel(this);
		Font terminalFont = new Font("newFont", 1, 15);
		GridBagConstraints tCon = new GridBagConstraints();
		
		graphProps = new JTabbedPane();
		
		graphProps.add("Graphs", new JScrollPane(new GraphFuncPanel(this)));
		
		startInt = new JTextField(5);
		startInt.setFont(terminalFont);
		startInt.setText("startInt");
		
		endInt = new JTextField(5);
		endInt.setFont(terminalFont);
		endInt.setText("endInt");
		
		intVal = new JTextField(10);
		intVal.setFont(terminalFont);
		intVal.setEditable(false);
		
		JButton setInt = new JButton("Integrate");
		setInt.addActionListener(this);
		setInt.setActionCommand("findInt");
		
		point2Trace = new JTextField(7);
		point2Trace.setFont(terminalFont);
		point2Trace.setText("point2Trace");
		point2Trace.addActionListener(this);
		point2Trace.setActionCommand("point2Trace");
		
		yVal = new JTextField(15);
		yVal.setEditable(false);
		
		graphCalcs = new JPanel(new GridBagLayout());
		graphCalcs.setBorder(BorderFactory.createTitledBorder("Graph Options"));
		
		graphCalcs.add(point2Trace);
		graphCalcs.add(yVal);
		graphCalcs.add(startInt);
		graphCalcs.add(endInt);
		graphCalcs.add(setInt);
		graphCalcs.add(intVal);
		
		graphProps.add("Calc", new GraphCalcPanel(this));
		
		JPanel gridProps = new GridPropsPanel(this);
		graphProps.add("Grid", gridProps);
		
		Dimension graphSize = new Dimension(320,320);
		g.setPreferredSize(graphSize);
		graph.add(g);
		
		graphCalcDraw.add("Graph",graph);
		graphCalcDraw.add("Calculator", text);
		
		GridBagConstraints pCon = new GridBagConstraints();
		pCon.gridheight = 6;
		pCon.gridwidth = 4;
		pCon.gridx = 0;
		pCon.gridy = 0;
		pCon.fill = GridBagConstraints.BOTH;
		pCon.weightx = 1;
		pCon.weighty = 1;
		
		this.add(graphCalcDraw, pCon);
		
		pCon.gridheight = 2;
		pCon.gridwidth = 4;
		pCon.gridx = 4;
		pCon.gridy = 0;
		pCon.weightx = .3;
		pCon.weighty = .3;
		this.add(mathFunc, pCon);
		
		pCon.gridheight = 4;
		pCon.gridwidth = 4;
		pCon.gridx = 4;
		pCon.gridy = 2;
		this.add(Nums, pCon);
		
		pCon.gridheight = 3;
		pCon.gridwidth = 8;
		pCon.gridx = 0;
		pCon.gridy = 6;
		this.add(graphProps, pCon);
	}
	
	public int getFocusedComp(){
		int currPos = graphCalcDraw.getSelectedIndex();
		return currPos;
	}
	
	public JTextField getCalcText(){
		return entryLine;
	}
	
	public void updateGraph(){
		g.repaint();
	}
	
	public void updateGraph(String func){
		g.SetGraph(func);
		g.repaint();
	}
	
	public calc getBasicCalc(){
		return CALC1;
	}
	
	public void setCalcText(String s){
		entryLine.setText(s);
	}
	
	public JTextField getCurrTextField(){
		return textWithFocus;
	}
	
	public void setCurrTextField(OCTextField focused){
		if (!textWithFocus.equals(focused)){
			textWithFocus.associatedAction();
			textWithFocus = focused;
			textWithFocus.requestFocus();
		}
	}
	
	public void setCurrTextField(JTextField focused){
		textWithFocus2 = focused;
	}
	
	public int getCurrCaretPos(){
		return textWithFocusCaretPos;
	}
	
	public void setCurrCaretPos(int pos){
		textWithFocusCaretPos = pos;
	}
	
	public void addToCaretPos(int i){
		textWithFocusCaretPos += i;
		updateCaretPos();
	}
	
	public void updateCaretPos(){
		textWithFocus.setCaretPosition(textWithFocusCaretPos);
	}
	
	public calc getCalcObj(){
		return CALC1;
	}
	
	public Var2 getVarsObj() {
		// TODO Auto-generated method stub
		return CALC1.getVarList();
	}
	
	public Graph getGraphObj(){
		return g;
	}
	
	public String evalCalc(String eqtn){
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
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if("findInt".equals(e.getActionCommand())){
			double a = Double.parseDouble(startInt.getText());
			double b = Double.parseDouble(endInt.getText());
			g.setIntegral(true, a, b);
			g.repaint();
			CALC1.parse(g.getFunc(1));//set custom number for multiple graphs
			String integral = new String();
			integral += (float) CALC1.integrate(a, b);
			intVal.setText(integral);
		}
		else if ("entry".equals(e.getActionCommand())){
			String eqtn = entryLine.getText();
			terminal.append(">  " + eqtn + "\n");
			String result = new String();
			result += evalCalc(eqtn);
			terminal.append(result + "\n");
			entryLine.setText("");
		}
		
		else if ("graph".equals(e.getActionCommand())){
			g.SetGraph(graphEntry.getText());
			g.repaint();
		}
		
		else if ("point2Trace".equals(e.getActionCommand())){
			if (point2Trace.getText().equals(""))
				g.setTracePt(false, 0);
			else{
				CALC1.parse(point2Trace.getText());
				double xVal = CALC1.solve();
				g.setTracePt(true, xVal);
				String xString = new String();
				xString += xVal;
				point2Trace.setText(xString);
				CALC1.VARLIST.setVarVal("x", xVal);
				CALC1.parse(g.getFunc(1));//func to change for multiple graphs
				double yValue = CALC1.solve();
				String yString = new String();
				yString += yValue;
				yVal.setText(yString);
				g.repaint();
			}
		}
	}
	
	private static void createAndShowGUI(){

		frame = new JFrame("OpenCalc");
		Dimension frameDim = new Dimension(800,500);
		frame.setPreferredSize(frameDim);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		NewCalc currCalc = new NewCalc();
		frame.add(currCalc);

        frame.pack();
        frame.setVisible(true);
	}
	
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
	    javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run(){
				createAndShowGUI();
			}
			
	    });
    }
}
