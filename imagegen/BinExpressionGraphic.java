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

public class BinExpressionGraphic extends ValueGraphic<BinExpression> {

	int symbolX1, symbolX2, symbolY1, symbolY2;
	static final int space = 4;
	
	public BinExpressionGraphic(BinExpression b, CompleteExpressionGraphic gr) {
		super(b, gr);
		// TODO Auto-generated constructor stub
	}

	@Override
	
	public void draw() {
		// TODO Auto-generated method stub
//		super.getCompExGraphic().getGraphics().setColor(Color.gray);
//		super.getCompExGraphic().getGraphics().fillRect(symbolX1, symbolY1, symbolX2 - symbolX1, symbolY2 - symbolY1);
//		super.getCompExGraphic().getGraphics().setColor(Color.black);
		
		//super.getCompExGraphic().getGraphics().setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		super.getCompExGraphic().getGraphics().drawString(getValue().getOp().getSymbol(),
				symbolX1 + space, symbolY2);
	}

	@Override
	public int[] requestSize(Graphics g, Font f, int x1, int y1) throws Exception {
		// TODO Auto-generated method stub
		
		g.setFont(f);
		setFont(f);
		FontMetrics fm = g.getFontMetrics();
		System.out.println(fm.getStringBounds("+", g).getHeight());
		((BinExpression)super.getValue()).getOp().getSymbol();
		Value tempLeft = ((BinExpression)super.getValue()).getLeftChild();
		Value tempRight = ((BinExpression)super.getValue()).getRightChild();
		ValueGraphic leftValGraphic = null;
		ValueGraphic rightValGraphic = null; 
		int[] rightSize = {0,0};
		int[] leftSize = {0, 0};
		int[] symbolSize = {0, 0};
		int[] totalSize = {0, 0};
		
		leftValGraphic = makeValueGraphic(tempLeft);
		
		super.getCompExGraphic().components.add(leftValGraphic);
		leftSize = leftValGraphic.requestSize(g, f, x1, y1);
		//other if statements for checking the left, decimal, imaginary, other val types
		
		rightValGraphic = makeValueGraphic(tempRight);
		
		rightSize = rightValGraphic.requestSize(g, f, x1, y1);
		super.getCompExGraphic().components.add(rightValGraphic);
		
		System.out.println("leftSize: " + leftSize[0]);
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
		
		super.getComponents().add(leftValGraphic);
		super.getComponents().add(rightValGraphic);
		
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
	
	void shiftToX1(int x1) {
		int xChange = x1 - getX1();
		for (ValueGraphic vg : getComponents()){
			vg.shiftToX1(vg.getX1() + xChange);
		}
		setX2(getX2() + xChange);
		symbolX1 += xChange;
		symbolX2 += xChange;
		setX1(x1);
	}

	void shiftToY1(int y1) {
		int yChange = y1 - getY1();
		for (ValueGraphic vg : getComponents()){
			vg.shiftToY1(vg.getY1() + yChange);
		}
		setY2(getY2() + yChange);
		symbolY1 += yChange;
		symbolY2 += yChange;
		setY1(y1);
	}
}
