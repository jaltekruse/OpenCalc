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

public class Operator extends Element {
	String operator;
	calc basicCalcObj;

	public Operator(calc currCalc) {
		basicCalcObj = currCalc;
	}

	public Operator() {
		operator = null;
	}

	public Operator(String op, calc currCalc) {
		basicCalcObj = currCalc;
		super.setElmType(1);
		super.setPrec(basicCalcObj.OPSLIST.getPrec(op));
		operator = op;
	}

	public String getOp() {
		return operator;
	}

	public void setOp(String op) {
		operator = op;
	}

	public String toString() {
		return operator;
	}
}
