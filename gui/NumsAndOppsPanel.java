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

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;

import tree.EvalException;
import tree.ParseException;
import tree.ValueNotStoredException;

public class NumsAndOppsPanel extends SubPanel {
	/**
	 * 
	 */

	NumsAndOppsPanel(final NewCalc calcObj) {
		// SubPanel(Numbuttons, NumTextFields)
		this.setLayout(new GridBagLayout());
		this.setSize(new Dimension(100, 100));
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		this.setSize(calcObj.getSize().width / 2,
				calcObj.getSize().height / 9 * 4);

		// OCButton( string, width, height, x, y, container, NewCalc)

		OCButton carret = new OCButton("^", 1, 1, 0, 1, this, calcObj);
		OCButton inv = new OCButton("^-1", 1, 1, 0, 2, this, calcObj);
		OCButton dot = new OCButton(".", 1, 1, 3, 5, this, calcObj);
		OCButton plus = new OCButton("+", 1, 2, 4, 2, this, calcObj);
		OCButton minus = new OCButton("-", 1, 1, 4, 1, this, calcObj);
		OCButton mult = new OCButton("*", 1, 1, 3, 1, this, calcObj);
		OCButton divide = new OCButton("/", 1, 1, 2, 1, this, calcObj);
		OCButton leftParen = new OCButton("(", 1, 1, 3, 0, this, calcObj);
		OCButton rightParen = new OCButton(")", 1, 1, 4, 0, this, calcObj);
		OCButton equals = new OCButton("=", 1, 1, 1, 1, this, calcObj);
		OCButton natLog = new OCButton("ln(", 1, 1, 0, 5, this, calcObj);
		OCButton log = new OCButton("log(", 1, 1, 0, 4, this, calcObj);
		OCButton sqrt = new OCButton("sqrt(", 1, 1, 0, 3, this, calcObj);
		OCButton sciNote = new OCButton("E", 1, 1, 2, 0, this, calcObj);
		OCButton zero = new OCButton("0", 2, 1, 1, 5, this, calcObj);
		OCButton one = new OCButton("1", 1, 1, 1, 4, this, calcObj);
		OCButton two = new OCButton("2", 1, 1, 2, 4, this, calcObj);
		OCButton three = new OCButton("3", 1, 1, 3, 4, this, calcObj);
		OCButton four = new OCButton("4", 1, 1, 1, 3, this, calcObj);
		OCButton five = new OCButton("5", 1, 1, 2, 3, this, calcObj);
		OCButton six = new OCButton("6", 1, 1, 3, 3, this, calcObj);
		OCButton seven = new OCButton("7", 1, 1, 1, 2, this, calcObj);
		OCButton eight = new OCButton("8", 1, 1, 2, 2, this, calcObj);
		OCButton nine = new OCButton("9", 1, 1, 3, 2, this, calcObj);

		OCButton back = new OCButton("<-", 1, 1, 1, 0, this, calcObj) {
			public void associatedAction() {
				JTextField currField = calcObj.getCurrTextField();
				if (currField != null) {
					String currText = currField.getText();
					int caretPos = calcObj.getCurrCaretPos();

					if (currText.equals("")) {
						// do nothing
					}

					if (caretPos > 0) {
						if (currField.getSelectionStart() == currField
								.getSelectionEnd()) {
							currField = calcObj.getCurrTextField();
							currText = currField.getText();
							String tempText = currText.substring(0,
									caretPos - 1);
							tempText += currText.substring(caretPos, currText
									.length());
							currField.setText(tempText);
							currField.requestFocus();
							currField.setCaretPosition(caretPos - 1);
						} else {
							int selectStart = currField.getSelectionStart();
							int selectEnd = currField.getSelectionEnd();
							String tempText = currText
									.substring(0, selectStart);
							tempText += currText.substring(selectEnd, currText
									.length());
							currField.setText(tempText);
							currField.requestFocus();
							currField.setCaretPosition(selectStart);
						}
					}
				}
			}
		};

		OCButton clear = new OCButton("CL", 1, 1, 0, 0, this, calcObj) {
			public void associatedAction() {
				JTextField currField = calcObj.getCurrTextField();
				if (currField != null) {
					currField.setText("");
					currField.requestFocus();
				}
			}
		};
		GridBagConstraints bCon = new GridBagConstraints();
		JButton currButton = new JButton("<-");

		OCButton enter = new OCButton("entr", 1, 2, 4, 4, this, calcObj){
			public void associatedAction() throws ParseException, ValueNotStoredException, EvalException{
				calcObj.getCurrTextField().associatedAction();
			}
		};
		 
	}
}
