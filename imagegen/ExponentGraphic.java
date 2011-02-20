package imagegen;

import imagegen.DivisionGraphic.Style;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import tree.BinExpression;
import tree.Fraction;
import tree.Operator;
import tree.Value;

public class ExponentGraphic extends BinExpressionGraphic {

	public static enum Style{
		CARET, SUPERSCRIPT
	}
	
	private int spaceBetweenBaseAndSuper;
	private Style style;

	public ExponentGraphic(BinExpression b, CompleteExpressionGraphic gr) {
		super(b, gr);
		style = Style.SUPERSCRIPT;
		spaceBetweenBaseAndSuper = 3;
		// TODO Auto-generated constructor stub
	}
	
	public void draw(){
		//no symbol to draw
	}

	public int[] requestSize(Graphics g, Font f, int x1, int y1) throws Exception {
		// TODO Auto-generated method stub

		g.setFont(f);
		((BinExpression)super.getValue()).getOp().getSymbol();
		Value tempLeft = ((BinExpression)super.getValue()).getLeftChild();
		Value tempRight = ((BinExpression)super.getValue()).getRightChild();
		ValueGraphic leftValGraphic = null;
		ValueGraphic rightValGraphic = null; 
		int[] rightSize = {0,0};
		int[] leftSize = {0, 0};
		int[] symbolSize = {0, 0};
		int[] totalSize = {0, 0};
		
		if (false){
//		if (style == Style.CARET)
//		{
//			BinExpressionGraphic ex = new BinExpressionGraphic(((BinExpression)getValue()), super.getCompExGraphic());
//			return ex.requestSize(g, f, x1, y1);
		}
		else if (style == Style.SUPERSCRIPT){

			leftValGraphic = makeValueGraphic(tempLeft);
			
			leftSize = leftValGraphic.requestSize(g, f, x1, y1);
			super.getCompExGraphic().components.add(leftValGraphic);
			
			rightValGraphic = makeValueGraphic(tempRight);
			
			rightSize = rightValGraphic.requestSize(g, getCompExGraphic().getSmallFont(), x1, y1);
			super.getCompExGraphic().components.add(rightValGraphic);
			
			super.getComponents().add(leftValGraphic);
			super.getComponents().add(rightValGraphic);
			
			rightValGraphic.shiftToX1(x1 + leftSize[0] + spaceBetweenBaseAndSuper);
			
			int shiftDownLeft = 0;
			shiftDownLeft = rightValGraphic.getUpperHeight();
			if (leftValGraphic instanceof ExponentGraphic)
			{
				if ( ((ExponentGraphic) leftValGraphic).getRightGraphic().getHeight() / 2.0 <
						rightValGraphic.getLowerHeight() )
				{
					shiftDownLeft = rightValGraphic.getHeight() - (int) 
							Math.round(((ExponentGraphic)leftValGraphic).getRightGraphic().getHeight()/2.0);
				}
			}
			else
			{
				if ( leftValGraphic.getHeight() / 2.0 < rightValGraphic.getLowerHeight() )
				{
					shiftDownLeft = rightValGraphic.getHeight() - (int) 
							Math.round(leftValGraphic.getHeight()/2.0);
				}
			}
			leftValGraphic.shiftToY1(y1 + shiftDownLeft);
			
			setUpperHeight( shiftDownLeft + leftValGraphic.getUpperHeight() );
			setLowerHeight( leftValGraphic.getLowerHeight() );
			totalSize[0] = leftSize[0] + rightSize[0] + spaceBetweenBaseAndSuper;
			totalSize[1] = leftSize[1] + shiftDownLeft;
			super.setX1(x1);
			super.setY1(y1);
			super.setX2(x1 + totalSize[0]);
			super.setY2(y1 + totalSize[1]);
			return totalSize;
		}
		
		return null;
	}
}
