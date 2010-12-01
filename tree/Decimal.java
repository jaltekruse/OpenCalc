package tree;

public class Decimal extends Number{
	
	private double value;
	private static ExpressionParser parser;
	
	public Decimal(ExpressionParser p){
		parser = p;
	}
	
	public Decimal(double num){
		value = num;
	}
	
	public double getValue(){
		return value;
	}
	
	public void setValue(double num){
		value = num;
	}
	
	public Number eval(){
		return this;
	}
	
	public String toString(){
		return "" + value;
	}

	@Override
	public Value multiply(Decimal d) {
		// TODO Auto-generated method stub
		return new Decimal(value * d.value);
	}

	@Override
	public Value multiply(Fraction f) {
		// TODO Auto-generated method stub
		return new Decimal(value * Fraction.Frac2Dec(f).value);
	}

	@Override
	public Value multiply(Matrix m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value multiply(Irrational i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value add(Decimal d) {
		// TODO Auto-generated method stub
		return new Decimal(value + d.value);
	}

	@Override
	public Value add(Fraction f) {
		// TODO Auto-generated method stub
		return new Decimal(value + Fraction.Frac2Dec(f).value);
	}

	@Override
	public Value add(Matrix m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value add(Irrational i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value subtract(Decimal d) {
		// TODO Auto-generated method stub
		return new Decimal(value - d.value);
	}

	@Override
	public Value subtract(Fraction f) {
		// TODO Auto-generated method stub
		return new Decimal(value - Fraction.Frac2Dec(f).value);
	}

	@Override
	public Value subtract(Matrix m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value subtract(Irrational i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value divide(Decimal d) {
		// TODO Auto-generated method stub
		return new Decimal(value / d.value);
	}

	@Override
	public Value divide(Fraction f) {
		// TODO Auto-generated method stub
		return new Decimal(value / Fraction.Frac2Dec(f).value);
	}

	@Override
	public Value divide(Matrix m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value divide(Irrational i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value power(Decimal d) {
		// TODO Auto-generated method stub
		return new Decimal(Math.pow(value, d.value));
	}

	@Override
	public Value power(Fraction f) {
		// TODO Auto-generated method stub
		return new Decimal(Math.pow(value, Fraction.Frac2Dec(f).value));
	}

	@Override
	public Value power(Matrix m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value power(Irrational i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value sin() {
		// TODO Auto-generated method stub
		return new Decimal(Math.sin(convertAngle2Rad(value)));
	}

	@Override
	public Value cos() {
		// TODO Auto-generated method stub
		return new Decimal(Math.cos(convertAngle2Rad(value)));
	}

	@Override
	public Value tan() {
		// TODO Auto-generated method stub
		
		return new Decimal(Math.tan(convertAngle2Rad(value)));
	}

	@Override
	public Value invSin() {
		// TODO Need to make this adjust for current TrigUnit setting
		// i.e.   Rad, Deg, Grad
		return new Decimal(Math.asin(value));
	}

	@Override
	public Value invCos() {
		// TODO Need to make this adjust for current TrigUnit setting
		// i.e.   Rad, Deg, Grad
		return new Decimal(Math.acos(value));
	}

	@Override
	public Value invTan() {
		// TODO Need to make this adjust for current TrigUnit setting
		// i.e.   Rad, Deg, Grad
		return new Decimal(Math.acos(value));
	}
	
	/**
	 * Takes an angle in the current units and converts it to radians, for use
	 * with the standard JAVA math functions involving trig.
	 * @param angle - an angle value in the current unit type
	 * @return the value of the angle in radians, if Rad passed in, passed back
	 */
	private double convertAngle2Rad(double angle) {
		// TODO Auto-generated method stub
		if(parser.getAngleUnits()== 2){
			angle *= (Math.PI/180);
		}
		else if(parser.getAngleUnits() == 3){
			angle *= (Math.PI/200);
		}
		return angle;
	}
	
	/**
	 * Takes an angle in radians and converts it to the needed angleUnits.
	 * @param angle - in radians
	 * @return angle in the current angleUnits
	 */
	private double convertAngleFromRad(double angle) {
		// TODO Auto-generated method stub
		if(parser.getAngleUnits() == 2){
			angle *= (180/Math.PI);
		}
		else if(parser.getAngleUnits() == 3){
			angle *= (200/Math.PI);
		}
		return angle;
	}

	@Override
	public Value neg() {
		// TODO Auto-generated method stub
		return new Decimal(-1 * value);
	}

	@Override
	public Decimal toDec() throws EvalException {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Value assign(Value v) throws EvalException {
		// TODO Auto-generated method stub
		throw new EvalException("Cannot assign a value to a Decimal");
	}
}
