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

import java.awt.Color;


public class Function {
	private String funcEqtn;
	private boolean takeIntegral;
	private boolean tracingPt;
	private boolean graphing;
	private double point2Trace;
	private double startIntegral;
	private double endIntegral;
	private Color color;
	
	public Function(){
		funcEqtn = "";
		takeIntegral = false;
		tracingPt = false;
		point2Trace = 0;
		startIntegral = 0;
		endIntegral = 0;
		color = Color.blue;
	}
	
	public void setFuncEqtn(String s){
		funcEqtn = s;
	}
	
	public String getFuncEqtn(){
		return funcEqtn;
	}
	
	public void setIntegral(double a, double b){
		if (a == 0 && b == 0){
			takeIntegral = false;
		}
		else{
			startIntegral = a;
			endIntegral = b;
			takeIntegral = true;
		}
	}
	
	public void setTrace(double val){
		if (val == Double.MAX_VALUE){
			tracingPt = false;
		}
		else{
			tracingPt = true;
			point2Trace = val;
		}
	}
	
	public double getTraceVal(){
		return point2Trace;
	}
}
