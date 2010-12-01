package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;

import tree.Decimal;
import tree.Value;
import tree.Number;

public class FuncCalcPanel extends SubPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private NewCalc calcObj;
	private Function func;
	private Graph graph;
	private OCButton trace, integrate, graphButton, derive;
	private OCTextField pt2Trace, tracePtVal, startInt, endInt, intVal, slopeVal, indVarVal;
	private OCLabel indVar, depVar, start, end, intApprox, slopeApprox, spacer;
	private Color color;
	
	public FuncCalcPanel(NewCalc currCalcObj, Function f, Color c){
		calcObj = currCalcObj;
		graph = calcObj.getGraphObj();
		func = f;
		color = c;
		
		refreshFields();
	}
	
	/**
	 * Need to rewrite this for the tree
	 */
	public void integrate(){
		if ("".equals(func.getFuncEqtn())) {
			intVal.setText("no eqtn");
		}
		else if (!startInt.getText().equals("") && !endInt.getText().equals("")){
			double a = Double.parseDouble(startInt.getText());
			double b = Double.parseDouble(endInt.getText());
			func.setIntegral(a, b);
			graph.repaint();
			//calcObj.getBasicCalcObj().parse(func.getFuncEqtn());
			String integral = new String();
			//integral += (float) calcObj.getBasicCalcObj().integrate(a, b);
			intVal.setText(integral);
		}
		else{
			func.setIsTakingIntegral(false);
			intVal.setText("");
			graph.repaint();
		}
	}
	
	/**
	 * Need to rewrite this for tree.
	 */
	public void derive(){
		if ("".equals(func.getFuncEqtn())) {
			slopeVal.setText("no eqtn");
		}
		else if (!indVarVal.getText().equals("")){
			double x = Double.parseDouble(indVarVal.getText());
			func.setDerivative(x);
			func.setDeriving(true);
			graph.repaint();
			//calcObj.getBasicCalcObj().parse(func.getFuncEqtn());
			String derivative = new String();
			//derivative += (float) calcObj.getBasicCalcObj().deriveAtPoint(x);
			slopeVal.setText(derivative);
		}
		else{
			func.setDeriving(false);
			indVarVal.setText("");
			graph.repaint();
		}
	}

	public void tracePt(){
		if (pt2Trace.getText().equals("")) {
			func.setTracingPt(false);
			tracePtVal.setText("");
		}
		else {
			if ("".equals(func.getFuncEqtn())) {
				pt2Trace.setText("no eqtn");
				tracePtVal.setText("error");
			}
			else {
				try{
					Value xAns = calcObj.evalCalc(pt2Trace.getText());
					if (!"error".equals(xAns)) {
						double xVal = xAns.toDec().getValue();
						func.setTrace(xVal);
						pt2Trace.setText(xAns.toString());
						func.getIndependentVar().setValue(new Decimal(xVal));
						Value yVal = calcObj.getParser().ParseExpression(func.getFuncEqtn());
						func.getDependentVar().setValue((Number) yVal);
						tracePtVal.setText(yVal.toString());
					}
				}
				catch (Exception e) {
					pt2Trace.setText("error");
				}
			}
		}
		graph.repaint();
	}
	
	public void refreshFields(){
		
		this.removeAll();		
		GridBagConstraints pCon = new GridBagConstraints();
		
		JPanel colorBox = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void paint(Graphics g) {
				g.setColor(color);
				g.fillRect(0, 0, 30,30);
			}
		};
		colorBox.setPreferredSize(new Dimension(30,30));
		
		pCon.gridx = 0;
		pCon.gridy = 0;
		pCon.gridheight = 1;
		pCon.gridwidth = 1;
		pCon.ipadx = 3;
		pCon.ipady = 3;
		this.add(colorBox, pCon);
		
		indVar = new OCLabel(func.getIndependentVar().getName() + ":", 1, 1, 1, 0, this, calcObj);
		pt2Trace = new OCTextField(true, 9, 3, 1, 2, 0, this, calcObj) {
			public void associatedAction() {
				tracePt();
			}
		};
		
		trace = new OCButton("Trace", 1, 1, 5, 0, this, calcObj){
			public void associatedAction() {
				tracePt();
			}
		};
		
		depVar = new OCLabel(func.getDependentVar().getName() + ":", 1, 1, 6, 0, this, calcObj);
		tracePtVal = new OCTextField(false, 9, 2, 1, 7, 0, this, calcObj);
		
		if(func.getGraphType() == 1){
			start = new OCLabel("start:", 1, 1, 0, 1, this, calcObj);
			startInt = new OCTextField(true, 4, 3, 1, 1, 1, this, calcObj){
				public void associatedAction(){
				}
			};
			end = new OCLabel("end:", 1, 1, 4, 1, this, calcObj);
			endInt = new OCTextField(true, 4, 3, 1, 5, 1, this, calcObj){
				public void associatedAction(){
				}
			};
			
			integrate = new OCButton("Integrate", 1, 1, 8, 1, this, calcObj){
				public void associatedAction(){
					integrate();
				}
			};
			
			intApprox = new OCLabel("IntApprox:", 1, 1, 9, 1, this, calcObj);
			intVal = new OCTextField(false, 9, 3, 1, 10, 1, this, calcObj);
			
			
			indVar = new OCLabel(func.getIndependentVar().getName() + ":", 1, 1, 0, 2, this, calcObj);
			indVarVal = new OCTextField(true, 4, 3, 1, 1, 2, this, calcObj){
				public void associatedAction(){
					derive();
				}
			};
			
			derive = new OCButton("derive", 1, 1, 4, 2, this, calcObj){
				public void associatedAction(){
					derive();
				}
			};
			
			slopeApprox = new OCLabel("SlopeApprox:", 1, 1, 5, 2, this, calcObj);
			slopeVal = new OCTextField(false, 9, 3, 1, 6, 2, this, calcObj);
			
			if(func.isTakingIntegral()){
				startInt.setText("" + func.getStartIntegral());
				endInt.setText("" + func.getEndIntegral());
				integrate();
			}
			
			if(func.isDeriving()){
				indVarVal.setText("" + func.getDerivative());
				derive();
			}
			
			if(func.isTracingPt()){
				pt2Trace.setText("" + func.getTraceVal());
				tracePt();
			}
		}
		else if(func.getGraphType() == 2){
			spacer = new OCLabel("Integrate", 1, 1, 5, 0, this, calcObj);
		}
		else
			;//do nothing, will add when more graph types supported
		this.revalidate();
		this.repaint();
	}

}
