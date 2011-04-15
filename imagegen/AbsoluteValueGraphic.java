package imagegen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import tree.Operator;
import tree.UnaryExpression;
import tree.Value;

public class AbsoluteValueGraphic extends UnaryExpressionGraphic {

	private int space;
	private int overhang;
	private int widthSpaceAndLines;
	
	public AbsoluteValueGraphic(UnaryExpression v, CompleteExpressionGraphic compExGraphic) {
		super(v, compExGraphic);
		space = 2;
		overhang = 1;
		widthSpaceAndLines = 3;
		setMostInnerNorth(this);
		setMostInnerSouth(this);
		if (v.getChild() instanceof UnaryExpression){
			if (((UnaryExpression)v.getChild()).getOp() == Operator.PAREN){
				v.setChild(((UnaryExpression)v.getChild()).getChild());
			}
		}
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		if (isSelected()){
			super.getCompExGraphic().getGraphics().setColor(getSelectedColor());
			super.getCompExGraphic().getGraphics().fillRect(symbolX1, symbolY1, symbolX2 - symbolX1, symbolY2 - symbolY1);
			super.getCompExGraphic().getGraphics().setColor(Color.black);
		}
		
		super.getCompExGraphic().getGraphics().drawLine(symbolX1 + (int) Math.round(widthSpaceAndLines/2.0), 
				getY1(), getX1() + (int) Math.round(widthSpaceAndLines/2.0), getY2());
		super.getCompExGraphic().getGraphics().drawLine(getX2() - (int) Math.round(widthSpaceAndLines/2.0),
				getY1(), getX2() - (int) Math.round(widthSpaceAndLines/2.0), getY2());
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
		//g is used to decide the size of the text to display for this element
		g.setFont(f);
		
		//to draw this element later, the font must be the same, so its stored in this object
		setFont(f);
		
		int[] childSize = {0,0};
		int[] symbolSize = {0, 0};
		int[] totalSize = {0, 0};

		// The call to getChild() skips the first paren inside of the operator, the parens are needed to have
		// an expression inside of a UrinaryOp, but they are not usually displayed
		// if a user wants to show parens, the can use  two pairs of parens: abs((5/6))
		Value tempChild = ((UnaryExpression)super.getValue()).getChild();
		
		ValueGraphic childValGraphic = makeValueGraphic(tempChild);
		childSize = childValGraphic.requestSize(g, f, x1 + widthSpaceAndLines + space, y1 + overhang);
		
		//set the west and east fields for inside an outside of the expression
		setMostInnerWest(this);
		childValGraphic.getMostInnerEast().setEast(this);
		
		setMostInnerEast(this);
		childValGraphic.getMostInnerWest().setWest(this);
		
		super.getComponents().add(childValGraphic);
		super.getCompExGraphic().getComponents().add(childValGraphic);
		
		widthSpaceAndLines += (int) Math.round(childSize[1]/14.0);
		childValGraphic.shiftToX1(x1 + widthSpaceAndLines + space);
		
		symbolSize[0] = childSize[0] + space * 2 + widthSpaceAndLines * 2;
		symbolSize[1] = childSize[1] + overhang * 2;
		
		symbolY1 = y1;
		symbolY2 = symbolY1 + symbolSize[1];
		symbolX1 = x1;
		symbolX2 = x1 + symbolSize[0];
		
		setUpperHeight(childValGraphic.getUpperHeight() + overhang);
		setLowerHeight(childValGraphic.getLowerHeight() + overhang);
		
		totalSize[0] = symbolSize[0];
		totalSize[1] = symbolSize[1];
		
		//set the outer bounds of this element
		super.setX1(x1);
		super.setY1(y1);
		super.setX2(x1 + totalSize[0]);
		super.setY2(y1 + totalSize[1]);
		
		return totalSize;
	}
}
