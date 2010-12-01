package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tree.EvalException;
import tree.ParseException;
import tree.Value;
import tree.Number;

public class OCTextWithValButton extends JPanel {

	NewCalc calcObj;
	OCTextField field;
	OCButton button;
	String varName;

	public OCTextWithValButton(String s, boolean editable, int length,
			int gridWidth, int gridHeight, int gridx, int gridy,
			JComponent comp, NewCalc currCalcObj) {

		varName = s;
		calcObj = currCalcObj;
		super.setLayout(new GridBagLayout());
		GridBagConstraints pCon = new GridBagConstraints();
		pCon.fill = GridBagConstraints.BOTH;
		pCon.gridwidth = gridWidth;
		pCon.gridheight = gridHeight;
		pCon.gridx = gridx;
		pCon.gridy = gridy;
		pCon.weightx = 1;
		pCon.weighty = 1;

		comp.add(this, pCon);

		field = new OCTextField(editable, length, 2, gridHeight, 0, 0, this,
				calcObj) {
			public void associatedAction() {
				String currText = field.getText();
				if (!currText.equals(null) && !currText.equals("")
						&& calcObj != null) {
					JTextField currField = calcObj.getCurrTextField();
					Value v = null;
					try {
						v = calcObj.evalCalc(currText);
					} catch (EvalException e) {
						// TODO Auto-generated catch block
						//do something, right now just skips reassignment,
						//does not inform user of error
						return;
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						//do something, right now just skips reassignment,
						//does not inform user of error
						return;
					}
					currField.setText(v.toString());
					calcObj.getParser().getVarList().setVarVal(varName,
							(Number) v);
					calcObj.updateGraph();
				}
			}
		};
		button = new OCButton(varName, 1, 1, 2, 0, this, currCalcObj);
	}

	public String getVarName() {
		return varName;
	}

	public void setText(String s) {
		field.setText(s);
	}

	public OCTextField getTextField() {
		return field;
	}
}
