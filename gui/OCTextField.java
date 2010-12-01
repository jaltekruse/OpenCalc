package gui;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import tree.EvalException;
import tree.ParseException;
import tree.ValueNotStoredException;

public class OCTextField extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	NewCalc calcObj;
	OCTextField thisField;

	public OCTextField() {

	}

	public OCTextField(boolean editable, int length, int gridWidth,
			int gridHeight, int gridx, int gridy, JComponent comp,
			final NewCalc currCalcObj) {

		super(length);
		this.setEditable(editable);
		GridBagConstraints tCon = new GridBagConstraints();

		thisField = this;
		tCon.fill = GridBagConstraints.HORIZONTAL;
		tCon.weightx = .5;
		tCon.gridheight = 1;
		tCon.gridwidth = gridWidth;
		tCon.gridheight = gridHeight;
		tCon.gridx = gridx;
		tCon.gridy = gridy;
		//tCon.insets = new Insets(2,2,2,2);

		calcObj = currCalcObj;
		this.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				try {
					calcObj.setCurrTextField(thisField);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ValueNotStoredException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (EvalException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});

		this.addCaretListener(new CaretListener() {

			public void caretUpdate(CaretEvent arg0) {
				// TODO Auto-generated method stub
				calcObj.setCurrCaretPos(thisField.getCaretPosition());
			}

		});

		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					associatedAction();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ValueNotStoredException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (EvalException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		comp.add(this, tCon);
	}

	public void associatedAction() throws ParseException, ValueNotStoredException, EvalException {

		String currText = this.getText();
		if (!currText.equals(null) && !currText.equals("") && calcObj != null) {
			this.setText(calcObj.evalCalc(currText).toString());
			calcObj.updateGraph();
		}
	}
}
