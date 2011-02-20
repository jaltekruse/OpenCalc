package imagegen;

import imagegen.FractionGraphic.Style;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import tree.Decimal;
import tree.Value;

public class DecimalGrpahic extends ValueGraphic<Decimal> {

	public DecimalGrpahic(Decimal v, CompleteExpressionGraphic compExGraphic) {
		super(v, compExGraphic);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		Graphics g = super.getCompExGraphic().getGraphics();
//		super.getCompExGraphic().getGraphics().setColor(Color.gray);
//		super.getCompExGraphic().getGraphics().fillRect(getX1(), getY1(), getX2() - getX1(), getY2() - getY1());
//		super.getCompExGraphic().getGraphics().setColor(Color.black);
		g.drawString(getValue().toString(), getX1(), getY2());
	}

	@Override
	public int[] requestSize(Graphics g, Font f, int x1, int y1) throws Exception {
		// TODO right now prints toString representation, need to make horizonal, and slash representations soon
		g.setFont(f);
		setFont(f);
		String s = getValue().toString();
		int[] size = new int[2];
		size[0] = getCompExGraphic().getStringWidth(s, f);
		size[1] = getCompExGraphic().getFontHeight(f);
		setUpperHeight((int) Math.round(size[1]/2.0));
		setLowerHeight(getUpperHeight());
		super.setX1(x1);
		super.setY1(y1);
		super.setX2(x1 + size[0]);
		super.setY2(y1 + size[1]);
		return size;
	}

	@Override
	public int[] requestSize(Graphics g, Font f) {
		// TODO Auto-generated method stub
		return null;
	}

}
