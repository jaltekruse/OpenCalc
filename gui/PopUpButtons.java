package gui;

import java.awt.Dimension;

public class PopUpButtons extends SubPanel {

	MainApplet mainApp;
	
	public PopUpButtons(TopLevelContainer topLevelComp, MainApplet mainApp) {
		super(topLevelComp);
		this.mainApp = mainApp;
		
		OCButton advanced = new OCButton("Calculator", 1, 1, 10, 0, this, mainApp){
			public void associatedAction() {
				
			}
		};
	}

}
