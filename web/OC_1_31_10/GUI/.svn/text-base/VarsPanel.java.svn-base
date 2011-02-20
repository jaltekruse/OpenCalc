package GUI;
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

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;

//this class has been depreciated, please use Var2
public class VarsPanel extends SubPanel {
	
	private NewCalc calcObj;
	private OCButton varButton;
	
	public VarsPanel(final NewCalc currCalcObj){
		calcObj = currCalcObj;
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(280, 200));
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		this.setSize(calcObj.getSize().width/2, calcObj.getSize().height/9 * 4);
		
		refreshButtons();
	}
	
	public void refreshButtons(){
		for (int i = 0; i < calcObj.getCalcObj().getVarList().getDefaultVars().length; i++){
			String varName = calcObj.getVarsObj().getDefaultVars()[i];
			varButton = new OCButton(varName, 1, 1, i%4, i/4, this, calcObj);
		}
	}
}
