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

import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import calc.calc;



public class GraphCalcPanel extends SubPanel{
	
	OCTextField pt2Trace, tracePtVal;
	OCButton trace;
	NewCalc calcObj;
	
	public GraphCalcPanel(NewCalc currCalcObj){
		
		calcObj = currCalcObj;
		/*
		startInt = new JTextField(5);
		startInt.setFont(terminalFont);
		startInt.setText("startInt");
		
		endInt = new JTextField(5);
		endInt.setFont(terminalFont);
		endInt.setText("endInt");
		
		intVal = new JTextField(10);
		intVal.setFont(terminalFont);
		intVal.setEditable(false); 
		
		JButton setInt = new JButton("Integrate");
		setInt.addActionListener(this);
		setInt.setActionCommand("findInt");
		
		*/
		
		pt2Trace = new OCTextField(true, 7, 2, 1, 0, 0, this, calcObj){
			public void associatedAction(){
				Graph tempGraph = calcObj.getGraphObj();
				if (pt2Trace.getText().equals("")){
					tempGraph.setTracePt(false, 0);
					tracePtVal.setText("");
				}
				else{
					if("".equals(tempGraph.getFunc(1))){
						pt2Trace.setText("no eqtn");
						tracePtVal.setText("error");
					}
					else{
						String xAns = calcObj.evalCalc(pt2Trace.getText());
						if(!"error".equals(xAns)){
							double xVal = Double.valueOf(xAns);
							double yVal;
							tempGraph.setTracePt(true, xVal);
							String xString = new String();
							xString += xVal;
							pt2Trace.setText(xString);
							calcObj.getCalcObj().getVarList().setVarVal("x", xVal);
							String yAns = calcObj.evalCalc(tempGraph.getFunc(1));
							yVal = Double.valueOf(yAns);
							calcObj.getCalcObj().getVarList().setVarVal("y", yVal);
							tracePtVal.setText(yAns);
						}
						else{
							pt2Trace.setText("error");
						}
					}
				}
				tempGraph.repaint();
			}
		};
		
		tracePtVal = new OCTextField(false, 7, 2, 1, 2, 0, this, calcObj);
		/*
		yVal = new JTextField(15);
		yVal.setEditable(false);
		
		graphCalcs = new JPanel(new GridBagLayout());
		graphCalcs.setBorder(BorderFactory.createTitledBorder("Graph Options"));
		

		graphCalcs.add(startInt);
		graphCalcs.add(endInt);
		graphCalcs.add(setInt);
		graphCalcs.add(intVal); */
	}
}
