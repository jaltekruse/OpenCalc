package gui;

import javax.swing.JFrame;

public class OCFrame extends JFrame implements TopLevelContainer{

	private GlassPane glassPane;
	
	public OCFrame(MainApplet mainApp){
		glassPane = new GlassPane(mainApp, this);
		this.setGlassPane(glassPane);
	}
	
	@Override
	public GlassPane getGlassPanel() {
		// TODO Auto-generated method stub
		return glassPane;
	}
	
	public void addFieldHistory(){
		glassPane.addFieldHistory();
	}

}
