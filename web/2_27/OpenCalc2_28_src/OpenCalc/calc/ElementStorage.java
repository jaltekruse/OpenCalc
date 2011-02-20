package calc;

import java.util.ArrayList;

public abstract class ElementStorage {
	ArrayList<ElmStorageGroup> groups;
	calc basicCalcObj;
	int firstIndexWithElm, numGroupsRemaining, typeStorage;
	//typeStorage  1- Var     2-Const
	String nameOfLast;
	
	public ElementStorage(calc basicCalc, int type){
		groups = new ArrayList<ElmStorageGroup>();
		basicCalcObj = basicCalc;
		typeStorage = type;
	}
	
	public ElementWithIdentifier findIfStored(String s){
		ElementWithIdentifier tempElm;
		for(int i = 0; i < groups.size(); i++){
			tempElm = ((ElmStorageGroup) groups.get(i)).findIfStored(s);
			if(tempElm != null)
				return tempElm;
		}
		return null;
	}
	
	public ElementWithIdentifier storeInGroup(String group, ElementWithIdentifier e){
		int index = findGroupIndex(group);
		if (index != Integer.MAX_VALUE){
			return groups.get(index).storeElm(e);
		}
		return null;
	}
	
	public boolean isInGroup(String group, String var){
		if(findGroupIndex(group) != Integer.MAX_VALUE){
			if (groups.get(findGroupIndex(group)).findIfStored(var) != null)
				return true;
		}
			return false;
	}
	
	public int findGroupIndex(String s){
		for(int i = 0; i < groups.size(); i++){
			if(groups.get(i).getGroupName().equals(s))
				return i;
		}
		return Integer.MAX_VALUE;
	}
	
	public void toggleSelect(int index){
		if(groups.get(index).isSelected()){
			groups.get(index).setSelected(false);
		}
		else{
			groups.get(index).setSelected(true);
		}
	}
	
	public String findGroupName(int index){
		return groups.get(index).getGroupName();
	}
	
	public String getFirstStored(){
		String currFirst = null;
		firstIndexWithElm = 0;
		int index = 0, i;
		for (i = firstIndexWithElm; i < groups.size(); i++){
			if(groups.get(i).getFirst() == null){
				continue;
			}
			else if(currFirst == null){
				currFirst = groups.get(i).getFirst().getName();
				index = i;
				continue;
			}
			
			if (currFirst.compareTo(groups.get(i).getFirst().getName()) > 0){
				currFirst = groups.get(i).getLastReturned().getName();
				index = i;
			}
		}
		groups.get(index).getNext();
		return currFirst;
	}
	
	public String getNextStored(){
		int i;
		int nextIndex = 0;
		String name;
		String currFirst = null;
		for (i = firstIndexWithElm; i < groups.size(); i++){
			if(!groups.get(i).hasNext()){
				continue;
			}
			else if(currFirst == null){
				currFirst = groups.get(i).getLastReturned().getName();
				nextIndex = i;
				continue;
			}
			if (currFirst.compareTo(groups.get(i).getLastReturned().getName()) > 0){
				currFirst = groups.get(i).getLastReturned().getName();
				nextIndex = i;
			}
		}
		if(currFirst != null){
			name = groups.get(nextIndex).getLastReturned().getName();
			if (groups.get(nextIndex).getNext() == null){
				nameOfLast = null;
			}
			return name;
		}
		else{
			return null;
		}
	}
	
	public void addGroup(ElmStorageGroup group){
		groups.add(group);
	}
	
	public ArrayList<ElmStorageGroup> getGroups(){
		return groups;
	}
	
	public int getTypeStorage(){
		return typeStorage;
	}
	
	public void setTypeStorage(int i){
		typeStorage = i;
	}
}