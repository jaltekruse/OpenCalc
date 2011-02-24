package imagegen;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import tree.BinExpression;
import tree.Constant;
import tree.Fraction;
import tree.Operator;
import tree.Value;
import tree.Var;

public class DivisionGraphic extends BinExpressionGraphic {

	public static enum Style{
		SLASH, DIAGONAL, HORIZONTAL
	}
	//number of pixels the bar overhangs the widest child (numerator or denominator)
	private int sizeOverhang;
	
	//number of pixels left above and below the horizontal bar
	private int spaceAroundBar;
	private Style style;
	private int heightNumer, heightDenom;
	
	public DivisionGraphic(BinExpression b, CompleteExpressionGraphic gr) {
		super(b, gr);
		style = Style.HORIZONTAL;
		sizeOverhang = 2;
		spaceAroundBar = 5;
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
//			super.getCompExGraphic().getGraphics().fillRect(getX1(), getY1(), getX2() - getX1(), getY2() - getY1());
//			super.getCompExGraphic().getGraphics().setColor(Color.black);
			super.getCompExGraphic().getGraphics().drawLine(symbolX1, symbolY1 + spaceAroundBar + 1, symbolX2, 
					symbolY1 + spaceAroundBar + 1); 
		}
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
		
		if (style == Style.SLASH)
		{
			
			leftValGraphic = makeValueGraphic(tempLeft);
			
			super.getCompExGraphic().components.add(leftValGraphic);
			leftSize = leftValGraphic.requestSize(g, f, x1, y1);
			symbolSize[0] = getCompExGraphic().getStringWidth(getValue().getOp().getSymbol(), f);
			symbolSize[1] = getCompExGraphic().getFontHeight(f);
			symbolX1 = x1 + leftSize[0];
			System.out.println(leftSize[1]/2.0);
			symbolY1 = y1 + ((int)Math.round(leftSize[1]/2.0) - (int) (Math.round(symbolSize[1])/2.0));
			symbolX2 = x1 + leftSize[0] + symbolSize[0];
			symbolY2 = symbolSize[1] + symbolY1;
			
			rightValGraphic = makeValueGraphic(tempRight);
			
			rightSize = rightValGraphic.requestSize(g, f, symbolX2, y1);
			super.getCompExGraphic().components.add(rightValGraphic);
			
			super.getComponents().add(leftValGraphic);
			super.getComponents().add(rightValGraphic);
			
			totalSize[0] = symbolX2 + rightSize[0] - x1;
			totalSize[1] = symbolY2 - y1;
			setUpperHeight((int) Math.round((totalSize[1]/2.0)));
			setLowerHeight(getUpperHeight());
			super.setX1(x1);
			super.setY1(y1);
			super.setX2(x1 + totalSize[0]);
			super.setY2(y1 + totalSize[1]);
			return totalSize;
		}
		else if (style == Style.HORIZONTAL){
			
			leftValGraphic = makeValueGraphic(tempLeft);
			
			leftSize = leftValGraphic.requestSize(g, f, x1, y1);
			super.getCompExGraphic().components.add(leftValGraphic);
			
			//other if statements for checking the left, decimal, imaginary, other val types
			
			rightValGraphic = makeValueGraphic(tempRight);
			
			rightSize = rightValGraphic.requestSize(g, f, symbolX2, y1);
			super.getCompExGraphic().components.add(rightValGraphic);
			setHeightNumer(leftSize[1]);
			setHeightDenom(rightSize[1]);
			
			super.getComponents().add(leftValGraphic);
			super.getComponents().add(rightValGraphic);
			
			symbolX1 = x1;
			
			if (leftSize[0] > rightSize[0]){
				symbolX2 = x1 + leftSize[0] + 2 * sizeOverhang;
			}
			else
			{
				symbolX2 = x1 + rightSize[0] + 2 * sizeOverhang;
			}
			
			leftValGraphic.shiftToX1((int)(Math.round(((symbolX2 - symbolX1) - leftSize[0]))/2.0) + x1);
			//leftValGraphic.shiftToX2(leftValGraphic.getX1() + leftSize[0]);
			
			symbolY1 = leftSize[1] + y1;
			symbolY2 = symbolY1 + 1 + 2 * spaceAroundBar;
			System.out.println("symbolY2: " + symbolY2);

			rightValGraphic.shiftToX1((int)(Math.round(((symbolX2 - symbolX1) - rightSize[0]))/2.0) + x1);
			//rightValGraphic.shiftToX2(rightValGraphic.getX1() + rightSize[0]);
			rightValGraphic.shiftToY1(symbolY2);
			//rightValGraphic.shiftToY2(rightValGraphic.getY1() + rightSize[1]);
			
			setUpperHeight(leftSize[1] + spaceAroundBar);
			setLowerHeight(rightSize[1] + spaceAroundBar);
			totalSize[0] = symbolX2 - x1;
			totalSize[1] = symbolY2 + rightSize[1] - y1;
			super.setX1(x1);
			super.setY1(y1);
			super.setX2(x1 + totalSize[0]);
			super.setY2(y1 + totalSize[1]);
			return totalSize;
		}
		
		return null;
	}

	public void setHeightNumer(int heightNumer) {
		this.heightNumer = heightNumer;
	}

	public int getHeightNumer() {
		//adds 3 for the whitespace
		return heightNumer + spaceAroundBar;
	}

	public void setHeightDenom(int heightDenom) {
		this.heightDenom = heightDenom;
	}

	public int getHeightDenom() {
		//adds 3 for the whitespace
		return heightDenom + spaceAroundBar;
	}

}
