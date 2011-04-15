package imagegen;

import imagegen.FractionGraphic.Style;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import tree.Value;
import tree.ValueWithName;

public class ValueWithNameGraphic extends ValueGraphic {

	public ValueWithNameGraphic(Value v, CompleteExpressionGraphic compExGraphic) {
		super(v, compExGraphic);
		setMostInnerWest(this);
		setMostInnerEast(this);
		setMostInnerNorth(this);
		setMostInnerSouth(this);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw() {
		if (isSelected()){
			super.getCompExGraphic().getGraphics().setColor(getSelectedColor());
			super.getCompExGraphic().getGraphics().fillRect(getX1() - 1, getY1() - 1, getX2() - getX1()+ 2, getY2() - getY1() + 2);
			super.getCompExGraphic().getGraphics().setColor(Color.black);
		}
		getCompExGraphic().getGraphics().setFont(getFont());
		getCompExGraphic().getGraphics().drawString(getValue().toString(), getX1(), getY2());
	}

	public void drawCursor(int pos){
		
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
