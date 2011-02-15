package gui;

/**
 *
 * @author Brendan DuBois
 */

import tree.*;

import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Graph3DPanel extends SubPanel
{
    private Graph3D2 graph;
    private SubPanel panel3d;
    private ExpressionParser parser;
    public VarStorage varList;
    private OCTextField field;
    private Graph3DPanel thisPanel;
    private MainApplet mainApp;

    public Graph3DPanel(int xSize, int ySize, MainApplet mApp)
    {
        super();
      	this.mainApp = mApp;
        thisPanel = this;
        
        graph = new Graph3D2(thisPanel);
        
        panel3d = new SubPanel(){
            public void paint(Graphics g)
            {
                if(this != null && mainApp != null)
                {
                	graph.render(g);
                }
            }
        };
        GridBagConstraints bCon  = new GridBagConstraints();
        
        panel3d.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent k) {
				// TODO Auto-generated method stub
	            System.out.println("MOO!");
	            int key = k.getKeyCode();

	            boolean r = true;
	            switch(key)
	            {
	                case KeyEvent.VK_1: graph.viewAngles[0] += .2; break;
	                case KeyEvent.VK_2: graph.viewAngles[1] += .2; break;
	                case KeyEvent.VK_3: graph.viewAngles[2] += .2; break;
	                case KeyEvent.VK_4: graph.viewAngles[0] -= .2; break;
	                case KeyEvent.VK_5: graph.viewAngles[1] -= .2; break;
	                case KeyEvent.VK_6: graph.viewAngles[2] -= .2; break;
	                default: r = false;
	            }
	            if(r)
	                panel3d.repaint();
			}
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
        panel3d.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				panel3d.requestFocus();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
		bCon.fill = GridBagConstraints.BOTH;
		bCon.weightx = 1;
		bCon.weighty = 1;
		bCon.gridheight = 7;
		bCon.gridwidth = 1;
		bCon.gridx = 0;
		bCon.gridy = 0;
		this.add(panel3d, bCon);
		
		field = new OCTextField(true, 20, 1,1,0,8,this, mainApp){
			public void associatedAction(){
		        try
		        {
		            Expression[] expressions = new Expression[1];
		            parser = new ExpressionParser();
		            String expression = field.getText();
		            expressions[0] = (Expression)parser.ParseExpression(expression);
		            varList = parser.getVarList();
		            graph = new Graph3D2(thisPanel, expressions);
		            panel3d.repaint();
		        }
		        catch(ParseException e)
		        {
		            graph = new Graph3D2(thisPanel);
		        }
			}
		};


        setPreferredSize(new Dimension(xSize, ySize));
    }
    
}