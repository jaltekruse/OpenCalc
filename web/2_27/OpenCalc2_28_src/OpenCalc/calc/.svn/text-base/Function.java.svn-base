package calc;

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

import GUI.NewCalc;

import java.awt.Color;


public class Function {
	private String funcEqtn;
	private boolean takeIntegral;
	private boolean tracingPt;
	private boolean graphing;
	private boolean connected;
	private double point2Trace;
	private double startIntegral;
	private double endIntegral;
	private Color color;
	private Var independentVar;
	private Var dependentVar;
	private calc basicCalcObj;
	private int graphType;
	//graphTypes: 1-Cartesian   2-Polar    3-parametric    4-Sequential
	
	public Function(calc currCalcObj) {
		basicCalcObj = currCalcObj;
		funcEqtn = "";
		graphing = false;
		connected = true;
		setIsTakingIntegral(false);
		setTracingPt(false);
		point2Trace = 0;
		setStartIntegral(0);
		setEndIntegral(0);
		setColor(Color.blue);
		setGraphType(1);
		setIndependentVar("x");
		setDependentVar("y");
	}
	
	public Function(calc currCalcObj, String eqtn, String ind, String dep, 
			boolean connected,boolean trace, double tracePt, boolean integral,
			double startInt, double endInt, Color c) {
		basicCalcObj = currCalcObj;
		setIndependentVar(ind);
		setDependentVar(dep);
		setGraphType(1);
		graphing = true;
		this.connected = connected;
		funcEqtn = eqtn;
		setTracingPt(trace);
		point2Trace = tracePt;
		setIsTakingIntegral(integral);
		setStartIntegral(startInt);
		setEndIntegral(endInt);
		setColor(c);
	}

	public void setFuncEqtn(String s) {
		funcEqtn = s;
	}

	public String getFuncEqtn() {
		return funcEqtn;
	}

	public void setIntegral(double a, double b) {
		if (a == 0 && b == 0) {
			setIsTakingIntegral(false);
		} else {
			setStartIntegral(a);
			setEndIntegral(b);
			setIsTakingIntegral(true);
		}
	}

	public void setTrace(double val) {
		if (val == Double.MAX_VALUE) {
			setTracingPt(false);
		} else {
			setTracingPt(true);
			point2Trace = val;
		}
	}

	public double getTraceVal() {
		return point2Trace;
	}

	public void setIsTakingIntegral(boolean takeIntegral) {
		this.takeIntegral = takeIntegral;
	}

	public boolean isTakingIntegral() {
		return takeIntegral;
	}

	public void setTracingPt(boolean tracingPt) {
		this.tracingPt = tracingPt;
	}

	public boolean isTracingPt() {
		return tracingPt;
	}

	public void setGraphing(boolean graphing) {
		this.graphing = graphing;
	}

	public boolean isGraphing() {
		return graphing;
	}

	public void setStartIntegral(double startIntegral) {
		this.startIntegral = startIntegral;
	}

	public double getStartIntegral() {
		return startIntegral;
	}

	public void setEndIntegral(double endIntegral) {
		this.endIntegral = endIntegral;
	}

	public double getEndIntegral() {
		return endIntegral;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public void setIndependentVar(Var independentVar) {
		this.independentVar = independentVar;
	}
	
	public void setIndependentVar(String varName) {
		independentVar = basicCalcObj.getVarList().storeVar(varName);
	}
	
	public void setDependentVar(String varName) {
		dependentVar = basicCalcObj.getVarList().storeVar(varName);
	}


	public Var getIndependentVar() {
		return independentVar;
	}

	public void setDependentVar(Var dependentVar) {
		this.dependentVar = dependentVar;
	}

	public Var getDependentVar() {
		return dependentVar;
	}

	public void setConneted(boolean conneted) {
		this.connected = conneted;
	}

	public boolean isConneted() {
		return connected;
	}

	public void setGraphType(int graphType) {
		this.graphType = graphType;
	}

	public int getGraphType() {
		return graphType;
	}
}
