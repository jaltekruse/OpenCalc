package imagegen;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.RenderingHints;

import tree.BinExpression;
import tree.Operator;
import tree.Value;
import tree.Var;
import tree.Constant;
import tree.Fraction;

public class BinExpressionGraphic extends ExpressionGraphic{

	static final int space = 4;
	
	public BinExpressionGraphic(BinExpression b, CompleteExpressionGraphic gr) {
		super(b, gr);
	}

	@Override
	public void draw() {
		
		if (isSelected()){
			super.getCompExGraphic().getGraphics().setColor(getSelectedColor());
			super.getCompExGraphic().getGraphics().fillRect(symbolX1, symbolY1 - 2,
					symbolX2 - symbolX1, symbolY2 - symbolY1 + 4);
			super.getCompExGraphic().getGraphics().setColor(Color.black);
		}
		
		super.getCompExGraphic().getGraphics().drawString(getValue().getOp().getSymbol(),
				symbolX1 + space, symbolY2);
	}

	@Override
	public int[] requestSize(Graphics g, Font f, int x1, int y1) throws Exception {
		// TODO Auto-generated method stub
		
		g.setFont(f);
		setFont(f);
		Value tempLeft = ((BinExpression)super.getValue()).getLeftChild();
		Value tempRight = ((BinExpression)super.getValue()).getRightChild();
		ValueGraphic leftValGraphic = null;
		ValueGraphic rightValGraphic = null; 
		int[] rightSize = {0,0};
		int[] leftSize = {0, 0};
		int[] symbolSize = {0, 0};
		int[] totalSize = {0, 0};
		
		leftValGraphic = makeValueGraphic(tempLeft);
		
		super.getCompExGraphic().getComponents().add(leftValGraphic);
		leftSize = leftValGraphic.requestSize(g, f, x1, y1);
		
		rightValGraphic = makeValueGraphic(tempRight);
		
		rightSize = rightValGraphic.requestSize(g, f, x1, y1);
		super.getCompExGraphic().getComponents().add(rightValGraphic);
		
		//set the west and east fields for inside an outside of the expression
//		setMostInnerWest(leftValGraphic.getMostInnerWest());
//		leftValGraphic.getMostInnerEast().setEast(this);
//		this.setWest(leftValGraphic.getMostInnerEast());
//		
//		setMostInnerEast(rightValGraphic.getMostInnerEast());
//		rightValGraphic.getMostInnerWest().setWest(this);
//		this.setEast(rightValGraphic.getMostInnerWest());
		
		symbolSize[0] = getCompExGraphic().getStringWidth(getValue().getOp().getSymbol(), f) + 2 * space;
		symbolSize[1] = getCompExGraphic().getFontHeight(f);
		rightValGraphic.shiftToX1(leftSize[0] + symbolSize[0] + x1);
		
		int height = 0;
		
		if (leftValGraphic.getUpperHeight() > rightValGraphic.getUpperHeight()){
			height = leftValGraphic.getUpperHeight();
			symbolY1 = leftValGraphic.getUpperHeight() + y1 - (int) (Math.round((symbolSize[1]/2.0)));
			symbolY2 = symbolY1 + symbolSize[1];
			setUpperHeight(leftValGraphic.getUpperHeight());
			rightValGraphic.shiftToY1(y1 - rightValGraphic.getUpperHeight() + leftValGraphic.getUpperHeight());
		}
		else
		{
			height = rightValGraphic.getUpperHeight();
			symbolY1 = rightValGraphic.getUpperHeight() + y1 - (int) (Math.round((symbolSize[1]/2.0)));
			symbolY2 = symbolY1 + symbolSize[1];
			setUpperHeight(rightValGraphic.getUpperHeight());
			leftValGraphic.shiftToY1(y1 - leftValGraphic.getUpperHeight() + rightValGraphic.getUpperHeight());
		}
		if (leftValGraphic.getLowerHeight() > rightValGraphic.getLowerHeight()){
			height += leftValGraphic.getLowerHeight();
			setLowerHeight(leftValGraphic.getLowerHeight());
		}
		else
		{
			height += rightValGraphic.getLowerHeight();
			setLowerHeight(rightValGraphic.getLowerHeight());
		}
		
		symbolX1 = x1 + leftSize[0];
		symbolX2 = x1 + leftSize[0] + symbolSize[0];
		
		//do not mess up the order of these adds!!!, look at method getLeftGraphic to find out why
		super.getComponents().setSize(2);
		super.getComponents().set(0, leftValGraphic);
		super.getComponents().set(1, rightValGraphic);
		
		totalSize[0] = symbolX2 + rightSize[0] - x1;
		totalSize[1] = height;
		super.setX1(x1);
		super.setY1(y1);
		super.setX2(x1 + totalSize[0]);
		super.setY2(y1 + totalSize[1]);
		return totalSize;
	}

	@Override
	public int[] requestSize(Graphics g, Font f) {
		return null;
	}
	
	public ValueGraphic getLeftGraphic(){
		return super.getComponents().get(0);
	}
	
	public ValueGraphic getRightGraphic(){
		return super.getComponents().get(1);
	}
}
