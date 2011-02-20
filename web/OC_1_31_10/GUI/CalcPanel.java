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

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;


public class CalcPanel extends SubPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final JTextArea terminal;
	NewCalc calcObj;
	OCTextField entryLine;
	OCButton solve;
	
	
	public CalcPanel(final NewCalc currCalcObj){
		
		calcObj = currCalcObj;
		terminal = new JTextArea(14, 20);
		Font terminalFont = new Font("newFont", 1, 14);
		terminal.setFont(terminalFont);
		terminal.setEditable(false);
		JScrollPane termScrollPane = new JScrollPane(terminal);
		termScrollPane.setWheelScrollingEnabled(true);
		
		GridBagConstraints tCon = new GridBagConstraints();

		entryLine = new OCTextField(true, 16, 1, 1, 0, 7, this, calcObj){
			public void associatedAction(){
				solverAction();
			}
		};
		
		solve = new OCButton("solve",1, 1, 5, 7, this, calcObj){
			public void associatedAction(){
				solverAction();
			}
		};
		
		tCon.fill = GridBagConstraints.BOTH;
		tCon.weightx = 1;
		tCon.weighty = 1;
		tCon.gridx = 0;
		tCon.gridy = 0;
		tCon.gridheight = 7;
		tCon.gridwidth = 7;
		this.add(termScrollPane, tCon);
		
	}
	
	public OCTextField getTextTerminal(){
		return entryLine;
	}
	
	public void solverAction(){
		String eqtn = entryLine.getText();
		if (!eqtn.equals(null) && !eqtn.equals("")){
			terminal.append(">  " + eqtn + "\n");
			String result = new String();
			result += calcObj.evalCalc(eqtn);
			terminal.append(result + "\n");
			entryLine.setText("");
			entryLine.requestFocus();
		}
	}
}
