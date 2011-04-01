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



import gui.graph.GraphPanel;
import gui.graph.GraphWindow;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
public class MainApplet extends JApplet implements TopLevelContainer{

	private static final long serialVersionUID = 1L;
	private JScrollPane varScrollPane, constScrollPane;
	private static OCTextField textWithFocus;
	private static CalcPanel text;
	private ExpressionParser parser;
	private ValStoragePanel varPanel, constPanel;
	private JTabbedPane calcTabs, mathFunc, graphTabs;
	private FunctionsPane graphFunctions;
	private GridPropsPanel gridProps;
	private GraphOld g;
	private static int textWithFocusCaretPos;
	private static JFrame frame;
	private JMenuBar menuBar;
	private JMenuItem help;
	private OCFrame tutorialFrame, licenseFrame, calcFrame, drawFrame, graphFrame;
	static GlassPane glassPane;
	private Container contentPane;
	private GraphPanel graphPanel;
	private static RenderPanel render;
	
	/**
	 * @throws ValueNotStoredException 
	 * @throws ParseException 
	 * @throws EvalException 
	 * 
	 */

	public MainApplet() throws ParseException, ValueNotStoredException, EvalException {

		contentPane = this.getContentPane();
		contentPane.setLayout(new GridBagLayout());
		parser = new ExpressionParser(this);
		textWithFocus = new OCTextField(this);
		glassPane = new GlassPane(this, this);
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

		
		OCFrame calcFrame = new OCFrame(this, "Calculator");
		text = new CalcPanel(this, calcFrame);
		NumsAndOppsPanel Nums = new NumsAndOppsPanel(this, calcFrame);
		
		mathFunc = new JTabbedPane();
		varPanel = new ValStoragePanel(this, calcFrame, parser.getVarList());
		parser.getVarList().setStorageGUI(varPanel);
		varScrollPane = new JScrollPane(varPanel);
		constPanel = new ValStoragePanel(this, calcFrame, parser.getConstantList());
		parser.getConstantList().setStorageGUI(constPanel);
		constScrollPane = new JScrollPane(constPanel);

		mathFunc.add(Nums, "Math");
		mathFunc.add(varScrollPane, "Vars");
		mathFunc.add(constScrollPane, "Const");
		
		GridBagConstraints pCon = new GridBagConstraints();
		pCon.fill = GridBagConstraints.BOTH;
		pCon.insets = new Insets(2, 2, 2, 2);
		pCon.weightx = 1;
		pCon.weighty = 1;
		pCon.gridheight = 1;
		pCon.gridwidth = 1;
		pCon.weightx = 1;
		pCon.weighty = 1;
		pCon.gridx = 0;
		pCon.gridy = 0;
		calcFrame.setLayout(new GridBagLayout());
		calcFrame.add(text, pCon);
		calcFrame.setVisible(true);
		
		pCon.weighty = .1;
		pCon.gridy = 1;
		pCon.fill = GridBagConstraints.HORIZONTAL;
		calcFrame.add(mathFunc, pCon);
		calcFrame.setPreferredSize(new Dimension(460, 700));
		calcFrame.pack();

		g = new GraphOld(this, this, 360, 360);
		graphTabs.add("Graph", g);

		graphFunctions = new FunctionsPane(this, this);
		graphTabs.add("Func", graphFunctions);

		gridProps = new GridPropsPanel(this, this);
		graphTabs.add("Grid", gridProps);

		graphTabs.add(new DrawPad(this, this, 300, 300), "Draw");
		
		graphPanel = new GraphPanel(this, this, 200, 200);
		graphTabs.add(graphPanel, "newGraph");
		
		graphTabs.add(new Graph3DPanel(this, this, 200, 200), "3Dgraph");
		
		render = new RenderPanel(this, this);
		graphTabs.add(render,"render");
		
		graphTabs.setSelectedIndex(0);

		graphTabs.addChangeListener(graphTabsListener());

//		pCon.fill = GridBagConstraints.BOTH;
//		pCon.insets = new Insets(2, 2, 2, 2);
//		pCon.weightx = 1;
//		pCon.weighty = 1;
//		pCon.gridheight = 1;
//		pCon.gridwidth = 1;
//		pCon.weightx = .01;
//		pCon.weighty = 1;
//		pCon.gridx = 0;
//		pCon.gridy = 0;
//		contentPane.add(text, pCon);
		
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

//		pCon.fill = GridBagConstraints.HORIZONTAL;
//
//		pCon.insets = new Insets(2, 2, 2, 2);
//		pCon.gridheight = 1;
//		pCon.gridwidth = 1;
//		pCon.weightx = .01;
//		pCon.weighty = .1;
//		pCon.gridx = 0;
//		pCon.gridy = 1;
//		contentPane.add(mathFunc, pCon);
		
		this.repaint();
	}
	
	public void showCalc(){
		if (!calcFrame.isShowing()){
			calcFrame.setVisible(true);
		}
	}

	public void setGlassVisible(boolean b){
		glassPane.setVisible(b);
	}
	
	public Container getContentPane2(){
		return contentPane;
	}
	
	public GlassPane getGlassPanel(){
		return glassPane;
	}
	
	protected OCFrame makeTextViewer(String string) {
		OCFrame newFrame = new OCFrame(this, string);
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
					graphPanel.repaint();
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
			if (textWithFocus != text.getEntryLine())
			{//if the focus changes to a textfield other than the calculators entryline
				//perform the associatedAction of the last field
				textWithFocus.primaryAction();
			}
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
		Dimension frameDim = new Dimension(1000, 700);
		frame.setPreferredSize(frameDim);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setResizable(false);

		MainApplet currCalc = new MainApplet();
		frame.add(currCalc);
		
		frame.pack();
		frame.setVisible(true);
		
		setCurrTextField(text.getEntryLine());
		text.getEntryLine().getField().selectAll();
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
