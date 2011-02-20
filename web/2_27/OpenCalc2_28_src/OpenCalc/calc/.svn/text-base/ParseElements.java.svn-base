package calc;

import java.util.LinkedList;

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

public class ParseElements {

	private calc basicCalcObj;
	private LinkedList<Element> list;
	private Element lastScanned;
	private int currCharNum, elementCount;
	private char currChar;
	private int lengthLast;

	public ParseElements(calc currBasicCalcObj, LinkedList<Element> origionalList) {
		basicCalcObj = currBasicCalcObj;
		list = origionalList;
		lengthLast = 0;
	}

	public void ParseExpression(String eqtn){
		currCharNum = 0;
		elementCount = 0;
		currChar = eqtn.charAt(currCharNum);

		while (currCharNum <= eqtn.length() - 1) {
			lastScanned = parseNext(eqtn);
			if (lastScanned != null){
				list.add(lastScanned);
			}
			elementCount++;
			currCharNum += lengthLast;
			//System.out.println(basicCalcObj.printList(list));
		}
	}
	
	public Element parseNext(String s){
		
		currChar = s.charAt(currCharNum);
		//System.out.println(currChar);
		lengthLast = 1;
		
		if (currChar == '-' || currChar == '+' || currChar == '*'
				|| currChar == '/' || currChar == '^' || currChar == '('
				|| currChar == ')' || currChar == '=' || currChar == '!') {

			lastScanned = scanBasicOp(s, currCharNum);
		}

		else if ((currChar <= '9' && currChar >= '0') || currChar == '.') {
			lastScanned = scanNum(s, currCharNum);
		}
		
		else if ((currChar <= 'Z' && currChar >= 'A')
				|| (currChar <= 'z' && currChar >= 'a') || currChar == '_') {
			
			lastScanned = scanVar(s, currCharNum);
		}

		else if (currChar == ' ') {
			return null;
		}
		else {
			return null;
		}
		return lastScanned;
	}
	
	public Operator scanBasicOp(String s, int pos){
		char newElm = 0;
		newElm = currChar;
		String newOp = new String();
		lengthLast = 1;

		switch (currChar) {
		case '*':
			if (s.charAt(pos + 1) == '*') {
				// '**' exception for powers, stores '^' in list
				newElm = '^';
				lengthLast++;
				pos++;
			}
			break;
		case '(':
			if (elementCount > 0) {
				if (lastHasVal()) {
					list.add(new Operator("*",
							basicCalcObj));
					elementCount++;
				}
				simplifyIfSubracted();
			}
			break;
		}

		newOp += newElm; // add operator to an empty string
		Operator newOperator = new Operator(newOp, basicCalcObj);
		return newOperator;
	}

	public Num scanNum(String s, int pos) {
		int length = 0;
		boolean hasPowOfTen = false, hasNegPower = false;

		for (int i = 0; pos + i < s.length(); i++) {

			currChar = s.charAt(pos + length);
			if ((currChar >= '0' && currChar <= '9') || currChar == '.'
					|| currChar == 'E' || currChar == '-') {
				length++;
				if (currChar == 'E' && hasPowOfTen) {
					length--;
					break;
				} else if (currChar == 'E' && !hasPowOfTen)
					hasPowOfTen = true;
				else if (currChar == '-') {
					if (hasPowOfTen && !hasNegPower) {
						hasNegPower = true;
					} else {
						length--;
						break;
					}
				}
			}
			else
				break;
		}
		lengthLast = length;
		double number = Double.parseDouble(s.substring(pos, pos + length));

		if (elementCount > 0) {
			if (lastHasVal()) {
				list.add(new Operator("*", basicCalcObj));
				elementCount++;
			} 
			simplifyIfSubracted();
		}
		Num newNum = new Num(number);
		return newNum;
	}

	public Element scanVar(String s, int pos) {
		int length = 0;

		for (int i = 0; pos + i < s.length(); i++) {

			currChar = s.charAt(pos + length);
			if ((currChar <= 'Z' && currChar >= 'A')
					|| (currChar <= 'z' && currChar >= 'a')
					|| (currChar == '_')
					|| (currChar <= '9' && currChar >= '0'))
				length++;
			else
				break;
		}
		lengthLast = length;
		
		String varElm = s.substring(pos, pos + length);
		int opPrec = basicCalcObj.OPSLIST.getPrec(varElm);

		if (opPrec >= 0) { // if the string is a predefined operator
			if (elementCount > 0) {
				if (lastHasVal()) {
					list.add(new Operator("*", basicCalcObj));
					elementCount++;
				} 
				else if (list.getLast() instanceof Operator) {
					Operator lastOp = (Operator) list
							.getLast();
					if ("-".equals(lastOp.getOp())) {
						list.removeLast();
						elementCount--;
						if (elementCount > 0) {
							if (lastHasVal()) {
								list.add(new Operator("+", basicCalcObj));
								elementCount++;
							}
						}
						list.add(new Num(-1));
						list.add(new Operator("*", basicCalcObj));
						elementCount++;
					}
				}
			}

			if ("sin".equals(varElm) || "cos".equals(varElm)
					|| "tan".equals(varElm)) {
				if (s.charAt(pos + length) == '-'
						&& s.charAt(pos + length + 1) == '1') {
					varElm += "-1";
					pos += 2;
					lengthLast += 2;
					opPrec = basicCalcObj.OPSLIST.getPrec(varElm);
				}
			}
			Operator newOp = new Operator(varElm, basicCalcObj);
			pos += length - 1;
			return newOp;
		}
		
		
		else {
			ElementWithIdentifier tempElm = basicCalcObj.CONSTLIST.findIfStored(varElm);
			if (tempElm != null){ //if not a current Constant
				if (elementCount > 0) {
					if (lastHasVal()) {
						list.add(new Operator("*", basicCalcObj));
						elementCount++;
					}
					simplifyIfSubracted();
				}
				return tempElm;
			}
			if (elementCount > 0) {
				if (lastHasVal()) {
					list.add(new Operator("*", basicCalcObj));
					elementCount++;
				}
				simplifyIfSubracted();
			}
			Var newVar = basicCalcObj.VARLIST.storeVar(varElm);
			return newVar;
		}
	}

	private boolean lastHasVal() {
		if (list.getLast() instanceof Num
				|| list.getLast() instanceof Var
				|| list.getLast() instanceof Constant
				|| (list.getLast() instanceof Operator && ((Operator) 
						list.getLast()).getOp().equals(")"))) {
			return true;
		} else
			return false;
	}
	
	private void simplifyIfSubracted(){
		if (list.getLast() instanceof Operator) {
			Operator lastOp = (Operator) list.getLast();
			if ("-".equals(lastOp.getOp())) {
				list.removeLast();
				elementCount--;
				if (elementCount > 0) {
					if (lastHasVal()) {
						list.add(new Operator("+", basicCalcObj));
						elementCount++;
					}
				}
				list.add(new Num(-1));
				list.add(new Operator("*", basicCalcObj));
				elementCount += 2;
			}
		}
	}
	
	public int getLengthLast(){
		return lengthLast;
	}
}
