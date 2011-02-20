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

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class TrigPanel extends SubPanel {

	private OCButton sin, cos, tan, sin_1, cos_1, tan_1;
	private ButtonGroup angleUnits;
	private JRadioButton radians, degrees, gradians;
	private NewCalc calcObj;
	private SubPanel angleUnitPanel;

	TrigPanel(final NewCalc currCalcObj) {
		calcObj = currCalcObj;
		this.setLayout(new GridBagLayout());
		this.setSize(new Dimension(320, 60));
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		this.setSize(calcObj.getSize().width / 2,
				calcObj.getSize().height / 9 * 4);

		sin = new OCButton("sin(", 1, 1, 0, 0, this, calcObj);
		cos = new OCButton("cos(", 1, 1, 1, 0, this, calcObj);
		tan = new OCButton("tan(", 1, 1, 2, 0, this, calcObj);
		sin_1 = new OCButton("sin-1(", 1, 1, 0, 1, this, calcObj);
		cos_1 = new OCButton("cos-1(", 1, 1, 1, 1, this, calcObj);
		tan_1 = new OCButton("tan-1(", 1, 1, 2, 1, this, calcObj);
		
		radians = new JRadioButton("Rad");
		radians.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				calcObj.getCalcObj().setAngleUnits(1);
			}
		});
		
		degrees = new JRadioButton("Deg");
		degrees.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				calcObj.getCalcObj().setAngleUnits(2);
			}
		});
		
		gradians = new JRadioButton("Grad");
		gradians.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				calcObj.getCalcObj().setAngleUnits(3);
			}
		});
		
		angleUnits = new ButtonGroup();
		angleUnits.add(radians);
		angleUnits.add(degrees);
		angleUnits.add(gradians);
		
		radians.setSelected(true);
		
		angleUnitPanel = new SubPanel();
		
		GridBagConstraints bCon = new GridBagConstraints();
		
		bCon.gridx = 0;
		bCon.gridy = 0;
		angleUnitPanel.add(radians, bCon);
		
		bCon.gridx = 0;
		bCon.gridy = 1;
		angleUnitPanel.add(degrees, bCon);
		
		bCon.gridx = 0;
		bCon.gridy = 2;
		angleUnitPanel.add(gradians, bCon);
		
		bCon.gridx = 3;
		bCon.gridy = 0;
		bCon.gridheight = 2;
		this.add(angleUnitPanel, bCon);
	}
}
