package gui;

/*
 OpenCalc is a Graphing Calculator for the web.
 Copyright (C) 2009, 2010 Jason Altekruse

 This file is part of OpenCalc.

 OpenCalc is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 OpenCalc is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with OpenCalc  If not, see <http://www.gnu.org/licenses/>.
 */



import gui.graph.GraphWindow;

import imagegen.CompleteExpressionGraphic;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sun.org.apache.bcel.internal.generic.GETFIELD;

import tree.EvalException;
import tree.ExpressionParser;
import tree.ParseException;
import tree.Value;
import tree.ValueNotStoredException;

import java.io.*;
import java.net.*;


/**
 * A NewCalc object represents the entire user interface. It uses the standard
 * GridBag Layout manager. 
 * @author jason
 *
 */
public class MainApplet extends JApplet{

	private static final long serialVersionUID = 1L;
	private JScrollPane varScrollPane, constScrollPane;
	private static OCTextField textWithFocus;
	private JPanel graph;
	private static CalcPanel text;
	private ExpressionParser parser;
	private ValStoragePanel varPanel, constPanel;
	private JTabbedPane calcTabs, mathFunc, graphTabs;
	private FunctionsPane graphFunctions;
	private GridPropsPanel gridProps;
	private GraphOld g;
	private KeyListener keys;
	private static int textWithFocusCaretPos;
	private static JFrame frame;
	private JMenuBar menuBar;
	private JMenuItem help;
	private MainApplet thisCalc;
	private JFrame tutorialFrame, licenseFrame;
	private JTextArea terminal;
	static GlassPane glassPane;
	private Container contentPane;
	private GraphWindow graphWindow;
	private static RenderPanel render;
	
	/**
	 * @throws ValueNotStoredException 
	 * @throws ParseException 
	 * @throws EvalException 
	 * 
	 */

	public MainApplet() throws ParseException, ValueNotStoredException, EvalException {

		thisCalc = this;
		contentPane = this.getContentPane();
		contentPane.setLayout(new GridBagLayout());
		parser = new ExpressionParser(this);
		textWithFocus = new OCTextField();
		glassPane = new GlassPane(this);
		this.setGlassPane(glassPane);
		menuBar = new JMenuBar();
		help = new JMenu("Help");
		menuBar.add(help);

		JMenuItem tutorial = new JMenuItem("Tutorial");
		tutorial.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(tutorialFrame == null){
					tutorialFrame = makeTextViewer("README.txt");
				}
				else{
					tutorialFrame.setVisible(true);
				}
			}
		});

		JMenuItem license = new JMenuItem("License");
		license.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(licenseFrame == null){
					licenseFrame = makeTextViewer("COPYING.txt");
				}
				else{
					licenseFrame.setVisible(true);
				}
			}
		});

		help.add(tutorial);
		help.add(license);

		this.setJMenuBar(menuBar);
		
		graphTabs = new JTabbedPane(JTabbedPane.TOP);

		NumsAndOppsPanel Nums = new NumsAndOppsPanel(this);

		mathFunc = new JTabbedPane();
		varPanel = new ValStoragePanel(this, parser.getVarList());
		parser.getVarList().setStorageGUI(varPanel);
		varScrollPane = new JScrollPane(varPanel);
		constPanel = new ValStoragePanel(this, parser.getConstantList());
		parser.getConstantList().setStorageGUI(constPanel);
		constScrollPane = new JScrollPane(constPanel);

		mathFunc.add(Nums, "Math");
		mathFunc.add(varScrollPane, "Vars");
		mathFunc.add(constScrollPane, "Const");

		text = new CalcPanel(this);

		g = new GraphOld(360, 360, this);
		graphTabs.add("Graph", g);

		graphFunctions = new FunctionsPane(this);
		graphTabs.add("Func", graphFunctions);

		gridProps = new GridPropsPanel(this);
		graphTabs.add("Grid", gridProps);

		graphTabs.add(new DrawPad(500, 500, this), "Draw");
		
		graphWindow = new GraphWindow(200, 200, this);
		//graphTabs.add(graphWindow, "newGraph");
		
		//graphTabs.add(new Graph3DPanel(200, 200, this), "3Dgraph");
		
		render = new RenderPanel(this);
		//graphTabs.add(render,"render");
		
		graphTabs.setSelectedIndex(0);

		graphTabs.addChangeListener(graphTabsListener());
		
		GridBagConstraints pCon = new GridBagConstraints();

		pCon.fill = GridBagConstraints.BOTH;
		pCon.insets = new Insets(2, 2, 2, 2);
		pCon.weightx = 1;
		pCon.weighty = 1;
		pCon.gridheight = 1;
		pCon.gridwidth = 1;
		pCon.weightx = .01;
		pCon.weighty = 1;
		pCon.gridx = 0;
		pCon.gridy = 0;
		contentPane.add(text, pCon);
		
		pCon.fill = GridBagConstraints.BOTH;
		pCon.insets = new Insets(2, 2, 2, 2);
		pCon.weightx = 1;
		pCon.weighty = 1;
		pCon.gridheight = 2;
		pCon.gridwidth = 1;
		pCon.weightx = 1;
		pCon.weighty = 1;
		pCon.gridx = 1;
		pCon.gridy = 0;
		contentPane.add(graphTabs, pCon);

		pCon.fill = GridBagConstraints.HORIZONTAL;

		pCon.insets = new Insets(2, 2, 2, 2);
		pCon.gridheight = 1;
		pCon.gridwidth = 1;
		pCon.weightx = .01;
		pCon.weighty = .1;
		pCon.gridx = 0;
		pCon.gridy = 1;
		contentPane.add(mathFunc, pCon);
		
//		I was thinking that we should prevent the larger containers on the interface from
//		changing their size, right now the best example of why is when the "Inv" button(used to make the
//		trig buttons into their inverse operations) is pressed, the whole interface is resized,
//		including making the graph smaller
//		however the panel containing the number and operation buttons has enough space to
//		accommodate the larger buttons. I think it would make the most sense to enforce exact bounds
//		for any major panels. Inside those panels things will have to be automatically resized to make
//		all of the elements fit, and we will have to be smart about how much we allow to be in each panel
//		based on the absolute size constraint we gave it when adding it to the largest mainApplet panel.
		
//		JInternalFrame termFrame = new JInternalFrame();
//		termFrame.setBounds(0,10, 400, 250);
//		termFrame.setLayout(new FlowLayout());
//		termFrame.getContentPane().add(text);
//		
//		
//		JInternalFrame mathFrame = new JInternalFrame();
//		mathFrame.setBounds(0, 265, 400, 300);
//		termFrame.setLayout(new FlowLayout());
//		mathFrame.getContentPane().add(mathFunc);
//		
//		JInternalFrame graphFrame = new JInternalFrame();
//		graphFrame.setBounds(0,10, 400, 250);
//		termFrame.setLayout(new FlowLayout());
//		graphFrame.getContentPane().add(graphTabs);
//		
//		
//		contentPane.add(termFrame, 0);
//		contentPane.add(mathFrame, 0);
//		contentPane.add(graphFrame, 0);
		
//		Second attempt to make the major panels have exact sizes.
//		contentPane.add(text);package gui;

//		contentPane.add(mathFunc);
//		contentPane.add(graphTabs);
//		//contentPane.add(new JButton("a"));
//
//		text.setBounds(0,10, 400, 250);
//		
//		mathFunc.setBounds(0, 265, 400, 300);
//		
//		graphTabs.setBounds(401, 10, 400, 300);
		
		this.repaint();
	}

	public void setGlassVisible(boolean b){
		glassPane.setVisible(b);
	}
	
	public Container getContentPane2(){
		return contentPane;
	}
	
	public static GlassPane getGlassPanel(){
		return glassPane;
	}
	
	protected JFrame makeTextViewer(String string) {
		JFrame newFrame = new JFrame(string);
		newFrame.setPreferredSize(new Dimension(640, 400));
		JTextArea terminal = new JTextArea(14, 20);

		Font terminalFont = new Font("newFont", 1, 12);
		terminal.setFont(terminalFont);
		terminal.setEditable(false);
		JScrollPane termScrollPane = new JScrollPane(terminal);
		termScrollPane.setWheelScrollingEnabled(true);

		newFrame.add(termScrollPane);
		newFrame.pack();

		newFrame.setVisible(true);
		terminal.append(readTextDoc(string));
		termScrollPane.revalidate();
		JScrollBar tempScroll = termScrollPane.getVerticalScrollBar();
		tempScroll.setValue(0);
		newFrame.pack();
		newFrame.repaint();
		return newFrame;
	}

	private ChangeListener graphTabsListener() {
		return new ChangeListener(){

			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				int selected = 	graphTabs.getSelectedIndex();
				String nameSelected = graphTabs.getTitleAt(selected);
				if (nameSelected.equals("Graph"))
				{//hack to make the graph update when you hit the tab, this should be done better
					try {
						if (! getCurrTextField().equals(text.getEntryLine()))
						{
							getCurrTextField().primaryAction();
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ValueNotStoredException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (EvalException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (nameSelected.equals("newGraph")){
					graphWindow.repaint();
				}
				if (nameSelected.equals("render")){
					try {
						setCurrTextField(render.getEntryLine());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ValueNotStoredException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (EvalException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
	}

	public int getFocusedComp() {
		int currPos = calcTabs.getSelectedIndex();
		return currPos;
	}

	public ExpressionParser getParser(){
		return parser;
	}
	
	public JMenuBar getJMenuBar(){
		return menuBar;
	}

	/**
	 * This is not currently being used, but it is code to make an HTML request. So
	 * we could scan in a database of constants, functions or other stuff from our
	 * server.
	 * 
	 * @param args
	 * @return
	 */
	public String getWebPage(String[] args){

		String page= "";
		try {
			// Construct data
			String data = URLEncoder.encode("key1", "UTF-8") + "=" + URLEncoder.encode("value1", "UTF-8");
			data += "&" + URLEncoder.encode("key2", "UTF-8") + "=" + URLEncoder.encode("value2", "UTF-8");

			// Send data
			URL url = new URL("http://paigeinvaders.sourceforge.net/index.html");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				page += line;
			}
			wr.close();
			rd.close();
		} catch (Exception e) {

		}
		return page;
	}

	/**
	 * Reads a specified text document.
	 * 
	 * @param docName - the filename
	 * @return a String representation of the document
	 */
	public String readTextDoc(String docName){
		String line;
		try{
			InputStream in = getClass().getClassLoader().getResourceAsStream("txt/" + docName);
			BufferedReader bf = new BufferedReader(new InputStreamReader(in));
			StringBuffer strBuff = new StringBuffer();
			while((line = bf.readLine()) != null){
				strBuff.append(line + "\n");
			}
			getClass().getClassLoader().getResourceAsStream("/txt/" + docName);
			return strBuff.toString();
		}
		catch(IOException e){
			System.out.println("error");
			e.printStackTrace();
		}
		return null;
	}

	public void updateGraph() {
		g.repaint();
	}

	public void updateGraph(String func) {
		g.repaint();
	}

	public OCTextField getCurrTextField() {
		return textWithFocus;
	}

	public ValStoragePanel getVarsPanel(){
		return varPanel;
	}

	public static void setCurrTextField(OCTextField focused) throws ParseException, ValueNotStoredException, EvalException {
		if (!textWithFocus.equals(focused)) {
			textWithFocus.primaryAction();
			glassPane.setHistoryVisible(false);
			textWithFocus = focused;
		}
		textWithFocus.getField().requestFocus();
	}
	
	public CalcPanel getCalcPanel(){
		return text;
	}

	public void updateCaretPos() {
		textWithFocus.getField().setCaretPosition(textWithFocusCaretPos);
	}

	public GraphOld getGraphObj() {
		return g;
	}

	public GridPropsPanel getGridProps(){
		return gridProps;
	}

	public Value evalCalc(String eqtn) throws EvalException, ParseException {
		return parser.ParseExpression(eqtn).eval();
	}

	private static void createAndShowGUI() throws ParseException, ValueNotStoredException, EvalException {

		frame = new JFrame("OpenCalc");
		Dimension frameDim = new Dimension(950, 700);
		frame.setPreferredSize(frameDim);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		MainApplet currCalc = new MainApplet();
		frame.add(currCalc);
		
		frame.pack();
		frame.setVisible(true);
		
//		setCurrTextField(text.getEntryLine());
//		text.getEntryLine().getField().selectAll();
		setCurrTextField(render.getEntryLine());
	}

	public static void main(String[] args) {
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					createAndShowGUI();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ValueNotStoredException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (EvalException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}
