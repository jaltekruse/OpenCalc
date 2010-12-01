package gui;

public class PointIn3d {
	
	private double X, Y, Z;
	
	public PointIn3d(double x, double y, double z){
		setX(x);
		setY(y);
		setZ(z);
	}
	
	public PointIn3d(){
		
	}

	public void setX(double x) {
		X = x;
	}

	public double getX() {
		return X;
	}

	public void setY(double y) {
		Y = y;
	}

	public double getY() {
		return Y;
	}

	public void setZ(double z) {
		Z = z;
	}

	public double getZ() {
		return Z;
	}
	
	
}
