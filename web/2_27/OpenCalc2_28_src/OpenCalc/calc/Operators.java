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

public class Operators {

	private calc basicCalcObj;
	private int NumPrecLevels, NumOps;
	private String[][] ops = {
			{ "(", ")" },
			{ "sin", "sin-1", "cos", "cos-1", "tan", "tan-1", "!", "log",
					"ln", "sqrt", "abs", "int", "floor", "ceil"},
			{ "^" }, 
			{ "*", "/" }, 
			{ "-", "+" },
			{ "=" } };
	
	private String[] requirePrevNum = {"!", "^", "*", "/", "-", "+", "^-1"};

	public Operators(calc currCalc) {
		basicCalcObj = currCalc;
	}

	public int getPrec(String op) {
		boolean isFound = false;
		int precLevel;

		for (precLevel = 0; precLevel < ops.length; precLevel++) {
			for (int j = 0; j < ops[precLevel].length; j++) {
				if (op.equals(ops[precLevel][j])) {
					isFound = true;
					break;
				}
			}
			if (isFound) {
				return precLevel;
			}
		}

		return -1;
	}
	
	public boolean requiresPrevious(String s){
		for(int i = 0; i < requirePrevNum.length; i++){
			if (s.equals(requirePrevNum[i])){
				return true;
			}
		}
		return false;
	}

}
