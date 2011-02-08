package gui.graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import tree.Decimal;

public class CartAxis extends GraphComponent{

	public CartAxis(Graph g) {
		super(g);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
		try {
			//these four statements are for resizing the grid after zooming
			if((graph.X_MAX-graph.X_MIN)/graph.X_STEP >= 24)
			{
				System.out.println("too many x");
				if ((graph.X_MAX-graph.X_MIN)/20 > 1)
				{
					System.out.println("greater than 1");
					graph.varList.setVarVal("xStep", new Decimal((int)(graph.X_MAX-graph.X_MIN)/20));
					graph.X_STEP = graph.varList.getVarVal("xStep").toDec().getValue();
					
	//				varList.setVarVal("yStep", new Decimal((int)(Y_MAX-Y_MIN)/20));
	//				Y_STEP = varList.getVarVal("yStep").toDec().getValue();
				}
				else
				{
					for (int i = 0; i < 25; i ++){
						if ((graph.X_MAX-graph.X_MIN)/20/Math.pow(.5, i) < .7){
							graph.varList.setVarVal("xStep", new Decimal(Math.pow(.5, i)));
							graph.X_STEP = graph.varList.getVarVal("xStep").toDec().getValue();
						}
					}
				}
			}
			
			else if((graph.X_MAX-graph.X_MIN)/graph.X_STEP <= 16){
				System.out.println("too few x");
				if ((graph.X_MAX-graph.X_MIN)/20 > 1)
				{
					System.out.println("greater than 1");
					graph.varList.setVarVal("xStep", new Decimal((int)(graph.X_MAX-graph.X_MIN)/20));
					graph.X_STEP = graph.varList.getVarVal("xStep").toDec().getValue();
					
	//				varList.setVarVal("yStep", new Decimal((int)(Y_MAX-Y_MIN)/20));
	//				Y_STEP = varList.getVarVal("yStep").toDec().getValue();
				}
				else
				{
					System.out.println("do loop to find dec");
					for (int i = 0; i < 25; i ++){
						if ((graph.X_MAX-graph.X_MIN)/20 < Math.pow(.5, i)){
							graph.varList.setVarVal("xStep", new Decimal(Math.pow(.5, i)));
							graph.X_STEP = graph.varList.getVarVal("xStep").toDec().getValue();
						}
					}
				}
			}
			
			if((graph.Y_MAX-graph.Y_MIN)/graph.Y_STEP >= 24){
				System.out.println("too many y");
				graph.varList.setVarVal("yStep", new Decimal((graph.Y_MAX-graph.Y_MIN)/20));
				if ((graph.Y_MAX-graph.Y_MIN)/20 > 1)
				{
	//				varList.setVarVal("xStep", new Decimal((int)(X_MAX-X_MIN)/20));
	//				X_STEP = varList.getVarVal("xStep").toDec().getValue();
					
					System.out.println("greater than 1");
					graph.varList.setVarVal("yStep", new Decimal((int)(graph.Y_MAX-graph.Y_MIN)/20));
					graph.Y_STEP = graph.varList.getVarVal("yStep").toDec().getValue();
				}
				else
				{
					for (int i = 0; i < 25; i ++){
						if ((graph.Y_MAX-graph.Y_MIN)/20/Math.pow(.5, i) < .7){
							graph.varList.setVarVal("yStep", new Decimal(Math.pow(.5, i)));
							graph.Y_STEP = graph.varList.getVarVal("xStep").toDec().getValue();
						}
					}
				}
			}
			
			else if((graph.Y_MAX-graph.Y_MIN)/graph.Y_STEP <= 16){
				System.out.println("too few y");
				if ((graph.Y_MAX-graph.Y_MIN)/20 > 1)
				{
	//				varList.setVarVal("xStep", new Decimal((int)(X_MAX-X_MIN)/20));
	//				X_STEP = varList.getVarVal("xStep").toDec().getValue();
					System.out.println("greater than 1");
					graph.varList.setVarVal("yStep", new Decimal((int)(graph.Y_MAX-graph.Y_MIN)/20));
					graph.Y_STEP = graph.varList.getVarVal("yStep").toDec().getValue();
				}
				else
				{
					System.out.println("do loop to find dec");
					for (int i = 0; i < 25
					; i ++){
						if ((graph.Y_MAX-graph.Y_MIN)/20 < Math.pow(.5, i)){
							graph.varList.setVarVal("yStep", new Decimal(Math.pow(.5, i)));
							graph.Y_STEP = graph.varList.getVarVal("xStep").toDec().getValue();
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		// finds the fist factor of the graph.Y_STEP on the screen
		// used to draw the first dash mark on the y-axis
		double tempY = (int) (graph.Y_MIN / graph.Y_STEP);
		tempY *= graph.Y_STEP;
		int width;
		int height;

		if (graph.X_MIN <= 0 && graph.X_MAX >= 0) {
			while (tempY < graph.Y_MAX) {
				setLineSize(1);
				drawLineSeg(graph.X_MIN, tempY, graph.X_MAX, tempY, Color.GRAY, g);
				setLineSize(graph.LINE_SIZE_DEFAULT);
				drawLineSeg(2 * graph.LINE_SIZE * graph.X_PIXEL, tempY, -2 * graph.LINE_SIZE
						* graph.X_PIXEL, tempY, Color.BLACK, g);
				if(tempY%(2 * graph.Y_STEP) == 0 && tempY != 0){
					String ptText = Double.toString(tempY);
					width = g.getFontMetrics().stringWidth(ptText);
					height = g.getFontMetrics().getHeight();
					g.setColor(Color.white);
					g.fillRect(gridXPtToScreen(0) - width - 2*graph.LINE_SIZE, gridYPtToScreen(tempY)- 2*graph.LINE_SIZE, width, 11);
					g.setColor(Color.black);
					g.drawString(ptText, gridXPtToScreen(0) - width - 2*graph.LINE_SIZE, gridYPtToScreen(tempY)+ 2*graph.LINE_SIZE);
				} 
				tempY += graph.Y_STEP;
			}
		}
		else
		{
			if(graph.X_MIN >= 0)
			{
				while (tempY < graph.Y_MAX)
				{
					setLineSize(1);
					drawLineSeg(graph.X_MIN, tempY, graph.X_MAX, tempY, Color.GRAY, g);
					setLineSize(graph.LINE_SIZE_DEFAULT);
					g.setColor(Color.BLACK);
					ptOn(graph.X_MIN + 1 * graph.X_PIXEL, tempY, g);
					if(tempY%(2 * graph.Y_STEP) == 0 && tempY != 0)
					{
						String ptText = Double.toString(tempY);
						width = g.getFontMetrics().stringWidth(ptText);
						height = g.getFontMetrics().getHeight();
						g.setColor(Color.white);
						g.fillRect(gridXPtToScreen(graph.X_MIN) + 8, gridYPtToScreen(tempY)- 4, width, 11);
						g.setColor(Color.black);
						g.drawString(ptText, gridXPtToScreen(graph.X_MIN) + 8, gridYPtToScreen(tempY)+ 6);
					} 
					tempY += graph.Y_STEP;
				}
			}
			else if(graph.X_MAX <= 0){
				while (tempY < graph.Y_MAX) {
					setLineSize(1);
					drawLineSeg(graph.X_MIN, tempY, graph.X_MAX, tempY, Color.GRAY, g);
					setLineSize(graph.LINE_SIZE_DEFAULT);
					g.setColor(Color.BLACK);
					ptOn(graph.X_MAX - 1 * graph.X_PIXEL, tempY, g);
					if(tempY%(2 * graph.Y_STEP) == 0 && tempY != 0){
						String ptText = Double.toString(tempY);
						width = g.getFontMetrics().stringWidth(ptText);
						height = g.getFontMetrics().getHeight();
						g.setColor(Color.white);
						g.fillRect(gridXPtToScreen(graph.X_MAX) - width - 4, gridYPtToScreen(tempY)- 4, width, 11);
						g.setColor(Color.black);
						g.drawString(ptText, gridXPtToScreen(graph.X_MAX)- width - 4, gridYPtToScreen(tempY)+ 6);
					}
					tempY += graph.Y_STEP;
					}
			}
		}
		
		// finds the fist factor of the graph.X_STEP on the screen
		// used to draw the first dash mark on the x-axis
		double tempX = (int) (graph.X_MIN / graph.X_STEP);
		tempX *= graph.X_STEP;
		height = g.getFontMetrics().getHeight();

		int tempWidth = g.getFontMetrics().stringWidth(Double.toString(tempX)) + 20;
		if(tempWidth > (int) ((graph.X_MAX-graph.X_MIN)/(graph.X_STEP * graph.NUM_FREQ))){
			graph.NUM_FREQ = (int) (((graph.X_MAX-graph.X_MIN)/(graph.X_STEP))/((graph.X_SIZE)/tempWidth)) + 1;
		}
		if (graph.Y_MIN <= 0 && graph.Y_MAX >= 0) {
			while (tempX < graph.X_MAX) {
				setLineSize(1);
				drawLineSeg(tempX, graph.Y_MIN, tempX, graph.Y_MAX, Color.GRAY, g);
				setLineSize(graph.LINE_SIZE_DEFAULT);
				drawLineSeg(tempX, 2 * graph.LINE_SIZE * graph.Y_PIXEL, tempX, -2
						* graph.LINE_SIZE * graph.Y_PIXEL, Color.BLACK, g);
				
				if(tempX%(graph.NUM_FREQ * graph.X_STEP) == 0 && tempX != 0)
				{//if its reached a place where a number should be drawn
					String ptText = Double.toString(tempX);
					width = g.getFontMetrics().stringWidth(ptText);
					height = g.getFontMetrics().getHeight();
					g.setColor(Color.white);
					g.fillRect(gridXPtToScreen(tempX) - (width/2), gridYPtToScreen(0) - 18, width + 2, height - 4);
					g.setColor(Color.black);
					g.drawString(ptText, gridXPtToScreen(tempX) - (width/2), gridYPtToScreen(0) - 8);
				} 
				tempX += graph.X_STEP;
			}
		} else {
			if( graph.Y_MIN >= 0){
				while (tempX < graph.X_MAX) {
					setLineSize(1);
					drawLineSeg(tempX, graph.Y_MIN, tempX, graph.Y_MAX, Color.GRAY, g);
					setLineSize(graph.LINE_SIZE_DEFAULT);
					g.setColor(Color.BLACK);
					ptOn(tempX, graph.Y_MIN, g);
					if(tempX%(graph.NUM_FREQ * graph.X_STEP) == 0 && tempX != 0){
						String ptText = Double.toString(tempX);
						width = g.getFontMetrics().stringWidth(ptText);
						height = g.getFontMetrics().getHeight();
						g.setColor(Color.white);
						g.fillRect(gridXPtToScreen(tempX) - (width/2), gridYPtToScreen(graph.Y_MIN) - 18, width + 2, height - 4);
						g.setColor(Color.black);
						g.drawString(ptText, gridXPtToScreen(tempX) - (width/2), gridYPtToScreen(graph.Y_MIN) - 8);
					} 
					tempX += graph.X_STEP;
				}
			}
			if( graph.Y_MAX <= 0){
				while (tempX < graph.X_MAX) {
					setLineSize(1);
					drawLineSeg(tempX, graph.Y_MIN, tempX, graph.Y_MAX, Color.GRAY, g);
					setLineSize(graph.LINE_SIZE_DEFAULT);
					g.setColor(Color.BLACK);
					ptOn(tempX, graph.Y_MAX - 1 * graph.LINE_SIZE * graph.Y_PIXEL, g);
					if(tempX%(graph.NUM_FREQ * graph.X_STEP) == 0 && tempX != 0){
						String ptText = Double.toString(tempX);
						width = g.getFontMetrics().stringWidth(ptText);
						height = g.getFontMetrics().getHeight();
						g.setColor(Color.white);
						g.fillRect(gridXPtToScreen(tempX) - (width/2), gridYPtToScreen(graph.Y_MAX) + 12, width + 2, height - 4);
						g.setColor(Color.black);
						g.drawString(ptText, gridXPtToScreen(tempX) - (width/2), gridYPtToScreen(graph.Y_MAX) + 22);
					} 
					tempX += graph.X_STEP;
				}
			}
		}

		if (graph.X_MIN <= 0 && graph.X_MAX >= 0)
			drawLineSeg(0, graph.Y_MIN, 0, graph.Y_MAX, Color.BLACK, g);

		if (graph.Y_MIN <= 0 && graph.Y_MAX >= 0)
			drawLineSeg(graph.X_MIN, 0, graph.X_MAX, 0, Color.BLACK, g);
	}

}
