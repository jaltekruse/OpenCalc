package gui.graph;

public class Selection {

	private double start, end;
	public static final double EMPTY = Double.MAX_VALUE;
	
	public Selection(){
		start = end = EMPTY;
	}
	
	public Selection(double a){
		start = a;
		end = EMPTY;
	}
	
	public Selection(double a, double b){
		start = a;
		end = b;
	}
	
	public void setStart(double d){
		start = d;
	}

	public void setEnd(double d){
		end = d;
	}
	
	public double getStart(){
		return start;
	}
	
	public double getEnd(){
		return end;
	}
}
