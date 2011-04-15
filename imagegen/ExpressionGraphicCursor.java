package imagegen;

public class ExpressionGraphicCursor {
	
	private ValueGraphic currValue;
	private int positionWithinValue;
	
	public ExpressionGraphicCursor(ValueGraphic vg, int pos){
		currValue = vg;
		positionWithinValue = pos;
	}
	
	public void setCurrValue(ValueGraphic currValue) {
		this.currValue = currValue;
	}
	public ValueGraphic getCurrValue() {
		return currValue;
	}

	public void setPositionWithinValue(int positionWithinValue) {
		this.positionWithinValue = positionWithinValue;
	}

	public int getPositionWithinValue() {
		return positionWithinValue;
	}

}
