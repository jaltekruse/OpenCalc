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

public class ParseElements {

	public calc basicCalcObj;
	
	public ParseElements(calc currBasicCalcObj){
		basicCalcObj = currBasicCalcObj;
	}
	public void Parse(char[] eqtn)
	{
	    int currCharNum = 0, elementCount = 0, prec = 0;
	    char currChar = eqtn[currCharNum];
	    Element prevElm = new Element();
	    boolean isOp = false;
	    
	    for(currCharNum = 0; currCharNum <= eqtn.length - 1 ; currCharNum++)
		{
	    	currChar = eqtn[currCharNum];
			//System.out.println("char:" + currChar + " elmCount:" + elementCount);
			if (currChar == '-' || currChar == '+' || currChar == '*' ||
				currChar == '/' || currChar == '^' || currChar == '(' ||
				currChar == ')' || currChar == '=' || currChar == '!') {
				
				char newElm = 0;
				newElm = currChar;
				String newOp = new String();
				
				switch(currChar)
				{
					case '*':
						if (eqtn[currCharNum + 1] == '*')
						{
							//** exception for powers, stores '^' in list
					    	newElm = '^';
							currCharNum++;
						}
						break;
					case '(':
						if (elementCount > 0){
							if(lastHasVal()){
								basicCalcObj.ORIGIONALLIST.add(new Operator("*", basicCalcObj));
								elementCount++;
							}
							else if (basicCalcObj.ORIGIONALLIST.getLast() instanceof Operator){
								Operator lastOp = (Operator) basicCalcObj.ORIGIONALLIST.getLast();
								//System.out.println("lastOp: " + lastOp);
								if ("-".equals(lastOp.getOp())){
									basicCalcObj.ORIGIONALLIST.removeLast();
									elementCount--;
									if (elementCount > 0){
										if(lastHasVal()){
											basicCalcObj.ORIGIONALLIST.add(new Operator("+", basicCalcObj));
											elementCount++;
										}
									}
									basicCalcObj.ORIGIONALLIST.add(new Num(-1));
									basicCalcObj.ORIGIONALLIST.add(new Operator("*", basicCalcObj));
									elementCount++;
								}
							}
						}
						break;
				}
				
				newOp += newElm; //add operator to an empty string
				if (elementCount > 0){
					if (basicCalcObj.ORIGIONALLIST.getLast() instanceof Operator){
						Operator lastOp = (Operator) basicCalcObj.ORIGIONALLIST.getLast();
						if ("-".equals(lastOp.getOp())){
							basicCalcObj.ORIGIONALLIST.removeLast();
							basicCalcObj.ORIGIONALLIST.add(new Num(-1));
							basicCalcObj.ORIGIONALLIST.add(new Operator("*", basicCalcObj));
							elementCount++;
						}
					}
				}
				
				elementCount++;
				basicCalcObj.ORIGIONALLIST.add(new Operator(newOp, basicCalcObj));
			}
			
			else if ((currChar <= '9' && currChar >= '0') || currChar == '.')
			{
				int length = 0;
				boolean hasPowOfTen = false, hasNegPower = false;
				
				for (int i = 0; currCharNum + i < eqtn.length; i++){
					
					currChar = eqtn[currCharNum+length];
					//System.out.println(currChar);
					if ((currChar >= '0' && currChar <= '9') || currChar == '.'
						  || currChar == 'E' || currChar == '-'){
						length++;
						if (currChar == 'E' && hasPowOfTen){
							length--;
							break;
						}
						else if (currChar == 'E' && !hasPowOfTen)
							hasPowOfTen = true;
						else if (currChar == '-'){
							if(hasPowOfTen && !hasNegPower){
								hasNegPower = true;
							}
							else{
								length--;
								break;
							}
						}
					}
					else
						break;
				}
				double number = scanNum(eqtn, currCharNum, length);
				
				if (elementCount > 0){
					if(lastHasVal()){
						basicCalcObj.ORIGIONALLIST.add(new Operator("*", basicCalcObj));
						elementCount++;
					}
					else if (basicCalcObj.ORIGIONALLIST.getLast() instanceof Operator){
						Operator lastOp = (Operator) basicCalcObj.ORIGIONALLIST.getLast();
						//System.out.println("lastOp: " + lastOp);
						if ("-".equals(lastOp.getOp())){
							basicCalcObj.ORIGIONALLIST.removeLast();
							elementCount--;
							if (elementCount > 0){
								if(lastHasVal()){
									basicCalcObj.ORIGIONALLIST.add(new Operator("+", basicCalcObj));
									elementCount++;
								}
							}
							basicCalcObj.ORIGIONALLIST.add(new Num(-1));
							basicCalcObj.ORIGIONALLIST.add(new Operator("*", basicCalcObj));
							elementCount++;
						}
					}
				}
					
				Num newNum = new Num(number);
				basicCalcObj.ORIGIONALLIST.add(newNum);
				currCharNum += length;
				currCharNum--;
				elementCount++;

			}
			else if ((currChar <= 'Z' && currChar >= 'A') || 
				(currChar <= 'z' && currChar >= 'a') || currChar == '_')
			{
				int length = 0;

				for (int i = 0; currCharNum + i < eqtn.length; i++){
					
					currChar = eqtn[currCharNum+length];
					if ((currChar <= 'Z' && currChar >= 'A') || 
							(currChar <= 'z' && currChar >= 'a') || 
							(currChar == '_')
							|| (currChar <= '9' && currChar >= '0'))
						length++;
					else
						break;
				}
				
				String varElm = scanVar(eqtn, currCharNum, length);
				int opPrec = basicCalcObj.OPSLIST.getPrec(varElm);
				
				if (opPrec >= 0){ //if the string is an predefined operator
					isOp = true;
					if (elementCount > 0){
						if(lastHasVal()){
							basicCalcObj.ORIGIONALLIST.add(new Operator("*", basicCalcObj));
							elementCount++;
						}
						else if (basicCalcObj.ORIGIONALLIST.getLast() instanceof Operator){
							Operator lastOp = (Operator) basicCalcObj.ORIGIONALLIST.getLast();
							//System.out.println("lastOp: " + lastOp);
							if ("-".equals(lastOp.getOp())){
								basicCalcObj.ORIGIONALLIST.removeLast();
								elementCount--;
								if (elementCount > 0){
									if(lastHasVal()){
										basicCalcObj.ORIGIONALLIST.add(new Operator("+", basicCalcObj));
										elementCount++;
									}
								}
								basicCalcObj.ORIGIONALLIST.add(new Num(-1));
								basicCalcObj.ORIGIONALLIST.add(new Operator("*", basicCalcObj));
								elementCount++;
							}
						}
					}
					
					if ("sin".equals(varElm) || "cos".equals(varElm) || "tan".equals(varElm)){
						if (eqtn[currCharNum + length] == '-' && eqtn[currCharNum + length + 1] == '1'){
							varElm += "-1";
							currCharNum += 2;
							opPrec = basicCalcObj.OPSLIST.getPrec(varElm);
						}
					}
					basicCalcObj.ORIGIONALLIST.add(new Operator(varElm, basicCalcObj));
					elementCount++;
				}
				
				else{
					
					if (elementCount > 0){
						if(lastHasVal()){
							basicCalcObj.ORIGIONALLIST.add(new Operator("*", basicCalcObj));
							elementCount++;
						}
						else if (basicCalcObj.ORIGIONALLIST.getLast() instanceof Operator){
							Operator lastOp = (Operator) basicCalcObj.ORIGIONALLIST.getLast();
							//System.out.println("lastOp: " + lastOp);
							if ("-".equals(lastOp.getOp())){
								basicCalcObj.ORIGIONALLIST.removeLast();
								elementCount--;
								if (elementCount > 0){
									if(lastHasVal()){
										basicCalcObj.ORIGIONALLIST.add(new Operator("+", basicCalcObj));
										elementCount++;
									}
								}
								basicCalcObj.ORIGIONALLIST.add(new Num(-1));
								basicCalcObj.ORIGIONALLIST.add(new Operator("*", basicCalcObj));
								elementCount++;
							}
						}
					}
					basicCalcObj.ORIGIONALLIST.add(basicCalcObj.VARLIST.storeVar(varElm));
					elementCount++;
				}
				
				isOp = false;
				currCharNum += length - 1;
			}
			
			else if (currChar == ' '){
				;//do nothing for spaces
			}
			
			else{
				System.out.println("input error, check syntax");
			}
			//System.out.println(basicCalcObj.printList(basicCalcObj.ORIGIONALLIST) + " ElmCount:" + elementCount + "\n");
		}
	}
	public double scanNum(char[] equation, int pos,int len)
	{
		char[] subArray = new char[len];
		System.arraycopy(equation, pos, subArray, 0, len);
		String num = new String();
		for (int i = 0; i < len; i++){
			num += subArray[i];
		}
		double number = Double.parseDouble(num);
		return number;
	}
	
	public String scanVar(char[] equation, int pos, int len)
	{
		String varStr = new String();
		char currChar = equation[pos];
		int charCount = 0;
		for(int i = 0; i < len; i++){
			currChar = equation[pos + i];	
			varStr += currChar;
		}
		return varStr;
	}
	
	public boolean lastHasVal(){
		if(basicCalcObj.ORIGIONALLIST.getLast() instanceof Num ||
				basicCalcObj.ORIGIONALLIST.getLast() instanceof Var ||
				(basicCalcObj.ORIGIONALLIST.getLast() instanceof Operator &&
						((Operator) basicCalcObj.ORIGIONALLIST.getLast()).getOp().equals(")"))){
			return true;
		}
		else
			return false;
	}
}
