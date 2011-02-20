package imagegen;

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

	Vector<ValueGraphic> components;
	ValueGraphic root;
	
	int xSize, ySize;
	private Graphics2D graphics;
	private Font bigFont, smallFont;
	
	public enum FontSize{
		BIG_FONT, SMALL_FONT
	};
	
	//will be replaced by completeExpression later
	Value v;
	
	public CompleteExpressionGraphic(Value v){
		this.v = v;
		bigFont =  new Font("Lucida Sans Unicode", 0, 16);
		smallFont = new Font("Lucida Sans Unicode", 0, 10);
	}
	
	public void draw(){

		draw(root);
//		for (ValueGraphic vg : components){
//			graphics.setFont(vg.getFont());
//			vg.draw();
//		}
	}
	
	public void draw(ValueGraphic v){
		
		graphics.setFont(v.getFont());
		v.draw();
		Vector<ValueGraphic> temp = v.getComponents(); 
		for (ValueGraphic vg : temp){
			draw(vg);
		}
	}

	public void generateExpressionGraphic(Graphics2D g, int x1, int y1) throws Exception{
		graphics = g;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		components = new Vector<ValueGraphic>();
		int[] tempSize = {0, 0};
		ValueGraphic temp = new NothingGraphic(new Nothing(), this);
		temp = temp.makeValueGraphic(v);
		
		components.add(temp);
		tempSize = temp.requestSize(g, bigFont, x1, y1);
		root = temp;
		
		xSize = tempSize[0];
		ySize = tempSize[1];
	}
	
	public int[] requestSize(Value v, Graphics g, Font f){
		
		return null;
	}

	public Vector<ValueGraphic> getComponents(){
		return components;
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
			return graphics.getFontMetrics().getHeight() - 6;
		}
		else
		{
			throw new RenderException("unsupported Font");
		}
	}
	
	public void setGraphics(Graphics2D graphics) {
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
}
