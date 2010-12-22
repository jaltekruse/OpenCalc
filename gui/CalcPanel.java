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

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import tree.*;

public class CalcPanel extends SubPanel {

	private static final long serialVersionUID = 1L;
	private final JTextArea terminal;
	private MainApplet mainApp;
	private OCTextField entryLine;
	private JTextField entryLineField;
	private OCButton solve;
	private JScrollPane termScrollPane;
	private Value treeResult;
	private ExpressionParser parser;
	private static final String startMessage = "Type an expression.";
	
	private String eqtn;
    private JRadioButton exact, dec;
    private ButtonGroup answersInSelect;
    private SubPanel answersInPanel;
    
    //tells the current selection for output, 1 = exact, 2 = decimal
    private int answersIn;

	public CalcPanel(final MainApplet currmainApp) {

		mainApp = currmainApp;
		terminal = new JTextArea(10, 20);
		Font terminalFont = new Font("newFont", 1, 14);
		parser = mainApp.getParser();
		
		terminal.setFont(terminalFont);
		terminal.setEditable(false);
		termScrollPane = new JScrollPane(terminal);
		termScrollPane.setWheelScrollingEnabled(true);
		
		GridBagConstraints tCon = new GridBagConstraints();

		entryLine = new OCTextField(true, 16, 1, 1, 0, 10, this, mainApp) {
			public void associatedAction() {
				try {
					solverAction();
					this.repaint();
					JScrollBar tempScroll = termScrollPane.getVerticalScrollBar();
					tempScroll.setValue(tempScroll.getMaximum());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		
		entryLineField = entryLine.getField();
		entryLineField.setText(startMessage);
		entryLineField.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				if (entryLineField.getText().equals(startMessage)){
					entryLineField.setSelectionStart(0);
					entryLineField.setSelectionEnd(startMessage.length());
				}
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				
			}// TODO Auto-generated method stub
				
			
		});
		
		entryLineField.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				JTextField currField = mainApp.getCurrTextField().getField();
				String s = Character.toString(e.getKeyChar());
				if (currField.getCaretPosition() == 0 && 
						Operator.requiresPrevious(s) && currField.getText().equals("")){
							currField.setText("ans" );
							currField.requestFocus();
							currField.setCaretPosition(3);
				}
			}
			
		});

		solve = new OCButton("solve", "Evaluate the current expression.", 1, 1, 5, 10, this, mainApp) {
			public void associatedAction() {
				try {
					entryLine.primaryAction();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		tCon.fill = GridBagConstraints.BOTH;
		tCon.weightx = 1;
		tCon.weighty = 1;
		tCon.gridx = 0;
		tCon.gridy = 0;
		tCon.gridheight = 10;
		tCon.gridwidth = 7;
		this.add(termScrollPane, tCon);
		
		exact = new JRadioButton("Exact");
		exact.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setAnswersIn(1);
			}
		});
		
		dec = new JRadioButton("Decimal");
		dec.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setAnswersIn(2);
			}
		});
		
		answersInSelect = new ButtonGroup();
		answersInSelect.add(exact);
		answersInSelect.add(dec);
		
		exact.setSelected(true);
		
		answersInPanel = new SubPanel();
		
		GridBagConstraints bCon = new GridBagConstraints();
		
		bCon.gridx = 0;
		bCon.gridy = 0;
		answersInPanel.add(exact, bCon);
		
		bCon.gridx = 1;
		bCon.gridy = 0;
		answersInPanel.add(dec, bCon);
		
		tCon.fill = GridBagConstraints.BOTH;
		tCon.weightx = 1;
		tCon.weighty = .1;
		tCon.gridx = 0;
		tCon.gridy = 11;
		tCon.gridheight = 1;
		tCon.gridwidth = 7;
		this.add(answersInPanel, tCon);
	}

	public OCTextField getEntryLine() {
		return entryLine;
	}

	public void solverAction() throws Exception{
		//System.out.println(getErrorLog());
		eqtn = entryLineField.getText();
		if (!eqtn.equals(null) && !eqtn.equals("") && !eqtn.equals(startMessage)) {
			terminal.append(">  " + eqtn + "\n");
			try{
				treeResult = parser.ParseExpression(eqtn);
				treeResult = treeResult.eval();
				treeResult = treeResult.eval();
				if (treeResult instanceof Fraction){
					//we need to decide where method call should go,
					//calculations will want to be simplified, but
					//teaching algebra will require use to sometimes do
					//operations like adding without simplifying, and making
					//the students do that on their own
					treeResult = ((Fraction) treeResult).reduce();
				}
				parser.getVarList().setVarVal("ans", treeResult);
				//treeDouble = treeResult.toDec().getValue();
				if (getAnswersIn() == 2){
					treeResult = treeResult.toDec();
				}
				terminal.append(treeResult.toString() + "\n");
			}
			catch (ParseException e){
				terminal.append("Parsing exception\n");
				terminal.append(e.getMessage()+ "\n");
			}
			catch (EvalException e){
				terminal.append("Evaluating exception\n");
				terminal.append(e.getMessage() + "\n");
			}
			entryLineField.setText("");
			JScrollBar tempScroll = termScrollPane.getVerticalScrollBar();
			tempScroll.setValue(tempScroll.getMaximum());
			mainApp.setCurrTextField(entryLine);
		}
	}

	public void setAnswersIn(int answersIn) {
		this.answersIn = answersIn;
	}

	public int getAnswersIn() {
		return answersIn;
	}
}
