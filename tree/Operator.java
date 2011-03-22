package tree;

public enum Operator {
	
	ASSIGN(1, "="),		GT(1, null),		LT(1, null),		EQ(1, null),		NOT(1, null),
	AND(1, null),		OR(1, null),		NE(1, null),		GTE(1, null),		LTE(1, null),
	ADD(2, "+"),		SUBTRACT(2, "-"),
	MULTIPLY(3, "*"), 	DIVIDE(3, "/"), 
	POWER(4, "^"),		SQUARE(4, null),	CUBE(4, null),		SQRT(4, "sqrt"),
	SIN(5, "sin"),		COS(5, "cos"),
	TAN(5, "tan"),		INV_COS(5, "cos-1"),	INV_SIN(5, "sin-1"),	INV_TAN(5, "tan-1"),	LN(5, "ln"),
	LOG(5, "log"),		ABS(5, "abs"),		FLOOR(5, "floor"),	CEILING(5, "ceil"),	INT(5, "int"),
	ROUND(5, "round"),	NEG(5, "neg"),		FACT(5, "!"), INTEGRAL(5, "integral"),
	PAREN(6, null),	BRACKET(6, null),	CURL_BRAC(6, null),
	SOLVE(7, "solve")
	;
	
	//these are not in any particular order
	private static String[] requirePrevNum = {"!", "^", "*", "/", "-", "+", "^-1"};
	
	
	private final int prec;
	private final String symbol;
	
	Operator(int precedence, String s){
		prec = precedence;
		symbol = s;
	}

	public int getPrec() {
		return prec;
	}
	
	public boolean isUrinaryPost(){
		if(this == FACT)
		{
			return true;
		}
		return false;
	}
	
	/**Takes a string representing an operator, determines if it requires a
	 *previous num by searching the hard coded list of operators.
	 *
	 *@param s - string representing an operator
	 *@return true if it does require previous, else false
	 */
	public static boolean requiresPrevious(String s){
		for(int i = 0; i < requirePrevNum.length; i++){
			if (s.equals(requirePrevNum[i])){
				return true;
			}
		}
		return false;
	}

	public String getSymbol() {
		return symbol;
	}
}
