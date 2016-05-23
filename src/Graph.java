
import java.util.ArrayList;

public class Graph {
	ArrayList<Node> graphNodes = new ArrayList<>();
	public Graph(){}
	public void addNode(Node n){
		graphNodes.add(n);
	}
	public ArrayList<Node> getNodes(){
		return graphNodes;
		
	}
	public void connectExistingNodes(Node id1, Node id2){
		id1.addNode(id2);
		id2.addNode(id1);
		
	}
	public boolean isConnected(Node id1, Node id2){
		for (int i = 0; i < graphNodes.size(); i++){
			if (graphNodes.get(i).getNodeID() == id1.getNodeID()){
				ArrayList<Node> connectedIDs = graphNodes.get(i).getConnectedNodes();
				for (int j = 0; j < connectedIDs.size(); j++){
					if (connectedIDs.get(j).getNodeID() == id2.getNodeID()){
						return true;
					}
				}
			}
			if(graphNodes.get(i).getNodeID() == id2.getNodeID()){
				ArrayList<Node> connectedIDs = graphNodes.get(i).getConnectedNodes();
				for (int j = 0; j < connectedIDs.size(); j++){
					if (connectedIDs.get(j).getNodeID() == id1.getNodeID()){
						return true;
					}
				}
			}
		}
		return false;
	}
}

