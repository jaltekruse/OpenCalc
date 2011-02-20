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

public class Term {
	//this is the superclass for the three types of terms
	//this class will be used in the implementation of an algebra system
	//Because of structural issues, I have not implemented the reading in of terms
	//to the various term structures. Once a decent system is developed, code will be
	//written, for now this is merely food for thought, along with SimpleTerm, CompleteTerm
	//and ComplexTerm
	//you can see that this system as it stands would work, but cannot handle variables as
	//exponents, which would make me consider changing the power from a double to a term.
	//The bigger issue is that I need a recursive system to scan in everything, as it
	//stands I need to keep making new classes to handle new layers of complexity.
}
