package tree;

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

/**
* A Constant is used to represent a mathematical constant. It extends
* from the {@link ValueWithName} which stores its name. Stored here are
* a double for its value.
*/
public class Constant extends NumberWithName {

	private Number v;
	
	/**
	 * Constructor takes a string to pass to the superclass's constructor.
	 * The only other param is a double that is stored here. The precedence value is 
	 * hard coded.
	 * @param s
	 * @param val
	 */
	public Constant(String s, Number val) {
		super(s);
		v = val;
		
	}

	/**
	 * Gets the value stored with this constant.
	 * 
	 * @return - a double represented by this constant
	 */
	public Number getValue() {
		return v;
	}

	/**
	 * Returns a string representation of this constant.
	 * 
	 * @return a string in the format "[constName: value]"
	 */
	public String toString() {
		String varInfo = new String();
		varInfo += getName();
//		varInfo += "[";
//		varInfo += getName();
//		varInfo += ": ";
//		varInfo += getValue();
//		varInfo += "]";
		return varInfo;
	}
	
	public Number eval(){
		return v;
	}

	@Override
	public Value multiply(Decimal d) throws EvalException {
		// TODO Auto-generated method stub
		return v.multiply(d);
	}

	@Override
	public Value multiply(Fraction f) throws EvalException {
		// TODO Auto-generated method stub
		return v.multiply(f);
	}

	@Override
	public Value multiply(Matrix m) throws EvalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value multiply(Irrational i) throws EvalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value add(Decimal d) throws EvalException {
		// TODO Auto-generated method stub
		return v.add(d);
	}

	@Override
	public Value add(Fraction f) throws EvalException {
		// TODO Auto-generated method stub
		return v.add(f);
	}

	@Override
	public Value add(Matrix m) throws EvalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value add(Irrational i) throws EvalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value subtract(Decimal d) throws EvalException {
		// TODO Auto-generated method stub
		return v.subtract(d);
	}

	@Override
	public Value subtract(Fraction f) throws EvalException {
		// TODO Auto-generated method stub
		return v.subtract(f);
	}

	@Override
	public Value subtract(Matrix m) throws EvalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value subtract(Irrational i) throws EvalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value divide(Decimal d) throws EvalException {
		// TODO Auto-generated method stub
		return v.divide(d);
	}

	@Override
	public Value divide(Fraction f) throws EvalException {
		// TODO Auto-generated method stub
		return v.divide(f);
	}

	@Override
	public Value divide(Matrix m) throws EvalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value divide(Irrational i) throws EvalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value power(Decimal d) throws EvalException {
		// TODO Auto-generated method stub
		return v.power(d);
	}

	@Override
	public Value power(Fraction f) throws EvalException {
		// TODO Auto-generated method stub
		return v.power(f);
	}

	@Override
	public Value power(Matrix m) throws EvalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value power(Irrational i) throws EvalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value sin() throws EvalException {
		// TODO Auto-generated method stub
		return v.sin();
	}

	@Override
	public Value cos() throws EvalException {
		// TODO Auto-generated method stub
		return v.cos();
	}

	@Override
	public Value tan() throws EvalException {
		// TODO Auto-generated method stub
		return v.tan();
	}

	@Override
	public Value invSin() throws EvalException {
		// TODO Auto-generated method stub
		return v.invSin();
	}

	@Override
	public Value invCos() throws EvalException {
		// TODO Auto-generated method stub
		return v.invCos();
	}

	@Override
	public Value invTan() throws EvalException {
		// TODO Auto-generated method stub
		return v.invTan();
	}

	@Override
	public Value neg() throws EvalException {
		// TODO Auto-generated method stub
		return v.neg();
	}

	@Override
	public Decimal toDec() throws EvalException {
		// TODO Auto-generated method stub
		return v.toDec();
	}

	@Override
	public Value assign(Value v) throws EvalException {
		// TODO Auto-generated method stub
		throw new EvalException("cannot assign a value to a Constant");
	}

	@Override
	public Value squareRoot() throws EvalException {
		// TODO Auto-generated method stub
		return v.squareRoot();
	}

	@Override
	public Value log() throws EvalException {
		// TODO Auto-generated method stub
		return v.log();
	}

	@Override
	public Value natLog() throws EvalException {
		// TODO Auto-generated method stub
		return v.natLog();
	}
}
