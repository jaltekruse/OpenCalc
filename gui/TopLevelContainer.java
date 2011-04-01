package gui;

import java.awt.Container;
import java.awt.Point;

import javax.swing.JMenuBar;

public interface TopLevelContainer {

	public GlassPane getGlassPanel();
	
	public Point getLocationOnScreen();
	
	public Container getContentPane();
	
	public JMenuBar getJMenuBar();
	
	public int getHeight();
	
	public int getWidth();
}
