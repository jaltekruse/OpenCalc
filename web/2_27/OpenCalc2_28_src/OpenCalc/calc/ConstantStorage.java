package calc;

public class ConstantStorage extends ElementStorage {

	public ConstantStorage(calc basicCalc) {
		super(basicCalc, 2);

		addGroup(new ElmStorageGroup("math"));
		storeInGroup("math", new Constant("pi", Math.PI));
		storeInGroup("math", new Constant("e", Math.E));
		addGroup(new ElmStorageGroup("physics"));
		storeInGroup("physics", new Constant("g", -9.8));
		addGroup(new ElmStorageGroup("chem"));
		storeInGroup("chem", new Constant("mol", 2.066E23));
		storeInGroup("chem", new Constant("mwC", 14));

	}
}
