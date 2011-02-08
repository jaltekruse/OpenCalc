package tree;

import java.util.ArrayList;

public class Fraction extends Number{
	private int d;
	private int n;
	
	public Fraction(int n, int d){
		setDenominator(d);
		setNumerator(n);
	}

	public void setNumerator(int numerator) {
		n = numerator;
	}

	public int getNumerator() {
		return n;
	}

	public void setDenominator(int denominator) {
		d = denominator;
	}

	public int getDenominator() {
		return d;
	}

	@Override
	public Value eval() throws EvalException {
		return this;
	}
	
	public String toString(){
		if (d == 1){
			return "" + n;
		}
		return "[" + n + "/" + d + "]";
	}
	
	public static Fraction Dec2Frac(Decimal d){
		double temp = d.getValue();
		double remainder;
		int counter = 1;
		do{
			counter--;
			remainder = (temp/Math.pow(10, counter))%1;
		}while(remainder != 0);
		return new Fraction((int) Math.round(temp * Math.pow(10, -1 * counter)), 
				(int) Math.round((Math.pow(10, -1* counter))));
	}
	
	public static Decimal frac2Dec(Fraction f){
		return new Decimal(((double)f.n) / f.d);
	}
	
	public Fraction reduce(){
		if (d == 1){
			return this;
		}
		ArrayList<Integer> nList = new ArrayList<Integer>();
		ArrayList<Integer> dList = new ArrayList<Integer>();
		nList.add(n);
		dList.add(d);
		for (int i = Math.abs(n/2); i > 0; i--){
			if (n % i == 0){
				nList.add(i);
			}
		}
		for (int i = Math.abs(d/2); i > 0; i--){
			if (d % i == 0){
				dList.add(i);
			}
		}
		for (int i : nList){
			if (findInList(i, dList)){
				return new Fraction(n/i, d/i);
			}
		}
		return this;
	}
	
	private boolean findInList(int i, ArrayList<Integer> list){
		for (int a: list){
			if (a == i){
				return true;
			}
		}
		return false;
	}
	@Override
	public Value multiply(Decimal d) {
		// Compare this to the add method below, this is a shorter line of code, but
		// this logic can only be used with commutative operators, should we
		//write them all like add(Decimal d) for clarity?
		return d.multiply(frac2Dec(this));
	}

	@Override
	public Value multiply(Fraction f) {
		// TODO Auto-generated method stub
		return new Fraction(this.n * f.n, this.d * f.d);
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
		return new Decimal(frac2Dec(this).getValue() + d.getValue());
	}

	@Override
	public Value add(Fraction f) {
		// TODO Auto-generated method stub
		return new Fraction(n * f.d + f.n * d, d * f.d);
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
		return new Decimal(frac2Dec(this).getValue() - d.getValue());
	}

	@Override
	public Value subtract(Fraction f) {
		// TODO Auto-generated method stub
		return new Fraction(n * f.d - f.n * d, d * f.d);
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
		return new Decimal(frac2Dec(this).getValue() / d.getValue());
	}

	@Override
	public Value divide(Fraction f) {
		// TODO Auto-generated method stub
		return new Fraction(this.n * f.d, this.d * f.n);
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
		return new Decimal(Math.pow(frac2Dec(this).getValue(), d.getValue()));
	}

	@Override
	public Value power(Fraction f) {
		// TODO Auto-generated method stub
		if (f instanceof Fraction){
			if (f.d == 1){
				if (n < 100 && d < 100 && f.n < 5 && f.n > -1){
					return new Fraction((int) Math.pow(n, f.n), (int) Math.pow(d, f.n));
				}
				else if (n < 100 && d < 100 && f.n > -5 && f.n < 0){
					return new Fraction(1, (int) Math.pow(n, -1 * f.n));
				}
			}
		}
		return new Decimal(Math.pow(frac2Dec(this).getValue(), frac2Dec(f).getValue()));
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
	public Value sin() throws EvalException {
		// TODO Auto-generated method stub
		return toDec().sin();
	}

	@Override
	public Value cos() throws EvalException {
		// TODO Auto-generated method stub
		return toDec().cos();
	}

	@Override
	public Value tan() throws EvalException {
		// TODO Auto-generated method stub
		return toDec().tan();
	}

	@Override
	public Value invSin() throws EvalException {
		// TODO Need to make this adjust for current TrigUnit setting
		// i.e.   Rad, Deg, Grad
		return toDec().invSin();
	}

	@Override
	public Value invCos() throws EvalException {
		// TODO Need to make this adjust for current TrigUnit setting
		// i.e.   Rad, Deg, Grad
		return toDec().invCos();
	}

	@Override
	public Value invTan() throws EvalException {
		// TODO Need to make this adjust for current TrigUnit setting
		// i.e.   Rad, Deg, Grad
		return toDec().invTan();
	}

	@Override
	public Value neg() {
		// TODO Auto-generated method stub
		return new Fraction(-1 * n, d);
	}

	@Override
	public Decimal toDec() throws EvalException {
		// TODO Auto-generated method stub
		return frac2Dec(this);
	}

	@Override
	public Value assign(Value v) throws EvalException {
		// TODO Auto-generated method stub
		throw new EvalException("Cannot assign a value to a Fraction");
	}

	@Override
	public Value squareRoot() throws EvalException {
		// TODO Auto-generated method stub
		if (Math.sqrt(n)%1 == 0 && Math.sqrt(d)%1 == 0){
			return new Fraction((int) Math.sqrt(n), (int) Math.sqrt(d));
		}
		else{
			return new Decimal(Math.sqrt(toDec().getValue()));
		}
	}

	@Override
	public Value log() throws EvalException {
		// TODO Auto-generated method stub
		return new Decimal(frac2Dec(this).log().getValue());
	}

	@Override
	public Value natLog() throws EvalException {
		// TODO Auto-generated method stub
		return new Decimal(frac2Dec(this).natLog().getValue());
	}
}
