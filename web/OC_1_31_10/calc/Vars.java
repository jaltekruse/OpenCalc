package calc;
import GUI.NewCalc;

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

//WARNING!!!!!!!!!!!!!
//This class has been depreciated, please use Var2

public class Vars {
	private String[] varList;
	private double[] varVals;
	private int numVars;
	private int sizeArray;
	public NewCalc calcObj;
	
	public Vars(NewCalc currCalcObj){
		calcObj = currCalcObj;
		sizeArray = 50;
		varList = new String[sizeArray];
		varVals = new double[sizeArray];
		varList[0] = new String("x");
		varList[1] = new String("y");
		varList[2] = new String("ans");
		varList[3] = new String("z");
		varList[4] = new String("theta");
		varList[5] = new String("xMin");
		varList[6] = new String("xMax");
		varList[7] = new String("yMin");
		varList[8] = new String("yMax");
		varList[9] = new String("r");
		varList[10] = new String("a");
		varList[11] = new String("b");
		varList[12] = new String("c");
		varList[13] = new String("t");
		
		varVals[5] = -10;
		varVals[6] = 10;
		varVals[7] = -10;
		varVals[8] = 10;
		numVars = 14;
	}
	
	public int addVar(String newVar, double val){
		if (numVars == sizeArray){
			//add function to make list bigger, when spaces run out
		}
		numVars++;
		varList[numVars - 1] = newVar;
		varVals[numVars - 1] = val;
		return numVars - 1;
	}
	
	public int getLength(){
		return numVars;
	}
	
	public double getVarVal(String s){
		int pos = getVarPos(s);
		if(pos == Integer.MAX_VALUE)
			pos = addVar(s, 0); // considering change in initial value to 1
		return varVals[pos];
	}
	
	public void setVarVal(String s, double num){
		int pos = getVarPos(s);
		if(pos == Integer.MAX_VALUE)
			pos = addVar(s, 0); // considering change in initial value to 1
		varVals[pos] = num;
	}
	
	public int getVarPos(String s){
		int i;
		for (i = 0; i <= numVars - 1; i++){
			if (s.equals(varList[i])){
				//System.out.println(i);
				return i;
			}
		}
		return Integer.MAX_VALUE;
	}
	
	public String getVarAtPos(int pos){
		return varList[pos];
	}
	
	public void updateVar(int varPos, double update){
		varVals[varPos] += update;
	}
}

