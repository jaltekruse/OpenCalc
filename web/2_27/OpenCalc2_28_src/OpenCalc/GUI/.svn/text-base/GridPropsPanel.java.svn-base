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

import java.awt.ComponentOrientation;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;

public class GridPropsPanel extends SubPanel {

	NewCalc calcObj;
	OCTextWithValButton xMin, xMax, yMin, yMax, xStep, yStep, thetaMin, thetaMax, thetaStep;
	OCButton defaultGrid, zoom, zoomTrig;
	OCTextField zoomRate;

	public GridPropsPanel(NewCalc currCalcObj) {

		this.setLayout(new GridBagLayout());
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		calcObj = currCalcObj;

		/* OCTextWithValButton(String s, boolean editable, int length,
		 * 		int gridWidth, int gridHeight, int gridx, int gridy,
		 * 		JComponent comp, final NewCalc currCalcObj)
		 */

		xMin = new OCTextWithValButton("xMin", true, 15, 3, 1, 0, 0, this, calcObj) {
			public void associatedAction() {
				graphAttributeAction(varName, field);
			}
		};
		xMax = new OCTextWithValButton("xMax", true, 15, 3, 1, 0, 1, this,
				calcObj) {
			public void associatedAction() {
				graphAttributeAction(varName, field);
			}
		};
		yMin = new OCTextWithValButton("yMin", true, 15, 3, 1, 0, 2, this,
				calcObj) {
			public void associatedAction() {
				graphAttributeAction(varName, field);
			}
		};
		yMax = new OCTextWithValButton("yMax", true, 15, 3, 1, 0, 3, this,
				calcObj) {
			public void associatedAction() {
				graphAttributeAction(varName, field);
			}
		};
		
		xStep = new OCTextWithValButton("xStep", true, 15, 3, 1, 0, 4, this,
				calcObj) {
			public void associatedAction() {
				graphAttributeAction(varName, field);
			}
		};
		
		yStep = new OCTextWithValButton("yStep", true, 15, 3, 1, 0, 5, this,
				calcObj) {
			public void associatedAction() {
				graphAttributeAction(varName, field);
			}
		};
		
		thetaMin = new OCTextWithValButton("thetaMin", true, 15, 3, 1, 0, 6, this,
				calcObj) {
			public void associatedAction() {
				graphAttributeAction(varName, field);
			}
		};
		
		thetaMax = new OCTextWithValButton("thetaMax", true, 15, 3, 1, 0, 7, this,
				calcObj) {
			public void associatedAction() {
				graphAttributeAction(varName, field);
			}
		};
		
		thetaStep = new OCTextWithValButton("thetaStep", true, 15, 3, 1, 0, 8, this,
				calcObj) {
			public void associatedAction() {
				graphAttributeAction(varName, field);
			}
		};
		
		zoomRate = new OCTextField(true, 5, 2, 1, 0, 9, this, calcObj){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			public void associatedAction(){
				zoom();
			}
			
		};
		
		zoom = new OCButton("Zoom", 1, 1, 2, 9, this, calcObj){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			public void associatedAction(){
				zoom();
			}
		};
		
		defaultGrid = new OCButton("default", 1, 2, 2, 10, this, calcObj) {
			public void associatedAction() {
				calcObj.getCalcObj().getVarList().setVarVal("xMin", -10);
				xMin.setText("-10");
				calcObj.getCalcObj().getVarList().setVarVal("xMax", 10);
				xMax.setText("10");
				calcObj.getCalcObj().getVarList().setVarVal("yMin", -10);
				yMin.setText("-10");
				calcObj.getCalcObj().getVarList().setVarVal("yMax", 10);
				yMax.setText("10");
				calcObj.getCalcObj().getVarList().setVarVal("xStep", 1);
				yMax.setText("1");
				calcObj.getCalcObj().getVarList().setVarVal("yStep", 1);
				yMax.setText("1");
				calcObj.getCalcObj().getVarList().setVarVal("thetaMin", 0);
				thetaMin.setText("0");
				
				if(calcObj.getCalcObj().getAngleUnits() == 1){
					calcObj.getCalcObj().getVarList().setVarVal("thetaMax", 2*Math.PI);
					thetaMax.setText("2pi");
					calcObj.getCalcObj().getVarList().setVarVal("thetaStep", Math.PI/360);
					thetaStep.setText("2pi/360");
				}
				else if(calcObj.getCalcObj().getAngleUnits() == 2){
					calcObj.getCalcObj().getVarList().setVarVal("thetaMax", 360);
					thetaMax.setText("360");
					calcObj.getCalcObj().getVarList().setVarVal("thetaStep", 3);
					thetaStep.setText("3");
				}
				
				else if(calcObj.getCalcObj().getAngleUnits() == 3){
					calcObj.getCalcObj().getVarList().setVarVal("thetaMax", 400);
					thetaMax.setText("400");
					calcObj.getCalcObj().getVarList().setVarVal("thetaStep", 4);
					thetaStep.setText("4");
				}
				
				calcObj.updateGraph();
			}
		};
		
		zoomTrig = new OCButton("zoomTrig", 1, 2, 1, 10, this, calcObj) {
			public void associatedAction() {
				calcObj.getCalcObj().getVarList().setVarVal("xMin", -2*Math.PI);
				xMin.setText("-2pi");
				calcObj.getCalcObj().getVarList().setVarVal("xMax", 2*Math.PI);
				xMax.setText("2pi");
				calcObj.getCalcObj().getVarList().setVarVal("yMin", -2);
				yMin.setText("-2");
				calcObj.getCalcObj().getVarList().setVarVal("yMax", 2);
				yMax.setText("2");
				calcObj.getCalcObj().getVarList().setVarVal("xStep", 1);
				yMax.setText("1");
				calcObj.getCalcObj().getVarList().setVarVal("yStep", 1);
				yMax.setText("1");
				calcObj.getCalcObj().getVarList().setVarVal("thetaMin", 0);
				thetaMin.setText("0");
				if(calcObj.getCalcObj().getAngleUnits() == 1){
					calcObj.getCalcObj().getVarList().setVarVal("thetaMax", 2*Math.PI);
					thetaMax.setText("2pi");
					calcObj.getCalcObj().getVarList().setVarVal("thetaStep", Math.PI/360);
					thetaStep.setText("2pi/360");
				}
				else if(calcObj.getCalcObj().getAngleUnits() == 2){
					calcObj.getCalcObj().getVarList().setVarVal("thetaMax", 360);
					thetaMax.setText("360");
					calcObj.getCalcObj().getVarList().setVarVal("thetaStep", 3);
					thetaStep.setText("3");
				}
				
				else if(calcObj.getCalcObj().getAngleUnits() == 3){
					calcObj.getCalcObj().getVarList().setVarVal("thetaMax", 400);
					thetaMax.setText("400");
					calcObj.getCalcObj().getVarList().setVarVal("thetaStep", 4);
					thetaStep.setText("4");
				}
				
				
				
				calcObj.updateGraph();
			}
		};
		
		defaultGrid.associatedAction();
		this.revalidate();
		this.repaint();
	}
	
	public void zoom(){
		String currText = zoomRate.getText();
		String ansText = new String();
		if (!currText.equals(null) && !currText.equals("")
				&& calcObj != null) {
			JTextField currField = calcObj.getCurrTextField();
			ansText = calcObj.evalCalc(currText);
			currField.setText("");
			if (!ansText.equals("error")) {
				calcObj.getGraphObj().zoom(Double.valueOf(ansText));
			}
		}
		refreshAttributes();
	}
	
	public void graphAttributeAction(String s, OCTextField field){
		String currText = field.getText();
		String ansText = new String();
		if (!currText.equals(null) && !currText.equals("")
				&& calcObj != null) {
			JTextField currField = calcObj.getCurrTextField();
			ansText = calcObj.evalCalc(currText);
			currField.setText(ansText);
			if (!ansText.equals("error")) {
				if (Double.valueOf(ansText) < calcObj.getVarsObj()
						.getVarVal(s)) {
					calcObj.getCalcObj().getVarList().setVarVal(
							s, Double.valueOf(ansText));
					calcObj.updateGraph();
				} else
					currField.setText("error");
			}
		}
	}
	
	public void refreshAttributes(){
		xMin.setText(Float.toString((float)calcObj.getVarsObj().getVarVal("xMin")));
		xMax.setText(Float.toString((float)calcObj.getVarsObj().getVarVal("xMax")));
		yMin.setText(Float.toString((float)calcObj.getVarsObj().getVarVal("yMin")));
		yMax.setText(Float.toString((float)calcObj.getVarsObj().getVarVal("yMax")));
		xStep.setText(Float.toString((float)calcObj.getVarsObj().getVarVal("xStep")));
		yStep.setText(Float.toString((float)calcObj.getVarsObj().getVarVal("yStep")));
		thetaMin.setText(Float.toString((float)calcObj.getVarsObj().getVarVal("thetaMin")));
		thetaMax.setText(Float.toString((float)calcObj.getVarsObj().getVarVal("thetaMax")));
		thetaStep.setText(Float.toString((float)calcObj.getVarsObj().getVarVal("thetaStep")));
		this.revalidate();
		this.repaint();
	}
}
