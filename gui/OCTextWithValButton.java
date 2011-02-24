package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tree.EvalException;
import tree.ParseException;
import tree.Value;
import tree.Number;

public class OCTextWithValButton extends OCTextField{

	MainApplet mainApp;
	OCTextField field;
	OCButton button;
	String varName;
	JPanel box;

	public OCTextWithValButton(TopLevelContainer topLevelComp, String s, boolean editable, int length,
			int gridWidth, int gridHeight, int gridx, int gridy,
			JComponent comp, MainApplet currmainApp) {
		
		super(topLevelComp);
		varName = s;
		mainApp = currmainApp;
		super.setLayout(new GridBagLayout());
		GridBagConstraints pCon = new GridBagConstraints();
		pCon.fill = GridBagConstraints.BOTH;
		pCon.gridwidth = gridWidth;
		pCon.gridheight = gridHeight;
		pCon.gridx = gridx;
		pCon.gridy = gridy;
		pCon.weightx = 1;
		pCon.weighty = 1;
		box = new SubPanel(getTopLevelContainer());
		
		comp.add(box, pCon);

		field = new OCTextField(getTopLevelContainer(), editable, length, 2, gridHeight, 0, 0, box,
				mainApp) {
			public void associatedAction() {
				String currText = field.getText();
				if (!currText.equals(null) && !currText.equals("")
						&& mainApp != null) {
					JTextField currField = mainApp.getCurrTextField().getField();
					Value v = null;
					try {
						v = mainApp.evalCalc(currText);
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
					mainApp.getParser().getVarList().setVarVal(varName,
							(Number) v);
					mainApp.updateGraph();
				}
			}
		};
		field.setBorder(null);
		box.setBorder(BorderFactory.createLineBorder(Color.gray));
		button = new OCButton(varName, 1, 1, 2, 0, box, currmainApp);
	}

	public String getVarName() {
		return varName;
	}

	public void setText(String s) {
		field.getField().setText(s);
	}

	public OCTextField getTextField() {
		return field;
	}
}
