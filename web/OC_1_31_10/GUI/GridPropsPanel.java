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


import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;


public class GridPropsPanel extends SubPanel{
	
	NewCalc calcObj;
	OCTextWithValButton xMin, xMax, yMin, yMax;
	OCButton defaultGrid;
	
	public GridPropsPanel(NewCalc currCalcObj){
		
		this.setLayout(new GridBagLayout());
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		calcObj = currCalcObj;
		
		//OCTextWithValButton(String s, boolean editable, int length, 
				//int gridWidth, int gridHeight, int gridx, int gridy,
				//JComponent comp, final NewCalc currCalcObj){
		
		xMin = new OCTextWithValButton("xMin", true, 5, 3, 1, 0, 0, this, calcObj){
			public void actionPerformed(){
				String currText = field.getText();
				String ansText = new String();
				if (!currText.equals(null) && !currText.equals("") && calcObj != null){
					JTextField currField = calcObj.getCurrTextField();
					ansText = calcObj.evalCalc(currText);
					currField.setText(ansText);
					if (!ansText.equals("error")){
						if(Double.valueOf(ansText) < calcObj.getVarsObj().getVarVal("xMax")){
							calcObj.getCalcObj().getVarList().setVarVal(varName, Double.valueOf(ansText));
							calcObj.updateGraph();
						}
						else
							currField.setText("error");
					}
				}
			}
		};
		xMax = new OCTextWithValButton("xMax", true, 5, 3, 1, 0, 1, this, calcObj){
			public void actionPerformed(){
				String currText = field.getText();
				String ansText = new String();
				if (!currText.equals(null) && !currText.equals("") && calcObj != null){
					JTextField currField = calcObj.getCurrTextField();
					ansText = calcObj.evalCalc(currText);
					currField.setText(ansText);
					if (!ansText.equals("error")){
						if(Double.valueOf(ansText) > calcObj.getVarsObj().getVarVal("xMin")){
							calcObj.getCalcObj().getVarList().setVarVal(varName, Double.valueOf(ansText));
							calcObj.updateGraph();
						}
						else
							currField.setText("error");
					}
				}
			}
		};
		yMin = new OCTextWithValButton("yMin", true, 5, 3, 1, 3, 0, this, calcObj){
			public void actionPerformed(){
				String currText = field.getText();
				String ansText = new String();
				if (!currText.equals(null) && !currText.equals("") && calcObj != null){
					JTextField currField = calcObj.getCurrTextField();
					ansText = calcObj.evalCalc(currText);
					currField.setText(ansText);
					if (!ansText.equals("error")){
						if(Double.valueOf(ansText) < calcObj.getVarsObj().getVarVal("yMax")){
							calcObj.getCalcObj().getVarList().setVarVal(varName, Double.valueOf(ansText));
							calcObj.updateGraph();
						}
						else
							currField.setText("error");
					}
				}
			}
		};
		yMax = new OCTextWithValButton("yMax", true, 5, 3, 1, 3, 1, this, calcObj){
			public void actionPerformed(){
				String currText = field.getText();
				String ansText = new String();
				if (!currText.equals(null) && !currText.equals("") && calcObj != null){
					JTextField currField = calcObj.getCurrTextField();
					ansText = calcObj.evalCalc(currText);
					currField.setText(ansText);
					if (!ansText.equals("error")){
						if(Double.valueOf(ansText) > calcObj.getVarsObj().getVarVal("xMin")){
							calcObj.getCalcObj().getVarList().setVarVal(varName, Double.valueOf(ansText));
							calcObj.updateGraph();
						}
						else
							currField.setText("error");
					}
				}
			}
		};
		
		defaultGrid = new OCButton("default", 1, 2, 6, 0, this, calcObj){
			public void associatedAction(){
				calcObj.getCalcObj().getVarList().setVarVal("xMin", -10);
				xMin.setText("-10");
				calcObj.getCalcObj().getVarList().setVarVal("xMax", 10);
				xMax.setText("10");
				calcObj.getCalcObj().getVarList().setVarVal("yMin", -10);
				yMin.setText("-10");
				calcObj.getCalcObj().getVarList().setVarVal("yMax", 10);
				yMax.setText("10");
				calcObj.updateGraph();
			}
		};
	}
}
