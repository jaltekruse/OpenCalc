package imagegen;

import imagegen.FractionGraphic.Style;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import tree.Decimal;
import tree.Value;

public class DecimalGraphic extends ValueGraphic<Decimal> {

	public DecimalGraphic(Decimal v, CompleteExpressionGraphic compExGraphic) {
		super(v, compExGraphic);
		setMostInnerWest(this);
		setMostInnerEast(this);
		setMostInnerNorth(this);
		setMostInnerSouth(this);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		if (isSelected()){
//			drawSelection();
		}
		super.getCompExGraphic().getGraphics().setColor(Color.BLACK);
		super.getCompExGraphic().getGraphics().drawString(
				getValue().toString(), getX1(), getY2());
	}
	
	public void drawSelection(){
		super.getCompExGraphic().getGraphics().setColor(getSelectedColor());
		super.getCompExGraphic().getGraphics().fillRect(
				getX1(), getY1(), getX2() - getX1(), getY2() - getY1());
	}
	
	public void drawCursor(){
		
		int xPos = findCursorXPos();
		
		super.getCompExGraphic().getGraphics().setColor(Color.BLACK);
		super.getCompExGraphic().getGraphics().fillRect(xPos, getY1() - 3, 2, getY2() - getY1()+ 5);
		
	}
	
	public int getMaxCursorPos(){
		return getValue().toString().length();
	}
	
	public int findCursorXPos(){
		super.getCompExGraphic().getGraphics().setFont(getFont());
		String numberString = getValue().toString();
		return getX1() + super.getCompExGraphic().getGraphics().getFontMetrics().stringWidth(
				numberString.substring(0, super.getCompExGraphic().getCursor().getPos()));
	}
	
	public void setCursorPos(int xPixelPos){
		
		String numberString = getValue().toString();
		super.getCompExGraphic().getCursor().setValueGraphic(this);
		super.getCompExGraphic().getGraphics().setFont(getFont());
		
		if (xPixelPos <= getX1())
		{//if the pixel is in front of the graphic
			super.getCompExGraphic().getCursor().setPos(0);
			super.getCompExGraphic().getCursor().setValueGraphic(this);
			return;
		}
			
		else if (xPixelPos >= getX2())
		{// if the pixel is after the graphic
			super.getCompExGraphic().getCursor().setPos(numberString.length());
			super.getCompExGraphic().getCursor().setValueGraphic(this);
			return;
		}
		
		int startX, endX, xWidth;
		for (int i = 0; i < numberString.length() ; i++){
			
			startX = super.getCompExGraphic().getGraphics().getFontMetrics().stringWidth(
					numberString.substring(0, i)) + getX1();
			endX = super.getCompExGraphic().getGraphics().getFontMetrics().stringWidth(
					numberString.substring(0, i + 1)) + getX1();
			xWidth = endX - startX;
			if (startX <= xPixelPos && endX >= xPixelPos)
			{//if the x position is inside of a character, check if it is on the first or second
				//half of the character and set the cursor accordingly
				if (endX - xPixelPos > xWidth/2){
					super.getCompExGraphic().getCursor().setPos( i );
				}
				else{
					super.getCompExGraphic().getCursor().setPos( i + 1 );
				}
				super.getCompExGraphic().getCursor().setValueGraphic(this);
				return;
			}
		}
	}
	
	public void moveCursorWest(){
		if (super.getCompExGraphic().getCursor().getPos() > 0){
			super.getCompExGraphic().getCursor().setPos( super.getCompExGraphic().getCursor().getPos() - 1); 
		}
		else{
			if (getWest() == null)
			{
				return;
			}
			else
			{
				getWest().sendCursorInFromEast((getY2() - getY1())/2, this);
				return;
			}
		}
	}
	
	public void moveCursorEast(){
		if (super.getCompExGraphic().getCursor().getPos() < getValue().toString().length()){
			super.getCompExGraphic().getCursor().setPos( super.getCompExGraphic().getCursor().getPos() + 1); 
		}
		else{
			if (getEast() == null)
			{
				return;
			}
			else
			{
				getEast().sendCursorInFromWest((getY2() - getY1())/2, this);
				return;
			}
		}
	}
	
	public void moveCursorNorth(){
		if (getNorth() == null)
		{
			System.out.println("nothing to north");
			return;
		}
		else
		{
			getNorth().sendCursorInFromSouth(findCursorXPos(), this);
			return;
		}
	}
	
	public void moveCursorSouth(){
		if (getSouth() == null)
		{
			System.out.println("nothing to south");
			return;
		}
		else
		{
			getSouth().sendCursorInFromNorth(findCursorXPos(), this);
			return;
		}
	}
	
	public void sendCursorInFromEast(int yPos, ValueGraphic vg)
	{
		super.getCompExGraphic().getCursor().setValueGraphic(this);
		super.getCompExGraphic().getCursor().setPos(getMaxCursorPos() - 1);
	}
	
	public void sendCursorInFromWest(int yPos, ValueGraphic vg)
	{
		super.getCompExGraphic().getCursor().setValueGraphic(this);
		super.getCompExGraphic().getCursor().setPos(1);
	}

	public void sendCursorInFromNorth(int xPos, ValueGraphic vg){
//		super.getCompExGraphic().getCursor().setValueGraphic(this);
		setCursorPos(xPos);
		System.out.println("Dec graphic in from north, cursor: " +
				super.getCompExGraphic().getCursor().getValueGraphic().getValue().toString());
	}
	
	public void sendCursorInFromSouth(int xPos, ValueGraphic vg){
//		super.getCompExGraphic().getCursor().setValueGraphic(this);
		setCursorPos(xPos);
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
