package tree;

public abstract class Value {
	

	private Expression parent;
	private static ExpressionParser parser;
	
	public Value(ExpressionParser p){
		parser = p;
	}
	
	public Value(){
	}
	
	public void setParent(Expression e){
		parent = e;
	}
	
	public abstract Value eval() throws EvalException;
	
	public boolean hasParent(){
		if (parent != null){
			return true;
		}
		else return false;
	}
	
	public ExpressionParser getParser(){
		return parser;
	}
	
	public Expression getParent(){
		return parent;
	}
	
	public abstract Value multiply(Decimal d);
	
	public abstract Value multiply(Fraction f);
	
	public abstract Value multiply(Matrix m);
	
	public abstract Value multiply(Irrational i);
	
	
	
	public abstract Value add(Decimal d);
	
	public abstract Value add(Fraction f);
	
	public abstract Value add(Matrix m);
	
	public abstract Value add(Irrational i);
	
	
	
	public abstract Value subtract(Decimal d);
	
	public abstract Value subtract(Fraction f);
	
	public abstract Value subtract(Matrix m);
	
	public abstract Value subtract(Irrational i);
	
	
	
	public abstract Value divide(Decimal d);
	
	public abstract Value divide(Fraction f);
	
	public abstract Value divide(Matrix m);
	
	public abstract Value divide(Irrational i);
	
	
	
	public abstract Value power(Decimal d);
	
	public abstract Value power(Fraction f);
	
	public abstract Value power(Matrix m);
	
	public abstract Value power(Irrational i);
	
	
	public abstract Value squareRoot() throws EvalException;
	
	public abstract Value sin() throws EvalException;
	
	public abstract Value cos() throws EvalException;
	
	public abstract Value tan() throws EvalException;
	
	public abstract Value invSin() throws EvalException;

	public abstract Value invCos() throws EvalException;
	
	public abstract Value invTan() throws EvalException;

	public abstract Value neg() throws EvalException;
	
	public abstract Value log() throws EvalException;
	
	public abstract Value natLog() throws EvalException;

	public Value add(Value v){
		if (v instanceof Decimal){
			return add((Decimal) v);
		}
		else if (v instanceof Fraction){
			return add((Fraction) v);
		}
		else if (v instanceof Matrix){
			return add((Matrix) v);
		}
		else if (v instanceof Irrational){
			return add((Irrational) v);
		}
		else if (v instanceof Var){
			return add(((Var)v).getValue());
		}
		else{
			return null;
		}
	}

	public Value subtract(Value v){
		if (v instanceof Decimal){
			return subtract((Decimal) v);
		}
		else if (v instanceof Fraction){
			return subtract((Fraction) v);
		}
		else if (v instanceof Matrix){
			return subtract((Matrix) v);
		}
		else if (v instanceof Irrational){
			return subtract((Irrational) v);
		}
		else if (v instanceof Var){
			return subtract(((Var)v).getValue());
		}
		else{
			return null;
		}
	}
	
	public Value multiply(Value v){
		if (v instanceof Decimal){
			return multiply((Decimal) v);
		}
		else if (v instanceof Fraction){
			return multiply((Fraction) v);
		}
		else if (v instanceof Matrix){
			return multiply((Matrix) v);
		}
		else if (v instanceof Irrational){
			return multiply((Irrational) v);
		}
		else if (v instanceof Var){
			return multiply(((Var)v).getValue());
		}
		else{
			return null;
		}
	}
	
	public Value divide(Value v){
		if (v instanceof Decimal){
			return divide((Decimal) v);
		}
		else if (v instanceof Fraction){
			return divide((Fraction) v);
		}
		else if (v instanceof Matrix){
			return divide((Matrix) v);
		}
		else if (v instanceof Irrational){
			return divide((Irrational) v);
		}
		else if (v instanceof Var){
			return divide(((Var)v).getValue());
		}
		else{
			return null;
		}
	}

	public Value power(Value v) {
		// TODO Auto-generated method stub
		if (v instanceof Decimal){
			return power((Decimal) v);
		}
		else if (v instanceof Fraction){
			return power((Fraction) v);
		}
		else if (v instanceof Matrix){
			return power((Matrix) v);
		}
		else if (v instanceof Irrational){
			return power((Irrational) v);
		}
		else if (v instanceof Var){
			return power(((Var)v).getValue());
		}
		else{
			return null;
		}
	}

	public abstract Value assign(Value v) throws EvalException;
	
	public abstract Decimal toDec() throws EvalException;
	//write more methods like the ones above, to route
	//a call to the appropriate method
	
}
