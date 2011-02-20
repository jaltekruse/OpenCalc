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

import java.util.LinkedList;

import GUI.NewCalc;


public class calc 
{
	//testing SVN update
	//testing again, this time in eclipse
	public LinkedList<Object> CURRLIST;
	public LinkedList<Object> ORIGIONALLIST;
	public Evaluator CURREVAL;
	public Var2 VARLIST;
	public Operators OPSLIST;
	public NewCalc calcObj;
	public ParseElements PARSER;
	
	public calc(NewCalc currCalcObj){
		calcObj = currCalcObj;
		ORIGIONALLIST = new LinkedList<Object>();
		CURRLIST = new LinkedList<Object>();
		CURREVAL = new Evaluator(this);
		VARLIST = new Var2(this);
		OPSLIST = new Operators(this);
		PARSER = new ParseElements(this);
	}
	
	public String printList(LinkedList list){
		String result = new String();
		int i = 0;
		Object currObj = new Object();
		
		do{
			currObj = list.get(i);
			i++;
			if (currObj instanceof Operator)
				currObj = (Operator) currObj;
			else if (currObj instanceof Num)
				currObj = (Num) currObj;
			else if (currObj instanceof Var)
				currObj = (Var) currObj;
			result += currObj.toString();
		}
		while (currObj != list.getLast());
		
		return result;
	}

	public Var2 getVarList(){
		return VARLIST;
	}
	
	/**
	 * @param args
	 */
	public void parse(String s) 
	{
		// TODO: get input string from user, call  to parse, then call to solve
			ORIGIONALLIST.clear();
			String eqtnStr = s;

			char[] equation = eqtnStr.toCharArray();
			
			//System.out.println(eqtnStr);
			
			//call parsing class
			PARSER.Parse(equation);
	}
	
	public double solve(){
		CURRLIST = (LinkedList<Object>) ORIGIONALLIST.clone();
		double result = 0;
		try{
			result = CURREVAL.eval();
		}
		catch(Exception e){
			result = Double.MAX_VALUE;
		}
		VARLIST.setVarVal("ans",result);
		return result;
		//System.out.println(result);
	}
	
	public double derivAtPoint(double x){
		double firstVal = 0;
		VARLIST.setVarVal("x", x);
		firstVal = solve();
		VARLIST.setVarVal("x", x + .000000001);
		return (solve() - firstVal/ .000000001);
	}
	public double integrate(double a, double b){
		double lastX = a, lastY, currX = a, currY, aveY, result = 0;
		VARLIST.setVarVal("x", a);
		lastY = solve();
		double xStep = (b-a)/10000;
		int trapCount = 0;
		
		while (currX < b - xStep){
			//System.out.println(currX);
			VARLIST.updateVarVal("x", xStep);
			currX = VARLIST.getVarVal("x");
			currY = solve();
			aveY = (lastY + currY)/2;
			result += aveY * xStep;
			trapCount++;
			lastY = currY;
		}
		System.out.println("num trapizoids: " + trapCount);
		return result;
		
	}
}
