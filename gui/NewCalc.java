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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
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
public class NewCalc extends JApplet{

	private static final long serialVersionUID = 1L;
	private JScrollPane varScrollPane, constScrollPane;
	private OCTextField textWithFocus;
	private JPanel graph;
	private CalcPanel text;
	private ExpressionParser parser;
	private ValStoragePanel varPanel, constPanel;
	private JTabbedPane graphCalcDraw, mathFunc;
	private FunctionsPane graphFunctions;
	private GridPropsPanel gridProps;
	private Graph g;
	private KeyListener keys;
	private static int textWithFocusCaretPos;
	private static JFrame frame;
	private JMenuBar menuBar;
	private JMenu help;
	private NewCalc thisCalc;
	private JFrame tutorialFrame, licenseFrame;
	private JTextArea terminal;

	/**
	 * @throws ValueNotStoredException 
	 * @throws ParseException 
	 * @throws EvalException 
	 * 
	 */

	public NewCalc() throws ParseException, ValueNotStoredException, EvalException {

		thisCalc = this;
		parser = new ExpressionParser(this);
		textWithFocus = new OCTextField();
		super.setLayout(new GridBagLayout());
		menuBar = new JMenuBar();
		help = new JMenu("Help");
		menuBar.add(help);

		JMenuItem tutorial = new JMenuItem("Tutorial");
		tutorial.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(tutorialFrame == null){
					tutorialFrame = new JFrame("Tutorial");
					tutorialFrame.setPreferredSize(new Dimension(550, 400));
					terminal = new JTextArea(14, 20);

					Font terminalFont = new Font("newFont", 1, 12);
					terminal.setFont(terminalFont);
					terminal.setEditable(false);
					final JScrollPane termScrollPane = new JScrollPane(terminal);
					termScrollPane.setWheelScrollingEnabled(true);
					terminal.append(readTextDoc("README.txt"));

					tutorialFrame.add(termScrollPane);
					tutorialFrame.pack();

					tutorialFrame.setVisible(true);
					JScrollBar tempScroll = termScrollPane.getVerticalScrollBar();
					tempScroll.setValue(0);
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
					licenseFrame = new JFrame("License");
					licenseFrame.setPreferredSize(new Dimension(640, 400));
					JTextArea terminal = new JTextArea(14, 20);

					Font terminalFont = new Font("newFont", 1, 12);
					terminal.setFont(terminalFont);
					terminal.setEditable(false);
					JScrollPane termScrollPane = new JScrollPane(terminal);
					termScrollPane.setWheelScrollingEnabled(true);

					licenseFrame.add(termScrollPane);
					licenseFrame.pack();

					licenseFrame.setVisible(true);
					terminal.append(readTextDoc("COPYING.txt"));
					termScrollPane.revalidate();
					JScrollBar tempScroll = termScrollPane.getVerticalScrollBar();
					tempScroll.setValue(0);
				}
				else{
					licenseFrame.setVisible(true);
				}
			}
		});

		help.add(tutorial);
		help.add(license);

		this.setJMenuBar(menuBar);

		graph = new JPanel();

		graphCalcDraw = new JTabbedPane(JTabbedPane.TOP);

		NumsAndOppsPanel Nums = new NumsAndOppsPanel(this);

		mathFunc = new JTabbedPane();
		varPanel = new ValStoragePanel(this, parser.getVarList());
		parser.getVarList().setStorageGUI(varPanel);
		varScrollPane = new JScrollPane(varPanel);
		constPanel = new ValStoragePanel(this, parser.getConstantList());
		parser.getConstantList().setStorageGUI(constPanel);
		constScrollPane = new JScrollPane(constPanel);

		mathFunc.add(Nums, "Math");
		mathFunc.add(new TrigPanel(this), "Trig");
		mathFunc.add(varScrollPane, "Vars");
		mathFunc.add(constScrollPane, "Const");

		text = new CalcPanel(this);

		graphCalcDraw.add("Calculator", text);
		g = new Graph(360, 360, this);
		graphCalcDraw.add("Graph", g);

		//Graph3D g3d = new Graph3D(360,360, this);
		//graphCalcDraw.add("3DGraph", g3d);
		//treePan = new TreeCalcPanel(this);
		//graphCalcDraw.add("treeSolver", treePan);

		graphFunctions = new FunctionsPane(this);
		graphCalcDraw.add("Func", graphFunctions);

		gridProps = new GridPropsPanel(this);
		graphCalcDraw.add("Grid", gridProps);

		graphCalcDraw.add(new DrawPad(500, 500, this), "Draw");

		graphCalcDraw.addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				int selected = 	graphCalcDraw.getSelectedIndex();
				String nameSelected = graphCalcDraw.getTitleAt(selected);
				if (nameSelected.equals("Calculator")){
					try {
						setCurrTextField(text.getTextTerminal());
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
		});

		GridBagConstraints pCon = new GridBagConstraints();


		pCon.fill = GridBagConstraints.BOTH;
		pCon.insets = new Insets(0, 0, 6, 0);
		pCon.weightx = 1;
		pCon.weighty = 1;
		pCon.gridheight = 5;
		pCon.gridwidth = 3;
		pCon.weightx = 1;
		pCon.weighty = 1;
		pCon.gridx = 0;
		pCon.gridy = 0;
		this.add(graphCalcDraw, pCon);

		pCon.fill = GridBagConstraints.BOTH;
		pCon.insets = new Insets(6, 0, 0, 0);
		pCon.gridheight = 5;
		pCon.gridwidth = 3;
		pCon.weightx = 1;
		pCon.weighty = .1;
		pCon.gridx = 0;
		pCon.gridy = 5;
		this.add(mathFunc, pCon);

		graphCalcDraw.setSelectedIndex(0);
		this.repaint();
	}

	public int getFocusedComp() {
		int currPos = graphCalcDraw.getSelectedIndex();
		return currPos;
	}

	public ExpressionParser getParser(){
		return parser;
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

	public void setCurrTextField(OCTextField focused) throws ParseException, ValueNotStoredException, EvalException {
		if (!textWithFocus.equals(focused)) {
			textWithFocus.associatedAction();
			textWithFocus = focused;
			textWithFocus.requestFocus();
		}
	}

	public int getCurrCaretPos() {
		return textWithFocusCaretPos;
	}

	public void setCurrCaretPos(int pos) {
		textWithFocusCaretPos = pos;
	}

	public void addToCaretPos(int i) {
		textWithFocusCaretPos += i;
		updateCaretPos();
	}

	public void updateCaretPos() {
		textWithFocus.setCaretPosition(textWithFocusCaretPos);
	}

	public Graph getGraphObj() {
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
		Dimension frameDim = new Dimension(450, 600);
		frame.setPreferredSize(frameDim);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		NewCalc currCalc = new NewCalc();
		frame.add(currCalc);

		frame.pack();
		frame.setVisible(true);
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
