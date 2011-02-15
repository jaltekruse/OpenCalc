package tree;




import gui.MainApplet;

import java.util.ArrayList;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Operators;


import tree.Operator;

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

public class ExpressionParser {
	
	private Expression e;
	private ArrayList<Value> vals;
	public VarStorage VARLIST;
	public ConstantStorage CONSTLIST;
	private int currCharNum, elementCount;
	private char currChar;
	private int matchedParens;
	private MainApplet GUI;
	
	private int angleUnits;
	public static final int RAD = 1;
	public static final int DEG = 2;
	public static final int GRAD = 3;
	
	private static final String dateModified = "12-17-2010";

	//this value stores the number of characters in the string input that
	//an element takes up, other elements that are automatically added by
	//the parser, such as multiplications in expressions like 5(x), do not affect it
	private int lengthLast;

	/** 
	 * Creates a new <code> ExpressionParser</code>
	 */
	public ExpressionParser() {
		lengthLast = 0;
		vals = new ArrayList<Value>();
		VARLIST = new VarStorage(this);
		CONSTLIST = new ConstantStorage();
		angleUnits = 1;
		
		//associates this object with all of the Decimal objects, so they can
		//find the current angleUnits
		Decimal staticDec = new Decimal(this);
	}

	public ExpressionParser(MainApplet mainApplet) {
		// TODO Auto-generated constructor stub
		lengthLast = 0;
		vals = new ArrayList<Value>();
		VARLIST = new VarStorage(this);
		CONSTLIST = new ConstantStorage();
		GUI = mainApplet;
		angleUnits = 1;
		
		//associates this object with all of the Decimal objects, so they can
		//find the current angleUnits
		Decimal staticDec = new Decimal(this);
	}
	
	public MainApplet getGUI(){
		return GUI;
	}
	
	public boolean hasGUI(){
		return (GUI != null);
	}

	public String getDateModified(){
		return dateModified;
	}
	
	/**
	 * Takes the equation to be parsed and returns the expression.
	 * 
	 * @param eqtn - string to be parsed
	 * @throws ParseException  
	 */
	public Value ParseExpression(String eqtn) throws ParseException{
		if(eqtn.equals("")){
			throw new ParseException("Empty expression entered");
		}
		
		matchedParens = 0;
		e = null;
		currCharNum = 0;
		elementCount = 0;
		currChar = eqtn.charAt(currCharNum);
		vals = new ArrayList<Value>();

		//the main loop
		while (currCharNum <= eqtn.length() - 1) {
			parseElement(eqtn, currCharNum);
			elementCount++;
			System.out.println("lengthLast: " + lengthLast);
			currCharNum += lengthLast;
			//uncomment the next two lines to print out the expression 
			//as the loop executes
			if (e != null)
				System.out.println(e.toString());
		}
		
		if (matchedParens == 1)
		{//there was one open paren that did not get close, assume one at the end
			hitCloseParen();
		}
		
		else if (matchedParens != 0){
			throw new ParseException("Did not match parenthesis");
		}
		else{} //parens were matched 
		if (vals.size() == 1){//there were no operators given
			
//			if (vals.get(0) instanceof Var 
//					&& ((Var)vals.get(0)).getValue() == null){
//				throw new ParseException("Variable \"" + 
//						((Var)vals.get(0)).getName() + "\" has not been given a value");
//			}
			return vals.get(0);
		}
		if (e == null){
			throw new ParseException("invalid expression");
		}
		if (e instanceof BinExpression){
			if (((BinExpression)e).getRightChild() == null){
				addNewValue(new Nothing());
			}
		}
		while(e.hasParent()){
			e = e.getParent();
		}
		//System.out.println(e.toString());
		return e;
	}
	
	/**
	 * Takes the string currently being parsed, and the position of
	 * the next character that needs to be parsed.
	 * 
	 * @param s - the string being parsed
	 * @return the next element in the equation
	 * @throws ParseException 
	 */
	private void parseElement(String s, int pos) throws ParseException{
		
		currChar = s.charAt(pos);
		lengthLast = 1;
		System.out.println("currChar: " + currChar);
		switch(currChar){
		case '+':
			addBinOp(Operator.ADD);
			break;
		case '-':
			addBinOp(Operator.SUBTRACT);
			break;
		case '*':
			if (currCharNum < s.length() - 1 && s.charAt(currCharNum + 1) == '*'){
				lengthLast += 1; 
				addBinOp(Operator.POWER);
				break;
			}
			else{
				addBinOp(Operator.MULTIPLY);
				break;
			}
		case '/':
			addBinOp(Operator.DIVIDE);
			break;
		case '=':
			addBinOp(Operator.ASSIGN);
			break;
		case '^':
			addBinOp(Operator.POWER);
			break;
		case '!':
			if(vals.size() == 1)
				addUrinaryOp(Operator.FACT);
			else
				addUrinaryOp(Operator.NOT);
			break;
		case '(':
			matchedParens++;
			addUrinaryOp(Operator.PAREN);
			break;
		case ')':
			matchedParens--;
			hitCloseParen();
			break;
		}

		if ((currChar <= '9' && currChar >= '0') || currChar == '.') {
			scanNum(s, currCharNum);
			return;
		}
		
		else if ((currChar <= 'Z' && currChar >= 'A')
				|| (currChar <= 'z' && currChar >= 'a') || currChar == '_') {
			scanVar(s, pos);
			return;
		}

		else if (currChar == ' ') {
			;
		}
		else { //if hit any unrecognized character, skip it and go on
			;
		}
	}
	
	private void hitCloseParen() throws ParseException {
		// TODO Auto-generated method stub
		if (e.isContainerOp()){
			if(vals.size() == 1){
				addUrinaryOp(Operator.NOTHING);
			}
		}
		int numParensHit = 0;
		do{
			e = e.getParent();
			if (e.getOp() == Operator.PAREN){
				numParensHit++;;
			}
		}while (e.hasParent() && numParensHit != 2);
		
		if (numParensHit == 2)
			e = (Expression) ((UrinaryExpression)e).getChild();
	}

	/**
	 * Scans a Number. Takes the string being parsed, and the position at which
	 * to begin. Actual scanning is done by the standard java Double scan
	 * function. Length of the number in the string is determined in a basic
	 * loop.
	 * 
	 * @param s - string to parse
	 * @param pos - current position
	 * @return a Decimal object
	 * @throws ParseException 
	 */
	public void scanNum(String s, int pos) throws ParseException {
		int length = 0;
		boolean hasPowOfTen = false, hasNegPower = false;
		
		//this loop determines the length of the number, it is scanned
		//in by a standard Java function
		for (int i = 0; pos + i < s.length(); i++) {

			currChar = s.charAt(pos + length);
			if ((currChar >= '0' && currChar <= '9') || currChar == '.'
					|| currChar == 'E' || currChar == '-') {
				length++;
				if ((currChar == 'E' || currChar == '.') && hasPowOfTen) {
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
		Decimal newNum = new Decimal(number);
		if (!hasPowOfTen && length < 9){
			Value newFrac = Fraction.Dec2Frac(newNum);
			addNewValue(newFrac);
		}
		else{
			addNewValue(newNum);
		}
	}
	
	/**
	 * Scans a Variable. Checks if it is an existing variable or constant
	 * and if it is adds the respective element to the expression.
	 * 
	 * @param s - string to parse
	 * @param pos - current position
	 * @return an Operator object
	 * @throws ParseException 
	 */

	public void scanVar(String s, int pos) throws ParseException{
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
		
		//adds "-1" to the operator for inverse trig functions
		if ("sin".equals(varElm) || "cos".equals(varElm)
				|| "tan".equals(varElm)) {
			if(s.length() - pos > 3){
				if (s.charAt(pos + length) == '-'
						&& s.charAt(pos + length + 1) == '1') {
					varElm += "-1";
					pos += 2;
					lengthLast += 2;
				}
			}
		}
		
		if(varElm.equals("sin")){
			addUrinaryOp(Operator.SIN);
			return;
		}
		else if(varElm.equals("cos")){
			addUrinaryOp(Operator.COS);
			return;
		}
		else if(varElm.equals("tan")){
			addUrinaryOp(Operator.TAN);
			return;
		}
		else if(varElm.equals("sin-1")){
			addUrinaryOp(Operator.INV_SIN);
			return;
		}
		else if(varElm.equals("cos-1")){
			addUrinaryOp(Operator.INV_COS);
			return;
		}
		else if(varElm.equals("tan-1")){
			addUrinaryOp(Operator.INV_TAN);
			return;
		}
		else if(varElm.equals("log")){
			addUrinaryOp(Operator.LOG);
			return;
		}
		else if(varElm.equals("ln")){
			addUrinaryOp(Operator.LN);
			return;
		}
		else if(varElm.equals("int")){
			addUrinaryOp(Operator.INT);
			return;
		}
		else if(varElm.equals("floor")){
			addUrinaryOp(Operator.FLOOR);
			return;
		}
		else if(varElm.equals("ceil")){
			addUrinaryOp(Operator.CEILING);
			return;
		}
		else if(varElm.equals("sqrt")){
			addUrinaryOp(Operator.SQRT);
			return;
		}
		else if(varElm.equals("frac")){
			//think of how to add things like this, functions with multiple inputs
		}
		else if(varElm.equals("solve")){
			
		}
		else{
			Constant tempElm = (Constant) CONSTLIST.findIfStored(varElm);
			if (tempElm != null){ //if not a current Constant
				addNewValue(tempElm);
				return;
			}
//			if (!(e == null && vals.size() == 0)){// something has been scanned in
//				if (VARLIST.findIfStored(varElm) == null){
//					throw new ParseException("Variable \"" + varElm + "\" has not been given a value");
//				}
//			}
			
			Var newVar = VARLIST.storeVar(varElm, null);
			addNewValue(newVar);
		}
	}
	
	public void addNewValue(Value v) throws ParseException{
//		if (v instanceof Var && ((Var)v).getValue() == null & e != null)
//		{//a new variable is being added to the tree, that has not been given a value (a tree has already
//			//been started because the current expression "e" is not null)
//			throw new ParseException("Variable \"" + ((Var)v).getName()
//					+ "\" has not been given a value");
//		}
//		if (vals.size() == 1 && e == null && vals.get(0) instanceof Var 
//				&& ((Var)vals.get(0)).getValue() == null)
//		{//A variable was found and before a subsequent operator was found, another 
//			//value was found, system would add implied multiplication, but the only use of 
//			//an undeclared variable is "varName = 8*9" etc.
//			throw new ParseException("Variable \"" + ((Var)vals.get(0)).getName()
//					+ "\" has not been given a value");
//		}
		if(e instanceof BinExpression){
			if(((BinExpression)e).getRightChild() == null){
				((BinExpression)e).setRightChild(v);
				return;
			}
			else{
				addBinOp(Operator.MULTIPLY);
				addNewValue(v);
				return;
			}
		}
		else if(e instanceof UrinaryExpression){
			if (vals.size() == 1 || ((UrinaryExpression)e).hasChild()){
//				if (vals.get(0) instanceof Var){
//					if (((Var)vals.get(0)).getValue() == null){
//						throw new ParseException("Variable \"" + ((Var)vals.get(0)).getName()
//								+ "\" has not been given a value");
//					}
//				}
				addBinOp(Operator.MULTIPLY);
				addNewValue(v);
				return;
			}
			else if (e.isContainerOp()){
				vals.add(v);
				return;
			}
			else if(((UrinaryExpression)e).getChild() == null){
				((UrinaryExpression)e).setChild(v);
				return;
			}
			else{
				throw new ParseException("Two values following a urinary operator");
			}
		}
		else if(vals.size() == 1){
			addBinOp(Operator.MULTIPLY);
			addNewValue(v);
			return;
		}
		vals.add(v);
	}

	public void addBinOp(Operator o) throws ParseException{
		BinExpression newEx;
		
		if (e instanceof BinExpression  && ((BinExpression)e).getRightChild() == null){
			if (o == Operator.SUBTRACT && vals.isEmpty()){
				addUrinaryOp(Operator.NEG);
				return;
			}
			else
			{
				//throw new ParseException("2 binary operators adjacent");
				addNewValue(new Nothing());
				System.out.println("RAWSRASRWEFSADFSDF: " + e.toString());
			}
		}
		if (e instanceof UrinaryExpression && ((UrinaryExpression)e).getChild() == null){
			if (vals.size() == 1 && e.isContainerOp()){
				newEx = new BinExpression(o);
				newEx.setLeftChild(vals.remove(0));
				((UrinaryExpression)e).setChild(newEx);
				e = newEx;
				vals = new ArrayList<Value>();
				return;
			}
			else{
				//throw new ParseException("urinary expression without a value");
				addNewValue(new Nothing());
			}
		}
		else{
			newEx = new BinExpression(o);
			if(e == null){
				if (o == Operator.SUBTRACT && vals.isEmpty()){
					addUrinaryOp(Operator.NEG);
					return;
				}
				else if (o != Operator.ASSIGN){
//					if (vals.size() == 1 && vals.get(0) instanceof Var){
//						if (((Var)vals.get(0)).getValue() == null){
//							throw new ParseException("Variable \"" + ((Var)vals.get(0)).getName()
//									+ "\" has not been given a value");
//						}
//					}
					e = newEx;
					if (vals.size() == 1){
						newEx.setLeftChild(vals.get(0));
					}
					else
					{
						newEx.setLeftChild(new Nothing());
					}
					vals = new ArrayList<Value>();
					return;
				}
				else{//it is a assignment used properly
					e = newEx;
					if (vals.size() == 1){
						newEx.setLeftChild(vals.get(0));
					}
					else
					{
						newEx.setLeftChild(new Nothing());
					}
					vals = new ArrayList<Value>();
					return;
				}
			}
//			if(e.getOp() == null){
//				if (vals.size() == 1){
//					e = newEx;
//					newEx.setLeftChild(vals.get(0));
//					vals = new ArrayList<Value>();
//					return;
//				}
//			}
			if (vals.size() == 1){
				newEx.setLeftChild(vals.get(0));
				vals = new ArrayList<Value>();
			}
			else{
				if (newEx.getOp().getPrec() > e.getOp().getPrec()){
					if (e instanceof BinExpression){
						newEx.setLeftChild(((BinExpression)e).getRightChild());
						((BinExpression)e).setRightChild(newEx);
						e = newEx;
						return;
					}
					else if (e instanceof UrinaryExpression){
						newEx.setLeftChild(e);
						e = newEx;
						return;
					}
				}
				else {
					if (e.isContainerOp() && ((UrinaryExpression)e).hasChild()){
						while(newEx.getOp().getPrec() < e.getOp().getPrec() && e.hasParent()
								&& !(e.getParent().isContainerOp())){
							e = e.getParent();
						}
						if (e.hasParent() && e.getParent().isContainerOp()){
							((UrinaryExpression)e.getParent()).setChild(newEx);
						}
						newEx.setLeftChild(e);
						e = newEx;
						return;
					}
					else{
						while(e.hasParent() && newEx.getOp().getPrec() < e.getOp().getPrec()
								&& !(e.getParent().isContainerOp())){
							e = e.getParent();
						}
						System.out.println("afterloop: " + e.toString());
						if (e.hasParent() && e.getParent().isContainerOp()){
							((UrinaryExpression)e.getParent()).setChild(newEx);
						}
						if (e instanceof BinExpression)
						{
							if (e.getOp().getPrec() < newEx.getOp().getPrec())
							{
								((BinExpression)newEx).setLeftChild(((BinExpression)e).getRightChild());
								((BinExpression)e).setRightChild(newEx);
								e = newEx;
								return;
							}
							else
							{
								System.out.println("after loop: " + e.toString());
								if (e.hasParent())
								{
									Expression parent = e.getParent();
									if (parent instanceof BinExpression){
										((BinExpression) parent).setRightChild(newEx);
									}
									else if(parent instanceof UrinaryExpression){
										((UrinaryExpression) parent).setChild(newEx);
									}
								}
								newEx.setLeftChild(e);
								e = newEx;
								return;
							}
						}
						newEx.setLeftChild(e);
						e = newEx;
						return;
					}
				}
			}
		}
	}
	
	public void addUrinaryOp(Operator o) throws ParseException{
		//this is wrong, need to modify for urinaryOp, just copied from Bin
		UrinaryExpression newEx = new UrinaryExpression(o);
		if (e instanceof BinExpression  && ((BinExpression)e).getRightChild() == null){
			//System.out.println("right child null");
			((BinExpression)e).setRightChild(newEx);
			e = newEx;
			return;
		}
		if (e instanceof BinExpression  && ((BinExpression)e).getRightChild() != null){
			addBinOp(Operator.MULTIPLY);
			addNewValue(newEx);
			e = newEx;
			return;
		}
		else if (e instanceof UrinaryExpression && newEx.isContainerOp()){
			((UrinaryExpression)e).setChild(newEx);
			e = newEx;
			return;
		}
		else if (e instanceof UrinaryExpression && e.isContainerOp()){
			((UrinaryExpression)e).setChild(newEx);
			if (vals.size() == 1){
				newEx.setChild(vals.get(0));
				vals = new ArrayList<Value>();
			}
			e = newEx;
			return;
		}
		else if (e instanceof UrinaryExpression && ((UrinaryExpression)e).getChild() == null){
			throw new ParseException("urinary expression without a value");
		}
		else{
			if (vals.size() == 1){
				if (o.isUrinaryPost()){
					newEx.setChild(vals.get(0));
				}
				else if (vals.size() == 1 && vals.get(0) instanceof Var){
					if (((Var)vals.get(0)).getValue() == null){
						throw new ParseException("Variable \"" + ((Var)vals.get(0)).getName()
								+ "\" has not been given a value");
					}
				}
				else{ //there is a value before the UrinaryOp, such as with 4sin x
					addBinOp(Operator.MULTIPLY);
					addUrinaryOp(o);
					return;
				}
			}
			if(e == null){
				e = newEx;
				vals = new ArrayList<Value>();
				return;
			}
			else{
				if (newEx.getOp().getPrec() > e.getOp().getPrec()){
					if (e instanceof BinExpression){
						((BinExpression)e).setRightChild(newEx);
						e = newEx;
					}
					else if (e instanceof UrinaryExpression){
						BinExpression newBinEx = new BinExpression();
						newBinEx.setLeftChild(e);
						e = newBinEx;
						addBinOp(Operator.MULTIPLY);
						addNewValue(newEx);
					}
				}
				else {
					if (e.isContainerOp() && ((UrinaryExpression)e).hasChild()){
						newEx.setChild(e);
						e = newEx;
						return;
					}
					while(newEx.getOp().getPrec() < e.getOp().getPrec() && e.hasParent()
							&& !(e.getParent().isContainerOp())){
						e = e.getParent();
					}
					newEx.setChild(e);
					e = newEx;
					return;
				}
			}
		}
	}
	
	/**
	 * Returns the length of the last element that was scanned.
	 * @return int - the length
	 */
	public int getLengthLast(){
		return lengthLast;
	}

	public VarStorage getVarList() {
		// TODO Auto-generated method stub
		return VARLIST;
	}

	public ValueStorage getConstantList() {
		// TODO Auto-generated method stub
		return CONSTLIST;
	}

	public void setAngleUnits(int i) {
		// TODO Auto-generated method stub
		angleUnits = i;
	}
	
	public int getAngleUnits(){
		return angleUnits;
	}

}
