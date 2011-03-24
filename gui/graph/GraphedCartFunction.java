package gui.graph;

import java.awt.Color;
import java.awt.Graphics;

import tree.Decimal;
import tree.ExpressionParser;
import tree.Number;
import tree.Value;

public class GraphedCartFunction extends GraphWithFunction {

	/**
	 * The default constructor, set the equation equal to an empty string,
	 * makes it not currently graphing, integral and tracing values are
	 * false.
	 */
	public GraphedCartFunction(ExpressionParser ep, Graph g) {
		super(ep, g);
	}
	
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
			boolean connected, boolean trace, double tracePt, boolean integral,
			double startInt, double endInt, boolean derive, double derivative, Color c) {
		super(exParser, g, eqtn, ind, dep, connected, trace, tracePt, integral, startInt, 
				endInt, derive, derivative, c);

	}
	
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
		//used to temporarily store the value stored in the independent and dependent vars,
		//this will allow it to be restored after graphing, so that if in the terminal a
		//value was assingned to x, it will not be overriden by the action of graphing
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
			//System.out.println(funcEqtn);
			Value expression = getParser().ParseExpression(getFuncEqtn());
			getIndependentVar().setValue(new Decimal(graph.X_MIN));
			expression.eval();
			lastX = getIndependentVar().getValue().toDec().getValue();
			lastY = getDependentVar().getValue().toDec().getValue();
			addPt(gridxToScreen(lastX), gridyToScreen(lastY));
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
				else if (gridyToScreen(lastY) >= graph.Y_SIZE && gridyToScreen(currY) <= 0)
				{//if the last point was off the the bottom of the screen, and this one is off
					//the top, add the two to the list of points
					addPt(gridxToScreen(lastX), graph.Y_SIZE);
					addPt(gridxToScreen(currX), 0);
				}
				else if (gridyToScreen(currY) >= graph.Y_SIZE && gridyToScreen(lastY) <= 0)
				{//if the last point was off the the top of the screen, and this one is off
					//the bottom, add the two to the list of points
					addPt(gridxToScreen(lastX), 0);
					addPt(gridxToScreen(currX), graph.Y_SIZE);
				}
				
				if (isConnected()){
					drawLineSeg(lastX, lastY, currX, currY, getColor(), g);
				}
	
				if (isTakingIntegral()) {
					if (currX >= getStartIntegral() && currX <= getEndIntegral()) {
						setColor(getColor().brighter());
						graph.LINE_SIZE = 1;
						if (currY < graph.Y_MAX && currY > graph.Y_MAX)
							drawLineSeg(currX, 0, currX, graph.Y_MAX, getColor(), g);
						else if (currY < graph.Y_MAX && currY > graph.Y_MIN)
							drawLineSeg(currX, 0, currX, currY, getColor(), g);
						else if (currY <= graph.Y_MIN)
							drawLineSeg(currX, 0, currX, graph.Y_MIN, getColor(), g);
						else if (currY >= graph.Y_MAX)
							drawLineSeg(currX, 0, currX, graph.Y_MAX, getColor(), g);
						else
							;// do nothing
						setColor(getColor().darker());
						graph.LINE_SIZE = tempLineSize;
					}
				}

				lastX = currX;
				lastY = currY;
			}
			if (isTracingPt()) {
				g.setColor(Color.black);
				getIndependentVar().setValue(new Decimal(getTraceVal()));
				super.drawTracer(super.getTraceVal(), expression.eval().toDec().getValue(), g);
				super.ptOn(10, 10, g);
				super.ptOn(5, 5, g);
			}
			//draws a line that is always 20 pixels in length, this is broken, will fix later
			if (isDeriving())
			{//this will be redone later
				
				
				getIndependentVar().setValue(new Decimal(getDerivative()));
//				System.out.println(super.getTraceVal() + " deriving"+ expression.eval().toDec().getValue());
				super.drawTangent(super.getDerivative(), expression.eval().toDec().getValue(), 
						expression.deriveAtPt(super.getDerivative(), "x", "y").toDec().getValue(), Color.BLACK, g);
				
//				double slope = CURRCALC.deriveAtPoint(derivative);
//				getIndependentVar().setValue(derivative);
//				double depVal = CURRCALC.solve();
//				double xChange = Math.sin(Math.atan(slope))*20;
//				double yChange = 20 - xChange;
//				if(slope > 1)
//					yChange = -1*yChange;
//				drawLineSeg(derivative - xChange*graph.X_PIXEL, depVal - yChange*graph.Y_PIXEL, 
//						derivative + xChange*graph.X_PIXEL, depVal + yChange*graph.Y_PIXEL, new Color(255, 69, 0), g);
				
			}
			
			//restore the previous values of x and y
			getIndependentVar().setValue(xVal);
			getDependentVar().setValue(yVal);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		graph.LINE_SIZE = 2;
		
	}

}
