package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class GlassPane extends JComponent{

	MainApplet mainApp;
	GlassPane thisPane;
	int width, height, x, y, fontHeight, indexSelected;
	ArrayList<String> history;
	JTextArea historyPane;

	public GlassPane(final MainApplet mainApp){
		this.mainApp = mainApp;
		thisPane = this;
		this.setVisible(true);
	}

	public void moveIndex(int i){
		indexSelected += i;
		if (indexSelected < 0){
			indexSelected = 0;
		}
		if (indexSelected > history.size() - 1){
			indexSelected = history.size() - 1;
		}
		refresh();
	}
	
	public void refresh(){
		if (!mainApp.getCurrTextField().isShowing()){
			historyPane.setVisible(false);
			return;
		}
		if (!history.isEmpty()){
			int totalChars = 0;
			Scanner s = new Scanner(historyPane.getText());
			for (int i = 0; i < indexSelected; i++){
				totalChars += s.nextLine().length() + 1;
			}
			historyPane.requestFocus();
			historyPane.setSelectionStart(totalChars);
			int lineLength =  s.nextLine().length();
			historyPane.setSelectionEnd(totalChars + lineLength);
			this.setVisible(true);
			this.repaint();
		}
	}

	public void addFieldHistory(){
		if (!mainApp.getCurrTextField().isShowing()){
			historyPane.setVisible(false);
			return;
		}
		this.removeAll();
		historyPane = new JTextArea(5,5);
		indexSelected = 0;
		history = mainApp.getCurrTextField().getHistory();
		historyPane.setEditable(true);
		historyPane.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					mainApp.getCurrTextField().getField().setText(historyPane.getSelectedText());
					mainApp.getCurrTextField().getField().requestFocus();
					setHistoryVisible(false);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_UP){
					if (indexSelected == 0){
						mainApp.getCurrTextField().getField().requestFocus();
						historyPane.setVisible(false);
						return;
					}
					moveIndex(-1);
					refresh();
				}
				else if (e.getKeyCode() == KeyEvent.VK_DOWN){
					moveIndex(1);
					refresh();
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					;
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}
			
		});
		
		historyPane.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				//historyPane.setVisible(false);
				//the above line doesn't work, need to find some way to
				//get rid of history when the interface changes and the
				//current field isn't being displayed
			}
			
		});
		
		height = history.size() * 17;
		for (int i = history.size() - 1; i >= 0; i--){
			historyPane.append(history.get(i) + "\n");
		}
		
		x = mainApp.getCurrTextField().getField().getLocationOnScreen().x
			- mainApp.getLocationOnScreen().x;
		y = mainApp.getCurrTextField().getField().getLocationOnScreen().y
			- mainApp.getLocationOnScreen().y 
			+ mainApp.getCurrTextField().getField().getHeight();
		width = 200;

		historyPane.setBorder(new LineBorder(Color.BLACK, 1));
		historyPane.setBounds(x, y, width, height);
		historyPane.setOpaque(true);
		
		this.add(historyPane);
		historyPane.setVisible(true);
		refresh();
	}

	public void setHistoryVisible(boolean b){
		if (historyPane != null)
		{
			historyPane.setVisible(b);
		}
	}
	
	public boolean historyIsVisible(){
		if (historyPane != null){
			return historyPane.isVisible();
		}
		return false;
	}
}
