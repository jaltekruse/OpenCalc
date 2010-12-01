package tree;

public abstract class NumberWithName extends Number implements ValueWithName {

	String name;
	
	public NumberWithName(String s) {
		name = s;
	}
	
	public String getName(){
		return name;
	}

	@Override
	public Value eval() throws EvalException {
		// TODO Auto-generated method stub
		return null;
	}

}
