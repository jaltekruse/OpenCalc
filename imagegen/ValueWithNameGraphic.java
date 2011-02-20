package imagegen;

import imagegen.FractionGraphic.Style;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import tree.Value;
import tree.ValueWithName;

public class ValueWithNameGraphic extends ValueGraphic {

	public ValueWithNameGraphic(Value v, CompleteExpressionGraphic compExGraphic) {
		super(v, compExGraphic);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		getCompExGraphic().getGraphics().setFont(getFont());
		getCompExGraphic().getGraphics().drawString(getValue().toString(), getX1(), getY2());
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
		g.setFont(f);
		setFont(f);
		FontMetrics fm = g.getFontMetrics();
		String s = getValue().toString();
		int[] size = new int[2];
		size[0] = fm.stringWidth(s);
		size[1] = fm.getHeight() - 9;
		setUpperHeight((int) Math.round(size[1]/2.0));
		setLowerHeight(getUpperHeight());
		super.setX1(x1);
		super.setY1(y1);
		super.setX2(x1 + size[0]);
		super.setY2(y1 + size[1]);
		return size;
	}

}
