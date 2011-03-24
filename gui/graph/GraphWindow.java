package gui.graph;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JPanel;

import tree.EvalException;
import tree.ParseException;
import tree.ValueNotStoredException;
import tree.VarStorage;

import gui.MainApplet;
import gui.SubPanel;
import gui.TopLevelContainer;

public class GraphWindow extends SubPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MainApplet mainApp;
	private int textHeight;
	private Graph graph;
	private JPanel graphPanel;
	private Selection selection;
	private BufferedImage buffer;
	private double mouseX,
	mouseY, mouseRefX, mouseRefY;
	boolean refPoint, dragSelection, justFinishedPic, isTimeToRedraw, movingSelectionEnd,
			draggingGraph, dragDiskShowing;
	private int heightInfoBar = 40;
	private Runnable current;
	private Object currObj;
	
	public GraphWindow(MainApplet mainApp, TopLevelContainer topLevelComp, int xSize, int ySize){
		super(topLevelComp);
		this.setPreferredSize(new Dimension(xSize, ySize));
		this.mainApp = mainApp;
		graph = new Graph(this, mainApp);
		selection = new Selection(graph, Color.orange);
		dragSelection = false;
		movingSelectionEnd = false;
		graph.setSelection(selection);
		buffer = new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR);
		repaint();
		
		graphPanel = new JPanel(){
			
			public void paint(Graphics g) {
				graph.repaint(g);
			}
		};
		
		graphPanel.addMouseListener(new MouseListener(){
			
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				refPoint = true;
				mouseRefX = e.getX();
				mouseRefY = e.getY();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				refPoint = false;
			}
			
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
				//check if the user clicked on the dragDisk in the center of the screen
				if (Math.sqrt(Math.pow(e.getX() - graph.X_SIZE/2.0, 2) + 
						Math.pow(e.getY() - graph.Y_SIZE/2.0, 2)) <= graph.getDragDisk().getPixelRadius()){
					if (graph.getDragDisk() != null && graph.getDragDisk().isShowingDisk()){
						draggingGraph = true;
						dragSelection = false;
						mouseX = e.getX();
						mouseY = e.getY();
						return;
					}
				}
				
				//check if the user clicked on a function
				if (selectClosestGraph(e.getX(), e.getY())){
					return;
				}
				
				if (selection.getStart() != selection.EMPTY){
					if (Math.abs(e.getX() - selection.gridxToScreen(selection.getEnd())) < 10){
						movingSelectionEnd = true;
						dragSelection = true;
						return;
					}
					else if (Math.abs(e.getX() - selection.gridxToScreen(selection.getStart())) < 10){
						if (selection.getEnd() == selection.EMPTY){
							movingSelectionEnd = true;
							dragSelection = true;
							return;
						}
						movingSelectionEnd = false;
						dragSelection = true;
						return;
					}
				}
				selection.setStart(e.getX() * graph.X_PIXEL + graph.X_MIN);
				
				//snap if close to a notch on the grid
				if (Math.abs((selection.getStart() - Math.round(selection.getStart() / 
						graph.X_STEP) * graph.X_STEP) / graph.X_PIXEL) < 8){
					selection.setStart(Math.round(selection.getStart() / graph.X_STEP) * graph.X_STEP);
				}
				
				selection.setEnd(selection.EMPTY);
				movingSelectionEnd = true;
				dragSelection = true;
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				graph.getDragDisk().setShowingDisk(false);
				repaint();
				dragSelection = false;
				draggingGraph = false;
			}
			
		});
		
		graphPanel.addMouseMotionListener(new MouseMotionListener(){
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
				if (draggingGraph){
					graph.shiftGraph(mouseX - e.getX(), e.getY() - mouseY);
					mouseX = e.getX();
					mouseY = e.getY();
				}
				if (dragSelection){
					if (movingSelectionEnd){
						if (e.getX() < selection.gridxToScreen(selection.getStart())){
							selection.setEnd(selection.getStart());
							selection.setStart(selection.screenxToGrid(e.getX()));
							movingSelectionEnd = !movingSelectionEnd;
							return;
						}
						
						selection.setEnd(selection.screenxToGrid(e.getX()));
						if (Math.abs((selection.getEnd() - Math.round(selection.getEnd() / 
								graph.X_STEP) * graph.X_STEP) / graph.X_PIXEL) < 8){
							selection.setEnd(Math.round(selection.getEnd() / graph.X_STEP) * graph.X_STEP);
						}
					}
					else{
						
						if (e.getX() > selection.gridxToScreen(selection.getEnd())){
							selection.setStart(selection.getEnd());
							selection.setEnd(selection.screenxToGrid(e.getX()));
							movingSelectionEnd = !movingSelectionEnd;
							return;
						}
						
						selection.setStart(selection.screenxToGrid(e.getX()));
						if (Math.abs((selection.getStart() - Math.round(selection.getStart() / 
								graph.X_STEP) * graph.X_STEP) / graph.X_PIXEL) < 8){
							selection.setStart(Math.round(selection.getStart() / graph.X_STEP) * graph.X_STEP);
						}
					}
				}
				
				repaint();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				if (Math.sqrt(Math.pow(e.getX() - graph.X_SIZE/2.0, 2) + 
						Math.pow(e.getY() - graph.Y_SIZE/2.0, 2)) <= graph.getDragDisk().getPixelRadius()){
					if ( ! graph.getDragDisk().isShowingDisk()){
						graph.getDragDisk().setShowingDisk(true);
						repaint();	
					}	
				}
				else{
					if (graph.getDragDisk().isShowingDisk()){
						graph.getDragDisk().setShowingDisk(false);
						repaint();
					}
				}
			}
			
		});
		
		this.addMouseWheelListener(new MouseWheelListener(){

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				// TODO Auto-generated method stu
				try {
					graph.zoom(100 - e.getWheelRotation() * 5);
					Runnable timer = new Runnable(){

						@Override
						public void run() {
							// TODO Auto-generated method stub
							repaint();
						}
						
					};
					javax.swing.SwingUtilities.invokeLater(timer);
				} catch (EvalException e1) {
					//TODO Auto-generated catch block
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
		this.add(graphPanel, bCon);
		this.setPreferredSize(new Dimension(xSize, ySize));
	}
	
	public Graph getGraph(){
		return graph;
	}
	
	public boolean hasValidSelection(){
		if (selection.getStart() != selection.EMPTY && selection.getEnd() != selection.EMPTY){
			return true;
		}
		return false;
	}
	
//	public void repaintBuffer(){
//		buffer = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
//		Graphics g = buffer.getGraphics();
//		textHeight = g.getFontMetrics().getHeight();
//		g.setColor(Color.white);
//		g.fillRect(0, 0, getWidth(), getHeight());
//		g.drawImage(largeGraph.getGraphPic(), 0, 0, null);
//		g.dispose();
//		graph.repaint();
//	}
	
//	
//	public void repaintAxis(){
//		justFinishedPic = false;
//		repaint();
//	}
//	
//	public void tempZoom(double rate){
//		Graphics g = buffer.getGraphics();
//		int xSize = graph.getWidth();
//		int ySize = graph.getHeight();
//		if ( rate < 100){
//			BufferedImage tempImg = new BufferedImage(xSize - 2 * ((int) (xSize * ((100 - rate)/100))),
//					ySize - 2 * ((int) (ySize * ((100 - rate)/100))), BufferedImage.TYPE_4BYTE_ABGR);
//			Graphics tempG = tempImg.getGraphics();
//			tempG.drawImage(buffer, 0, 0, tempImg.getWidth(), tempImg.getHeight(), 0, 0, xSize, ySize, null);
//			g.setColor(Color.gray);
//			g.fillRect(0, 0, xSize, ySize);
//			g.drawImage(tempImg,
//					(int) (xSize * ((100 - rate)/100)), (int) (ySize * ((100 - rate)/100)), Color.gray, null);
//		}
//		else{
//			g.drawImage(buffer, 0, 0, xSize, ySize,
//					(int) (xSize * ((rate - 100)/100)), (int) (ySize * ((rate - 100)/100)), (int) (xSize - xSize * ((rate - 100)/100))
//					, (int) (ySize - ySize * ((rate - 100)/100)), Color.gray, null);
//		}
//		repaint();
//		try {
//			largeGraph.zoom(rate);
//		} catch (EvalException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
	
	public int getGraphWidth(){
		if (this.getWidth() == 0){
			return 400;
		}
		return this.getWidth();
	}
	
	public int getGraphHeight(){
		if (this.getWidth() == 0){
			return 400;
		}
		return this.getHeight() - getInfoBarHeight();
	}
	
	public int getInfoBarHeight(){
		return 5 + 2 * textHeight;
	}

	public int getHeightInfoBar() {
		return heightInfoBar;
	}
	
	public boolean selectClosestGraph(int x, int y){
		
		if (graph.getGraphs().size() > 0){
			Vector<Double> shortestDistToGraphs = new Vector<Double>();
			
			for (int i = 0; i < graph.getGraphs().size(); i++){
				shortestDistToGraphs.add(Double.MAX_VALUE);
			}
			SingleGraph sg;
			for (int i = 0 ; i < graph.getGraphs().size(); i++ )
			{//cedcle through all of the graphs on the screen
				sg = graph.getGraphs().get(i);
				shortestDistToGraphs.set(graph.getGraphs().indexOf(sg), Double.MAX_VALUE);
				Vector<Integer> xVals = sg.getxVals();
				Vector<Integer> yVals = sg.getyVals();
				if (sg instanceof GraphWithFunction)
				{//if the current graph is associated with a function
					double tempDist;
					double lastX = xVals.get(0), lastY = yVals.get(0);
					for (int j = 1; j < xVals.size(); j++)
					{//go through all of the points that were used to draw the graph
						//determine the shortest distance from the click to any of them
						double distBetweenLast2Pts = Math.sqrt(Math.pow(xVals.get(j) - lastX, 2) + 
								Math.pow(yVals.get(j) - lastY,2));
						int distanceThreshold = 3;
						if (distBetweenLast2Pts > distanceThreshold)
						{//the distance between this point and the last one checked is greater than 4
							//some points between the two must be checked
							double slope = (yVals.get(j) - lastY)/(xVals.get(j) - lastX);
							if (Math.abs(slope) > 50)
							{//this segment of the line is almost vertical, it doesn't need
								//all of its segments searched
								
								//the y value to be used to determine the distance from this point
								//if the y value from the click is above the entire segment
								//the maximun y is used, if it is below the lower point, the smaller y is used
								double yValOnSegment;
								double largerY, smallerY;
								if (lastY > yVals.get(j)){
									largerY = lastY;
									smallerY = yVals.get(j);
								}
								else{
									largerY = yVals.get(j);
									smallerY = lastY;
								}
								if (y < smallerY){
									yValOnSegment = smallerY;
								}
								else if (y > largerY){
									yValOnSegment = largerY;
								}
								else{
									yValOnSegment = y;
								}
								
								tempDist = Math.sqrt(Math.pow(xVals.get(j) - x, 2) 
										+ Math.pow(yValOnSegment - y, 2)/2);
								if (tempDist < shortestDistToGraphs.get(i)){
									shortestDistToGraphs.set(i, tempDist);
								}
								lastX = xVals.get(j);
								lastY = yVals.get(j);
								continue;
							}
							double xStep = Math.abs(distanceThreshold/slope);
							double currSubX = lastX;
							double currSubY;
							do{
								currSubX += xStep;
								currSubY = lastY + (currSubX - lastX) * slope;
								tempDist = Math.sqrt(Math.pow(currSubX - x,2) 
										+ Math.pow(currSubY - y, 2));
								if (tempDist < shortestDistToGraphs.get(i)){
									shortestDistToGraphs.set(i, tempDist);
								}
							} while (currSubX < xVals.get(j));
						}
						tempDist = Math.sqrt(Math.pow(xVals.get(j) - x,2) 
								+ Math.pow(yVals.get(j) - y, 2));
						if (tempDist < shortestDistToGraphs.get(i)){
							shortestDistToGraphs.set(i, tempDist);
						}
						lastX = xVals.get(j);
						lastY = yVals.get(j);
					}
				}
			}
			
			double shortestDist = Double.MAX_VALUE;
			int indexShortestDist = 0;
			for (SingleGraph s : graph.getGraphs()){
				s.setfocus(false);
			}
			for (int i = 0; i < shortestDistToGraphs.size(); i++){
				if (shortestDistToGraphs.get(i) < shortestDist){
					shortestDist = shortestDistToGraphs.get(i);
					indexShortestDist = i;
				}
			}
			if (shortestDist < 5){
				graph.getGraphs().get(indexShortestDist).setfocus(true);
				repaint();
				return true;
			}
			else{
				for (SingleGraph s : graph.getGraphs()){
					s.setfocus(false);
				}
			}
		}
		return false;
	}
	
}
