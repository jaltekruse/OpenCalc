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
	private int extraShiftUp;
	private Style style;

	public ExponentGraphic(BinExpression b, CompleteExpressionGraphic gr) {
		super(b, gr);
		style = Style.SUPERSCRIPT;
		spaceBetweenBaseAndSuper = 3;
		extraShiftUp = 2;
		// TODO Auto-generated constructor stub
	}
	
	public void draw(){
		//no symbol to draw
		if (isSelected()){
			super.getCompExGraphic().getGraphics().setColor(getSelectedColor());
			super.getCompExGraphic().getGraphics().fillRect(getX1(), getY1(), getX2() - getX1(), getY2() - getY1());
			super.getCompExGraphic().getGraphics().setColor(Color.black);
		}
	}
	
	public void drawCursor(int pos){
		
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
			super.getCompExGraphic().getComponents().add(leftValGraphic);
			
			rightValGraphic = makeValueGraphic(tempRight);
			
			rightSize = rightValGraphic.requestSize(g, getCompExGraphic().getSmallFont(), x1, y1);
			super.getCompExGraphic().getComponents().add(rightValGraphic);
			
			//set the west and east fields for inside and outside of the expression
			setMostInnerWest(leftValGraphic.getMostInnerWest());
			leftValGraphic.getMostInnerEast().setEast(rightValGraphic.getMostInnerWest());
			this.setWest(leftValGraphic.getMostInnerEast());
			setMostInnerWest(leftValGraphic.getMostInnerWest());
			setMostInnerSouth(this);
			
			setMostInnerEast(rightValGraphic.getMostInnerEast());
			rightValGraphic.getMostInnerWest().setWest(leftValGraphic.getMostInnerEast());
			this.setEast(rightValGraphic.getMostInnerWest());
			this.setMostInnerEast(rightValGraphic.getMostInnerEast());
			this.setMostInnerNorth(this);
			
//			//set the west and east fields for inside and outside of the expression
//			setMostInnerWest(leftValGraphic.getMostInnerWest());
//			leftValGraphic.getMostInnerEast().setEast(rightValGraphic.getMostInnerWest());
//			leftValGraphic.getMostInnerNorth().setNorth(rightValGraphic.getMostInnerSouth());
//			this.setWest(leftValGraphic.getMostInnerEast());
//			this.setSouth(leftValGraphic.getMostInnerNorth());
//			this.setMostInnerWest(leftValGraphic.getMostInnerWest());
//			this.setMostInnerSouth(leftValGraphic.getMostInnerSouth());
//			
//			setMostInnerNorth(rightValGraphic.getMostInnerNorth());
//			setMostInnerSouth(leftValGraphic.getMostInnerSouth());
//			
//			setMostInnerEast(rightValGraphic.getMostInnerEast());
//			rightValGraphic.getMostInnerSouth().setSouth(leftValGraphic.getMostInnerEast());
//			rightValGraphic.getMostInnerWest().setWest(leftValGraphic.getMostInnerEast());
//			this.setNorth(rightValGraphic.getMostInnerSouth());
//			this.setEast(rightValGraphic.getMostInnerWest());
//			this.setMostInnerEast(rightValGraphic.getMostInnerEast());
//			this.setMostInnerNorth(rightValGraphic.getMostInnerNorth());
			
			//do not mess up the order of these adds!!!, look at method getLeftGraphic to find out why
			super.getComponents().setSize(2);
			super.getComponents().set(0, leftValGraphic);
			super.getComponents().set(1, rightValGraphic);
			
			rightValGraphic.shiftToX1(x1 + leftSize[0] + spaceBetweenBaseAndSuper);
			
			int shiftDownLeft = 0;
			shiftDownLeft = rightValGraphic.getUpperHeight() + extraShiftUp;
			if (leftValGraphic instanceof ExponentGraphic)
			{
				if ( ((ExponentGraphic) leftValGraphic).getRightGraphic().getHeight() / 2.0 <
						rightValGraphic.getLowerHeight() )
				{
					shiftDownLeft = rightValGraphic.getHeight() + extraShiftUp - (int) 
							Math.round(((ExponentGraphic)leftValGraphic).getRightGraphic().getHeight()/2.0);
				}
			}
			else
			{
				if ( leftValGraphic.getHeight() / 2.0 < rightValGraphic.getLowerHeight() )
				{
					shiftDownLeft = rightValGraphic.getHeight() + extraShiftUp - (int) 
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
