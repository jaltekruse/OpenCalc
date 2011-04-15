package tree;

public class TreeTester {

	public static void main(String[] args){
		String expression = "sin 324.23!+23.3";
		ExpressionParser parser = new ExpressionParser();
		Value parsedExpression = null;
		if (args.length > 0){
			if (args.length == 1){
				try {
					System.out.println(args[0]);
					parsedExpression = parser.ParseExpression(args[0]);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				System.out.println("please give one argument, an expression to be evaluated");
			}
		}
		else{
			try {
				System.out.println(expression);
				parsedExpression = parser.ParseExpression(expression);
				System.out.println(expression);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			System.out.println(parsedExpression.eval());
		} catch (EvalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
