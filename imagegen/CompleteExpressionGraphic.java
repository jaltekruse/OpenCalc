package imagegen;

import java.awt.Font;
import java.awt.Graphics;
import java.util.Vector;

import tree.BinExpression;
import tree.Expression;
import tree.Fraction;
import tree.Decimal;
import tree.Operator;
import tree.Value;

public class CompleteExpressionGraphic {

	Vector<ValueGraphic> components;
	
	int xSize, ySize;
	private Graphics graphics;
	private Font font;
	
	//will be replaced by completeExpression later
	Value v;
	
	public CompleteExpressionGraphic(Value v){
		this.v = v;
	}
	
	public void draw(){
		
		for (ValueGraphic vg : components){
			System.out.println(vg.toString());
			System.out.println("x1: " + vg.getX1() + " y1: " + vg.getY1() + " x2: " + vg.getX2() + " y2: " + vg.getY2());
			System.out.println(vg.getValue().toString());
			vg.draw();
		}
	}

	public void generateExpressionGraphic(Graphics g, int x1, int y1) throws Exception{
		graphics = g;
		components = new Vector<ValueGraphic>();
		int[] tempSize = {0, 0};
		
		if (v instanceof BinExpression){
			BinExpressionGraphic temp;
			if (((BinExpression)v).getOp() == Operator.DIVIDE){
				temp = new DivisionGraphic((BinExpression)v, this);
			}
			else{
				temp = new BinExpressionGraphic((BinExpression)v, this);
			}
			components.add(temp);
			tempSize = temp.requestSize(g,new Font("Lucida Sans Unicode", 0, 16), x1, y1);
//			temp.setX1(x1);
//			temp.setY1(y1);
//			temp.setX2(x1 + tempSize[0]);
//			temp.setY2(y1 + tempSize[1]);
		}
		
		if (v instanceof Decimal){
			
		}
		xSize = tempSize[0];
		ySize = tempSize[1];
	}
	
	public int generateExpressionGraphic(Value v, Graphics g){
		if (v instanceof BinExpression){
			BinExpressionGraphic temp = new BinExpressionGraphic((BinExpression)v, this);
			components.add(temp);
			temp.requestSize(g, new Font("Lucida Sans Unicode", 0, 16));
		}
		
		if (v instanceof Decimal){
			
		}
		return 0;
	}
	
	public int[] requestSize(Value v, Graphics g, Font f){
		
		return null;
	}

	public Vector<ValueGraphic> getComponents(){
		return components;
	}
	
	public void setGraphics(Graphics graphics) {
		this.graphics = graphics;
	}

	public Graphics getGraphics() {
		return graphics;
	}
}
