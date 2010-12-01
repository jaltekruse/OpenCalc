package tree;

public class BinExpression extends Expression {
	
	private Value leftChild;
	private Value rightChild;
	
	public BinExpression(){
		
	}
	
	public BinExpression(Operator oper){
		super(oper);
	}
	
	public void setOp(Operator oper) {
		// TODO Auto-generated method stub
		op = oper;
	}
	
	public Operator getOp(){
		return op;
	}

	public void setLeftChild(Value child){
		leftChild = child;
		leftChild.setParent(this);
	}
	
	public Value getLeftChild(){
		return leftChild;
	}
	
	public void setRightChild(Value child){
		rightChild = child;
		rightChild.setParent(this);
	}
	
	public Value getRightChild(){
		return rightChild;
	}
	
	@Override
	public Value eval() throws EvalException{
		
		Value leftVal = leftChild.eval();

		Value rightVal = rightChild.eval();
		
		switch(op){
			case ADD:		return leftVal.add(rightVal);
			case SUBTRACT:	return leftVal.subtract(rightVal);
			case MULTIPLY:	return leftVal.multiply(rightVal);
			case DIVIDE:	return leftVal.divide(rightVal);
			case POWER:		return leftVal.power(rightVal);
			case ASSIGN:	return leftVal.assign(rightVal);
		}
		throw new EvalException("unrecognized operation");
	}

	public String toString(){
		String result = new String();
		if (getLeftChild() != null)
			result += leftChild.toString();
		if (getOp() != null)
			switch(op){
			case ADD:
				result += "+";
				break;
			case SUBTRACT:
				result += "-";
				break;
			case MULTIPLY:
				result += "*";
				break;
			case DIVIDE:
				result += "/";
				break;
			case POWER:
				result += "^";
				break;
			case ASSIGN:
				result += "=";
				break;
		}
		else{
			result += " no op ";
		}
		if (getRightChild() != null)
			result += rightChild.toString();
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
