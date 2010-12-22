package gui.graph;

public abstract class GraphComponent {

	private Graph graph;
	
	public GraphComponent(Graph g){
		graph = g;
	}
	
	public abstract void draw();
	
	public Graph getGraph(){
		return graph;
	}
}
