package imagegen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import tree.UrinaryExpression;
import tree.Value;

public class ParenGraphic extends UnaryExpressionGraphic {

	private int space;
	private int overhang;
	private int widthParens;
	
	public ParenGraphic(UrinaryExpression v, CompleteExpressionGraphic compExGraphic) {
		super(v, compExGraphic);
		space = 0;
		overhang = 3;
		widthParens = 8;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
		super.getCompExGraphic().getGraphics().drawArc(getX1(), getY1(), widthParens,
				getY2() - getY1(), 90, 180);
		super.getCompExGraphic().getGraphics().drawArc(getX2() - widthParens, getY1(), widthParens,
				getY2() - getY1(), 270, 180);
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
		
		childValGraphic = makeValueGraphic(tempChild);
		
		super.getComponents().add(childValGraphic);
		super.getCompExGraphic().components.add(childValGraphic);
		
		childSize = childValGraphic.requestSize(g, f, x1 + widthParens + space, y1 + overhang);
		
		symbolSize[0] = childSize[0] + space * 2 + widthParens * 2;
		symbolSize[1] = childSize[1] + overhang * 2;
		
		symbolY1 = y1;
		symbolY2 = symbolY1 + symbolSize[1];
		symbolX1 = x1;
		symbolX2 = x1 + symbolSize[0];
		
		setUpperHeight(childValGraphic.getUpperHeight() + overhang);
		setLowerHeight(childValGraphic.getLowerHeight() + overhang);
		
		totalSize[0] = symbolSize[0];
		totalSize[1] = symbolSize[1];
		super.setX1(x1);
		super.setY1(y1);
		super.setX2(x1 + totalSize[0]);
		super.setY2(y1 + totalSize[1]);
		// TODO Auto-generated method stub
		return totalSize;
	}

}
