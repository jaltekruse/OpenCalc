package imagegen;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import tree.Nothing;
import tree.Value;

public class NothingGraphic extends ValueGraphic {

	public NothingGraphic(Nothing v, CompleteExpressionGraphic compExGraphic) {
		super(v, compExGraphic);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		getCompExGraphic().getGraphics().drawRect(getX1(), getY1(), getX2() - getX1(), getY2() - getY1());
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
		
		//random character that happens to be the size needed
		String s = "8";
		
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

}
