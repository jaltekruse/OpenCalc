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

import java.util.LinkedList;


public class calc {
	
	public LinkedList<Element> CURRLIST;
	public LinkedList<Element> ORIGIONALLIST;
	public Evaluator CURREVAL;
	public VarStorage VARLIST;
	public ConstantStorage CONSTLIST;
	public Operators OPSLIST;
	public NewCalc calcObj;
	public ParseElements PARSER;
	
	//1-radians  2-degrees  3-Gradians
	private int angleUnits;
	private boolean hadParsingError;

	/**
	 * @param currCalcObj
	 */

	public calc(NewCalc currCalcObj) {
		calcObj = currCalcObj;
		ORIGIONALLIST = new LinkedList<Element>();
		CONSTLIST = new ConstantStorage(this);
		CURRLIST = new LinkedList<Element>();
		CURREVAL = new Evaluator(this);
		VARLIST = new VarStorage (this);
		OPSLIST = new Operators(this);
		PARSER = new ParseElements(this, ORIGIONALLIST);
		setAngleUnits(1);
		hadParsingError = false;
	}

	/**
	 * Iterates through the LinkedList, adding each elements <code>toString()
	 * </code> result to a String representation of the entire expression.
	 * 
	 * @param list a linked list containing expression's elements
	 * @return String representation of the current expression
	 */

	public String printList(LinkedList<Element> list) {
		String result = new String();
		int i = 0;
		Object currObj = new Object();

		do {
			currObj = list.get(i);
			i++;
			if (currObj instanceof Operator)
				currObj = (Operator) currObj;
			else if (currObj instanceof Num)
				currObj = (Num) currObj;
			else if (currObj instanceof Var)
				currObj = (Var) currObj;
			result += currObj.toString();
		} while (currObj != list.getLast());

		return result;
	}

	public VarStorage getVarList() {
		return VARLIST;
	}

	/**
	 * Takes a string and parses it into {@link Element}s, which are stored in a
	 * <code>LinkedList</code>.
	 * 
	 * @param s
	 *            The string to be parsed into an expression
	 * @throws Exception 
	 */

	public void parse(String s){
		// TODO: get input string from user, call to parse, then call to solve
		ORIGIONALLIST.clear();
		try {
			PARSER.ParseExpression(s);
			hadParsingError = false;
		}
		catch (Exception e) {
			hadParsingError = true;
		}
			
	}

	/**
	 * Takes the currently stored list and evaluates the expression. 
	 * The List is copied initially, because elements are removed from the list 
	 * as individual operations are performed. This destroys a list, 
	 * necessitating the storage of the list to prevent the need to parse one 
	 * equation repeatedly.
	 * 
	 * {@link calc#parse(String s)} must be called first!!
	 * 
	 * @return result the answer of the expression
	 */

	@SuppressWarnings("unchecked")
	public double solve() {
		if(hadParsingError == true)
			return Double.MAX_VALUE;
		CURRLIST = (LinkedList<Element>) ORIGIONALLIST.clone();
		double result = 0;
		try {
			result = CURREVAL.eval(CURRLIST);
		} catch (Exception e) {
			result = Double.MAX_VALUE;
		}
		return result;
		// System.out.println(result);
	}
	/**
	 * Takes the function currently parsed and calculates the derivative
	 * at a point.
	 * 
	 * @param x - that value at which the derivation is approximated
	 * @return the approx. derivative
	 */
	public double derivAtPoint(double x) {
		double firstVal = 0;
		VARLIST.setVarVal("x", x);
		firstVal = solve();
		VARLIST.setVarVal("x", x + .000000001);
		return (solve() - firstVal / .000000001);
	}
	/**
	 * Takes the function currently parsed and finds the integral from
	 * a to b, using a trapezoid approximation.
	 * 
	 * @param a - start of integral
	 * @param b - end of integral
	 * @return - approximate integral
	 */
	public double integrate(double a, double b) {
		double lastY, currY, aveY, result = 0;
		int numTraps = 5000;
		VARLIST.setVarVal("x", a);
		lastY = solve();
		double xStep = (b - a) / numTraps;
		int trapCount = 0;

		for(int i = 0; i < numTraps; i++){
			//System.out.println(currX);
			VARLIST.updateVarVal("x", xStep);
			currY = solve();
			aveY = (lastY + currY) / 2;
			result += aveY * xStep;
			trapCount++;
			lastY = currY;
		}
		//System.out.println("num trapizoids: " + trapCount);
		return result;

	}
	
	/**
	 * Gets the GUI object associated with this <code>calc</code>
	 * object.
	 * @return current NewCalc object
	 */
	public NewCalc getGUI(){
		return calcObj;
	}

	public void setAngleUnits(int angleUnits) {
		this.angleUnits = angleUnits;
	}

	public int getAngleUnits() {
		return angleUnits;
	}

	public ConstantStorage getConstantList() {
		// TODO Auto-generated method stub
		return CONSTLIST;
	}
	
	public Operators getOpsList(){
		return OPSLIST;
	}
}
