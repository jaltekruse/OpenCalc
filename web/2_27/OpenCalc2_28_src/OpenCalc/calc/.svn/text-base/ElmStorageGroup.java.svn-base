package calc;

import java.util.ArrayList;

public class ElmStorageGroup {
	
	private int NUM_CHARS_TO_SCAN = 2;
	private int NUM_POSSIBLE_CHARS = 36;
	private final int NUM_BUCKETS = NUM_POSSIBLE_CHARS * NUM_POSSIBLE_CHARS;
	private ArrayList<ElementWithIdentifier>[] elmTable;
	private String groupName;
	private boolean hasNext;
	private boolean isSelected;
	
	private int CURR_BUCKET_NUM;
	private int CURR_ELM_NUM;
	private ElementWithIdentifier lastReturned;

	public ElmStorageGroup(String name) {
		elmTable = new ArrayList[NUM_BUCKETS];
		for (int i = 0; i < NUM_BUCKETS; i++) {
			elmTable[i] = new ArrayList<ElementWithIdentifier>();
		}
		groupName = name;
		setHasNext(false);
		setSelected(true);
	}

	private int generateHash(String s) {
		if (s.equals(""))
			return Integer.MAX_VALUE;
		s = s.toLowerCase();
		int hashVal = 0, numScanChars = NUM_CHARS_TO_SCAN;
		char currChar = '\0';
		if(s.length() == 1)
			numScanChars = 1;
		for (int i = 0; i < numScanChars; i++) {
			currChar = s.charAt(i);
			Character.toLowerCase(currChar);
			if(Character.isLetter(currChar))
				hashVal += Math.pow(36, 1-i) * (currChar - 'a');
			if(Character.isDigit(currChar))
				hashVal += Math.pow(36, 1-i) * (currChar - '0' + 25);
			if(currChar == '_')
				hashVal += Math.pow(36, 1-i) * 36;
		}
		return hashVal;
	}

	public ElementWithIdentifier storeElm(ElementWithIdentifier e) {
		int bucket = generateHash(e.getName());
		if (elmTable[bucket].isEmpty()) {
			elmTable[bucket].add(e);
			return e;
		}
		else {
			return storeInList(e);
		}
	}

	private ElementWithIdentifier storeInList(ElementWithIdentifier e){
		if (hasNext = false){
			setHasNext(true);
		}
		String name = e.getName();
		int bucket = generateHash(name);
		for (int index = 0; index < elmTable[bucket].size(); index++) {
			if (name.compareTo(elmTable[bucket].get(index).getName()) == 0){
				return elmTable[bucket].get(index);
			}
			else if (name.compareTo(elmTable[bucket].get(index).getName()) < 0){
				elmTable[bucket].add(index, e);
				return e;
			}
			else if (name.compareTo(elmTable[bucket].get(index).getName()) > 0){
				continue;
			}
		}
		elmTable[bucket].add(e);
		return e;
	}
	public ElementWithIdentifier findIfStored(String s){
		int bucket = generateHash(s);
		ElementWithIdentifier tempElm;
		for (int i = 0; i < elmTable[bucket].size(); i++) {
			tempElm = elmTable[bucket].get(i);
			if (s.equals(tempElm.getName())) {
				// String represents a previously stored Elm, which is
				// returned
				return tempElm;
			}
		}
		return null;
	}
	
	public ElementWithIdentifier getFirst(){
		if (isSelected == false){
			return null;
		}
		for (int i = 0; i < elmTable.length - 1; i++){
			if(elmTable[i].isEmpty())
				continue;
			else{
				CURR_BUCKET_NUM = i;
				CURR_ELM_NUM = 0;
				lastReturned = elmTable[i].get(0);
				setHasNext(true);
				return lastReturned;
			}
		}
		return null;
	}
	
	public ElementWithIdentifier getNext(){
		if (isSelected == false){
			return null;
		}
		if(CURR_ELM_NUM >= elmTable[CURR_BUCKET_NUM].size() - 1){
			for (int i = CURR_BUCKET_NUM + 1; i < elmTable.length; i++){
				if(elmTable[i].isEmpty())
					continue;
				else{
					CURR_BUCKET_NUM = i;
					CURR_ELM_NUM = 0;
					lastReturned = elmTable[i].get(0);
					return lastReturned;
				}
			}
			lastReturned = null;
			setHasNext(false);
			return null;
		}
		else if (CURR_BUCKET_NUM == NUM_BUCKETS){
			setHasNext(false);
			return null;
		}
		else{
			CURR_ELM_NUM++;
			lastReturned = elmTable[CURR_BUCKET_NUM].get(CURR_ELM_NUM);
			return lastReturned;
		}
	}
	
	public ElementWithIdentifier getLastReturned(){
		return lastReturned;
	}
	
	public String getGroupName(){
		return groupName;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public boolean hasNext() {
		return hasNext;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public boolean isSelected() {
		return isSelected;
	}
}
