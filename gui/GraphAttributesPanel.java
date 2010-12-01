package gui;

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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GraphAttributesPanel extends SubPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OCTextField graphEntry;
	private Graph graph;
	private NewCalc calcObj;
	private Color color;
	private Function func;
	private JComboBox graphType;
	private String[] graphTypes;
	private OCLabel depVar;
	private OCButton indVar;
	private GraphAttributesPanel redundant;
	private JPanel colorBox;
	private JCheckBox graphing;
	private JFrame funcDataFrame; 
	private FuncCalcPanel funcCalcPanel;

	public GraphAttributesPanel(NewCalc currCalcObj, FunctionsPane fp, Color c, Function f) {
		// TODO Auto-generated constructor stub
		
		func = f;
		redundant = this;
		calcObj = currCalcObj;
		graphTypes = fp.getGraphTypes();
		color = c;
		graph = calcObj.getGraphObj();
		funcCalcPanel = new FuncCalcPanel(calcObj, func, color);

		colorBox = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void paint(Graphics g) {
				g.setColor(color);
				g.fillRect(0, 0, 10, 10);
			}
		};
		colorBox.setPreferredSize(new Dimension(10, 10));
		
		graphing = new JCheckBox();
		graphing.setSelected(true);
		graphing.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(func.isGraphing() == false)
					func.setGraphing(true);
				else
					func.setGraphing(false);
				calcObj.getGraphObj().repaint();
			}
			
		});
		
		graphType = new JComboBox(graphTypes);
		
		graphType.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){
				int type = graphType.getSelectedIndex() + 1;
				
				
				if (type == 1){
					func.setDependentVar("y");
					func.setIndependentVar("x");
					func.setGraphType(1);
				}
				
				else if (type == 2){
					func.setDependentVar("r");
					func.setIndependentVar("theta");
					func.setGraphType(2);
				}
				
				else if (type == 4){
					func.setDependentVar("U_n");
					func.setIndependentVar("n");
					func.setGraphType(4);
				}
				redundant.paint();
			}
		});
		
		this.paint();
	}
	
	private void graph(){
		if(!graphEntry.getText().equals("")){
			func.setFuncEqtn(func.getDependentVar().getName() + "=" + graphEntry.getText());
			func.setColor(color);
			func.setGraphing(true);
			graph.repaint();
			funcCalcPanel.refreshFields();
		}
		else{
			func.setGraphing(false);
			func.setFuncEqtn("");
			graph.repaint();
		}
			
	}
	public void paint(){
		this.removeAll();
		this.revalidate();
		
		
		GridBagConstraints pCon = new GridBagConstraints();
		pCon.gridx = 0;
		pCon.gridy = 0;
		pCon.gridheight = 1;
		pCon.gridwidth = 1;
		pCon.ipadx = 3;
		pCon.ipady = 3;
		this.add(graphing, pCon);
		
		pCon.gridx = 1;
		this.add(colorBox, pCon);
		
		pCon.gridx = 2;
		this.add(graphType, pCon);
		
		depVar = new OCLabel(func.getDependentVar().getName() + " =", 1, 1, 3, 0, 
				.1, .1, this, calcObj);
		
		graphEntry = new OCTextField(true, 10, 5, 1, 4, 0, this, calcObj) {
				public void associatedAction() {
					graph();
				}
		};
		
		OCButton advanced = new OCButton("adv.", 1, 1, 10, 0, this, calcObj){
			public void associatedAction() {
				if(funcDataFrame == null){
					funcDataFrame = new JFrame("Function Data");
					funcDataFrame.setPreferredSize(new Dimension(550, 120));
					
					funcDataFrame.add(funcCalcPanel);
					funcDataFrame.pack();
					
					funcDataFrame.setVisible(true);
				}
				else
					funcDataFrame.setVisible(true);
			}
		};
		
		this.revalidate();
		this.repaint();
	}

}
