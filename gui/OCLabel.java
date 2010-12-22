package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class OCLabel extends SubPanel {
	
	String dispText;
	MainApplet mainApp;
	
	public OCLabel(String str, int gridwidth, int gridheight, int gridx,
			int gridy, double weightX, double weightY, JComponent comp, final MainApplet currmainApp) {
		dispText = str;
		mainApp = currmainApp;
		GridBagConstraints bCon = new GridBagConstraints();
		bCon.fill = GridBagConstraints.BOTH;
		bCon.weightx = .04;
		bCon.weighty = weightY;
		bCon.gridheight = gridheight;
		bCon.gridwidth = gridwidth;
		bCon.gridx = gridx;
		bCon.gridy = gridy;
		bCon.insets = new Insets(3, 6, 0, 0);
		this.repaint();
		comp.add(new JLabel(str), bCon);
	}
	
	public OCLabel(String str, int gridwidth, int gridheight, int gridx,
			int gridy, JComponent comp, final MainApplet currmainApp) {
		this(str, gridwidth, gridheight, gridx, gridy, 1, .2, comp, currmainApp);
	}
	
	public void paint(Graphics g){
		this.setPreferredSize(new Dimension(g.getFontMetrics().stringWidth(dispText), 20));
		int y = getSize().height;
		int x = getSize().width;
		g.setColor(Color.BLACK);
		g.drawString(dispText, 5, y/2);
	}
	
}
