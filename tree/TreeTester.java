package tree;
import calc.Calc;
import java.io.*;
import java.util.Scanner;

public class TreeTester {

	private static ExpressionParser treeParser;
	private static Value v; 
	private static Calc listCalc; 
	private static double listResult;
	private static double treeResult;
	private static String hardCodedEx;
	private static String Ex;
	private static File logFile;
    private static FileOutputStream out;
    private static PrintStream p;
    private static Scanner docScanner;
	
	public static void main(String[] args){
		
		hardCodedEx = "5sin(.33theta)";
		Ex = hardCodedEx;
		
		if(hardCodedEx == null){
			if (args.length == 0){
				System.out.println("please provide and expression");
				return;
			}
			else
				Ex = args[0];
		}
		listCalc = new Calc();
		treeParser = new ExpressionParser();
		System.out.println(Ex);
		listCalc.parse(Ex);
		listResult = listCalc.solve();
		System.out.println("listCalc: " + listResult);
		try{
			System.out.println("@---Debugging info:---@\n" +
					"The first line in the block below is the expression\n" +
					"as it was scanned into the tree.\n" +
					"The lines following show the operations done \n" +
					"from hightst precedence to lowest.\n" +
					"NOTHING is an operator, it is used when there\n" +
					"is no operator provided to act on a number.\n" +
					"-----------------------------");
			v = treeParser.ParseExpression(Ex);
			v = v.eval();
			if (v instanceof Fraction){
				//we need to decide where method call should go,
				//calculations will want to be simplified, but
				//teaching algebra will require use to sometimes do
				//operations like adding without simplifying, and making
				//the students do that on their own
				v = ((Fraction) v).reduce();
			}
			treeResult = v.toDec().getValue();
			System.out.println("-----------------------------");
			System.out.println("treeCalc: " + v.toString());
		}
		catch (ParseException e){
			System.out.println("treeCalc had a parsing exception");
			System.out.println("message: " + e.getMessage());
			try{
				updateLog("treeCalc had an evaluating exception" + "\nmessage: " + e.getMessage());
			}
			catch (Exception ex){
				System.out.println("Error outputing to errorLog.txt file.");
			}
		}
		catch (EvalException e){
			System.out.println("treeCalc had an evaluating exception");
			System.out.println("message: " + e.getMessage());
			try{
				updateLog("treeCalc had an evaluating exception" + "\nmessage: " + e.getMessage());
			}
			catch (Exception ex){
				System.out.println("Error outputing to errorLog.txt file.");
			}
		}
		if(listResult == treeResult){
			System.out.println("!!---results match---!!");
		}
		else{
			System.out.println("difference :" + (listResult - treeResult));
			try{
				updateLog("treeCalc: " + treeResult + "\ndifference :" + (listResult - treeResult));
			}
			catch (Exception e){
				System.out.println("Error outputing to errorLog.txt file.");
			}
		}
		System.out.println("listCalc should be right");
	}
	
	private static void updateLog(String s) throws FileNotFoundException{
		logFile = new File("errorLog.txt");
		docScanner = new Scanner(logFile);
		String newLine;
		String fileContents = "";
		while(docScanner.hasNextLine()){
			newLine = docScanner.nextLine();
			fileContents += newLine + "\n";
		}
		
		out = new FileOutputStream("errorLog.txt");
        p = new PrintStream( out );
        
		p.println(fileContents);
        p.println("Error found in version last updated: " + treeParser.getDateModified());
        p.println("expression: " + Ex);
        p.println("listCalc: " + listResult);
        if (v != null){
        	p.println(v.toString());
        }
        p.println(s);
        p.close();

	}
}
