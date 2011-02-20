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

/**
 * @author jason
 * 
 */
public abstract class Element {

	private int prec;
	private int elmType;

	public Element() {
		prec = 8;
		elmType = 2;
	}

	public int getPrec() {
		return prec;
	}

	public void setPrec(int precedence) {
		prec = precedence;
	}

	public void setElmType(int type) {
		elmType = type;
	}

	public int getElmType() {
		return elmType;
	}
}
