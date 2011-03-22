package imagegen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Vector;

import tree.BinExpression;
import tree.Expression;
import tree.Fraction;
import tree.Decimal;
import tree.Nothing;
import tree.Operator;
import tree.Value;

public class CompleteExpressionGraphic{

	ValueGraphic root;
	
	int xSize, ySize;
	private Graphics2D graphics;
	private Font bigFont, smallFont;
	private Vector<ValueGraphic> selectedVals;
	private Vector<ValueGraphic> components;
	private ValueGraphic firstSel;
	
	public enum FontSize{
		BIG_FONT, SMALL_FONT
	};
	
	//will be replaced by completeExpression later
	Value v;
	
	public CompleteExpressionGraphic(Value v){
		this.v = v;
		bigFont =  new Font("SansSerif", 0, 16);
		smallFont = new Font("SansSerif", 0, 10);
	}
	
	public void draw(){

		draw(root);
//		for (ValueGraphic vg : components){
//			graphics.setFont(vg.getFont());
//			vg.draw();
//		}
	}
	
	public void draw(ValueGraphic v){
		
		graphics.setColor(Color.black);
		graphics.setFont(v.getFont());
		graphics.setColor(Color.white);
		graphics.fillRect(v.getX1(), v.getY1(), v.getWidth(), v.getHeight());
		graphics.setColor(Color.black);
		v.draw();
		Vector<ValueGraphic> temp = v.getComponents(); 
		for (ValueGraphic vg : temp){
			draw(vg);
		}
	}

	public void generateExpressionGraphic(Graphics2D g, int x1, int y1) throws Exception{
		
		System.out.println(v.toString());
		graphics = g;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int[] tempSize = {0, 0};
		components = new Vector<ValueGraphic>();
		selectedVals = new Vector<ValueGraphic>();
		ValueGraphic temp = new NothingGraphic(new Nothing(), this);
		temp = temp.makeValueGraphic(v);
		
		components.add(temp);
		tempSize = temp.requestSize(g, bigFont, x1, y1);
		root = temp;
		
		//temp.getMostInnerWest().setSelected(true);
		//selectedVals.add(temp.getMostInnerWest());
		//firstSel = selectedVals.get(0);
		xSize = tempSize[0];
		ySize = tempSize[1];
	}
	
	public int[] requestSize(Value v, Graphics g, Font f){
		
		return null;
	}
	
	public int getStringWidth(String s, Font f){
		graphics.setFont(f);
		return graphics.getFontMetrics().stringWidth(s);
	}
	
	public int getFontHeight(Font f) throws RenderException{
		graphics.setFont(f);
		if (f.equals(bigFont)){
			return graphics.getFontMetrics().getHeight() - 7;
		}
		else if (f.equals(smallFont)){
			return graphics.getFontMetrics().getHeight() - 5;
		}
		else
		{
			throw new RenderException("unsupported Font");
		}
	}
	
	public void setGraphics(Graphics2D graphics) {
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.graphics = graphics;
	}

	public Graphics2D getGraphics() {
		return graphics;
	}
	
	public Font getBigFont(){
		return bigFont;
	}
	
	public Font getSmallFont(){
		return smallFont;
	}

	public void setSelectedVals(Vector<ValueGraphic> selectedVals) {
		this.selectedVals = selectedVals;
	}

	public Vector<ValueGraphic> getSelectedVals() {
		return selectedVals;
	}

	public void setComponents(Vector<ValueGraphic> components) {
		this.components = components;
	}

	public Vector<ValueGraphic> getComponents() {
		return components;
	}
}
