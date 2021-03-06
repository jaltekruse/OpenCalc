package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;

import javax.swing.JComponent;

public class OCLabel extends SubPanel {
	
	String dispText;
	NewCalc calcObj;
	
	public OCLabel(String str, int gridwidth, int gridheight, int gridx,
			int gridy, double weightX, double weightY, JComponent comp, final NewCalc currCalcObj) {
		dispText = str;
		calcObj = currCalcObj;
		this.setPreferredSize(new Dimension(9 * dispText.length(), 20));
		GridBagConstraints bCon = new GridBagConstraints();
		bCon.fill = GridBagConstraints.BOTH;
		bCon.weightx = weightX;
		bCon.weighty = weightY;
		bCon.gridheight = gridheight;
		bCon.gridwidth = gridwidth;
		bCon.gridx = gridx;
		bCon.gridy = gridy;
		this.repaint();
		comp.add(this, bCon);
	}
	
	public OCLabel(String str, int gridwidth, int gridheight, int gridx,
			int gridy, JComponent comp, final NewCalc currCalcObj) {
		this(str, gridwidth, gridheight, gridx, gridy, 1, 1, comp, currCalcObj);
	}
	
	public void paint(Graphics g){
		int y = getSize().height;
		int x = getSize().width;
		g.setColor(Color.BLACK);
		g.drawString(dispText, 5, y/2);
	}
	
}
