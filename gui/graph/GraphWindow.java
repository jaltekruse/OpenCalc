package gui.graph;

import java.awt.Color;
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

import tree.EvalException;
import tree.ParseException;
import tree.ValueNotStoredException;
import tree.VarStorage;

import gui.MainApplet;
import gui.SubPanel;

public class GraphWindow extends SubPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MainApplet mainApp;
	private int textHeight;
	private Graph largeGraph;
	private SubPanel graph;
	private BufferedImage buffer;
	private double mouseX,
	mouseY, mouseRefX, mouseRefY;
	boolean refPoint, justFinishedPic, isTimeToRedraw;
	private int heightInfoBar = 40;
	private Runnable current;
	private Object currObj;
	
	public GraphWindow(int xSize, int ySize, MainApplet mainApp){
		this.setPreferredSize(new Dimension(xSize, ySize));
		this.mainApp = mainApp;
		largeGraph = new Graph(this, mainApp);
		buffer = new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR);
		repaint();
		
		graph = new SubPanel(){
			
			public void paint(Graphics g) {
				largeGraph.repaint(g);
			}
		};
		
		graph.addMouseListener(new MouseListener(){
			
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
				mouseX = e.getX();
				mouseY = e.getY();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				repaint();
			}
			
		});
		
		graph.addMouseMotionListener(new MouseMotionListener(){
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
				largeGraph.shiftGraph(mouseX - e.getX(), e.getY() - mouseY);
				mouseX = e.getX();
				mouseY = e.getY();
				repaint();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				mouseRefX = e.getX();
				mouseRefY= e.getY();
				//repaintAxis();
			}
			
		});
		
		this.addMouseWheelListener(new MouseWheelListener(){

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				// TODO Auto-generated method stu
				try {
					largeGraph.zoom(100 - e.getWheelRotation() * 5);
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
		this.add(graph, bCon);
		this.setPreferredSize(new Dimension(xSize, ySize));
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
	
}
