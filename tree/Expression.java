package tree;

public abstract class Expression extends Value {

	Operator op;
	public Expression()
	{
		
	}
	
	public Expression(Operator o)
	{
		op = o;
	}
	
	public Operator getOp()
	{
		return op;
	}
	
	public void setOp(Operator o)
	{
		op = o;
	}
	
	public boolean isContainerOp()
	{
		return (op == Operator.PAREN || op == Operator.BRACKET || op == Operator.CURL_BRAC);
	}
}
