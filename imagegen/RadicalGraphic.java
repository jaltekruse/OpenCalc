package imagegen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import tree.Expression;
import tree.Operator;
import tree.UrinaryExpression;
import tree.Value;

public class RadicalGraphic extends ExpressionGraphic {

	private int space;
	private int widthFront;
	private int heightLeadingTail;
	private int lengthLittleTail;
	
	public RadicalGraphic(UrinaryExpression v, CompleteExpressionGraphic compExGraphic) {
		super(v, compExGraphic);
		space = 4;
		widthFront = 8;
		heightLeadingTail = 8;
		lengthLittleTail = 3;
		if (v.getChild() instanceof UrinaryExpression){
			if (((UrinaryExpression)v.getChild()).getOp() == Operator.PAREN){
				v.setChild(((UrinaryExpression)v.getChild()).getChild());
			}
		}
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
//		super.getCompExGraphic().getGraphics().setColor(Color.gray);
//		super.getCompExGraphic().getGraphics().fillRect(symbolX1, symbolY1, symbolX2 - symbolX1, symbolY2 - symbolY1);
//		super.getCompExGraphic().getGraphics().setColor(Color.black);
		
		super.getCompExGraphic().getGraphics().drawLine(symbolX1, symbolY2 - heightLeadingTail + lengthLittleTail,
				symbolX1 + 3, symbolY2 - heightLeadingTail);
		super.getCompExGraphic().getGraphics().drawLine(symbolX1 + 3, symbolY2 - heightLeadingTail,
				symbolX1 + (int) Math.round(0.5 * widthFront), symbolY2);
		super.getCompExGraphic().getGraphics().drawLine(symbolX1 + (int) Math.round(0.5 * widthFront),
				symbolY2, symbolX1 + widthFront, symbolY1);
		super.getCompExGraphic().getGraphics().drawLine(symbolX1 + widthFront, symbolY1, 
				symbolX2, symbolY1);
		super.getCompExGraphic().getGraphics().drawLine(symbolX2, symbolY1, symbolX2, symbolY1 + 5);
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
		
		childSize = childValGraphic.requestSize(g, f, x1 + widthFront + space, y1 + space);
		widthFront += (int) Math.round(childSize[1]/14.0);
		
		if (widthFront > 20)
		{
			widthFront = 20;
		}
		heightLeadingTail += (int) Math.round(childSize[1]/5.0);
		
		if (heightLeadingTail > 40)
		{
			heightLeadingTail = 40;
		}
		childValGraphic.shiftToX1(x1 + widthFront + space);
		
		symbolSize[0] = childSize[0] + space * 2 + widthFront;
		symbolSize[1] = childSize[1] + space;
		
		symbolY1 = y1;
		symbolY2 = symbolY1 + symbolSize[1];
		symbolX1 = x1;
		symbolX2 = x1 + symbolSize[0];
		
		setUpperHeight(childValGraphic.getUpperHeight() + space);
		setLowerHeight(childValGraphic.getLowerHeight() );
		
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
