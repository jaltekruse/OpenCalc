package GUI;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

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
		tCon.fill = GridBagConstraints.BOTH;
		tCon.weightx = .5;
		tCon.weighty = .5;
		tCon.gridheight = 1;
		tCon.gridwidth = gridWidth;
		tCon.gridheight = gridHeight;
		tCon.gridx = gridx;
		tCon.gridy = gridy;
		tCon.insets = new Insets(2,2,2,2);

		calcObj = currCalcObj;
		this.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				calcObj.setCurrTextField(thisField);
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
				associatedAction();
			}
		});

		comp.add(this, tCon);
	}

	public void associatedAction() {

		String currText = this.getText();
		if (!currText.equals(null) && !currText.equals("") && calcObj != null) {
			this.setText(calcObj.evalCalc(currText));
			calcObj.updateGraph();
		}
	}
	
	public void setText(int pos){
		this.setText(pos);
		this.setCaretPosition(0);
		this.revalidate();
		this.repaint();
	}
}
