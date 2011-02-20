package calc;
import java.util.ArrayList;


public class ConstantsList {
	private final static int NUM_BUCKETS = 47;
	private ArrayList[] constTable;
	public calc basicCalcObj;
	private String[] defaultConst;
	private double[] defaultConstVals;
	
	public ConstantsList(calc currCalc){
		constTable = new ArrayList[NUM_BUCKETS];
		for (int i = 0; i < NUM_BUCKETS; i++){
			constTable[i] = new ArrayList();
		}
		
		for(int i = 0; i < defaultConst.length - 1; i++){
			storeConst(defaultConst[i], defaultConstVals[i]);
		}
		basicCalcObj = currCalc;
	}
	
	private int generateHash(String s){
		if (s.equals(""))
			return Integer.MAX_VALUE;
		
		int hashVal = 0;
		for (int i = 0; i < s.length() - 1; i++){
			hashVal += s.charAt(i);
		}
		hashVal = hashVal/NUM_BUCKETS;
		return hashVal;
	}
	
	public Constant storeConst(String s, double val){
		int bucket = generateHash(s);
		Constant tempConst = new Constant("", 0);
		if (constTable[bucket].isEmpty()){
			tempConst = new Constant(s, val);
			constTable[bucket].add(tempConst);
			return tempConst;
		}
		else{
			for(int i = 0; i < constTable[bucket].size(); i++){
				//simply a sanity check, Constants should be the only thing stored
				if (constTable[bucket].get(i) instanceof Constant)
					tempConst = (Constant) constTable[bucket].get(i);
				if (s.equals(tempConst.getConstString())){
					//String represents a previously stored Var, which is returned
					return tempConst;
				}
			}
			
		}
		//the bucket does contain variables, but not the one requested, creating new
		tempConst = new Constant(s, val);
		constTable[bucket].add(tempConst);
		return tempConst;
	}
	
	public double getConstVal(String s){
		Constant currVar = storeConst(s, 0);
		return currVar.getValue();
	}
	
	public String[] getDefaultConst(){
		return defaultConst;
	}
}
