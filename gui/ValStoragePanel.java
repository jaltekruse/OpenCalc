package gui;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;

import tree.ValStorageGroup;
import tree.Value;
import tree.ValueStorage;
import tree.Constant;
import tree.Number;
import tree.Var;

public class ValStoragePanel extends SubPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5863708616289679710L;
	
	private NewCalc calcObj;
	private OCButton varButton, makeVar;
	private ValueStorage elements;
	private SubPanel groups, elmButtons, addElm;
	private OCTextField name, groupName, value;
	private OCLabel elmName, val, group;
	private ArrayList<JCheckBox> groupSelect;
	private int i;


	public ValStoragePanel(NewCalc currCalcObj, ValueStorage e) {
		calcObj = currCalcObj;
		elements = e;
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		groups = new SubPanel();
		//groups.setBorder(BorderFactory.createTitledBorder("Groups"));
		elmButtons = new SubPanel();
		addElm = new SubPanel();
		//addElm.setBorder(BorderFactory.createTitledBorder("Make/Set"));
		
		elmName = new OCLabel("Name:", 1, 1, 0, 0, addElm, calcObj);
		name = new OCTextField(true, 9, 1, 1, 1, 0, addElm, calcObj){
			public void associatedAction(){
				//do nothing, must hit button 
			}
		};
		
		val = new OCLabel("Val:", 1, 1, 2, 0, addElm, calcObj);
		value = new OCTextField(true, 9, 1, 1, 3, 0, addElm, calcObj){
			public void associatedAction(){
				//do nothing, must hit button
			}
		};
		
		group = new OCLabel("Group:", 1, 1, 0, 1, addElm, calcObj);
		groupName = new OCTextField(true, 9, 1, 1, 1, 1, addElm, calcObj){
			public void associatedAction(){
				//do nothing, must hit button
			}
		};
		
		makeVar = new OCButton("make/set", 1, 1, 3, 1, addElm, calcObj){
			public void associatedAction(){
				String elmStr = name.getText();
				String valStr = value.getText();
				String groupStr = groupName.getText();
				Value newVarVal;
				try{ //evaluate the value being given to the Var
					newVarVal = calcObj.getParser().ParseExpression(valStr);
					newVarVal = newVarVal.eval();
				}
				catch(Exception e){
					groupName.setText("error calculating");
					return;
				}
				if(elmStr.equals(""))
				{//if there is no value given
					if(!groupStr.equals(""))
					{//a group name was entered
						if(elements.findGroupIndex(groupStr) == Integer.MAX_VALUE)
						{//the group currently does not exist
							elements.addGroup(new ValStorageGroup(groupStr));
						}
					}
					else{
						return;
					}
				}
				else
				{
					if(groupStr.equals("") || groupStr.equals("var exists") || 
							groupStr.equals("const exists"))
					{//no group given, or input matches one of the messages put in group box
						groupStr = "user";//assume they want it group user
						groupName.setText(groupStr);
					}
					if(elements.findGroupIndex(groupStr) == Integer.MAX_VALUE)
					{//if the group given does not exist
						elements.addGroup(new ValStorageGroup(groupStr));
					}
					
					if(calcObj.getParser().CONSTLIST.findIfStored(elmStr) != null)
					{//if the name given is already a constant
						groupName.setText("constant exists");
						value.setText("no reassignment");
						return;
					}
					if(calcObj.getParser().VARLIST.findIfStored(elmStr) != null)
					{//if var exists, cannot be moved to another group
						groupName.setText("var exists");
							value.setText(newVarVal.toString());
							calcObj.getParser().VARLIST.setVarVal(elmStr, (Number) newVarVal);
							calcObj.getGraphObj().repaint();
							calcObj.getGridProps().refreshAttributes();
						return;
					}
					
					if(elements.getTypeStorage() == 1)
					{//if this panel is for Variables
						value.setText(newVarVal.toString());
						elements.storeInGroup(groupStr, new Var(elmStr, (Number) newVarVal));
						calcObj.getGraphObj().repaint();
						calcObj.getGridProps().refreshAttributes();
					}
					else if(elements.getTypeStorage() == 2)
					{//if this panel is for Constants
							value.setText(newVarVal.toString());
							elements.storeInGroup(groupStr, new Constant(elmStr, (Number) newVarVal));
						}
						else{
							value.setText("needs val");
						}
					}
				refreshButtons();
				}
			};
		
		groupSelect = new ArrayList<JCheckBox>();
		
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
		this.add(elmButtons, bCon);
		refreshButtons();
	}
	
	public void refreshButtons() { 
		
		groups.removeAll();
		groupSelect = new ArrayList<JCheckBox>();
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
		int buttonPaneHeight = 0;
		elmButtons.removeAll();
		String last = elements.getFirstStored();
		for (i = 0; last != null; i++){
			new OCButton(last, 1, 1, i%3, i/3, elmButtons, calcObj);
			last = elements.getNextStored();
		}
		
		if (i%3 == 0)
			buttonPaneHeight = ((i/3) - 1) * 33;
		else
			buttonPaneHeight = i/3 * 33;
		
		elmButtons.setPreferredSize(new Dimension(280, buttonPaneHeight));
		this.setPreferredSize(new Dimension(280, buttonPaneHeight + 100));
		
		elmButtons.revalidate();
		elmButtons.repaint();
	}
}
