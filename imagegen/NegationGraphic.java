package imagegen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import tree.UrinaryExpression;
import tree.Value;

public class NegationGraphic extends UnaryExpressionGraphic {

private int space;
	
	public NegationGraphic(UrinaryExpression v,
			CompleteExpressionGraphic compExGraphic) {
		super(v, compExGraphic);
		space = 2;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
//		super.getCompExGraphic().getGraphics().setColor(Color.gray);
//		super.getCompExGraphic().getGraphics().fillRect(symbolX1, symbolY1, symbolX2 - symbolX1, symbolY2 - symbolY1);
//		super.getCompExGraphic().getGraphics().setColor(Color.black);
		
		super.getCompExGraphic().getGraphics().drawLine(symbolX1 + 
				(int) Math.round(((symbolX2 - symbolX1) - space) * .1),
				symbolY1 + (int) Math.round((symbolY2 - symbolY1) * .4),
				symbolX2 - space, 
				symbolY1 + (int) Math.round((symbolY2 - symbolY1) * .4));
	}

	@Override
	public int[] requestSize(Graphics g, Font f) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] requestSize(Graphics g, Font f, int x1, int y1)
			throws Exception {
		g.setFont(f);
		setFont(f);
		Value tempChild = ((UrinaryExpression)super.getValue()).getChild();
		ValueGraphic childValGraphic = null;
		int[] childSize = {0,0};
		int[] symbolSize = {0, 0};
		int[] totalSize = {0, 0};
		
		
		symbolSize[0] = (int)Math.round(super.getCompExGraphic().getStringWidth("-", f) * .3) + space;
		symbolSize[1] = super.getCompExGraphic().getFontHeight(f);
		childValGraphic = makeValueGraphic(tempChild);
		
		super.getComponents().add(childValGraphic);
		super.getCompExGraphic().getComponents().add(childValGraphic);
		
		childSize = childValGraphic.requestSize(g, f, x1 + symbolSize[0], y1);
		
		symbolY1 = y1 + childValGraphic.getUpperHeight() - (int) Math.round(symbolSize[1]/2.0);
		symbolY2 = symbolY1 + symbolSize[1];
		symbolX1 = x1;
		symbolX2 = x1 + symbolSize[0];
		
		setUpperHeight(childValGraphic.getUpperHeight());
		setLowerHeight(childValGraphic.getLowerHeight());
		
		totalSize[0] = symbolSize[0] + childSize[0];
		totalSize[1] = childSize[1];
		super.setX1(x1);
		super.setY1(y1);
		super.setX2(x1 + totalSize[0]);
		super.setY2(y1 + totalSize[1]);
		// TODO Auto-generated method stub
		return totalSize;
	}

}
