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

import java.util.ArrayList;


public class Var2 {
	
	private final static int NUM_BUCKETS = 47;
	private ArrayList[] varTable;
	public calc basicCalcObj;
	private String[] defaultVars = {"ans","x","y","t","theta","r","speed","dist","xMin","xMax","yMin","yMax"};
	
	public Var2(calc currCalc){
		varTable = new ArrayList[NUM_BUCKETS];
		for (int i = 0; i < NUM_BUCKETS; i++){
			varTable[i] = new ArrayList();
		}
		
		for(int i = 0; i < defaultVars.length - 1; i++){
			storeVar(defaultVars[i]);
		}
		
		updateVarVal("xMin", -10);
		updateVarVal("yMin", -10);
		updateVarVal("xMax", 10);
		updateVarVal("yMax", 10);
		
		basicCalcObj = currCalc;
	}
	
	private int generateHash(String s){
		if (s.equals(""))
			return Integer.MAX_VALUE;
		
		int hashVal = 0;
		for (int i = 0; i < s.length() - 1; i++){
			hashVal += s.charAt(i);
		}
		hashVal = hashVal/NUM_BUCKETS;
		return hashVal;
	}
	
	public Var storeVar(String s){
		int bucket = generateHash(s);
		Var tempVar = new Var("",basicCalcObj);
		if (varTable[bucket].isEmpty()){
			tempVar = new Var(s, basicCalcObj);
			varTable[bucket].add(tempVar);
			return tempVar;
		}
		else{
			for(int i = 0; i < varTable[bucket].size(); i++){
				//simply a sanity check, vars should be the only thing stored
				if (varTable[bucket].get(i) instanceof Var)
					tempVar = (Var) varTable[bucket].get(i);
				if (s.equals(tempVar.getVarString())){
					//String represents a previously stored Var, which is returned
					return tempVar;
				}
			}
			
		}
		//the bucket does contain variables, but not the one requested, creating new
		tempVar = new Var(s, basicCalcObj);
		varTable[bucket].add(tempVar);
		return tempVar;
	}
	
	public void setVarVal(String s, double val){
		Var currVar = storeVar(s);
		currVar.setValue(val);
	}
	
	public double getVarVal(String s){
		Var currVar = storeVar(s);
		return currVar.getValue();
	}
	
	public String[] getDefaultVars(){
		return defaultVars;
	}
	
	public void updateVarVal(String s, double val){
		setVarVal(s, getVarVal(s) + val);
	}
}
