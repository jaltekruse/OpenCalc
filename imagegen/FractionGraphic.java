package imagegen;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import tree.Value;
import tree.Fraction;

public class FractionGraphic extends ValueGraphic<Fraction>{
	
	public static enum Style{
		SLASH, DIAGONAL, HORIZONTAL
	}
	
	private Style style;
	private int spaceAroundBar;
	private int sizeOverHang;
	
	public FractionGraphic(Fraction f, CompleteExpressionGraphic gr) {
		super(f, gr);
		style = Style.HORIZONTAL;
		spaceAroundBar = 2;
		sizeOverHang = 2;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		Graphics g = super.getCompExGraphic().getGraphics();
		g.setFont(getFont());
		FontMetrics fm = g.getFontMetrics();
		if (style == Style.SLASH || (style == Style.HORIZONTAL && getValue().getDenominator() == 1)){
//			super.getCompExGraphic().getGraphics().setColor(Color.gray);
//			super.getCompExGraphic().getGraphics().fillRect(getX1(), getY1(), getX2() - getX1(), getY2() - getY1());
//			super.getCompExGraphic().getGraphics().setColor(Color.black);
			g.drawString(getValue().toString(), getX1(), getY2());
		}
		else if (style == Style.HORIZONTAL){
			g.drawString("" + getValue().getDenominator(),
					(int)Math.round(((getX2() - getX1()) - getCompExGraphic().getStringWidth("" 
							+ getValue().getDenominator(), getFont()))/2.0) + getX1(), getY2());
			int heightDenom = (int) Math.round((getY2() - getY1() - (2 * spaceAroundBar + 1))/2.0);
			int lineY = heightDenom + spaceAroundBar + getY1();
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
		setFont(f);
		if (style == Style.SLASH){
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
		else if (style == Style.HORIZONTAL){
			int numerator = getValue().getNumerator();
			int denominator = getValue().getDenominator();
			
			if (denominator != 1){
				int[] size = new int[2];
				int numerWidth = getCompExGraphic().getStringWidth("" + numerator, f);
				
				int denomWidth = getCompExGraphic().getStringWidth("" + denominator, f);
				if (numerWidth > denomWidth){
					size[0] = numerWidth + 2 * sizeOverHang;
				}
				else
				{
					size[0] = denomWidth + 2 * sizeOverHang;
				}
				size[1] = getCompExGraphic().getFontHeight(f) * 2 + 2 * spaceAroundBar + 1;
				setUpperHeight((int) Math.round(size[1]/2.0));
				setLowerHeight(getUpperHeight());
				super.setX1(x1);
				super.setY1(y1);
				super.setX2(x1 + size[0]);
				super.setY2(y1 + size[1]);
				return size;
			}
			else{
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
		}
		throw new Exception("error rendering fraction");
	}

	@Override
	public int[] requestSize(Graphics g, Font f) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
