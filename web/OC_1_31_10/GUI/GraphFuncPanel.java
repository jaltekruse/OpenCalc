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
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;


public class GraphFuncPanel extends SubPanel {

	public GraphFuncPanel(final NewCalc calcObj) {
		// TODO Auto-generated constructor stub
		GraphAttributesPanel[] graphArray = new GraphAttributesPanel[9];
		int numGraphs = 9;
		GridBagConstraints tCon = new GridBagConstraints();
		Color[] graphColors = {Color.GREEN, Color.BLUE, Color.MAGENTA, Color.YELLOW, Color.CYAN, Color.ORANGE};
		
		for (int i = 0; i < numGraphs; i++){
			tCon.gridx = 0;
			tCon.gridy = i;
			tCon.gridheight = 1;
			tCon.gridwidth = 7;
			this.add(new GraphAttributesPanel(calcObj), tCon);
		}
		
	}

}
