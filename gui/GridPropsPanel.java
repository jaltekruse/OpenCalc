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

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JScrollPane;
import javax.swing.JTextField;

import tree.*;
import tree.Number;

public class GridPropsPanel extends SubPanel {

	private NewCalc calcObj;
	private OCTextWithValButton xMin, xMax, yMin, yMax, xStep, yStep, thetaMin, thetaMax, thetaStep;
	private OCButton defaultGrid, zoom, zoomTrig;
	private OCTextField zoomRate;
	private SubPanel fields;
	private JScrollPane scroll;
	private ExpressionParser parser;

	public GridPropsPanel(NewCalc currCalcObj) throws ParseException, ValueNotStoredException, EvalException {

		this.setLayout(new GridBagLayout());
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		calcObj = currCalcObj;
		parser = calcObj.getParser();
		fields = new SubPanel();
		fields.setPreferredSize(new Dimension(320, 300));
		scroll = new JScrollPane(fields);
		GridBagConstraints pCon = new GridBagConstraints();
		
		
		pCon.fill = GridBagConstraints.BOTH;
		pCon.weightx = 1;
		pCon.weighty = 1;
		pCon.gridx = 0;
		pCon.gridy = 0;
		this.add(scroll, pCon);

		/* OCTextWithValButton(String s, boolean editable, int length,
		 * 		int gridWidth, int gridHeight, int gridx, int gridy,
		 * 		JComponent comp, final NewCalc currCalcObj)
		 */

		xMin = new OCTextWithValButton("xMin", true, 15, 3, 1, 0, 0, fields, calcObj) {
			public void associatedAction() {
				graphAttributeAction(varName, field);
			}
		};
		xMax = new OCTextWithValButton("xMax", true, 15, 3, 1, 0, 1, fields,
				calcObj) {
			public void associatedAction() {
				graphAttributeAction(varName, field);
			}
		};
		yMin = new OCTextWithValButton("yMin", true, 15, 3, 1, 0, 2, fields,
				calcObj) {
			public void associatedAction() {
				graphAttributeAction(varName, field);
			}
		};
		yMax = new OCTextWithValButton("yMax", true, 15, 3, 1, 0, 3, fields,
				calcObj) {
			public void associatedAction() {
				graphAttributeAction(varName, field);
			}
		};
		
		xStep = new OCTextWithValButton("xStep", true, 15, 3, 1, 0, 4, fields,
				calcObj) {
			public void associatedAction() {
				graphAttributeAction(varName, field);
			}
		};
		
		yStep = new OCTextWithValButton("yStep", true, 15, 3, 1, 0, 5, fields,
				calcObj) {
			public void associatedAction() {
				graphAttributeAction(varName, field);
			}
		};
		
		thetaMin = new OCTextWithValButton("thetaMin", true, 15, 3, 1, 0, 6, fields,
				calcObj) {
			public void associatedAction() {
				graphAttributeAction(varName, field);
			}
		};
		
		thetaMax = new OCTextWithValButton("thetaMax", true, 15, 3, 1, 0, 7, fields,
				calcObj) {
			public void associatedAction() {
				graphAttributeAction(varName, field);
			}
		};
		
		thetaStep = new OCTextWithValButton("thetaStep", true, 15, 3, 1, 0, 8, fields,
				calcObj) {
			public void associatedAction() {
				String currText = field.getText();
				if (!currText.equals(null) && !currText.equals("") && calcObj != null)
				{
					try
					{
						Value v = calcObj.evalCalc(currText);
						//check here to make sure value isn't too small, will cause crashes
						//Permanent solution will be multi-threading, and handling graphing better
						field.setText(v.toString());
						calcObj.getParser().getVarList().setVarVal(varName, (Number) v);
						calcObj.updateGraph();
					}
					catch(Exception e)
					{
						field.setText("error");
					}
				}
			}
		};
		
		zoomRate = new OCTextField(true, 5, 2, 1, 0, 9, fields, calcObj){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			public void associatedAction(){
				zoom();
			}
			
		};
		
		zoom = new OCButton("Zoom", 1, 1, 2, 9, fields, calcObj){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			public void associatedAction(){
				zoom();
			}
		};
		
		defaultGrid = new OCButton("default", 1, 2, 2, 10, fields, calcObj) {
			public void associatedAction() {
				calcObj.getParser().getVarList().setVarVal("xMin", new Fraction(-10,1));
				xMin.setText("-10");
				calcObj.getParser().getVarList().setVarVal("xMax", new Fraction(10,1));
				xMax.setText("10");
				calcObj.getParser().getVarList().setVarVal("yMin", new Fraction(-10,1));
				yMin.setText("-10");
				calcObj.getParser().getVarList().setVarVal("yMax", new Fraction(10,1));
				yMax.setText("10");
				calcObj.getParser().getVarList().setVarVal("xStep", new Fraction(1,1));
				yMax.setText("1");
				calcObj.getParser().getVarList().setVarVal("yStep", new Fraction(1,1));
				yMax.setText("1");
				calcObj.getParser().getVarList().setVarVal("thetaMin", new Fraction(0,1));
				thetaMin.setText("0");
				
				if(calcObj.getParser().getAngleUnits() == 1){
					calcObj.getParser().getVarList().setVarVal("thetaMax", new Decimal(2*Math.PI));
					thetaMax.setText("2pi");
					calcObj.getParser().getVarList().setVarVal("thetaStep", new Decimal(Math.PI/360));
					thetaStep.setText("2pi/360");
				}
				else if(calcObj.getParser().getAngleUnits() == 2){
					calcObj.getParser().getVarList().setVarVal("thetaMax", new Fraction(360,1));
					thetaMax.setText("360");
					calcObj.getParser().getVarList().setVarVal("thetaStep", new Fraction(3,1));
					thetaStep.setText("3");
				}
				
				else if(calcObj.getParser().getAngleUnits() == 3){
					calcObj.getParser().getVarList().setVarVal("thetaMax", new Fraction(400,1));
					thetaMax.setText("400");
					calcObj.getParser().getVarList().setVarVal("thetaStep", new Fraction(4,1));
					thetaStep.setText("4");
				}
				
				calcObj.updateGraph();
			}
		};
		
		zoomTrig = new OCButton("zoomTrig", 1, 2, 1, 10, fields, calcObj) {
			public void associatedAction() {
				calcObj.getParser().getVarList().setVarVal("xMin", new Decimal(-2*Math.PI));
				xMin.setText("-2pi");
				calcObj.getParser().getVarList().setVarVal("xMax", new Decimal(2*Math.PI));
				xMax.setText("2pi");
				calcObj.getParser().getVarList().setVarVal("yMin", new Fraction(-2, 1));
				yMin.setText("-2");
				calcObj.getParser().getVarList().setVarVal("yMax", new Fraction(2,1));
				yMax.setText("2");
				calcObj.getParser().getVarList().setVarVal("xStep", new Fraction(1,1));
				yMax.setText("1");
				calcObj.getParser().getVarList().setVarVal("yStep", new Fraction(1,1));
				yMax.setText("1");
				calcObj.getParser().getVarList().setVarVal("thetaMin", new Fraction(0,1));
				thetaMin.setText("0");
				if(calcObj.getParser().getAngleUnits() == 1){
					calcObj.getParser().getVarList().setVarVal("thetaMax", new Decimal(2*Math.PI));
					thetaMax.setText("2pi");
					calcObj.getParser().getVarList().setVarVal("thetaStep", new Decimal(Math.PI/360));
					thetaStep.setText("2pi/360");
				}
				else if(calcObj.getParser().getAngleUnits() == 2){
					calcObj.getParser().getVarList().setVarVal("thetaMax", new Fraction(360,1));
					thetaMax.setText("360");
					calcObj.getParser().getVarList().setVarVal("thetaStep", new Fraction(3,1));
					thetaStep.setText("3");
				}
				
				else if(calcObj.getParser().getAngleUnits() == 3){
					calcObj.getParser().getVarList().setVarVal("thetaMax", new Fraction(400,1));
					thetaMax.setText("400");
					calcObj.getParser().getVarList().setVarVal("thetaStep", new Fraction(4,1));
					thetaStep.setText("4");
				}
				
				
				
				calcObj.updateGraph();
			}
		};
		
		defaultGrid.associatedAction();
		this.revalidate();
		refreshAttributes();
		this.repaint();
	}
	
	public void zoom(){
		String currText = zoomRate.getText();
		String ansText = new String();
		if (!currText.equals(null) && !currText.equals("")
				&& calcObj != null)
		{
			JTextField currField = calcObj.getCurrTextField();
			currField.setText("");
			try
			{
				calcObj.getGraphObj().zoom(calcObj.evalCalc(currText).toDec().getValue());
			} catch (EvalException e) {
				// TODO Auto-generated catch block
				//do something
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				//do something
			}
		}
		refreshAttributes();
	}
	
	public void graphAttributeAction(String s, OCTextField field)
	{
		String currText = field.getText();
		if (!currText.equals(null) && !currText.equals("") && calcObj != null)
		{
			try
			{
				Value v = calcObj.evalCalc(currText);
				field.setText(v.toString());
				calcObj.getParser().getVarList().setVarVal(s, (Number) v);
				calcObj.updateGraph();
			}
			catch(Exception e)
			{
					field.setText("error");
			}
		}
	}
	
	public void refreshAttributes(){
		try{
			xMin.setText(calcObj.getParser().getVarList().getVarVal("xMin").toString());
			xMax.setText(calcObj.getParser().getVarList().getVarVal("xMax").toString());
			yMin.setText(calcObj.getParser().getVarList().getVarVal("yMin").toString());
			yMax.setText(calcObj.getParser().getVarList().getVarVal("yMax").toString());
			xStep.setText(calcObj.getParser().getVarList().getVarVal("xStep").toString());
			yStep.setText(calcObj.getParser().getVarList().getVarVal("yStep").toString());
			thetaMin.setText(calcObj.getParser().getVarList().getVarVal("thetaMin").toString());
			thetaMax.setText(calcObj.getParser().getVarList().getVarVal("thetaMax").toString());
			thetaStep.setText(calcObj.getParser().getVarList().getVarVal("thetaStep").toString());
		}
		catch(Exception e){
			System.out.println("error with refreshing graphProps panel");
		}
		this.revalidate();
		this.repaint();
	}
}
