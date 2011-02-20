package GUI;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;


public class OCButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String s;
	private NewCalc calcObj;
	
	public OCButton(String str, int gridwidth, int gridheight, int gridx, 
			int gridy, JComponent comp, final NewCalc currCalcObj){
		
		super(str);
		s = str;
		calcObj = currCalcObj;
		GridBagConstraints bCon = new GridBagConstraints();
		bCon.fill = GridBagConstraints.BOTH;
		bCon.weightx = 1;
		bCon.weighty = 1;
		bCon.gridheight = gridheight;
		bCon.gridwidth = gridwidth;
		bCon.gridx = gridx;
		bCon.gridy = gridy;
		this.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				associatedAction();
			}
		});
		
		comp.add(this, bCon);
	}
	
	public void associatedAction(){
		JTextField currField = calcObj.getCurrTextField();
		int caretPos = 0; 
		if (currField != null){
			String currText = currField.getText();
			if (currField.getSelectionStart() == currField.getSelectionEnd()){
				currText = currField.getText();
				caretPos = calcObj.getCurrCaretPos(); 
				String tempText = currText.substring(0, caretPos);
				tempText += s;
				tempText += currText.substring(caretPos, currText.length());
				currField.setText(tempText);
				currField.requestFocus();
				currField.setCaretPosition(caretPos + s.length());
			}
			else{
				int selectStart = currField.getSelectionStart();
				int selectEnd = currField.getSelectionEnd();
				String tempText = currText.substring(0, selectStart);
				tempText += s;
				tempText += currText.substring(selectEnd, currText.length());
				currField.setText(tempText);
				currField.requestFocus();
				currField.setCaretPosition(selectStart + s.length());
			}
		}
	}

}
