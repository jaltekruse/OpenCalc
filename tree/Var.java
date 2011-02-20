package tree;

public class Var extends Value implements ValueWithName {

	private String varString;
	private Number num;
	
	public Var(){
	}
	
	public Var(String s, Number n) {
		varString = s;
		num = n;
		varString = s;
	}

	public Value setValue(Number n) {
		num = n;
		return num;
	}

	public Number getValue() {
		return num;
	}
	
	@Override
	public Value eval() throws EvalException{
		if (num == null){
			throw new EvalException("Variable \"" + getName() + "\" has not been given a value");
		}
		return num;
	}
	
	public String toString() {
		String varInfo = new String();
		varInfo += varString;
//		varInfo += "[";
//		varInfo += varString + " = ";
//		if (num != null)
//		{
//			varInfo += num.toString();
//		}
//		else
//		{
//			varInfo += "null";
//		}
//		varInfo += "]";
		return varInfo;
	}

	@Override
	public Value multiply(Decimal d) throws EvalException {
		// TODO Auto-generated method stub
		return num.multiply(d);
	}

	@Override
	public Value multiply(Fraction f) throws EvalException {
		// TODO Auto-generated method stub
		return num.multiply(f);
	}

	@Override
	public Value multiply(Matrix m) throws EvalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value multiply(Irrational i) throws EvalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value add(Decimal d) throws EvalException {
		// TODO Auto-generated method stub
		return num.add(d);
	}

	@Override
	public Value add(Fraction f) throws EvalException {
		// TODO Auto-generated method stub
		return num.add(f);
	}

	@Override
	public Value add(Matrix m) throws EvalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value add(Irrational i) throws EvalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value subtract(Decimal d) throws EvalException {
		// TODO Auto-generated method stub
		return num.subtract(d);
	}

	@Override
	public Value subtract(Fraction f) throws EvalException {
		// TODO Auto-generated method stub
		return num.subtract(f);
	}

	@Override
	public Value subtract(Matrix m) throws EvalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value subtract(Irrational i) throws EvalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value divide(Decimal d) throws EvalException {
		// TODO Auto-generated method stub
		return num.divide(d);
	}

	@Override
	public Value divide(Fraction f) throws EvalException {
		// TODO Auto-generated method stub
		return num.divide(f);
	}

	@Override
	public Value divide(Matrix m) throws EvalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value divide(Irrational i) throws EvalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value power(Decimal d) throws EvalException {
		// TODO Auto-generated method stub
		return num.power(d);
	}

	@Override
	public Value power(Fraction f) throws EvalException {
		// TODO Auto-generated method stub
		return num.power(f);
	}

	@Override
	public Value power(Matrix m) throws EvalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value power(Irrational i) throws EvalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value sin() throws EvalException {
		// TODO Auto-generated method stub
		return num.sin();
	}

	@Override
	public Value cos() throws EvalException {
		// TODO Auto-generated method stub
		return num.cos();
	}

	@Override
	public Value tan() throws EvalException {
		// TODO Auto-generated method stub
		return num.tan();
	}

	@Override
	public Value invSin() throws EvalException {
		// TODO Auto-generated method stub
		return num.invSin();
	}

	@Override
	public Value invCos() throws EvalException {
		// TODO Auto-generated method stub
		return num.invCos();
	}

	@Override
	public Value invTan() throws EvalException {
		// TODO Auto-generated method stub
		return num.invTan();
	}

	@Override
	public Value neg() throws EvalException {
		// TODO Auto-generated method stub
		return num.neg();
	}

	@Override
	public Decimal toDec() throws EvalException {
		// TODO Auto-generated method stub
		return num.toDec();
	}

	@Override
	public Value assign(Value val) throws EvalException {
		// TODO Auto-generated method stub
		if (val instanceof Var){
			return num = ((Var) val).getValue();
		}
		else
		{
			//going through the VarStorage object to assign variables allows additional 
			//actions to be triggered for all assignments, such as redrawing the graph 
			//if the value of xMin, yStep, etc. was assigned in the terminal
			//return parser.getVarList().setVarVal(varString, val);
			
			//correction, I would have used the above line to produce the result that I was
			//describing, but this made graphing slow, we can find some way to make it check for
			//regraphing only when the value was not assigned by the graphing algorithm itself
			if (val instanceof Number){
				return num = (Number) val;
			}
			throw new EvalException("A non-number value cannot be assigned to a variable");
		}
	}

	public void updateValue(double d) throws EvalException {
		// TODO Auto-generated method stub
		num = (Number) num.add(new Decimal(d));
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return varString;
	}

	@Override
	public Value squareRoot() throws EvalException {
		// TODO Auto-generated method stub
		return num.squareRoot();
	}

	@Override
	public Value log() throws EvalException {
		// TODO Auto-generated method stub
		return num.log();
	}

	@Override
	public Value natLog() throws EvalException {
		// TODO Auto-generated method stub
		return num.natLog();
	}
}
