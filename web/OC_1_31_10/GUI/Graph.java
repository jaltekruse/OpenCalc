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
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JApplet;
import javax.swing.JPanel;

import calc.calc;


public class Graph extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public static int X_SIZE;
	public static int Y_SIZE;
	public int LINE_SIZE;
	public double X_MAX;
	public double X_MIN;
	public double Y_MAX;
	public double Y_MIN;
	public double X_STEP;
	public double Y_STEP;
	public double X_PIXEL;
	public double Y_PIXEL;
	private calc CURRCALC;
	private NewCalc calcObj;
	//stuff to put in separate graph class
	private String EQTN;
	private boolean takeIntegral;
	private boolean tracingPt;
	private boolean graphing;
	private double point2Trace;
	private double startIntegral;
	private double endIntegral;
	
	public Graph(int xSize, int ySize, NewCalc currCalcObj){
		calcObj = currCalcObj;
		X_SIZE = xSize;
		Y_SIZE = ySize;
		LINE_SIZE = 2;
		X_MAX = 10;
		X_MIN = -10;
		Y_MAX = 10;
		Y_MIN = -10;
		X_STEP = 1;
		Y_STEP = 1;
		X_PIXEL = (X_MAX-X_MIN)/X_SIZE;
		Y_PIXEL = (Y_MAX-Y_MIN)/Y_SIZE;
		graphing = false;
		EQTN = "";
		CURRCALC = new calc(calcObj);
		//to be new class for graph
		takeIntegral = false;
		tracingPt = false;
		point2Trace = 0;
		startIntegral = 0;
		endIntegral = 0;
		this.setSize(xSize, ySize);
	}
	public void SetGraph(String eqtn){
		if ("".equals(eqtn)){
			graphing = false;
			EQTN = "";
		}
		else{
			graphing = true;
			EQTN = eqtn;
		}
		return;
	}
	
	public String getFunc(int funcNum){
		return EQTN;
	}
	
	public void setLineSize(int sizeInPixels){
		LINE_SIZE = sizeInPixels;
		return;
	}
	
	public void setIntegral(boolean isOn, double a, double b){
		takeIntegral = isOn;
		startIntegral = a;
		endIntegral = b;
	}
	
	public void setTracePt(boolean isOn, double val){
		tracingPt = isOn;
		point2Trace = val;
	}
	
	public void SetXMin(double xMin){
		X_MIN = xMin;
	}
	
	public void SetXMax(double xMax){
		X_MAX = xMax;
	}
	
	public void SetYMin(double yMin){
		Y_MIN = yMin;
	}
	
	public void SetYMax(double yMax){
		Y_MAX = yMax;
	}
	
	public void paint (Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, X_SIZE, Y_SIZE);
		g.setColor  (Color.BLACK);
		X_MIN = calcObj.getVarsObj().getVarVal("xMin");
		X_MAX = calcObj.getVarsObj().getVarVal("xMax");
		Y_MIN = calcObj.getVarsObj().getVarVal("yMin");
		Y_MAX = calcObj.getVarsObj().getVarVal("yMax");
		X_PIXEL = (X_MAX-X_MIN)/X_SIZE;
		Y_PIXEL = (Y_MAX-Y_MIN)/Y_SIZE;
		
		drawAxis(g);
		
		if (graphing)
			graph(EQTN, g, Color.GREEN, true, takeIntegral, startIntegral, endIntegral);
		
	}
	
	private void ptOn(double a, double b, Graphics g){
		if (a <= X_MAX && a >= X_MIN && b <= Y_MAX && b >= Y_MIN){
			g.fillRect(roundDouble((a - X_MIN)/X_PIXEL) - LINE_SIZE, 
					(Y_SIZE - LINE_SIZE) - roundDouble((b - Y_MIN)/Y_PIXEL),
					LINE_SIZE, LINE_SIZE);
			//g.fillOval(roundDouble((a - X_MIN)/X_PIXEL), Y_SIZE - roundDouble((b - Y_MIN)/Y_PIXEL),
					//2*LINE_SIZE, 2*LINE_SIZE);
			//System.out.println("(" + a + "," + b + ")");
			//System.out.println("(" + roundDouble(a/X_PIXEL) + "," + roundDouble(b/Y_PIXEL) + ")" + "\n");
		}
	}
	
	private void drawAxis(Graphics g){
		
		//finds the fist factor of the X_STEP on the screen
		//used to draw the first dash mark on the x-axis
		double tempX = (int) X_MIN/X_STEP;
		tempX++;
		tempX *= X_STEP;
		
		if(Y_MIN <= 0 && Y_MAX >= 0){
			while (tempX < X_MAX){
				setLineSize(1);
				drawLineSeg(tempX, Y_MIN, tempX, Y_MAX, Color.GRAY, g);
				setLineSize(2);
				drawLineSeg(tempX, 2 * LINE_SIZE * Y_PIXEL, tempX, -1 * LINE_SIZE * Y_PIXEL, Color.BLACK, g);
				tempX += X_STEP;
			}
		}
		else{
			while (tempX < X_MAX){
				setLineSize(1);
				drawLineSeg(tempX, Y_MIN, tempX, Y_MAX, Color.GRAY, g);
				setLineSize(2);
				g.setColor(Color.BLACK);
				ptOn(tempX, Y_MIN, g);
				tempX += X_STEP;
			}
		}
		
		//finds the fist factor of the Y_STEP on the screen
		//used to draw the first dash mark on the y-axis
		double tempY = (int) Y_MIN/Y_STEP;
		tempY++;
		tempY *= Y_STEP;
		
		if (X_MIN <= 0 && X_MAX >= 0){
			while (tempY < Y_MAX){
				setLineSize(1);
				drawLineSeg(X_MIN, tempY, X_MAX, tempY, Color.GRAY, g);
				setLineSize(2);
				drawLineSeg(2 * LINE_SIZE * X_PIXEL, tempY, -1 * LINE_SIZE * X_PIXEL, tempY, Color.BLACK, g);
				tempY += Y_STEP;
			}
		}
		else{
			while (tempY < Y_MAX){
				setLineSize(1);
				drawLineSeg(X_MIN, tempY, X_MAX, tempY, Color.GRAY, g);
				setLineSize(2);
				g.setColor(Color.BLACK);
				ptOn(X_MIN + 1 * LINE_SIZE * X_PIXEL, tempY, g);
				tempY += Y_STEP;
			}
		}
		
		if (X_MIN <= 0 && X_MAX >= 0)
			drawLineSeg(0, Y_MIN, 0, Y_MAX, Color.BLACK, g);
		
		if (Y_MIN <= 0 && Y_MAX >= 0)
			drawLineSeg(X_MIN, 0, X_MAX, 0, Color.BLACK, g);
	}
	private void drawLineSeg(double x1, double y1, double x2, double y2, Color color, Graphics g){
		//System.out.println(x1 + "  " + y1 + "  " + x2 + "  " + y2);
		g.setColor(color);
		double smallX= 0, bigX = 0, smallY = 0, bigY = 0;
		
		if (x1 == x2){
			
			if (y1 > y2){
				bigY = y1;
				smallY = y2;
			}
			else{
				bigY = y2;
				smallY = y1;
			}
			
			while (smallY <= bigY - LINE_SIZE * Y_PIXEL){
				ptOn( x1, smallY, g);
				smallY += Y_PIXEL;
			}
		}
		
		else if (y1 == y2){
			
			if (x1 > x2){
				bigX = x1;
				smallX = x2;
			}
			else{
				bigX = x2;
				smallX = x1;
			}
			
			while (smallX <= bigX - LINE_SIZE * X_PIXEL){
				ptOn( smallX, y1, g);
				smallX += X_PIXEL;
			}
		}
		else{
			if (x1 > x2){
				bigX = x1;
				bigY = y1;
				smallX = x2;
				smallY = y2;
			}
			else{
				bigX = x2;
				smallX = x1;
				bigY = y2;
				smallY = y1;
			}
			double slope = (y2-y1)/(x2-x1);
			//System.out.println("slope: " + slope);
			if (slope > 0 ){
				while (smallY <= bigY){
					ptOn(smallX, smallY, g);
					smallX += Y_PIXEL / slope;
					smallY += Y_PIXEL;
					//System.out.println("smallX: " + smallX + " smallY: " + smallY);
				}
			}
			else{ //slope is negative
				while (bigY <= smallY){
					ptOn(smallX, smallY, g);
					smallX -= Y_PIXEL / slope;
					smallY -= Y_PIXEL;
					//System.out.println("smallX: " + smallX + " smallY: " + smallY);
				}
			}
		}
	}
	public int roundDouble(double a){
		if (a % 1 >= .5)
			return (int) a + 1;
		else
			return (int) a;
	}
	
	public void graph(String eqtn, Graphics g, Color color, boolean isConnected, boolean takeIntegral, double a, double b){
		//System.out.println(eqtn);
		CURRCALC.VARLIST.setVarVal("x", X_MIN);
		g.setColor(color);
		CURRCALC.parse(eqtn);
		double lastX, lastY, currX, currY;
		
		CURRCALC.solve();
		lastX = CURRCALC.VARLIST.getVarVal("x");
		lastY = CURRCALC.VARLIST.getVarVal("y");
		for (int i = 1; i < X_SIZE; i += 2){
			CURRCALC.VARLIST.setVarVal("x", CURRCALC.VARLIST.getVarVal("x") + 2*X_PIXEL);
			CURRCALC.solve();
			currX = CURRCALC.VARLIST.getVarVal("x");
			currY = CURRCALC.VARLIST.getVarVal("y");
			
			//System.out.println("currX:" + currX + "     currY:" + currY + "     lastX:" + lastX + "     lastY:" + lastY);

			if(lastY <= Y_MAX && currY >= Y_MAX){
				//System.out.println("1");
				drawLineSeg(lastX, lastY, lastX, Y_MAX, color, g);
			}
			else if (lastY >= Y_MAX && currY <= Y_MAX){
				//System.out.println("2");
				drawLineSeg(currX, Y_MAX, currX, currY, color, g);
			}
			else if (lastY <= Y_MIN && currY >= Y_MIN){
				//System.out.println("3");
				drawLineSeg(currX, Y_MIN, currX, currY, color, g);
			}
			else if (lastY >= Y_MIN && currY <= Y_MIN){
				//System.out.println("4");
				drawLineSeg(lastX, lastY, lastX, Y_MIN, color, g);
			}
			else if (lastY >= Y_MIN && lastY <= Y_MAX && currY <= Y_MAX && currY >= Y_MIN){
				//System.out.println("5");
				drawLineSeg(lastX, lastY, currX, currY, color, g);
			}
			
			if (takeIntegral){
				if (currX >= a && currX <= b){
					if(currY < Y_MAX && currY > Y_MAX)
						drawLineSeg(currX, 0, currX, Y_MAX, color, g);
					else if (currY < Y_MAX && currY > Y_MIN)
						drawLineSeg(currX, 0, currX, currY, color, g);
					else if (currY <= Y_MIN)
						drawLineSeg(currX, 0, currX, Y_MIN, color, g);
					else if (currY >= Y_MAX)
						drawLineSeg(currX, 0, currX, Y_MAX, color, g);
					else
						;//do nothing
				}
			}
			lastX = CURRCALC.VARLIST.getVarVal("x");
			lastY = CURRCALC.VARLIST.getVarVal("y");
		}
		if (tracingPt){
			calcPt(point2Trace, EQTN, g);
		}
	}
	public void drawTracer(double x, double y, Graphics g){
		ptOn( x, y, g);
		ptOn( x + LINE_SIZE * X_PIXEL, y + LINE_SIZE * Y_PIXEL, g);
		ptOn( x + LINE_SIZE * X_PIXEL, y - LINE_SIZE * Y_PIXEL, g);
		ptOn( x - LINE_SIZE * X_PIXEL, y + LINE_SIZE * Y_PIXEL, g);
		ptOn( x - LINE_SIZE * X_PIXEL, y - LINE_SIZE * Y_PIXEL, g);
	}
	
	public void calcPt(double x, String eqtn, Graphics g){
		g.setColor(Color.black);
		CURRCALC.parse(eqtn);
		CURRCALC.VARLIST.setVarVal("x", x);
		drawTracer(x, CURRCALC.solve(), g);
	}
}
	
	
