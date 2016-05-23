
import java.util.ArrayList;

public class Node {
	public int nodeID;
	public double x;
	public double y;
	public double radius;
	public boolean explored = false;
	public String color = "magenta";
	ArrayList<Node> nodesConnected = new ArrayList<>();
	public Node(double x, double y, int nodeID, double radius){
		this.nodeID = nodeID;
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	public void printConnected(){
		for (int i = 0; i < nodesConnected.size(); i++){
			System.out.print(nodesConnected.get(i).getNodeID() + " ");
		}
		System.out.println();
	}
	public void addNode(Node ID){
		nodesConnected.add(ID);
	}
	public int getNodeID(){
		return nodeID;
	}
	public ArrayList<Node> getConnectedNodes(){
		return nodesConnected;
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public double getRadius(){
		return radius;
	}
	public void setColor(String color){
		this.color = color;
	}
	public String getColor(){
		return color;
	}
	public boolean isIsolated(){
		if (nodesConnected.size() == 0){
			return true;
		}
		return false;
	}
	public boolean isLeaf(){
		if (nodesConnected.size() == 1){
			return true;
		}
		return false;
	}
	public boolean getExplored(){
		return explored;
	}
	public void setExplored(boolean explored){
		this.explored = explored;
	}
}
