package tree;

public class UrinaryExpression extends Expression {
	
	private Value child;
	
	public UrinaryExpression(Operator o){
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
			
			child = child.eval();
			
			switch(op){
				case SIN:		return child.sin();
				case COS:		return child.cos();
				case TAN:		return child.tan();
				case INV_SIN:	return child.invSin();
				case INV_COS:	return child.invCos();
				case INV_TAN:	return child.invTan();
				case NOTHING:	return child.eval();
				case PAREN:		return child.eval();
				case NEG:		return child.neg();
				default:
					throw new EvalException("unrecognized operation");
			}
		}
		throw new EvalException("a urinary operator did not have a value");
	}
	
	public String toString(){
		String result = new String();
		if (op != null)
			if (op == Operator.PAREN){
				result += "(";
			}
			else
			{
				result += op.name(); 
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
	
}
