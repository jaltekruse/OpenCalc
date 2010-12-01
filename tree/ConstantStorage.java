package tree;


public class ConstantStorage extends ValueStorage {

	public ConstantStorage(){
		addGroup(new ValStorageGroup("math"));
		storeInGroup("math", new Constant("pi", new Decimal (Math.PI)));
		storeInGroup("math", new Constant("e", new Decimal(Math.E)));
		addGroup(new ValStorageGroup("physics"));
		storeInGroup("physics", new Constant("g", new Decimal(-9.8)));
		addGroup(new ValStorageGroup("chem"));
		storeInGroup("chem", new Constant("mol", new Decimal(6.022E23)));
		storeInGroup("chem", new Constant("mwC", new Decimal(14)));
	}
}
