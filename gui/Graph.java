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


import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import tree.Decimal;
import tree.EvalException;
import tree.Value;
import tree.ValueNotStoredException;
import tree.VarStorage;
import tree.ExpressionParser;
import tree.Var;

public class Graph extends SubPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double X_MAX, X_MIN, Y_MAX, Y_MIN, X_STEP, Y_STEP, X_PIXEL, Y_PIXEL,
		THETA_STEP, THETA_MIN,THETA_MAX, POL_STEP, POL_AX_STEP;
	public int X_SIZE, Y_SIZE, LINE_SIZE, LINE_SIZE_DEFAULT, NUM_FREQ, mouseX,
		mouseY, mouseRefX, mouseRefY, NUM_GRAPHS;
	private boolean refPoint;
	private MainApplet mainApp;
	private ExpressionParser parser;
	private VarStorage varList;
	private JPanel graph;
	// stuff to put in separate graph class
	private Function[] functions;

	public Graph(MainApplet currmainApp, TopLevelContainer topLevelComp, int xSize, int ySize) {
		super(topLevelComp);
		mainApp = currmainApp;
		parser = mainApp.getParser();
		varList = parser.getVarList();
		X_SIZE = xSize;
		Y_SIZE = ySize;
		NUM_GRAPHS = 6;
		NUM_FREQ = 2;
		LINE_SIZE = 2;
		LINE_SIZE_DEFAULT = 2;
		X_MAX = 10;
		X_MIN = -10;
		Y_MAX = 10;
		Y_MIN = -10;
		X_STEP = 1;
		Y_STEP = 1;
		POL_AX_STEP = 1;
		X_PIXEL = (X_MAX - X_MIN) / X_SIZE;
		Y_PIXEL = (Y_MAX - Y_MIN) / Y_SIZE;
		POL_STEP = Math.PI/360;

		functions = new Function[NUM_GRAPHS];
		
		for( int i = 0; i < NUM_GRAPHS; i++){
			functions[i] = new Function(parser);
		}
		
		graph = new SubPanel(getTopLevelContainer()){
			
			public void paint(Graphics g) {
				g.setColor(Color.WHITE);
				X_SIZE = graph.getSize().width;
				Y_SIZE = graph.getSize().height;
				g.fillRect(0, 0, X_SIZE, Y_SIZE);
				g.setColor(Color.BLACK);
				try{
					X_MIN = varList.getVarVal("xMin").toDec().getValue();
					X_MAX = varList.getVarVal("xMax").toDec().getValue();
					Y_MIN = varList.getVarVal("yMin").toDec().getValue();
					Y_MAX = varList.getVarVal("yMax").toDec().getValue();
					X_STEP = varList.getVarVal("xStep").toDec().getValue();
					Y_STEP = varList.getVarVal("yStep").toDec().getValue();
					THETA_MIN = varList.getVarVal("thetaMin").toDec().getValue();
					THETA_MAX = varList.getVarVal("thetaMax").toDec().getValue();
					THETA_STEP = varList.getVarVal("thetaStep").toDec().getValue();
					
					//these four statements are for resizing the grid after zooming
					if((X_MAX-X_MIN)/X_STEP >= 24){
						varList.setVarVal("xStep", new Decimal((int)(X_MAX-X_MIN)/20));
						X_STEP = varList.getVarVal("xStep").toDec().getValue();
					}
					
					else if((X_MAX-X_MIN)/X_STEP <= 16 && X_STEP >= 2){
						varList.setVarVal("xStep", new Decimal((int)(X_MAX-X_MIN)/20));
						X_STEP = varList.getVarVal("xStep").toDec().getValue();
					}
					
					if((Y_MAX-Y_MIN)/Y_STEP >= 24){
						varList.setVarVal("yStep", new Decimal((int)(Y_MAX-Y_MIN)/20));
						Y_STEP = varList.getVarVal("yStep").toDec().getValue();
					}
					
					else if((Y_MAX-Y_MIN)/Y_STEP <= 16 && Y_STEP >= 2){
						varList.setVarVal("yStep", new Decimal((int)(Y_MAX-Y_MIN)/20));
						Y_STEP = varList.getVarVal("yStep").toDec().getValue();
					}
				}
				catch (EvalException e)
				{
					// TODO Auto-generated catch block
					//do something to show there was an error
				}
				
				X_PIXEL = (X_MAX - X_MIN) / X_SIZE;
				Y_PIXEL = (Y_MAX - Y_MIN) / Y_SIZE;

				drawAxis(g);
				//drawPolarAxis(g);
				
				Function f = null;
				for (int i = 0; i < NUM_GRAPHS; i++){
					f = functions[i];
					if (f != null){
						if (f.isGraphing()){
							if(f.getGraphType() == 1){
								graphCart(f, g);
							}
							else if(f.getGraphType() == 2){
								graphPolar(f, g);
							}
							else if(f.getGraphType() == 4){
								graphCart(f, g);
							}
						}
							
					}
				}
				if(refPoint)
					drawMousePlacement(g);

			}
		};
		
		graph.setBorder(BorderFactory.createTitledBorder(getBorder(), "graph"));
		
		graph.addMouseListener(new MouseListener(){
			private int xStart, yStart;
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				refPoint = true;
				mouseRefX = e.getX();
				mouseRefY= e.getY();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				refPoint = false;
				repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				mouseX = e.getX();
				mouseY = e.getY();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		graph.addMouseMotionListener(new MouseMotionListener(){
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				try{
					varList.updateVarVal("xMin", (mouseX - e.getX())*X_PIXEL);
					varList.updateVarVal("xMax", (mouseX - e.getX())*X_PIXEL);
					varList.updateVarVal("yMin", (e.getY()- mouseY)*Y_PIXEL);
					varList.updateVarVal("yMax", (e.getY()- mouseY)*Y_PIXEL);
				} catch (Exception ex){
					;
				}
				repaint();
				mouseX = e.getX();
				mouseY = e.getY();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				mouseRefX = e.getX();
				mouseRefY= e.getY();
				repaint();
			}
			
		});
		
		this.addMouseWheelListener(new MouseWheelListener(){

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				// TODO Auto-generated method stub
				try {
					zoom(100 - e.getWheelRotation() * 5);
				} catch (EvalException e1) {
					// TODO Auto-generated catch block
					//need to do something for errors
				}
			}
			
		});
		GridBagConstraints bCon = new GridBagConstraints();
		bCon.fill = GridBagConstraints.BOTH;
		bCon.weightx = 1;
		bCon.weighty = 1;
		bCon.gridheight = 7;
		bCon.gridwidth = 1;
		bCon.gridx = 0;
		bCon.gridy = 0;
		this.add(graph, bCon);
		this.setPreferredSize(new Dimension(xSize, ySize));
		
		JPanel props = new JPanel();
		OCButton zoomPlus = new OCButton("Zoom+", 1, 1, 0, 0, props, mainApp){
			public void associatedAction(){
				try {
					zoom(120);
				} catch (EvalException e) {
					// TODO Auto-generated catch block
					//think of something to do here
				}
			}
		};
		
		OCButton zoomMinus = new OCButton("Zoom-", 1, 1, 1, 0, props, mainApp){
			public void associatedAction(){
				try {
					zoom(80);
				} catch (EvalException e) {
					// TODO Auto-generated catch block
					//need something here too
				}
			}
		};
		
		bCon.fill = GridBagConstraints.BOTH;
		bCon.weightx = .1;
		bCon.weighty = .1;
		bCon.gridheight = 1;
		bCon.gridwidth = 1;
		bCon.gridx = 0;
		bCon.gridy = 7;
		this.add(props, bCon);
	}
	 /**
	  * 
	  * @param rate
	 * @throws EvalException 
	  */
	public void zoom(double rate) throws EvalException{
		X_MIN = varList.getVarVal("xMin").toDec().getValue();
		X_MAX = varList.getVarVal("xMax").toDec().getValue();
		Y_MIN = varList.getVarVal("yMin").toDec().getValue();
		Y_MAX = varList.getVarVal("yMax").toDec().getValue();
		
		//hacked solution to prevent graphing, the auto-rescaling of the 
		//grid stops working after the numbers get too big
		if (X_MIN < -7E8 || X_MAX > 7E8 || Y_MIN < -7E8 || Y_MAX > 7E8){
			if (rate < 100)
			{//if the user is trying to zoom out farther, do nothing
				return;
			}
		}
		
		varList.updateVarVal("xMin", -1 * (X_MAX-X_MIN)*(100-rate)/100);
		varList.updateVarVal("xMax", (X_MAX-X_MIN)*(100-rate)/100);
		varList.updateVarVal("yMin", -1 * (Y_MAX-Y_MIN)*(100-rate)/100);
		varList.updateVarVal("yMax", (Y_MAX-Y_MIN)*(100-rate)/100);
		
		repaint();
	}
	
	public Function[] getFunctions(){
		return functions;
	}

	public void setLineSize(int sizeInPixels) {
		LINE_SIZE = sizeInPixels;
		return;
	}

	private void drawMousePlacement(Graphics g){
		float currX = (float) ((mouseRefX * X_PIXEL) + X_MIN);
		float currY = (float) (Y_MAX - (mouseRefY * Y_PIXEL));
		String ptText = "(" + currX + ", " + currY + ")";
		int width = g.getFontMetrics().stringWidth(ptText);
		g.setColor(Color.white);
		g.fillRect( X_SIZE - width - 5, Y_SIZE - 20, width, 12);
		g.setColor(Color.black);
		g.drawString(ptText, X_SIZE - width - 5, Y_SIZE - 10);
	}
	
	private void ptOn(double a, double b, Graphics g) {
		if (a <= X_MAX && a >= X_MIN && b <= Y_MAX && b >= Y_MIN) {
			g.fillRect(roundDouble((a - X_MIN) / X_PIXEL) - LINE_SIZE,
					(Y_SIZE - LINE_SIZE) - roundDouble((b - Y_MIN) / Y_PIXEL),
					LINE_SIZE, LINE_SIZE);
			// g.fillOval(roundDouble((a - X_MIN)/X_PIXEL), Y_SIZE -
			// roundDouble((b - Y_MIN)/Y_PIXEL),
			// 2*LINE_SIZE, 2*LINE_SIZE);
			// System.out.println("(" + a + "," + b + ")");
			// System.out.println("(" + roundDouble(a/X_PIXEL) + "," +
			// roundDouble(b/Y_PIXEL) + ")" + "\n");
		}
	}
	
	private int gridXPtToScreen(double x){
		return roundDouble((x - X_MIN) / X_PIXEL) - LINE_SIZE;
	}
	
	private int gridYPtToScreen(double y){
		return (Y_SIZE - LINE_SIZE) - roundDouble((y - Y_MIN) / Y_PIXEL);
	}
	
	private void polPtOn(double r, double theta, Graphics g){
		ptOn(r*Math.cos(theta), r*Math.sin(theta), g);
	}

	private void drawPolarAxis(Graphics g){
		
		setLineSize(1);
		int numCircles = 0; 
		if (X_MAX > Y_MAX || X_MAX > Math.abs(Y_MIN))
			numCircles = (int) Math.abs(X_MAX - X_MIN);
		else if (Y_MAX > X_MAX || Y_MAX > Math.abs(X_MIN)){
			numCircles = (int) Math.abs(Y_MAX - Y_MIN);
		}
		else
			numCircles = (int) X_MAX;
		int i = roundDouble(Y_MIN);
		for ( ; i <= numCircles; i++){
			double currT = 0;
			double lastX = i * POL_AX_STEP;
			double lastY = 0, currX, currY;
			for(int j = 1; j < 240; j++){
				currT += POL_STEP * 2;
				currX = i * Math.cos(currT);
				currY = i * Math.sin(currT);
				drawLineSeg(lastX, lastY, currX, currY, Color.gray, g);
				lastX = currX;
				lastY = currY;
			}
		}
	}
	
	private void drawAxis(Graphics g) {

		// finds the fist factor of the Y_STEP on the screen
		// used to draw the first dash mark on the y-axis
		double tempY = (int) (Y_MIN / Y_STEP);
		tempY *= Y_STEP;
		int width;
		int height;

		if (X_MIN <= 0 && X_MAX >= 0) {
			while (tempY < Y_MAX) {
				setLineSize(1);
				drawLineSeg(X_MIN, tempY, X_MAX, tempY, Color.GRAY, g);
				setLineSize(LINE_SIZE_DEFAULT);
				drawLineSeg(2 * LINE_SIZE * X_PIXEL, tempY, -1 * LINE_SIZE
						* X_PIXEL, tempY, Color.BLACK, g);
				if(tempY%(NUM_FREQ * Y_STEP) == 0 && tempY != 0){
					String ptText = Double.toString(tempY);
					width = g.getFontMetrics().stringWidth(ptText);
					height = g.getFontMetrics().getHeight();
					g.setColor(Color.white);
					g.fillRect(gridXPtToScreen(0) - width - 4, gridYPtToScreen(tempY)- 4, width, 11);
					g.setColor(Color.black);
					g.drawString(ptText, gridXPtToScreen(0) - width - 4, gridYPtToScreen(tempY)+ 6);
				} 
				tempY += Y_STEP;
			}
		} else {
			if(X_MIN >= 0){
				while (tempY < Y_MAX) {
					setLineSize(1);
					drawLineSeg(X_MIN, tempY, X_MAX, tempY, Color.GRAY, g);
					setLineSize(LINE_SIZE_DEFAULT);
					g.setColor(Color.BLACK);
					ptOn(X_MIN + 2 * X_PIXEL, tempY, g);
					if(tempY%(NUM_FREQ* Y_STEP) == 0 && tempY != 0){
						String ptText = Double.toString(tempY);
						width = g.getFontMetrics().stringWidth(ptText);
						height = g.getFontMetrics().getHeight();
						g.setColor(Color.white);
						g.fillRect(gridXPtToScreen(X_MIN) + 8, gridYPtToScreen(tempY)- 4, width, 11);
						g.setColor(Color.black);
						g.drawString(ptText, gridXPtToScreen(X_MIN) + 8, gridYPtToScreen(tempY)+ 6);
					} 
					tempY += Y_STEP;
				}
			}
			else if(X_MAX <= 0){
				while (tempY < Y_MAX) {
					setLineSize(1);
					drawLineSeg(X_MIN, tempY, X_MAX, tempY, Color.GRAY, g);
					setLineSize(LINE_SIZE_DEFAULT);
					g.setColor(Color.BLACK);
					ptOn(X_MAX - 1 * X_PIXEL, tempY, g);
					if(tempY%(NUM_FREQ* Y_STEP) == 0 && tempY != 0){
						String ptText = Double.toString(tempY);
						width = g.getFontMetrics().stringWidth(ptText);
						height = g.getFontMetrics().getHeight();
						g.setColor(Color.white);
						g.fillRect(gridXPtToScreen(X_MAX) - width - 4, gridYPtToScreen(tempY)- 4, width, 11);
						g.setColor(Color.black);
						g.drawString(ptText, gridXPtToScreen(X_MAX)- width - 4, gridYPtToScreen(tempY)+ 6);
					}
					tempY += Y_STEP;
					}
			}
		}
		
		// finds the fist factor of the X_STEP on the screen
		// used to draw the first dash mark on the x-axis
		double tempX = (int) (X_MIN / X_STEP);
		tempX *= X_STEP;
		height = g.getFontMetrics().getHeight();

		int tempWidth = g.getFontMetrics().stringWidth(Double.toString(tempX)) + 20;
		if(tempWidth > (int) ((X_MAX-X_MIN)/(X_STEP * NUM_FREQ))){
			NUM_FREQ = (int) (((X_MAX-X_MIN)/(X_STEP))/((X_SIZE)/tempWidth)) + 1;
		}
		if (Y_MIN <= 0 && Y_MAX >= 0) {
			while (tempX < X_MAX) {
				setLineSize(1);
				drawLineSeg(tempX, Y_MIN, tempX, Y_MAX, Color.GRAY, g);
				setLineSize(LINE_SIZE_DEFAULT);
				drawLineSeg(tempX, 2 * LINE_SIZE * Y_PIXEL, tempX, -1
						* LINE_SIZE * Y_PIXEL, Color.BLACK, g);
				
				if(tempX%(NUM_FREQ * X_STEP) == 0 && tempX != 0){
					String ptText = Double.toString(tempX);
					width = g.getFontMetrics().stringWidth(ptText);
					height = g.getFontMetrics().getHeight();
					g.setColor(Color.white);
					g.fillRect(gridXPtToScreen(tempX) - (width/2), gridYPtToScreen(0) - 18, width + 2, height - 4);
					g.setColor(Color.black);
					g.drawString(ptText, gridXPtToScreen(tempX) - (width/2), gridYPtToScreen(0) - 8);
				} 
				tempX += X_STEP;
			}
		} else {
			if( Y_MIN >= 0){
				while (tempX < X_MAX) {
					setLineSize(1);
					drawLineSeg(tempX, Y_MIN, tempX, Y_MAX, Color.GRAY, g);
					setLineSize(LINE_SIZE_DEFAULT);
					g.setColor(Color.BLACK);
					ptOn(tempX, Y_MIN, g);
					if(tempX%(NUM_FREQ * X_STEP) == 0 && tempX != 0){
						String ptText = Double.toString(tempX);
						width = g.getFontMetrics().stringWidth(ptText);
						height = g.getFontMetrics().getHeight();
						g.setColor(Color.white);
						g.fillRect(gridXPtToScreen(tempX) - (width/2), gridYPtToScreen(Y_MIN) - 18, width + 2, height - 4);
						g.setColor(Color.black);
						g.drawString(ptText, gridXPtToScreen(tempX) - (width/2), gridYPtToScreen(Y_MIN) - 8);
					} 
					tempX += X_STEP;
				}
			}
			if( Y_MAX <= 0){
				while (tempX < X_MAX) {
					setLineSize(1);
					drawLineSeg(tempX, Y_MIN, tempX, Y_MAX, Color.GRAY, g);
					setLineSize(LINE_SIZE_DEFAULT);
					g.setColor(Color.BLACK);
					ptOn(tempX, Y_MAX - 1 * LINE_SIZE * Y_PIXEL, g);
					if(tempX%(NUM_FREQ * X_STEP) == 0 && tempX != 0){
						String ptText = Double.toString(tempX);
						width = g.getFontMetrics().stringWidth(ptText);
						height = g.getFontMetrics().getHeight();
						g.setColor(Color.white);
						g.fillRect(gridXPtToScreen(tempX) - (width/2), gridYPtToScreen(Y_MAX) + 12, width + 2, height - 4);
						g.setColor(Color.black);
						g.drawString(ptText, gridXPtToScreen(tempX) - (width/2), gridYPtToScreen(Y_MAX) + 22);
					} 
					tempX += X_STEP;
				}
			}
		}

		if (X_MIN <= 0 && X_MAX >= 0)
			drawLineSeg(0, Y_MIN, 0, Y_MAX, Color.BLACK, g);

		if (Y_MIN <= 0 && Y_MAX >= 0)
			drawLineSeg(X_MIN, 0, X_MAX, 0, Color.BLACK, g);
	}

	private void drawLineSeg(double x1, double y1, double x2, double y2,
			Color color, Graphics g) {
		// System.out.println(x1 + "  " + y1 + "  " + x2 + "  " + y2);
		g.setColor(color);
		double smallX = 0, bigX = 0, smallY = 0, bigY = 0;

		if (x1 == x2) {

			if (y1 > y2) {
				bigY = y1;
				smallY = y2;
			} else {
				bigY = y2;
				smallY = y1;
			}

			while (smallY <= bigY - LINE_SIZE * Y_PIXEL) {
				ptOn(x1, smallY, g);
				smallY += Y_PIXEL;
			}
		}

		else if (y1 == y2) {

			if (x1 > x2) {
				bigX = x1;
				smallX = x2;
			} else {
				bigX = x2;
				smallX = x1;
			}

			while (smallX <= bigX - LINE_SIZE * X_PIXEL) {
				ptOn(smallX, y1, g);
				smallX += X_PIXEL;
			}
		}
		else {
			double slope = (y2 - y1) / (x2 - x1);
			double b = y1-(x1*slope);
			
			// System.out.println("slope: " + slope);
				if (Math.abs(slope) > 1) {
					if (y1 > y2) {
						bigX = x1;
						bigY = y1;
						smallX = x2;
						smallY = y2;
					}
					else {
						bigX = x2;
						bigY = y2;
						smallX = x1;
						smallY = y1;
					}
					if(smallY < Y_MIN)
						smallY = Y_MIN;
					smallX = (smallY - b)/slope;
					while (smallY <= Y_MAX && smallY <= bigY) {
						ptOn(smallX, smallY, g);
						smallX += Y_PIXEL / slope;
						smallY += Y_PIXEL;
					}
				}
				else { // absolute value of slope is less than 1
					if (x1 > x2) {
						bigX = x1;
						bigY = y1;
						smallX = x2;
						smallY = y2;
					}
					else {
						bigX = x2;
						bigY = y2;
						smallX = x1;
						smallY = y1;
					}
					if(smallX < X_MIN)
						smallX = X_MIN;
					smallY = smallX * slope  + b;
					while (smallX <= X_MAX && smallX <= bigX) {
						ptOn(smallX, smallY, g);
						smallX += X_PIXEL;
						smallY += X_PIXEL * slope;
					}
				}
		}
	}

	public int roundDouble(double a) {
		if (a % 1 >= .5)
			return (int) a + 1;
		else
			return (int) a;
	}

	public void graphCart(Function f, Graphics g) {
		String eqtn = f.getFuncEqtn();
		Var ind = f.getIndependentVar();
		Var dep = f.getDependentVar();
		Color color = f.getColor();
		boolean tracing = f.isTracingPt();
		double tracePt = f.getTraceVal();
		boolean isConnected = f.isConneted();
		boolean takeIntegral = f.isTakingIntegral();
		double a = f.getStartIntegral();
		double b = f.getEndIntegral();
		double derivative = f.getDerivative();
		boolean deriving = f.isDeriving();
		
		double lastX, lastY, currX, currY;
		g.setColor(color);
		try{
			Value expression = parser.ParseExpression(eqtn);
			ind.setValue(new Decimal(X_MIN));
			expression.eval();
			lastX = ind.getValue().toDec().getValue();
			lastY = dep.getValue().toDec().getValue();
			for (int i = 1; i < X_SIZE; i += 2) {
				ind.updateValue(2 * X_PIXEL);
				expression.eval();
				currX = ind.getValue().toDec().getValue();
				currY = dep.getValue().toDec().getValue();
	
				ptOn(currX, currY, g);
				if (isConnected){
					drawLineSeg(lastX, lastY, currX, currY, color, g);
				}
	
				if (takeIntegral) {
					if (currX >= a && currX <= b) {
						color = color.brighter();
						if (currY < Y_MAX && currY > Y_MAX)
							drawLineSeg(currX, 0, currX, Y_MAX, color, g);
						else if (currY < Y_MAX && currY > Y_MIN)
							drawLineSeg(currX, 0, currX, currY, color, g);
						else if (currY <= Y_MIN)
							drawLineSeg(currX, 0, currX, Y_MIN, color, g);
						else if (currY >= Y_MAX)
							drawLineSeg(currX, 0, currX, Y_MAX, color, g);
						else
							;// do nothing
						color = color.darker();
					}
				}
				lastX = ind.getValue().toDec().getValue();
				lastY = dep.getValue().toDec().getValue();
			}
			if (tracing) {
				g.setColor(Color.black);
				ind.setValue(new Decimal(tracePt));
				
				drawTracer(tracePt, parser.ParseExpression(eqtn).eval().toDec().getValue(), g);
			}
			//draws a line that is always 20 pixels in length, this is broken, will fix later
			if (deriving)
			{//this will be redone later
				/*
				CURRCALC.parse(eqtn);
				double slope = CURRCALC.deriveAtPoint(derivative);
				ind.setValue(derivative);
				double depVal = CURRCALC.solve();
				double xChange = Math.sin(Math.atan(slope))*20;
				double yChange = 20 - xChange;
				if(slope > 1)
					yChange = -1*yChange;
				drawLineSeg(derivative - xChange*X_PIXEL, depVal - yChange*Y_PIXEL, 
						derivative + xChange*X_PIXEL, depVal + yChange*Y_PIXEL, new Color(255, 69, 0), g);
				*/
			}
		}
		catch(Exception e)
		{
			System.out.println("error while drawing graph");
		}
		
	}
	
	public void graphPolar(Function f, Graphics g) {
		String eqtn = f.getFuncEqtn();
		Var ind = f.getIndependentVar();
		Var dep = f.getDependentVar();
		Color color = f.getColor();
		boolean tracing = f.isTracingPt();
		double tracePt = f.getTraceVal();
		boolean isConnected = f.isConneted();
		boolean takeIntegral = f.isTakingIntegral();
		double a = f.getStartIntegral();
		double b = f.getEndIntegral();
		int angleUnits = parser.getAngleUnits();
		setLineSize(LINE_SIZE_DEFAULT);
		
		double currR, currT, lastX, lastY, currX, currY;
		g.setColor(color);
		
		try{
			ind.setValue(new Decimal(THETA_MIN));
			Value expression = parser.ParseExpression(eqtn);
			expression.eval();
			currR = dep.getValue().toDec().getValue();
			currT = ind.getValue().toDec().getValue();
			
			lastX = currR * Math.cos(currT);
			lastY = currR * Math.sin(currT);
			int numCalcs = (int)((THETA_MAX-THETA_MIN)/THETA_STEP);
			for (int i = 1; i <= numCalcs; i++) {
				ind.updateValue(THETA_STEP);
				expression.eval();
				currR = dep.getValue().toDec().getValue();
				currT = ind.getValue().toDec().getValue();
				
				if(angleUnits == 2)
					currT *= (Math.PI/180);
				if(angleUnits == 3)
					currT *= (Math.PI/200);
				currX = currR * Math.cos(currT);
				currY = currR * Math.sin(currT);
				//polPtOn(currT, currR, g);
				drawLineSeg(lastX, lastY, currX, currY, color, g);
				lastX = currX;
				lastY = currY;
			}
		}
		catch (Exception e){
			//do something
		}
	}

	public void drawTracer(double x, double y, Graphics g) {
		ptOn(x, y, g);
		ptOn(x + LINE_SIZE * X_PIXEL, y + LINE_SIZE * Y_PIXEL, g);
		ptOn(x + LINE_SIZE * X_PIXEL, y - LINE_SIZE * Y_PIXEL, g);
		ptOn(x - LINE_SIZE * X_PIXEL, y + LINE_SIZE * Y_PIXEL, g);
		ptOn(x - LINE_SIZE * X_PIXEL, y - LINE_SIZE * Y_PIXEL, g);
	}
}
