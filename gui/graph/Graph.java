package gui.graph;

import gui.MainApplet;
import gui.SubPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.Vector;

import tree.EvalException;
import tree.VarStorage;

public class Graph {
	
	public double X_MIN, X_MAX, Y_MIN, Y_MAX, X_STEP, Y_STEP, X_PIXEL, Y_PIXEL,
	THETA_STEP, THETA_MIN,THETA_MAX, POL_STEP, POL_AX_STEP;
	public int X_SIZE, Y_SIZE, LINE_SIZE, LINE_SIZE_DEFAULT = 2, NUM_FREQ;
	private GraphWindow graphWindow;
	private MainApplet mainApp;
	VarStorage varList;
	private BufferedImage graphPic;
	private Vector<GraphComponent> comp;
	private CartAxis axis;
	
	public Graph(GraphWindow window, MainApplet app){
		mainApp = app;
		graphWindow = window;
		varList = mainApp.getParser().getVarList();
		X_SIZE = graphWindow.getGraphWidth();
		Y_SIZE = graphWindow.getGraphHeight();
		graphPic = new BufferedImage(X_SIZE, Y_SIZE, BufferedImage.TYPE_4BYTE_ABGR);
		axis = new CartAxis(this);
		
		comp = new Vector<GraphComponent>();
		
		comp.add(axis);
		for (int i = 0; i < 5; i++){
			comp.add(new GraphedCartFunction("y = x +5 - 6x^4 + 3x^2", mainApp.getParser(), this));
		}
	}
	
	public void repaint(Graphics g){
		
		
		try
		{	
			X_SIZE = graphWindow.getWidth(); 
			Y_SIZE = graphWindow.getHeight();
			X_MIN = varList.getVarVal("xMin").toDec().getValue();
			X_MAX = varList.getVarVal("xMax").toDec().getValue();
			Y_MIN = varList.getVarVal("yMin").toDec().getValue();
			Y_MAX = varList.getVarVal("yMax").toDec().getValue();
			X_STEP = varList.getVarVal("xStep").toDec().getValue();
			Y_STEP = varList.getVarVal("yStep").toDec().getValue();
			THETA_MIN = varList.getVarVal("thetaMin").toDec().getValue();
			THETA_MAX = varList.getVarVal("thetaMax").toDec().getValue();
			THETA_STEP = varList.getVarVal("thetaStep").toDec().getValue();
			X_PIXEL = (X_MAX - X_MIN) / X_SIZE;
			Y_PIXEL = (Y_MAX - Y_MIN) / Y_SIZE;
			NUM_FREQ = 2;
			
		}catch (Exception e){
			;//do something, probably paint to the picture, this should not throw errors, but
			//just to be safe
		}
		
		System.out.println("reapaint garph!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		graphPic.flush();
		//graphPic = new BufferedImage(X_SIZE, Y_SIZE, BufferedImage.TYPE_4BYTE_ABGR);
		//BufferedImage graphCompPic = new BufferedImage(X_SIZE, Y_SIZE, BufferedImage.TYPE_4BYTE_ABGR);
		//Graphics g = graphPic.getGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, X_SIZE, Y_SIZE);
		
		// Create a rescale filter op that makes the image 90% opaque
		float[] scales = { 1f, 1f, 1f, 0.9f };
		float[] offsets = new float[4];
		RescaleOp rop = new RescaleOp(scales, offsets, null);
		
		for (GraphComponent c : comp){
			System.out.println("draw comp  " + c.toString());
			//graphCompPic = new BufferedImage(X_SIZE, Y_SIZE, BufferedImage.TYPE_4BYTE_ABGR);
			//c.draw(graphCompPic.getGraphics());
			c.draw(g);
			//g.drawImage(c.getImage(), 0, 0, null);
			//Graphics2D g2d = (Graphics2D) g;

			//Draw the image, applying the filter
			//g2d.drawImage(graphCompPic, rop, 0, 0);

			//g.drawImage(c.getImage(), 0, 0, new Color(250, 250, 250, 20), null);
			//graphCompPic.flush();
		}
		g.setColor(Color.black);
		g.drawString("final graph", 50, 50);
		g.dispose();
		//graphCompPic.flush();
	}
	
	public void shiftGraph(double xPix, double yPix){
		varList.updateVarVal("xMin", (xPix)*X_PIXEL);
		varList.updateVarVal("xMax", (xPix)*X_PIXEL);
		varList.updateVarVal("yMin", (yPix)*Y_PIXEL);
		varList.updateVarVal("yMax", (yPix)*Y_PIXEL);
		graphWindow.repaint();
	}
	
	public void zoom(double rate) throws EvalException{
		X_MIN = varList.getVarVal("xMin").toDec().getValue();
		X_MAX = varList.getVarVal("xMax").toDec().getValue();
		Y_MIN = varList.getVarVal("yMin").toDec().getValue();
		Y_MAX = varList.getVarVal("yMax").toDec().getValue();
		
		//hacked solution to prevent drawing the grid, the auto-rescaling of the 
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
		
		graphWindow.repaint();
	}
	
	public void setLineSize(int sizeInPixels) {
		LINE_SIZE = sizeInPixels;
	}

	public void setGraphPic(BufferedImage graphPic) {
		this.graphPic = graphPic;
	}

	public BufferedImage getGraphPic() {
		return graphPic;
	}
}
