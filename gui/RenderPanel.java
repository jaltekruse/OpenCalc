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

import imagegen.CompleteExpressionGraphic;
import imagegen.ImageGenerator;
import imagegen.ValueGraphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import tree.*;

public class RenderPanel extends SubPanel {

	private static final long serialVersionUID = 1L;
	private MainApplet mainApp;
	private OCTextField entryLine, mousePos;
	private JTextArea selected;
	private OCButton solve;
	private ExpressionParser parser;

    private SubPanel thisPanel;
    private SubPanel render;
    private CompleteExpressionGraphic ceg;
    

	public RenderPanel(final MainApplet currmainApp) {

		mainApp = currmainApp;
		thisPanel = this;
		
		parser = mainApp.getParser();
		
		GridBagConstraints tCon = new GridBagConstraints();

		entryLine = new OCTextField(true, 16, 1, 1, 0, 10, this, mainApp) {
			public void associatedAction() {
				try {
					render.repaint();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		
		selected = new JTextArea(16,8);
		
		Font terminalFont = new Font("newFont", 1, 14);
		parser = mainApp.getParser();
		
		selected.setFont(terminalFont);
		selected.setEditable(false);
		selected.setText("Enter an expression in the line,\n" +
				"then click on the rendered expression\n" +
				"in the window to find what valueGraphics\n" +
				"each component belongs to.");
		JScrollPane termScrollPane = new JScrollPane(selected);
		termScrollPane.setWheelScrollingEnabled(true);
		
		mousePos = new OCTextField(false, 16, 1, 1, 0, 12, this, mainApp) {};

		solve = new OCButton("render", "Evaluate the current expression.", 1, 1, 5, 10, this, mainApp) {
			public void associatedAction() {
				try {
					render.repaint();
				
					entryLine.primaryAction();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		render = new SubPanel(){
			public void paint(Graphics g){
				try {
					if (!entryLine.getField().getText().equals(""))
					{
						render(g);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
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
		this.add(render, tCon);
		//this.add(comp, tCon);
		
		tCon.fill = GridBagConstraints.BOTH;
		tCon.weightx = 1;
		tCon.weighty = 1;
		tCon.gridx = 0;
		tCon.gridy = 13;
		tCon.gridheight = 2;
		tCon.gridwidth = 2;
		this.add(termScrollPane, tCon);
		
		render.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				int mouseX = e.getX();
				int mouseY = e.getY();
				selected.setText("");
				mousePos.getField().setText("x: " + mouseX + "   y: " + mouseY);
				for (ValueGraphic vg : ceg.getComponents()){
					if (mouseX >= vg.getX1() && mouseX <= vg.getX2() && mouseY >= vg.getY1() && mouseY <= vg.getY2())
					{
						selected.append(vg.toString() + '\n' + vg.getValue().toString() + "\n\n");
					}
				}
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}

	public void render(Graphics g) throws ParseException, Exception{
		ceg = new CompleteExpressionGraphic(parser.ParseExpression(entryLine.getField().getText()));
		g.setColor(Color.white);
		g.fillRect(0, 0, render.getWidth(), render.getHeight());
		ceg.generateExpressionGraphic(g, 30, 40);
		g.setColor(Color.black);
		ceg.draw();
	}
	public OCTextField getEntryLine() {
		return entryLine;
	}
	
}
