package imagegen;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import tree.Value;
import tree.Fraction;

public class FractionGraphic extends ValueGraphic<Fraction>{
	
	public enum Style{
		SLASH, DIAGONAL, HORIZONTAL
	}
	
	private Style style;
	
	public FractionGraphic(Fraction f, CompleteExpressionGraphic gr) {
		super(f, gr);
		style = Style.HORIZONTAL;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		Graphics g = super.getCompExGraphic().getGraphics();
		FontMetrics fm = g.getFontMetrics();
		if (style == Style.SLASH || (style == Style.HORIZONTAL && getValue().getDenominator() == 1)){
			g.drawString(getValue().toString(),
					getX1(), getY2());
		}
		else if (style == Style.HORIZONTAL){
			g.drawString("" + getValue().getDenominator(),
					(int)Math.round(((getX2() - getX1()) - fm.stringWidth("" + getValue().getDenominator()))/2.0)
					+ getX1(), getY2());
			int heightDenom = (int) Math.round((getY2() - getY1() - 7)/2.0);
			int lineY = heightDenom + 2 + getY1();
			g.drawLine(getX1(), lineY, getX2(), lineY);
			
			g.drawString("" + getValue().getNumerator(),
					(int)Math.round(((getX2() - getX1()) - fm.stringWidth("" + getValue().getNumerator()))/2.0)
					+ getX1(), getY1() + heightDenom);
		}
	}

	@Override
	public int[] requestSize(Graphics g, Font f, int x1, int y1) throws Exception {
		// TODO right now prints toString representation, need to make horizonal, and slash representations soon
		g.setFont(f);
		FontMetrics fm = g.getFontMetrics();
		if (style == Style.SLASH){
			String s = getValue().toString();
			int[] size = new int[2];
			size[0] = fm.stringWidth(s);
			size[1] = fm.getHeight() - 9;
			super.setX1(x1);
			super.setY1(y1);
			super.setX2(x1 + size[0]);
			super.setY2(y1 + size[1]);
			return size;
		}
		else if (style == Style.HORIZONTAL){
			int numerator = getValue().getNumerator();
			int denominator = getValue().getDenominator();
			
			if (denominator != 1){
				int[] size = new int[2];
				int numWidth = fm.stringWidth("" + numerator);
				int denomWidth = fm.stringWidth("" + denominator);
				if (numWidth > denomWidth){
					size[0] = numWidth;
				}
				else
				{
					size[0] = denomWidth;
				}
				size[1] = (fm.getHeight() - 9) * 2 + 7;
				super.setX1(x1);
				super.setY1(y1);
				super.setX2(x1 + size[0]);
				super.setY2(y1 + size[1]);
				return size;
			}
			else{
				System.out.println("print int");
				String s = getValue().toString();
				int[] size = new int[2];
				size[0] = fm.stringWidth(s);
				size[1] = fm.getHeight() - 9;
				super.setX1(x1);
				super.setY1(y1);
				super.setX2(x1 + size[0]);
				super.setY2(y1 + size[1]);
				return size;
			}
		}
		throw new Exception("error rendering fraction");
	}

	@Override
	public int[] requestSize(Graphics g, Font f) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
