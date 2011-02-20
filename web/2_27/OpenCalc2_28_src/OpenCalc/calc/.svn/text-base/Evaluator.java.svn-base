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

public class Evaluator {

	private int lastOpenParen;
	private int firstCloseParen;
	private int startPos, endPos;
	private double finalResult;
	private boolean calcComplete;
	private calc basicCalcObj;
	private LinkedList<Element> list;
	
	//1-radians  2-degrees  3-Gradians
	private int angleUnits;

	public Evaluator(calc currCalc) {
		lastOpenParen = 0;
		firstCloseParen = Integer.MAX_VALUE;
		finalResult = 0;
		startPos = 0;
		endPos = 0;
		calcComplete = false;
		basicCalcObj = currCalc;
		list = basicCalcObj.CURRLIST;
	}

	public double eval(LinkedList<Element> l) {
		list = l;
		angleUnits = basicCalcObj.getAngleUnits();
		double result = 0;

		int elmWithPrec = 0;
		Operator currOp = new Operator();
		Var currVar = new Var("");

		while (!calcComplete) {
			//System.out.println(basicCalcObj.printList(list));
			
			endPos = list.size() - 1;
			
			if (firstCloseParen != Integer.MAX_VALUE) {
				startPos = lastOpenParen + 1;
				endPos = firstCloseParen - 1;
			}

			//System.out.println("StartPos:" + startPos + "  endPos:" + endPos);
			elmWithPrec = FindHighestPrec(list, startPos,
					endPos);

			if (list.get(elmWithPrec) instanceof Num) {
				// if there is a set of parens
				if (firstCloseParen != Integer.MAX_VALUE) {
					
					if (elmWithPrec >= 2) {
						Operator tempOp = (Operator) list
								.get(elmWithPrec - 2);
						double currNum = ((Num) list
								.get(elmWithPrec)).getNumber();
						double funcResult = 0;
						boolean opHandled = false;

						if ("sin".equals(tempOp.getOp())) {
							currNum = convertAngle2Rad(currNum);
							funcResult = Math.sin(currNum);
							opHandled = true;
						}
						else if ("cos".equals(tempOp.getOp())) {
							currNum = convertAngle2Rad(currNum);
							funcResult = Math.cos(currNum);
							opHandled = true;
						}

						else if ("tan".equals(tempOp.getOp())) {
							currNum = convertAngle2Rad(currNum);
							funcResult = Math.tan(currNum);
							opHandled = true;
						}

						else if ("sin-1".equals(tempOp.getOp())) {
							funcResult = Math.asin(currNum);
							funcResult = convertAngleFromRad(funcResult);
							opHandled = true;
						}

						else if ("cos-1".equals(tempOp.getOp())) {
							funcResult = Math.acos(currNum);
							funcResult = convertAngleFromRad(funcResult);
							opHandled = true;
						}

						else if ("tan-1".equals(tempOp.getOp())) {
							funcResult = Math.atan(currNum);
							funcResult = convertAngleFromRad(funcResult);
							opHandled = true;
						}

						else if ("sqrt".equals(tempOp.getOp())) {
							funcResult = Math.sqrt(currNum);
							opHandled = true;
						}

						else if ("ln".equals(tempOp.getOp())) {
							funcResult = Math.log(currNum);
							opHandled = true;
						}

						else if ("log".equals(tempOp.getOp())) {
							funcResult = Math.log10(currNum);
							opHandled = true;
						}

						else if ("abs".equals(tempOp.getOp())) {
							funcResult = Math.abs(currNum);
							opHandled = true;
						}

						else if ("int".equals(tempOp.getOp())) {
							funcResult = Math.floor(currNum);
							opHandled = true;
						}

						else if ("floor".equals(tempOp.getOp())) {
							funcResult = Math.floor(currNum);
							opHandled = true;
						}

						else if ("ceil".equals(tempOp.getOp())) {
							funcResult = Math.ceil(currNum);
							opHandled = true;
						}
						
						if (opHandled) {
							list.add(elmWithPrec - 2, new Num(
									funcResult));
							for (int i = 0; i < 4; i++)
								list.remove(elmWithPrec - 1);
							firstCloseParen = Integer.MAX_VALUE;
							startPos = 0;
						}
						else {
							list.remove(elmWithPrec - 1);
							// gets rid of other paren, shifted from last removal
							list.remove(elmWithPrec); 
							firstCloseParen = Integer.MAX_VALUE;
							startPos = 0;
						}
					}
					else {
						list.remove(elmWithPrec - 1);
						// gets rid of other paren, shifted from last removal
						list.remove(elmWithPrec); 
						firstCloseParen = Integer.MAX_VALUE;
						startPos = 0;
					}
				}
				else { // if there is just one number left
					Num currNum = (Num) list.get(elmWithPrec);
					finalResult = currNum.getNumber();
					calcComplete = true;
				}
			}

			else if (list.get(elmWithPrec) instanceof Operator) {
				currOp = (Operator) list.get(elmWithPrec);
				if (currOp.getOp().equals("(")) {
					continue;
				} else
					PerformOp(elmWithPrec);
			}

			else if (list.get(elmWithPrec) instanceof Var) {
				double var2Num;
				currVar = (Var) list.get(elmWithPrec);
				var2Num = currVar.getValue();
				list.set(elmWithPrec, new Num(var2Num));
				startPos = 0;
			}
			else if (list.get(elmWithPrec) instanceof Constant) {
				double const2Num;
				Constant currConst = (Constant) list.get(elmWithPrec);
				const2Num = currConst.getValue();
				list.set(elmWithPrec, new Num(const2Num));
				startPos = 0;
			}
		}
		calcComplete = false;
		return finalResult;
	}

	private double convertAngle2Rad(double angle) {
		// TODO Auto-generated method stub
		if(angleUnits == 2){
			angle *= (Math.PI/180);
		}
		else if(angleUnits == 3){
			angle *= (Math.PI/200);
		}
		return angle;
	}
	
	private double convertAngleFromRad(double angle) {
		// TODO Auto-generated method stub
		if(angleUnits == 2){
			angle *= (180/Math.PI);
		}
		else if(angleUnits == 3){
			angle *= (200/Math.PI);
		}
		return angle;
	}

	public int FindHighestPrec(LinkedList<Element> linkedList, int startPos,
			int endPos) {
		int elmWithHighPrec = startPos;
		int highestPrec = 8;
		int currPos = startPos;
		int parenMatcher = 0;
		Element currObj;
		Operator currOp = new Operator();
		
		currObj = (Element) list.get(currPos);

		for (currPos = startPos; currPos <= endPos; currPos++) {
			//System.out.println("currPrec:" + currObj.getPrec() + "  highPrec:" + highestPrec);
			currObj = (Element) list.get(currPos);
			if (currObj.getPrec() < highestPrec) {
				elmWithHighPrec = currPos;
				highestPrec = currObj.getPrec();
				//System.out.println("chengedElmWithPrec: " + elmWithHighPrec);
			}

			if (currObj instanceof Operator) {
				currOp = (Operator) currObj;
				if ("(".equals(currOp.getOp())) {
					parenMatcher++;
					// System.out.println("if openParen@@@@@@@@@@@@@@@@@@@@@@@@@");
					lastOpenParen = currPos;
				}

				else if (")".equals(currOp.getOp())) {
					// System.out.println("if closeparen!!!!!!!!!!!!!!!!!!!!!!!!");
					if (parenMatcher == 1
							&& firstCloseParen == Integer.MAX_VALUE) {
						firstCloseParen = currPos;
						parenMatcher--;
						break;
					}
					if (firstCloseParen == Integer.MAX_VALUE) {
						firstCloseParen = currPos;
					}
					parenMatcher--;
				}
				// System.out.println("parenMathcer: " + parenMatcher);
			}
			//System.out.println(list.get(currPos).toString() + " forloopPos:" + currPos + "\n");
		}
		if (parenMatcher != 0){
			System.out.println("match parens properly!");
			calcComplete = true;
			finalResult = Double.MAX_VALUE;
		}
		// System.out.println("emlwithprec:" +
		// list.get(elmWithHighPrec));
		return elmWithHighPrec;
	}

	public void PerformOp(int opPos) {
		//System.out.println("performOp");
		double result = 0;
		String currOp = ((Operator) list.get(opPos)).getOp();
		// System.out.println(currOp);
		Element objPrev = list.get(opPos - 1);
		//System.out.println(objPrev.toString());
		double numPrev = 0;
		boolean opHandled = false;

		if (objPrev instanceof Num) {
			numPrev = ((Num) objPrev).getNumber();
		}
		if (objPrev instanceof Var) {
			numPrev =((Var) objPrev).getValue();
		}
		if (objPrev instanceof Constant) {
			numPrev = ((Constant) objPrev).getValue();
		}

		if ("!".equals(currOp)) {
			int fact = 1;
			if (numPrev % 1 == 0) {
				boolean isNeg = false;
				if (numPrev < 0) {
					isNeg = true;
				}

				numPrev = Math.abs(numPrev);
				while (numPrev > 1) {
					fact *= numPrev;
					numPrev--;
				}
				if (isNeg == true) {
					fact *= -1;
				}
			} else {
				System.out.println("use factorial with Int values only!!");
			}
			Num newNum = new Num(fact);
			list.add(opPos - 1, newNum);
			for (int i = 0; i < 2; i++)
				list.remove(opPos);
			if (firstCloseParen != Integer.MAX_VALUE)
				firstCloseParen -= 1;
			return;
		}

		Object objPost = list.get(opPos + 1);
		//System.out.println(objPost.toString());
		Var varForStorage = new Var("");
		double numPost = 0;

		if (objPost instanceof Num) {
			numPost = ((Num) objPost).getNumber();
		}
		if (objPost instanceof Var) {
			numPost = ((Var) objPost).getValue();
		}
		if (objPost instanceof Constant) {
			numPost = ((Constant) objPost).getValue();
		}
		
		if ("-".equals(currOp)) {
			result = numPrev - numPost;
			opHandled = true;
		}
		
		if ("+".equals(currOp)) {
			result = numPrev + numPost;
			opHandled = true;
		}
		if ("/".equals(currOp)) {
			result = numPrev / numPost;
			opHandled = true;
		}
		
		if ("*".equals(currOp)) {
			result = numPrev * numPost;
			opHandled = true;
		}
		
		if ("^".equals(currOp)) {
			result = Math.pow(numPrev, numPost);
			opHandled = true;
		}
		
		if ("=".equals(currOp)) {
			varForStorage = (Var) objPrev;
			basicCalcObj.VARLIST.setVarVal(varForStorage.getVarString(),
					numPost);
			finalResult = numPost;
			opHandled = true;
			calcComplete = true;
		}

		if (opHandled) {
			Num newNum = new Num(result);
			list.add(opPos - 1, newNum);
			for (int i = 0; i < 3; i++)
				list.remove(opPos);
			// System.out.println(basicCalcObj.printList());
			if (firstCloseParen != Integer.MAX_VALUE)
				firstCloseParen -= 2;
		}

	}
}
