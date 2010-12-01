package tree;

public enum Operator {
	
	
	NOTHING(0),
	ASSIGN(1),	GT(1),		LT(1),		EQ(1),		NOT(1),
	AND(1),		OR(1),		NE(1),		GTE(1),		LTE(1),
	ADD(2),		SUBTRACT(2),
	MULTIPLY(3),DIVIDE(3), 
	POWER(4),	SQUARE(4),	CUBE(4),	SQRT(4),
	SIN(5),		COS(5),
	TAN(5),		INV_COS(5),	INV_SIN(5),	INV_TAN(5),	LN(5),
	LOG(5),		ABS(5),		FLOOR(5),	CEILING(5),	INT(5),
	ROUND(5),	NEG(5),		FACT(5),
	PAREN(6),	BRACKET(6),	CURL_BRAC(6),
	SOLVE(7)
	;
	
	//these are not in any particular order
	private static String[] requirePrevNum = {"!", "^", "*", "/", "-", "+", "^-1"};
	
	
	private final int prec;
	
	Operator(int precedence){
		prec = precedence;
	}

	public int getPrec() {
		return prec;
	}
	
	public boolean isUrinaryPost(){
		if(this == FACT)
			return true;
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
}
