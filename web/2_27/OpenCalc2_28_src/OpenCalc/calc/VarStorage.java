package calc;

public class VarStorage extends ElementStorage {

	calc basicCalcObj;
	
	public VarStorage(calc basicCalc) {
		super(basicCalc, 1);
		basicCalcObj = basicCalc;
		 
		addGroup(new ElmStorageGroup("math"));
		storeInGroup("math", new Var("a"));
		storeInGroup("math", new Var("ans"));
		storeInGroup("math", new Var("b"));
		storeInGroup("math", new Var("c"));
		addGroup(new ElmStorageGroup("user"));
		addGroup(new ElmStorageGroup("physics"));
		storeInGroup("physics", new Var("speed"));
		storeInGroup("physics", new Var("weight"));
		storeInGroup("physics", new Var("v0"));
		storeInGroup("physics", new Var("vT"));
		addGroup(new ElmStorageGroup("graph"));
		storeInGroup("graph", new Var("thetaMin", 0));
		storeInGroup("graph", new Var("thetaStep", Math.PI/360));
		storeInGroup("graph", new Var("thetaMax", 2*Math.PI));
		storeInGroup("graph", new Var("theta"));
		storeInGroup("graph", new Var("r"));
		storeInGroup("graph", new Var("yMin", -10));
		storeInGroup("graph", new Var("xMin", -10));
		storeInGroup("graph", new Var("yMax", 10));
		storeInGroup("graph", new Var("xMax", 10));
		storeInGroup("graph", new Var("xStep", 1));
		storeInGroup("graph", new Var("yStep", 1));
		//storeInGroup("graph", new Var("n", 10));
		//storeInGroup("graph", new Var("U_n", 10));
		storeInGroup("graph", new Var("x"));
		storeInGroup("graph", new Var("y"));
		storeInGroup("graph", new Var("z"));
		
	}
	
	public Var storeVar(String s){
		if (findIfStored(s) != null)
			return (Var) findIfStored(s);
		Var newVar = (Var) storeInGroup("user", new Var(s));
		try{
			basicCalcObj.getGUI().getVarsPanel().refreshButtons();
		}
		catch(Exception e){
			System.out.println("error");
		}
		return newVar;
	}
	
	public double getVarVal(String s){
		ElementWithIdentifier tempElm = findIfStored(s);
		if (tempElm instanceof Var){
			return ((Var) tempElm).getValue();
		}
		return Double.MAX_VALUE;
	}
	
	public void setVarVal(String s, double val){
		ElementWithIdentifier tempElm = findIfStored(s);
		if (tempElm instanceof Var){
			((Var) tempElm).setValue(val);
		}
	}
	
	public void updateVarVal(String s, double val){
		ElementWithIdentifier tempElm = findIfStored(s);
		if (tempElm instanceof Var){
			((Var) tempElm).updateValue(val);
		}
		if(isInGroup("graph", s)){
			basicCalcObj.getGUI().getGridProps().refreshAttributes();
		}
	}
	
	public void multVarVal(String s, double val){
		ElementWithIdentifier tempElm = findIfStored(s);
		if (tempElm instanceof Var){
			((Var) tempElm).multValue(val);
		}
		if(isInGroup("graph", s)){
			basicCalcObj.getGUI().getGridProps().refreshAttributes();
		}
	}

}
