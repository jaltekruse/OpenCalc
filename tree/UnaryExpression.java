package tree;

public class UnaryExpression extends Expression {
	
	private Value child;
	
	public UnaryExpression(Operator o){
		super(o);
	}
	
	public void setChild(Value v){
		child = v;
		child.setParent(this);
	}
	
	public Value getChild(){
		return child;
	}
	
	public boolean hasChild(){
		if (child != null)
			return true;
		return false;
	}
	
	
	public Value eval() throws EvalException{
		if(child != null){
			
			Value childVal = child.eval();
			
			switch(op){
				case SIN:		return childVal.sin();
				case COS:		return childVal.cos();
				case TAN:		return childVal.tan();
				case INV_SIN:	return childVal.invSin();
				case INV_COS:	return childVal.invCos();
				case INV_TAN:	return childVal.invTan();
				case PAREN:		return childVal.eval();
				case NEG:		return childVal.neg();
				case SQRT:		return childVal.squareRoot();
				case LOG:		return childVal.log();
				case LN:		return childVal.natLog();
				default:
					throw new EvalException("unrecognized operation");
			}
		}
		throw new EvalException("a urinary operator did not have a value");
	}
	
	public String toString(){
		String result = new String();
//		System.out.println("op:");
		if (op != null)
			if (op == Operator.PAREN){
				result += "(";
			}
			else
			{
				result += op.getSymbol(); 
			}
		if (child != null)
			result += child.toString();
		if (op == Operator.PAREN){
			result += ")";
		}
		return result;
	}

	@Override
	public Value multiply(Decimal d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value multiply(Fraction f) {
		// TODO Auto-generated method stub
		return null;
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
		return null;
	}

	@Override
	public Value add(Fraction f) {
		// TODO Auto-generated method stub
		return null;
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
		return null;
	}

	@Override
	public Value subtract(Fraction f) {
		// TODO Auto-generated method stub
		return null;
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
		return null;
	}

	@Override
	public Value divide(Fraction f) {
		// TODO Auto-generated method stub
		return null;
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
		return null;
	}

	@Override
	public Value power(Fraction f) {
		// TODO Auto-generated method stub
		return null;
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
		return null;
	}

	@Override
	public Value cos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value tan() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value invSin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value invCos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value invTan() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value neg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Decimal toDec() throws EvalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value assign(Value v) throws EvalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value squareRoot() throws EvalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value log() throws EvalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value natLog() throws EvalException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
