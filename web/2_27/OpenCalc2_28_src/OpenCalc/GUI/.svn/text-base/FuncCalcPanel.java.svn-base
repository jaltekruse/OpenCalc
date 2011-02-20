package GUI;

import calc.Function;

public class FuncCalcPanel extends SubPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private NewCalc calcObj;
	private Function func;
	private Graph graph;
	private OCButton trace, integrate, graphButton;
	private OCTextField pt2Trace, tracePtVal, startInt, endInt, intVal;
	private OCLabel indVar, depVar, start, end, intApprox, spacer;
	
	public FuncCalcPanel(NewCalc currCalcObj, Function f){
		calcObj = currCalcObj;
		graph = calcObj.getGraphObj();
		func = f;
		
		indVar = new OCLabel(func.getIndependentVar().getName() + ":", 1, 1, 0, 0, this, calcObj);
		pt2Trace = new OCTextField(true, 4, 2, 1, 1, 0, this, calcObj) {
			public void associatedAction() {
				tracePt();
			}
		};
		
		trace = new OCButton("Trace", 1, 1, 3, 0, this, calcObj){
			public void associatedAction() {
				tracePt();
			}
		};
		
		depVar = new OCLabel(func.getDependentVar().getName() + ":", 1, 1, 4, 0, this, calcObj);
		tracePtVal = new OCTextField(false, 9, 2, 1, 5, 0, this, calcObj);
		
		if(func.getGraphType() == 1){
			start = new OCLabel("start:", 1, 1, 7, 0, this, calcObj);
			startInt = new OCTextField(true, 4, 2, 1, 8, 0, this, calcObj){
				public void associatedAction(){
				}
			};
			end = new OCLabel("end:", 1, 1, 10, 0, this, calcObj);
			endInt = new OCTextField(true, 4, 2, 1, 11, 0, this, calcObj){
				public void associatedAction(){
				}
			};
			
			integrate = new OCButton("Integrate", 1, 1, 13, 0, this, calcObj){
				public void associatedAction(){
					integrate();
				}
			};
			
			intApprox = new OCLabel("Approx:", 1, 1, 14, 0, this, calcObj);
			intVal = new OCTextField(false, 9, 2, 1, 15, 0, this, calcObj);
		}
	}
	
	public void integrate(){
		if ("".equals(func.getFuncEqtn())) {
			intVal.setText("no eqtn");
		}
		else if (!startInt.getText().equals("") && !endInt.getText().equals("")){
			double a = Double.parseDouble(startInt.getText());
			double b = Double.parseDouble(endInt.getText());
			func.setIntegral(a, b);
			graph.repaint();
			calcObj.getCalcObj().parse(func.getFuncEqtn());
			String integral = new String();
			integral += (float) calcObj.getCalcObj().integrate(a, b);
			intVal.setText(integral);
		}
		else{
			func.setIsTakingIntegral(false);
			intVal.setText("");
			graph.repaint();
		}
	}

	public void tracePt(){
		if (pt2Trace.getText().equals("")) {
			func.setTracingPt(false);
			tracePtVal.setText("");
		}
		else {
			if ("".equals(func.getFuncEqtn())) {
				pt2Trace.setText("no eqtn");
				tracePtVal.setText("error");
			}
			else {
				String xAns = calcObj.evalCalc(pt2Trace.getText());
				if (!"error".equals(xAns)) {
					double xVal = Double.valueOf(xAns);
					double yVal;
					func.setTrace(xVal);
					pt2Trace.setText(Float.toString(Float.valueOf(xAns)));
					func.getIndependentVar().setValue(xVal);
					graph.getGraphCalc().parse(func.getFuncEqtn());
					String yAns = "" + (float) graph.getGraphCalc().solve();
					yVal = Double.valueOf(yAns);
					func.getDependentVar().setValue(yVal);
					tracePtVal.setText(Float.toString(Float.valueOf(yAns)));
				}
				else {
					pt2Trace.setText("error");
				}
			}
		}
		graph.repaint();
	}
	
	public void refreshFields(){
		
		this.removeAll();
		
		indVar = new OCLabel(func.getIndependentVar().getName() + ":", 1, 1, 0, 0, this, calcObj);
		pt2Trace = new OCTextField(true, 4, 2, 1, 1, 0, this, calcObj) {
			public void associatedAction() {
				tracePt();
			}
		};
		
		trace = new OCButton("Trace", 1, 1, 3, 0, this, calcObj){
			public void associatedAction() {
				tracePt();
			}
		};
		
		depVar = new OCLabel(func.getDependentVar().getName() + ":", 1, 1, 4, 0, this, calcObj);
		tracePtVal = new OCTextField(false, 9, 2, 1, 5, 0, this, calcObj);
		
		if(func.getGraphType() == 1){
			System.out.println("is cart");
			start = new OCLabel("start:", 1, 1, 7, 0, this, calcObj);
			startInt = new OCTextField(true, 4, 2, 1, 8, 0, this, calcObj){
				public void associatedAction(){
				}
			};
			end = new OCLabel("end:", 1, 1, 10, 0, this, calcObj);
			endInt = new OCTextField(true, 4, 2, 1, 11, 0, this, calcObj){
				public void associatedAction(){
				}
			};
			
			integrate = new OCButton("Integrate", 1, 1, 13, 0, this, calcObj){
				public void associatedAction(){
					integrate();
				}
			};
			
			intApprox = new OCLabel("Approx:", 1, 1, 14, 0, this, calcObj);
			intVal = new OCTextField(false, 9, 2, 1, 15, 0, this, calcObj);
		}
		else if(func.getGraphType() == 2){
			System.out.println("is polar");
			spacer = new OCLabel("Integrate", 1, 1, 5, 0, this, calcObj);
		}
		else
			;//do nothing, will add when more graph types supported
		this.revalidate();
		this.repaint();
	}

}
