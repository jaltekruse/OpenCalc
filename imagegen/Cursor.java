package imagegen;

public class Cursor {
	
	private ValueGraphic valueGraphic;
	private int pos;
	
	public Cursor(){}
	
	public Cursor(ValueGraphic vg, int p){
		setValueGraphic(vg);
		setPos(p);
	}

	public void setValueGraphic(ValueGraphic valueGraphic) {
		this.valueGraphic = valueGraphic;
	}

	public ValueGraphic getValueGraphic() {
		return valueGraphic;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public int getPos() {
		return pos;
	}
}
