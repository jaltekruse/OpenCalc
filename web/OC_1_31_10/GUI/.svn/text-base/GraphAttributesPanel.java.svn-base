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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;


public class GraphAttributesPanel extends SubPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	OCTextField graphEntry;
	OCButton graphButton;
	NewCalc calcObj;

	public GraphAttributesPanel(NewCalc currCalcObj) {
		// TODO Auto-generated constructor stub
		
		calcObj = currCalcObj;
		
		JPanel colorBox = new JPanel(){
			public void paint(Graphics g){
				g.setColor(Color.GREEN);
				g.fillRect(0, 0, 10, 10);
			}
		};
		GridBagConstraints pCon = new GridBagConstraints();
		pCon.gridx = 0;
		pCon.gridy = 0;
		pCon.gridheight = 1;
		pCon.gridwidth = 1;
		this.add(colorBox, pCon);
		
		graphEntry = new OCTextField(true, 20, 10, 1, 1, 0, this, calcObj){
			public void associatedAction(){
				graphAction();
			}
		};
		
		graphButton = new OCButton("Graph", 1, 1, 11, 0, this, calcObj){
			public void associatedAction(){
				graphAction();
			}
		};
	}
	
	public void graphAction(){
		calcObj.updateGraph(graphEntry.getText());
	}

}
