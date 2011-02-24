package imagegen;

import java.awt.Font;
import java.awt.Graphics;
import java.util.Vector;

import tree.BinExpression;
import tree.Constant;
import tree.Fraction;
import tree.Operator;
import tree.Value;
import tree.Var;
import tree.Nothing;
import tree.UrinaryExpression;
import tree.Decimal;

public abstract class ValueGraphic<E extends Value> {
	
	//used to store the amount of space above the object,used to center later
	//objects added to ExpressionGraphic
	private int upperHeight, lowerHeight;
	
	private int x1, y1, x2, y2;
	protected E value;
	private ValueGraphic north, south, east, west;
	private Vector<ValueGraphic> components;
	private CompleteExpressionGraphic compExGraphic;
	private Font f;
	
	public ValueGraphic(E v, CompleteExpressionGraphic compExGraphic){
		this.compExGraphic = compExGraphic;
		components = new Vector<ValueGraphic>();
		value = v;
	}
	
	ValueGraphic makeValueGraphic(Value v) throws RenderException{
		if (v instanceof Fraction)
		{
			return new FractionGraphic((Fraction)v, getCompExGraphic());
		}
		if (v instanceof Decimal){
			return new DecimalGrpahic((Decimal)v, getCompExGraphic());
		}
		else if(v instanceof Var || v instanceof Constant)
		{
			return new ValueWithNameGraphic(v, getCompExGraphic());
		}
		else if (v instanceof Nothing){
			return new NothingGraphic((Nothing)v, getCompExGraphic());
		}
		else if (v instanceof BinExpression)
		{
			if (((BinExpression)v).getOp() == Operator.DIVIDE){
				return new DivisionGraphic((BinExpression)v, getCompExGraphic());
			}
			else if (((BinExpression)v).getOp() == Operator.POWER){
				return new ExponentGraphic((BinExpression)v, getCompExGraphic());
			}
			else{
				return new BinExpressionGraphic((BinExpression)v, getCompExGraphic());
			}
			
		}
		else if (v instanceof UrinaryExpression){
			if (((UrinaryExpression)v).getOp() == Operator.PAREN)
			{
				return new ParenGraphic(((UrinaryExpression)v), getCompExGraphic());
			}
			else if (((UrinaryExpression)v).getOp() == Operator.NEG)
			{
				return new NegationGraphic(((UrinaryExpression)v), getCompExGraphic());
			}
			else if (((UrinaryExpression)v).getOp() == Operator.SQRT)
			{
				return new RadicalGraphic(((UrinaryExpression)v), getCompExGraphic());
			}
			else if (((UrinaryExpression)v).getOp() == Operator.FACT)
			{
				return new UnaryPostGraphic(((UrinaryExpression)v), getCompExGraphic());
			}
			return new UnaryExpressionGraphic((UrinaryExpression)v, getCompExGraphic());
		}
		System.out.println(v.toString());
		throw new RenderException("unsupported Value");
	}
	
	void setX1(int x1) {
		this.x1 = x1;
	}

	//decide on what visibility these methods should have
	public int getX1() {
		return x1;
	}
	
	void setX2(int x2) {
		this.x2 = x2;
	}

	public int getX2() {
		return x2;
	}

	void setY1(int y1) {
		this.y1 = y1;
	}

	public int getY1() {
		return y1;
	}
	
	void setY2(int y2) {
		this.y2 = y2;
	}

	public int getY2() {
		return y2;
	}

	void shiftToX1(int x1) {
		int xChange = x1 - this.x1;
		for (ValueGraphic vg : components){
			vg.setX1(vg.getX1() + xChange);
			vg.setX2(vg.getX2() + xChange);
		}
		this.x2 += xChange;
		this.x1 = x1;
	}

	void shiftToY1(int y1) {
		int yChange = y1 - this.y1;
		for (ValueGraphic vg : components){
			vg.setY1(vg.getY1() + yChange);
			vg.setY2(vg.getY2() + yChange);
		}
		this.y2 += yChange;
		this.y1 = y1;
	}
	
	int getHeight(){
		return y2 - y1;
	}
	
	int getWidth(){
		return x2 - x1;
	}
	
	void setValue(E v){
		value = v;
	}

	public E getValue(){
		return value;
	}

	public void setNorth(ValueGraphic north) {
		this.north = north;
	}

	public ValueGraphic getNorth() {
		return north;
	}

	public void setSouth(ValueGraphic south) {
		this.south = south;
	}

	public ValueGraphic getSouth() {
		return south;
	}

	public void setEast(ValueGraphic east) {
		this.east = east;
	}

	public ValueGraphic getEast() {
		return east;
	}

	public void setWest(ValueGraphic west) {
		this.west = west;
	}

	public ValueGraphic getWest() {
		return west;
	}
	
	public abstract int[] requestSize(Graphics g, Font f);
	
	public abstract void draw();

	public int[] requestSize(Font f) {
		// TODO Auto-generated method stub
		return null;
	}

	public CompleteExpressionGraphic getCompExGraphic() {
		return compExGraphic;
	}


	public abstract int[] requestSize(Graphics g, Font f, int x1, int y1) throws Exception;


	public void setComponents(Vector<ValueGraphic> components) {
		this.components = components;
	}


	public Vector<ValueGraphic> getComponents() {
		return components;
	}

	public void setUpperHeight(int upperHeight) {
		this.upperHeight = upperHeight;
	}

	public int getUpperHeight() {
		return upperHeight;
	}

	public void setLowerHeight(int lowerHeight) {
		this.lowerHeight = lowerHeight;
	}

	public int getLowerHeight() {
		return lowerHeight;
	}

	public void setFont(Font f) {
		this.f = f;
	}

	public Font getFont() {
		return f;
	}
}
