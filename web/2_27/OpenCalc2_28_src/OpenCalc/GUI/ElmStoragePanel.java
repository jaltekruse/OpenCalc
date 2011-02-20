package GUI;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;

import calc.Constant;
import calc.ElementStorage;
import calc.ElmStorageGroup;
import calc.Var;
import calc.VarStorage;

public class ElmStoragePanel extends SubPanel {

	private NewCalc calcObj;
	private OCButton varButton, makeVar;
	private ElementStorage elements;
	private SubPanel groups, varButtons, addElm;
	private OCTextField name, groupName, value;
	private OCLabel elmName, val, group;
	private ArrayList<JCheckBox> groupSelect;
	private int i;


	public ElmStoragePanel(NewCalc currCalcObj, ElementStorage e) {
		calcObj = currCalcObj;
		elements = e;
		this.setLayout(new GridBagLayout());
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		groups = new SubPanel();
		varButtons = new SubPanel();
		addElm = new SubPanel();
		
		elmName = new OCLabel("Name:", 1, 1, 0, 0, addElm, calcObj);
		name = new OCTextField(true, 5, 1, 1, 1, 0, addElm, calcObj){
			public void associatedAction(){
				//do nothing
			}
		};
		
		val = new OCLabel("Val:", 1, 1, 2, 0, addElm, calcObj);
		value = new OCTextField(true, 5, 1, 1, 3, 0, addElm, calcObj){
			public void associatedAction(){
				//do nothing
			}
		};
		
		group = new OCLabel("Group:", 1, 1, 4, 0, addElm, calcObj);
		groupName = new OCTextField(true, 5, 1, 1, 5, 0, addElm, calcObj){
			public void associatedAction(){
				//do nothing
			}
		};
		
		makeVar = new OCButton("make/set", 1, 1, 6, 0, addElm, calcObj){
			public void associatedAction(){
				String elmStr = name.getText();
				String groupStr = groupName.getText();
				Double valNum = Double.MAX_VALUE;
				try{
					valNum = Double.valueOf(calcObj.evalCalc(value.getText()));
				}
				catch(Exception e){
					System.out.println("error with varValCreate");
				}
				if(elmStr.equals("")){
					if(!groupStr.equals("")){
						if(elements.findGroupIndex(groupStr) == Integer.MAX_VALUE){
							elements.addGroup(new ElmStorageGroup(groupStr));
						}
					}
					else{
						return;
					}
				}
				else{
					if(groupStr.equals("") || groupStr.equals("var exists") || 
							groupStr.equals("const exists")){
						groupStr = "user";
						groupName.setText(groupStr);
					}
					if(elements.findGroupIndex(groupStr) == Integer.MAX_VALUE){
						elements.addGroup(new ElmStorageGroup(groupStr));
					}
					
					if(calcObj.getCalcObj().CONSTLIST.findIfStored(elmStr) != null){
						groupName.setText("const exists");
						if (valNum != Double.MAX_VALUE)
							value.setText("no reassignment");
						return;
					}
					if(calcObj.getCalcObj().VARLIST.findIfStored(elmStr) != null){
						groupName.setText("var exists");
						if(valNum != Double.MAX_VALUE){
							value.setText(Double.toString(valNum));
							calcObj.getCalcObj().VARLIST.setVarVal(elmStr, valNum);
							calcObj.getGraphObj().repaint();
							calcObj.getGridProps().refreshAttributes();
						}
						return;
					}
					
					if(elements.getTypeStorage() == 1)
						if(valNum != Double.MAX_VALUE){
							value.setText(Double.toString(valNum));
							elements.storeInGroup(groupStr, new Var(elmStr, valNum));
							calcObj.getGraphObj().repaint();
							calcObj.getGridProps().refreshAttributes();
						}
						else{
							elements.storeInGroup(groupStr, new Var(elmStr));
						}
					else if(elements.getTypeStorage() == 2){
						if(valNum != Double.MAX_VALUE){
							value.setText(Double.toString(valNum));
							elements.storeInGroup(groupStr, new Constant(elmStr, valNum));
						}
						else{
							value.setText("needs val");
						}
					}
				}
				refreshButtons();
			}
		};
		
		groupSelect = new ArrayList();
		
		for(i = 0; i < elements.getGroups().size(); i++){
			final int tempInt = i;
			String name = e.findGroupName(i);
			groupSelect.add(tempInt, new JCheckBox(name));
			groupSelect.get(tempInt).setSelected(true);
			groupSelect.get(tempInt).addItemListener(new ItemListener(){

				@Override
				public void itemStateChanged(ItemEvent e) {
					// TODO Auto-generated method stub
					if (e.getStateChange() == ItemEvent.SELECTED){
						elements.getGroups().get(tempInt).setSelected(true);
						refreshButtons();
					}
					else if (e.getStateChange() == ItemEvent.DESELECTED){
						elements.getGroups().get(tempInt).setSelected(false);
						refreshButtons();
					}
				}
			});
			GridBagConstraints bCon = new GridBagConstraints();
			bCon.fill = GridBagConstraints.BOTH;
			bCon.weightx = 1;
			bCon.weighty = 1;
			bCon.gridheight = 1;
			bCon.gridwidth = 1;
			bCon.gridx = i;
			bCon.gridy = 0;
			groups.add(groupSelect.get(i), bCon);
		}
		
		GridBagConstraints bCon = new GridBagConstraints();
		bCon.fill = GridBagConstraints.BOTH;
		bCon.weightx = .5;
		bCon.weighty = 1;
		bCon.gridheight = 1;
		bCon.gridwidth = 1;
		bCon.gridx = 0;
		bCon.gridy = 0;
		this.add(addElm, bCon);
		bCon.gridy = 1;
		this.add(groups, bCon);
		bCon.gridheight = 1;
		bCon.weightx = 1;
		bCon.weighty = 1;
		bCon.gridx = 0;
		bCon.gridy = 2;
		this.add(varButtons, bCon);
		refreshButtons();
	}
	
	public void refreshButtons() { 
		
		groups.removeAll();
		groupSelect = new ArrayList();
		for(i = 0; i < elements.getGroups().size(); i++){
			final int tempInt = i;
			String name = elements.findGroupName(i);
			groupSelect.add(tempInt, new JCheckBox(name, elements.getGroups().get(i).isSelected()));
			groupSelect.get(tempInt).addItemListener(new ItemListener(){

				@Override
				public void itemStateChanged(ItemEvent e) {
					// TODO Auto-generated method stub
					if (e.getStateChange() == ItemEvent.SELECTED){
						elements.getGroups().get(tempInt).setSelected(true);
						refreshButtons();
					}
					else if (e.getStateChange() == ItemEvent.DESELECTED){
						elements.getGroups().get(tempInt).setSelected(false);
						refreshButtons();
					}
				}
			});
			GridBagConstraints bCon = new GridBagConstraints();
			bCon.fill = GridBagConstraints.BOTH;
			bCon.weightx = 1;
			bCon.weighty = 1;
			bCon.gridheight = 1;
			bCon.gridwidth = 1;
			bCon.gridx = i;
			bCon.gridy = 0;
			groups.add(groupSelect.get(i), bCon);
		}
		
		int i = 0;
		varButtons.removeAll();
		String last = elements.getFirstStored();
		for (i = 0; last != null; i++){
			new OCButton(last, 1, 1, i%4, i/4, varButtons, calcObj);
			last = elements.getNextStored();
		}
		varButtons.setPreferredSize(new Dimension(280,i/4 * 35));
		
		varButtons.revalidate();
		varButtons.repaint();
	}
}
