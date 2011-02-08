package imagegen;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import tree.BinExpression;
import tree.Fraction;
import tree.Operator;
import tree.Value;

public class DivisionGraphic extends BinExpressionGraphic {

	public enum Style{
		SLASH, DIAGONAL, HORIZONTAL
	}
	
	private Style style;
	
	public DivisionGraphic(BinExpression b, CompleteExpressionGraphic gr) {
		super(b, gr);
		style = Style.HORIZONTAL;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		if (style == Style.SLASH)
		{
			super.getCompExGraphic().getGraphics().drawString(getValue().getOp().getSymbol(),
					symbolX1, symbolY2);
		}
		else if (style == Style.HORIZONTAL){
//			super.getCompExGraphic().getGraphics().setColor(Color.gray);
//			super.getCompExGraphic().getGraphics().fillRect(symbolX1, symbolY1, symbolX2 - symbolX1, symbolY2 - symbolY1);
//			super.getCompExGraphic().getGraphics().setColor(Color.black);
			super.getCompExGraphic().getGraphics().drawLine(symbolX1, symbolY1 + 3, symbolX2, symbolY1 + 3);
		}
	}
	
	@Override
	public int[] requestSize(Graphics g, Font f, int x1, int y1) throws Exception {
		// TODO Auto-generated method stub
		
		g.setFont(f);
		FontMetrics fm = g.getFontMetrics();
		((BinExpression)super.getValue()).getOp().getSymbol();
		Value tempLeft = ((BinExpression)super.getValue()).getLeftChild();
		Value tempRight = ((BinExpression)super.getValue()).getRightChild();
		ValueGraphic leftValGraphic = null;
		ValueGraphic rightValGraphic = null; 
		int[] rightSize = {0,0};
		int[] leftSize = {0, 0};
		int[] symbolSize = {0, 0};
		int[] totalSize = {0, 0};
		
		if (style == Style.SLASH)
		{
			
			if (tempLeft instanceof Fraction)
			{
				
				Fraction leftFrac = ((Fraction)tempLeft);
				leftValGraphic = new FractionGraphic(leftFrac, getCompExGraphic());
			}
			
			else if (tempLeft instanceof BinExpression)
			{
				
				BinExpression leftBinEx = ((BinExpression)tempLeft);
				leftValGraphic = new BinExpressionGraphic(leftBinEx, getCompExGraphic());
				
			}
			
			super.getCompExGraphic().components.add(leftValGraphic);
			leftSize = leftValGraphic.requestSize(g, f, x1, y1);
			symbolSize[0] = fm.stringWidth(getValue().getOp().getSymbol());
			symbolSize[1] = fm.getHeight() - 9;
			symbolX1 = x1 + leftSize[0];
			System.out.println(leftSize[1]/2.0);
			symbolY1 = y1 + ((int)Math.round(leftSize[1]/2.0) - (int) (Math.round(symbolSize[1])/2.0));
			symbolX2 = x1 + leftSize[0] + symbolSize[0];
			symbolY2 = symbolSize[1] + symbolY1;
			//other if statements for checking the left, decimal, imaginary, other val types
			
			if (tempRight instanceof Fraction){
				
				Fraction rightFrac = ((Fraction)tempRight);
				rightValGraphic = new FractionGraphic(rightFrac, getCompExGraphic());
				rightSize = rightValGraphic.requestSize(g, f, symbolX2, y1);
				super.getCompExGraphic().components.add(rightValGraphic);
				
			}
			
			else if (tempRight instanceof BinExpression){
				
				BinExpression rightBinEx = ((BinExpression)tempRight);
				rightValGraphic = new BinExpressionGraphic(rightBinEx, getCompExGraphic());
				rightSize = rightValGraphic.requestSize(g, f, symbolX2, y1);
				
				super.getCompExGraphic().components.add(rightValGraphic);
	
				
			}
			
			super.getComponents().add(leftValGraphic);
			super.getComponents().add(rightValGraphic);
			
			totalSize[0] = symbolX2 + rightSize[0] - x1;
			totalSize[1] = symbolY2 - y1;
			super.setX1(x1);
			super.setY1(y1);
			super.setX2(x1 + totalSize[0]);
			super.setY2(y1 + totalSize[1]);
			return totalSize;
		}
		else if (style == Style.HORIZONTAL){
			if (tempLeft instanceof Fraction)
			{
				
				Fraction leftFrac = ((Fraction)tempLeft);
				leftValGraphic = new FractionGraphic(leftFrac, getCompExGraphic());
			}
			
			else if (tempLeft instanceof BinExpression)
			{
				
				if (((BinExpression)tempLeft).getOp() == Operator.DIVIDE){
					leftValGraphic = new DivisionGraphic((BinExpression)tempLeft, super.getCompExGraphic());
				}
				else{
					leftValGraphic = new BinExpressionGraphic((BinExpression)tempLeft, super.getCompExGraphic());
				}
				
			}
			
			leftSize = leftValGraphic.requestSize(g, f, x1, y1);
			super.getCompExGraphic().components.add(leftValGraphic);
			
			//other if statements for checking the left, decimal, imaginary, other val types
			
			if (tempRight instanceof Fraction){
				
				Fraction rightFrac = ((Fraction)tempRight);
				rightValGraphic = new FractionGraphic(rightFrac, getCompExGraphic());
				
			}
			
			else if (tempRight instanceof BinExpression){
				
				if (((BinExpression)tempRight).getOp() == Operator.DIVIDE){
					rightValGraphic = new DivisionGraphic((BinExpression)tempRight, super.getCompExGraphic());
				}
				else{
					rightValGraphic = new BinExpressionGraphic((BinExpression)tempRight, super.getCompExGraphic());
				}
				
			}
			
			
			rightSize = rightValGraphic.requestSize(g, f, symbolX2, y1);
			super.getCompExGraphic().components.add(rightValGraphic);
			
			super.getComponents().add(leftValGraphic);
			super.getComponents().add(rightValGraphic);
			
			symbolX1 = x1;
			
			if (leftSize[0] > rightSize[0]){
				symbolX2 = x1 + leftSize[0] + 8;
			}
			else
			{
				symbolX2 = x1 + rightSize[0] + 8;
			}
			
			leftValGraphic.shiftToX1((int)(Math.round(((symbolX2 - symbolX1) - leftSize[0]))/2.0) + x1);
			leftValGraphic.shiftToX2(leftValGraphic.getX1() + leftSize[0]);
			
			symbolY1 = leftSize[1] + y1;
			symbolY2 = symbolY1 + 7;
			
			rightValGraphic.shiftToX1((int)(Math.round(((symbolX2 - symbolX1) - rightSize[0]))/2.0) + x1);
			rightValGraphic.shiftToX2(rightValGraphic.getX1() + rightSize[0]);
			rightValGraphic.shiftToY1(symbolY2 + 1);
			rightValGraphic.shiftToY2(rightValGraphic.getY1() + rightSize[1]);
			
			totalSize[0] = symbolX2 - x1;
			totalSize[1] = symbolY2 + rightSize[1]- y1;
			super.setX1(x1);
			super.setY1(y1);
			super.setX2(x1 + totalSize[0]);
			super.setY2(y1 + totalSize[1]);
			return totalSize;
		}
		
		return null;
	}

}
