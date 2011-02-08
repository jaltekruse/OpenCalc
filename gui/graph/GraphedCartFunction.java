package gui.graph;

import java.awt.Color;
import java.awt.Graphics;

import tree.Decimal;
import tree.Number;
import tree.ExpressionParser;
import tree.Value;
import tree.Var;

public class GraphedCartFunction extends GraphedFunction{

/**
 * A Function stores all of the data necessary for graphing.
 * @author jason
 *
 */
	private String funcEqtn;
	private boolean takeIntegral;
	private boolean tracingPt;
	private boolean graphing;
	private boolean connected;
	private double point2Trace;
	private double startIntegral;
	private double endIntegral;
	private double derivative;
	private boolean deriving;
	private Color color;
	private Var independentVar;
	private Var dependentVar;
	private ExpressionParser parser;
	private int graphType;
	//graphTypes: 1-Cartesian   2-Polar    3-parametric    4-Sequential
	
	/**
	 * The default constructor, set the equation equal to an empty string,
	 * makes it not currently graphing, integral and tracing values are
	 * false.
	 */
	public GraphedCartFunction(ExpressionParser ep, Graph g) {
		super(g);
		parser = ep;
		funcEqtn = "";
		graphing = false;
		connected = true;
		setIsTakingIntegral(false);
		setTracingPt(false);
		point2Trace = 0;
		setStartIntegral(0);
		setEndIntegral(0);
		setColor(Color.blue);
		setGraphType(1);
		setIndependentVar("x");
		setDependentVar("y");
	}
	
	public GraphedCartFunction(String s, ExpressionParser ep, Graph g) {
		super(g);
		parser = ep;
		funcEqtn = s;
		graphing = false;
		connected = true;
		setIsTakingIntegral(false);
		setTracingPt(false);
		point2Trace = 0;
		setStartIntegral(0);
		setEndIntegral(0);
		setColor(Color.blue);
		setGraphType(1);
		setIndependentVar("x");
		setDependentVar("y");
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
			boolean connected,boolean trace, double tracePt, boolean integral,
			double startInt, double endInt, boolean derive, double derivative, Color c) {
		super(g);
		parser = exParser;
		setIndependentVar(ind);
		setDependentVar(dep);
		setGraphType(1);
		graphing = true;
		this.connected = connected;
		funcEqtn = eqtn;
		setTracingPt(trace);
		point2Trace = tracePt;
		setDeriving(true);
		setDerivative(derivative);
		setIsTakingIntegral(integral);
		setStartIntegral(startInt);
		setEndIntegral(endInt);
		setColor(c);
	}

	public void setFuncEqtn(String s) {
		funcEqtn = s;
	}

	public String getFuncEqtn() {
		return funcEqtn;
	}

	/**
	 * Sets the values for integral. If both params are zero then set to false.
	 * Else, values are set and integral is set to true.
	 * @param a
	 * @param b
	 */
	public void setIntegral(double a, double b) {
		if (a == 0 && b == 0) {
			setIsTakingIntegral(false);
		} else {
			setStartIntegral(a);
			setEndIntegral(b);
			setIsTakingIntegral(true);
		}
	}

	/**
	 * Sets the value to trace. If Double.MAX_VALUE passed, makes false.
	 * Else, double is stored and tracing is true.
	 * @param val - the value to be traced.
	 */
	public void setTrace(double val) {
		if (val == Double.MAX_VALUE) {
			setTracingPt(false);
		} else {
			setTracingPt(true);
			point2Trace = val;
		}
	}

	public double getTraceVal() {
		return point2Trace;
	}

	public void setIsTakingIntegral(boolean takeIntegral) {
		this.takeIntegral = takeIntegral;
	}

	public boolean isTakingIntegral() {
		return takeIntegral;
	}

	public void setTracingPt(boolean tracingPt) {
		this.tracingPt = tracingPt;
	}

	public boolean isTracingPt() {
		return tracingPt;
	}

	public void setGraphing(boolean graphing) {
		this.graphing = graphing;
	}

	public boolean isGraphing() {
		return graphing;
	}

	public void setStartIntegral(double startIntegral) {
		this.startIntegral = startIntegral;
	}

	public double getStartIntegral() {
		return startIntegral;
	}

	public void setEndIntegral(double endIntegral) {
		this.endIntegral = endIntegral;
	}

	public double getEndIntegral() {
		return endIntegral;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public void setIndependentVar(Var independentVar) {
		this.independentVar = independentVar;
	}
	
	public void setIndependentVar(String varName) {
		independentVar = parser.getVarList().storeVar(varName);
	}
	
	public void setDependentVar(String varName) {
		dependentVar = parser.getVarList().storeVar(varName);
	}


	public Var getIndependentVar() {
		return independentVar;
	}

	public void setDependentVar(Var dependentVar) {
		this.dependentVar = dependentVar;
	}

	public Var getDependentVar() {
		return dependentVar;
	}

	public void setConneted(boolean conneted) {
		this.connected = conneted;
	}

	public boolean isConneted() {
		return connected;
	}

	public void setGraphType(int graphType) {
		this.graphType = graphType;
	}

	public int getGraphType() {
		return graphType;
	}

	public void setDerivative(double derivative) {
		this.derivative = derivative;
	}

	public double getDerivative() {
		return derivative;
	}

	public void setDeriving(boolean deriving) {
		this.deriving = deriving;
	}

	public boolean isDeriving() {
		return deriving;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
		//used to temporarily store the value stored in the independent and dependent vars,
		//this will allow it to be restored after graphing, so that if in the terminal a
		//value was assingned to x, it will not be overriden by the action of graphing
		
		Number xVal = independentVar.getValue();
		Number yVal = dependentVar.getValue();
		
		double lastX, lastY, currX, currY;
		g.setColor(color);
		try{
			System.out.println(funcEqtn);
			Value expression = parser.ParseExpression(funcEqtn);
			independentVar.setValue(new Decimal(graph.X_MIN));
			expression.eval();
			lastX = independentVar.getValue().toDec().getValue();
			lastY = dependentVar.getValue().toDec().getValue();
			for (int i = 1; i < graph.X_SIZE; i += 2) {
				independentVar.updateValue(2 * graph.X_PIXEL);
				expression.eval();
				currX = independentVar.getValue().toDec().getValue();
				currY = dependentVar.getValue().toDec().getValue();
	
				if (connected){
					drawLineSeg(lastX, lastY, currX, currY, color, g);
				}
	
				if (takeIntegral) {
					if (currX >= startIntegral && currX <= endIntegral) {
						color = color.brighter();
						if (currY < graph.Y_MAX && currY > graph.Y_MAX)
							drawLineSeg(currX, 0, currX, graph.Y_MAX, color, g);
						else if (currY < graph.Y_MAX && currY > graph.Y_MIN)
							drawLineSeg(currX, 0, currX, currY, color, g);
						else if (currY <= graph.Y_MIN)
							drawLineSeg(currX, 0, currX, graph.Y_MIN, color, g);
						else if (currY >= graph.Y_MAX)
							drawLineSeg(currX, 0, currX, graph.Y_MAX, color, g);
						else
							;// do nothing
						color = color.darker();
					}
				}
				lastX = independentVar.getValue().toDec().getValue();
				lastY = dependentVar.getValue().toDec().getValue();
			}
			if (tracingPt) {
				g.setColor(Color.black);
				independentVar.setValue(new Decimal(point2Trace));
				
				//replace line below with new class of objects drawn on screen, or place the method in this class
				//graph.drawTracer(point2Trace, expression.eval().toDec().getValue(), g);
			}
			//draws a line that is always 20 pixels in length, this is broken, will fix later
			if (deriving)
			{//this will be redone later
				/*
				CURRCALC.parse(eqtn);
				double slope = CURRCALC.deriveAtPoint(derivative);
				independentVar.setValue(derivative);
				double depVal = CURRCALC.solve();
				double xChange = Math.sin(Math.atan(slope))*20;
				double yChange = 20 - xChange;
				if(slope > 1)
					yChange = -1*yChange;
				drawLineSeg(derivative - xChange*graph.X_PIXEL, depVal - yChange*graph.Y_PIXEL, 
						derivative + xChange*graph.X_PIXEL, depVal + yChange*graph.Y_PIXEL, new Color(255, 69, 0), g);
				*/
			}
			
			//restore the previous values of x and y
			independentVar.setValue(xVal);
			dependentVar.setValue(yVal);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
}