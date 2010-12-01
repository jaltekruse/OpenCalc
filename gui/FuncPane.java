package gui;

import java.awt.Color;

import javax.swing.JTabbedPane;

public class FuncPane extends JTabbedPane {

	private NewCalc calcObj;
	private Function func;
	private Color color;
	private GraphAttributesPanel funcInfo;
	private FuncCalcPanel calcs;
	
	
	public FuncPane(NewCalc currCalcObj, FunctionsPane fp, Color c, Function f){
		calcObj = currCalcObj;
		color = c;
		func = f;
		
		calcs = new FuncCalcPanel(calcObj, func, c);
		funcInfo = new GraphAttributesPanel(calcObj, fp, color, func);
		this.add("function", funcInfo);
		this.add("calc", calcs);
	}
}
