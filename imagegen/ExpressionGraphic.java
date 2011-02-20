package imagegen;

import java.awt.Font;
import java.awt.Graphics;

import tree.Expression;
import tree.Value;

public abstract class ExpressionGraphic extends ValueGraphic<Expression> {

	protected int symbolX1, symbolX2, symbolY1, symbolY2;
	
	public ExpressionGraphic(Expression v, CompleteExpressionGraphic compExGraphic) {
		super(v, compExGraphic);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] requestSize(Graphics g, Font f) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] requestSize(Graphics g, Font f, int x1, int y1)
			throws Exception {
		// TODO Auto-generated method stub
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
