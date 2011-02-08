package imagegen;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import tree.BinExpression;
import tree.Operator;
import tree.Value;
import tree.Fraction;

public class BinExpressionGraphic extends ValueGraphic<BinExpression> {

	int symbolX1, symbolX2, symbolY1, symbolY2;
	
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
		super.getCompExGraphic().getGraphics().drawString(getValue().getOp().getSymbol(),
				symbolX1, symbolY2);
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
		
		super.getCompExGraphic().components.add(leftValGraphic);
		leftSize = leftValGraphic.requestSize(g, f, x1, y1);
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
		
		rightSize = rightValGraphic.requestSize(g, f, x1, y1);
		super.getCompExGraphic().components.add(rightValGraphic);
		
		symbolSize[0] = fm.stringWidth(getValue().getOp().getSymbol());
		symbolSize[1] = fm.getHeight() - 9;
		rightValGraphic.shiftToX1(leftSize[0]+ symbolSize[0] + x1);
		rightValGraphic.shiftToX2(leftSize[0]+ symbolSize[0] + rightSize[0] + x1);
		
		int height = 0;
		if (leftSize[1] > rightSize[1]){
			height = leftSize[1];
			symbolY1 = (int) (Math.round((height - symbolSize[1])/2.0) + y1);
			symbolY2 = symbolY1 + symbolSize[1];	
			rightValGraphic.shiftToY1((int) (Math.round((height - rightSize[1])/2.0) + y1));
			rightValGraphic.shiftToY2(rightValGraphic.getY1() + rightSize[1]);
		}
		else
		{
			height = rightSize[1];
			symbolY1 = (int) (Math.round((height - symbolSize[1])/2.0) + y1);
			symbolY2 = symbolY1 + symbolSize[1];
			leftValGraphic.shiftToY1((int) (Math.round((height - leftSize[1])/2.0) + y1));
			leftValGraphic.shiftToY2(leftValGraphic.getY1() + leftSize[1]);
		}
		
		symbolX1 = x1 + leftSize[0];
		//symbolY1 = y1 + ((int)Math.round(leftSize[1]/2.0) - (int) (Math.round(symbolSize[1])/2.0));
		symbolX2 = x1 + leftSize[0] + symbolSize[0];
		//symbolY2 = symbolSize[1] + symbolY1;
		
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
	
	void shiftToX2(int x2) {
		int xChange = x2 - getX2();
		for (ValueGraphic vg : getComponents()){
			vg.shiftToX2(vg.getX2() + xChange);
		}
		symbolX2 += xChange;
		setX2(x2);
	}
	
	void shiftToY2(int y2) {
		System.out.println(symbolY2);
		int yChange = y2 - getY2();
		for (ValueGraphic vg : getComponents()){
			vg.shiftToY2(vg.getY2() + yChange);
		}
		System.out.println("shift y2 of : " + getValue().toString());
		System.out.println(yChange);
		symbolY2 += yChange;
		System.out.println(symbolY2);
		setY2(y2);
	}
	void shiftToX1(int x1) {
		int xChange = x1 - getX1();
		for (ValueGraphic vg : getComponents()){
			vg.shiftToX1(vg.getX1() + xChange);
		}
		symbolX1 += xChange;
		setX1(x1);
	}

	void shiftToY1(int y1) {
		int yChange = y1 - getY1();
		for (ValueGraphic vg : getComponents()){
			vg.shiftToY1(vg.getY1() + yChange);
		}
		symbolY1 += yChange;
		setY1(y1);
	}
}
