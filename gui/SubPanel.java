package gui;

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

import java.awt.Container;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class SubPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TopLevelContainer topLevelContainer;

	public SubPanel(TopLevelContainer topLevelComp) {
		this.setLayout(new GridBagLayout());
		this.topLevelContainer = topLevelComp;
	}
	
	public TopLevelContainer getTopLevelContainer(){
		return topLevelContainer;
	}
}
