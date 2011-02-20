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
import java.util.Vector;


public class Evaluator {
	
	int lastOpenParen;
	int firstCloseParen;
	int startPos, endPos;
	double finalResult;
	boolean calcComplete;
	private calc basicCalcObj;
	
	public Evaluator(calc currCalc){
		lastOpenParen = 0;
		firstCloseParen = Integer.MAX_VALUE;
		finalResult = 0;
		startPos = 0;
		endPos = 0;
		calcComplete = false;
		basicCalcObj = currCalc;
	}
	
	public double eval()
	{
		double result = 0;
		
		int elmWithPrec = 0;
		Operator currOp = new Operator();
		Var currVar = new Var("", basicCalcObj);
		
		while (!calcComplete){
			//System.out.println(basicCalcObj.printList(basicCalcObj.CURRLIST));
			
			Object tempObj = basicCalcObj.CURRLIST.get(0);
			for(endPos = 0; tempObj != basicCalcObj.CURRLIST.getLast(); endPos++, tempObj = basicCalcObj.CURRLIST.get(endPos));
			
			if (firstCloseParen != Integer.MAX_VALUE){
				startPos = lastOpenParen + 1;
				endPos = firstCloseParen - 1;
			}
			
			//System.out.println("StartPos:" + startPos + "  endPos:" + endPos);
			elmWithPrec = FindHighestPrec(basicCalcObj.CURRLIST, startPos, endPos);
			
			if (basicCalcObj.CURRLIST.get(elmWithPrec) instanceof Num){
				if (firstCloseParen != Integer.MAX_VALUE){ //if there is a set of parens
					if (elmWithPrec >= 2){
						Operator tempOp = (Operator) basicCalcObj.CURRLIST.get(elmWithPrec - 2);
						double currNum = ((Num) basicCalcObj.CURRLIST.get(elmWithPrec)).getNumber();
						double funcResult = 0;
						boolean opHandled = false;
						
						if("sin".equals(tempOp.getOp())){
							funcResult = Math.sin(currNum);
							opHandled = true;
						}
						else if ("cos".equals(tempOp.getOp())){
							funcResult = Math.cos(currNum);
							opHandled = true;
						}
							
						else if ("tan".equals(tempOp.getOp())){
							funcResult = Math.tan(currNum);
							opHandled = true;
						}
							
						else if ("sin-1".equals(tempOp.getOp())){
							funcResult = Math.asin(currNum);
							opHandled = true;
						}
						
						else if ("cos-1".equals(tempOp.getOp())){
							funcResult = Math.acos(currNum);
							opHandled = true;
						}
						
						else if ("tan-1".equals(tempOp.getOp())){
							funcResult = Math.atan(currNum);
							opHandled = true;
						}
						
						else if ("sqrt".equals(tempOp.getOp())){
							funcResult = Math.sqrt(currNum);
							opHandled = true;
						}
						
						else if ("ln".equals(tempOp.getOp())){
							funcResult = Math.log(currNum);
							opHandled = true;
						}
						
						else if ("log".equals(tempOp.getOp())){
							funcResult = Math.log10(currNum);
							opHandled = true;
						}
						
						if (opHandled){
							basicCalcObj.CURRLIST.add(elmWithPrec - 2, new Num(funcResult));
							for(int i = 0; i < 4; i++) basicCalcObj.CURRLIST.remove(elmWithPrec - 1);
							firstCloseParen = Integer.MAX_VALUE;
							startPos = 0;
						}
						else{
							basicCalcObj.CURRLIST.remove(elmWithPrec - 1);
							basicCalcObj.CURRLIST.remove(elmWithPrec); //gets rid of other paren, shifted from last removal
							firstCloseParen = Integer.MAX_VALUE;
							startPos = 0;
						}
					}
					else{
						basicCalcObj.CURRLIST.remove(elmWithPrec - 1);
						basicCalcObj.CURRLIST.remove(elmWithPrec); //gets rid of other paren, shifted from last removal
						firstCloseParen = Integer.MAX_VALUE;
						startPos = 0;
					}
				}
				else{ //if there is just one number left
					Num currNum = (Num) basicCalcObj.CURRLIST.get(elmWithPrec);
					finalResult = currNum.getNumber();
					calcComplete = true;
				}
			}
			
			else if ( basicCalcObj.CURRLIST.get(elmWithPrec) instanceof Operator){
				currOp = (Operator) basicCalcObj.CURRLIST.get(elmWithPrec);
				if (currOp.getOp().equals("(")){
					continue;
				}
				else
					PerformOp(elmWithPrec);
			}
			
			else if ( basicCalcObj.CURRLIST.get(elmWithPrec) instanceof Var){
				double var2Num;
				currVar = (Var) basicCalcObj.CURRLIST.get(elmWithPrec);
				var2Num = basicCalcObj.VARLIST.getVarVal(currVar.getVarString());
				basicCalcObj.CURRLIST.set(elmWithPrec, new Num(var2Num));
				startPos = 0;
			}
		}
		calcComplete = false;
		return finalResult;
	}
	
	public int FindHighestPrec(LinkedList<Object> linkedList, int startPos, int endPos)
	{
		int elmWithHighPrec = startPos;
		int highestPrec = 8;
		int currPos = startPos;
		int parenMatcher = 0;
		Element currObj = new Element();
		Operator currOp = new Operator();
		currObj = (Element) basicCalcObj.CURRLIST.get(currPos);
		
		for (currPos = startPos ; currPos <= endPos; currPos++)
		{
			//System.out.println("currPrec:" + currObj.getPrec() + "  highPrec:" + highestPrec);
			currObj = (Element) basicCalcObj.CURRLIST.get(currPos);
			if (currObj.getPrec() < highestPrec){
				elmWithHighPrec = currPos;
				highestPrec = currObj.getPrec();
				//System.out.println("chengedElmWithPrec: " + elmWithHighPrec);
			}
			
			if (currObj instanceof Operator){
				currOp = (Operator) currObj;
				if ("(".equals(currOp.getOp())){
					parenMatcher++;
					//System.out.println("if openParen@@@@@@@@@@@@@@@@@@@@@@@@@");
					lastOpenParen = currPos;
				}
				
				else if (")".equals(currOp.getOp())){
					//System.out.println("if closeparen!!!!!!!!!!!!!!!!!!!!!!!!");
					if (parenMatcher == 1 && firstCloseParen == Integer.MAX_VALUE){
						firstCloseParen = currPos;
						parenMatcher--;
						break;
					}
					if (firstCloseParen == Integer.MAX_VALUE){
						firstCloseParen = currPos;
					}
					parenMatcher--;
				}
				//System.out.println("parenMathcer: " + parenMatcher);
			}
			//System.out.println(basicCalcObj.CURRLIST.get(currPos).toString() + " forloopPos:" + currPos + "\n");
		}
		if (parenMatcher <= 1 &&  parenMatcher > 0)
			basicCalcObj.CURRLIST.add(new Operator(")", basicCalcObj));
		else if (parenMatcher > 1 || parenMatcher < 0)
			System.out.println("match parens properly!");
		else
			;
		//System.out.println("emlwithprec:" + basicCalcObj.CURRLIST.get(elmWithHighPrec));
		return elmWithHighPrec;
	} 

	public void PerformOp(int opPos)
	{
		//System.out.println("performOp");
		double result = 0;
		String currOp = ((Operator) basicCalcObj.CURRLIST.get(opPos)).getOp();
		//System.out.println(currOp);
		Object objPrev = basicCalcObj.CURRLIST.get(opPos - 1);
		double numPrev = 0;
		boolean opHandled = false;
		
		if (objPrev instanceof Num) {
			numPrev = ((Num) objPrev).getNumber();
		}
		if (objPrev instanceof Var) {
			objPrev = (Var) objPrev;
			numPrev = basicCalcObj.VARLIST.getVarVal(((Var) objPrev).getVarString());
		}
		
		if ("!".equals(currOp)){
			int fact = 1;
			if (numPrev % 1 == 0){
				boolean isNeg = false;
				if (numPrev < 0){
					isNeg = true;
				}
				
				numPrev = Math.abs(numPrev);
				while (numPrev > 1){
					fact *= numPrev;
					numPrev--;
				}
				if (isNeg == true){
					fact *= -1;
				}
			}
			else{
				System.out.println("use factorial with Int values only!!");
			}
			Num newNum = new Num(fact);
			basicCalcObj.CURRLIST.add( opPos - 1, newNum);
			for(int i = 0; i < 2; i++) basicCalcObj.CURRLIST.remove(opPos);
			if(firstCloseParen != Integer.MAX_VALUE)
				firstCloseParen -= 1;
			return;
		}
		
		Object objPost = basicCalcObj.CURRLIST.get(opPos + 1);
		Var varForStorage = new Var("", basicCalcObj);
		double numPost = 0;
		
		if (objPost instanceof Num) {
			numPost = ((Num) objPost).getNumber();
		}
		if (objPost instanceof Var) {
			objPost = (Var) objPost;
			numPost = basicCalcObj.VARLIST.getVarVal(((Var) objPost).getVarString());
		}
		
		if ("-".equals(currOp)){
			result = numPrev - numPost;
			opHandled = true;
		}
		if ("+".equals(currOp)){
			result = numPrev + numPost;
			opHandled = true;
		}
		if ("/".equals(currOp)){
			result = numPrev / numPost;
			opHandled = true;
		}
		if ("*".equals(currOp)){
			result = numPrev * numPost;
			opHandled = true;
		}
		if ("^".equals(currOp)){
			result = Math.pow( numPrev, numPost);
			opHandled = true;
		}
		if ("=".equals(currOp)){
			varForStorage = (Var) objPrev;
			basicCalcObj.VARLIST.setVarVal(varForStorage.getVarString(), numPost);
			finalResult = numPost;
			opHandled = true;
			calcComplete = true;
		}
		
		if (opHandled){ 
			Num newNum = new Num(result);
			basicCalcObj.CURRLIST.add( opPos - 1, newNum);
			for(int i = 0; i < 3; i++) basicCalcObj.CURRLIST.remove(opPos);
			//System.out.println(basicCalcObj.printList());
			if(firstCloseParen != Integer.MAX_VALUE)
				firstCloseParen -= 2;
		}
			
	}
}
