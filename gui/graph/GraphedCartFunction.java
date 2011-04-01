package gui.graph;

import gui.MainApplet;

import java.awt.Color;
import java.awt.Graphics;

import tree.Decimal;
import tree.ExpressionParser;
import tree.Number;
import tree.Value;

public class GraphedCartFunction extends GraphWithFunction {

	MainApplet mainApp;

	/**
	 * The default constructor, set the equation equal to an empty string,
	 * makes it not currently graphing, integral and tracing values are
	 * false.
	 */
	
	public GraphedCartFunction(String s, ExpressionParser ep, Graph g, Color c) {
		super(s, ep, g, c);
	}
	
	/**
	 * Constructor that takes all attributes of a function.
	 * 
	 * @param exParser- associated 
	 * @param eqtn - string of equation
	 * @param ind - string of independent var
	 * @param dep - string of dependent var
	 * @param connected - boolean for points to be connected when graphed
	 * @param trace - boolean for tracing
	 * @param tracePt - double for value to trace
	 * @param integral - boolean for taking an integral
	 * @param startInt - double for start of integral
	 * @param endInt - double for end of integral
	 * @param c - a color to display the function with
	 */
	public GraphedCartFunction(ExpressionParser exParser, Graph g, String eqtn, String ind, String dep, 
			boolean connected, Color c) {
		super(exParser, g, eqtn, ind, dep, connected, c);

	}
	
	public GraphedCartFunction(ExpressionParser parser, Graph graph, Color color) {
		// TODO Auto-generated constructor stub
		super(parser, graph, color);
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
		//used to temporarily store the value stored in the independent and dependent vars,
		//this will allow it to be restored after graphing, so that if in the terminal a
		//value was assigned to x, it will not be overriden by the action of graphing
		Number xVal = getIndependentVar().getValue();
		Number yVal = getDependentVar().getValue();
		
		super.clearPts();
		
		g.setColor(getColor());
		if (hasFocus()){
			graph.LINE_SIZE = 3;
		}
		else{
			graph.LINE_SIZE = 2;
		}
		
		int tempLineSize = graph.LINE_SIZE;
		
		double lastX, lastY, currX, currY;
		try{
			Value expression = getParser().ParseExpression(getFuncEqtn());
			getIndependentVar().setValue(new Decimal(graph.X_MIN));
			expression.eval();
			lastX = getIndependentVar().getValue().toDec().getValue();
			lastY = getDependentVar().getValue().toDec().getValue();
			
			if (gridxToScreen(lastX) <= graph.X_SIZE &&  gridxToScreen(lastX) >= 0
					&& gridyToScreen(lastY) <= graph.Y_SIZE && gridyToScreen(lastY) >= 0)
			{//if the current point is on the screen, add it to the list of points
				addPt(gridxToScreen(lastX), gridyToScreen(lastY));
			}
			for (int i = 1; i < graph.X_SIZE; i += 2) {
				getIndependentVar().updateValue(2 * graph.X_PIXEL);
				expression.eval();
				currX = getIndependentVar().getValue().toDec().getValue();
				currY = getDependentVar().getValue().toDec().getValue();
				
				if (gridxToScreen(currX) <= graph.X_SIZE &&  gridxToScreen(currX) >= 0
						&& gridyToScreen(currY) <= graph.Y_SIZE && gridyToScreen(currY) >= 0)
				{//if the current point is on the screen, add it to the list of points
					addPt(gridxToScreen(currX), gridyToScreen(currY));
				}
				else if (lastY >= graph.Y_MAX && currY <= graph.Y_MIN)
				{//if the last point was off the the bottom of the screen, and this one is off
					//the top, add the two to the list of points
					addPt(gridxToScreen(lastX), graph.Y_SIZE);
					addPt(gridxToScreen(currX), 0);
				}
				else if (currY >= graph.Y_MAX && lastY <= graph.Y_MIN)
				{//if the last point was off the the top of the screen, and this one is off
					//the bottom, add the two to the list of points
					addPt(gridxToScreen(lastX), 0);
					addPt(gridxToScreen(currX), graph.Y_SIZE);
				}
				
				if (isConnected()){
					drawLineSeg(lastX, lastY, currX, currY, getColor(), g);
				}
				
				lastX = currX;
				lastY = currY;
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		graph.LINE_SIZE = 2;
		
	}

}
