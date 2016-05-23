
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MotionPlanning {
	static ArrayList<double[]> rectangleArray = new ArrayList<>();

	static ArrayList<double[]> circleArray = new ArrayList<>();
	
	static Graph g = new Graph();
	static int numBoxes = 60;
	static int xmin = -100;
	static int xmax = 100;
	static int ymin = -100;
	static int ymax = 100;
	static double circleRadius = 2.5;
	static int ID = 3;

	public static void main (String[] argv){
		
		DrawTool.display();
		DrawTool.setXYRange(xmin, xmax, ymin, ymax);
		DrawTool.drawMiddleAxes(true);
		
		
//		drawGraph(g);
//		printArray(rectangleArray);
//		Graph g = new Graph();
//		Node n1 = new Node(5,5,10);
//		Node n2 = new Node(8,8,11);
//		g.addNode(n1);
//		g.addNode(n2);
//		g.connectExistingNodes(10, 11);
		ArrayList<Node> nodeList = g.getNodes();
		//drawGraph(g);
		runMotionPlan();
		Graph pathGraph = findPath(nodeList.get(0),nodeList.get(1));
		
//		while (true){
//			if (pathGraph == null){
//				
//			}
//		}
		
//		drawNode(nodeList.get(0));
//		drawNode(nodeList.get(1));
		
//		if (pathGraph != null){
//			ArrayList<Node> nodeList1 = pathGraph.getNodes();
//			for (int i = 0; i < nodeList1.size(); i++){
////				ArrayList<Node> connected = nodeList1.get(i).getConnectedNodes();
//				System.out.print(nodeList1.get(i).getNodeID() + ": ");
//				nodeList.get(i).printConnected();
//			}
			drawGraph(pathGraph);
//		}
		
	}
	
	public static void drawNode(Node n){
		DrawTool.setOvalColor(n.getColor());
		DrawTool.drawOval(n.getX()-circleRadius/2, n.getY()+circleRadius/2, n.getRadius()*2, n.getRadius()*2);
	}
	
	public static void runMotionPlan(){
		generateField();
		generateCircles(numBoxes);
		connectNodes(g);
		
	}
	
	
	
	public static void connectNodes(Graph tempG){
		for (int i = 0; i < g.getNodes().size(); i++){
			Node n1 = g.getNodes().get(i);
			for (int j = i+1; j < g.getNodes().size(); j++){
				
				if (j < g.getNodes().size()){
					Node n2 = g.getNodes().get(j);
					if (clearPathExists(n1,n2)){
						g.connectExistingNodes(n1, n2);
//						DrawTool.drawLine(n1.getX()+circleRadius/2, n1.getY()-circleRadius/2, n2.getX()+circleRadius/2, n2.getY()-circleRadius/2);
						
					}
				}
			}
		}
	}
	
	public static void drawGraph(Graph g){
		if (g == null){
			return;
		}
		ArrayList<Node> nodeList = g.getNodes();
		DrawTool.setPointColor("dark gray");
		DrawTool.setPointSize(5);
		DrawTool.setLineColor("dark gray");
		for (int i = 0; i < nodeList.size(); i++){
//			DrawTool.drawPoint(nodeList.get(i).getX(), nodeList.get(i).getY());
			drawNode(nodeList.get(i));
			for (int j = 0; j < nodeList.size(); j++){
				if (g.isConnected(nodeList.get(i), nodeList.get(j))){
					DrawTool.drawLine(nodeList.get(i).getX()+circleRadius/2, nodeList.get(i).getY()-circleRadius/2, nodeList.get(j).getX()+circleRadius/2, nodeList.get(j).getY()-circleRadius/2);
				}
			}
		}
	}
	public static void generateField(){
		initStart();
		initEnd();
		DrawTool.setRectangleColor("blue");
		generateRandomSquares(numBoxes);
	}
	
	public static void generateRandomSquares(int num){
		
		for (int i = 0; i < num ; i++){
			double rectangle[] = new double[4];
			double randomx = randInt(xmin+10,xmax-10);
			double randomy = randInt(ymin+10,ymax-10);
			double width = randInt(3,10);
			double height = randInt(3,10);
			rectangle[0] = randomx+width/2;
			rectangle[1] = randomy-height/2;
			rectangle[2] = width;
			rectangle[3] = height;
			rectangleArray.add(rectangle);
			DrawTool.drawRectangle(randomx, randomy, width, height);
			colorRectangle(randomx, randomy, height, width);
			DrawTool.setLineColor("green");
//			DrawTool.drawLine(randomx-width/2, randomy-height/2, randomx-width/2, randomy+height/2);
		}

		
	}
	public static void colorRectangle(double x, double y, double height, double width){
		double centerx = x+width/2;
		double centery = y-height/2;
		DrawTool.setLineColor("blue");
		for (double i = centerx-width/2; i < centerx+width/2; i+=0.5){
//			for (int j = )
			DrawTool.drawLine(i, centery-height/2, i, centery+height/2);
		}
		
	}
	public static void generatePoints( int num){
		DrawTool.setPointSize(5);
		for (int i = 0; i < num ; i++){
			DrawTool.setPointColor("green");
			double randomx = randInt(xmin,xmax);
			double randomy = randInt(ymin,ymax);
			//System.out.println("x: " + randomx + " y: " + randomy);
			for (int j = 0; j < rectangleArray.size(); j++){
				double[] coords = rectangleArray.get(j);
				if (isPointInRectangle(randomx, randomy, coords[0], coords[1], coords[3], coords[2])){
//					System.out.println("true");
					DrawTool.setPointColor("red");
				}
			}
			DrawTool.drawPoint(randomx, randomy);
		}
		
	}
	public static void generateCircles(int num){
		
		for (int i = 0; i < num ; i++){
			
			double randomx = randInt(xmin,xmax);
			double randomy = randInt(ymin,ymax);
			double centerX = randomx + circleRadius/2;
			double centerY = randomy - circleRadius/2;//might actually be this
			Node n = new Node(centerX, centerY, ID, circleRadius);
//			Node n = new Node(randomx, randomy, ID, circleRadius);
			ID++;
			
			//System.out.println("x: " + randomx + " y: " + randomy);
			for (int j = 0; j < rectangleArray.size(); j++){
				double[] coords = rectangleArray.get(j);
				if (isRadialPointInRectangle(n.getRadius(), n.getX(), n.getY(), coords[0], coords[1], coords[3], coords[2])){
					n.setColor("red");
				}
			}
			
			g.addNode(n);
//			DrawTool.drawOval(randomx, randomy, circleRadius*2, circleRadius*2);
//			drawNode(n);
		}
		
	}
	public static void initStart(){
		Node n = new Node(-97.5, 97.5, 1, circleRadius);
		n.setColor("red");
		g.addNode(n);
		drawNode(n);
	}
	public static void initEnd(){
		Node n = new Node(95,-95, 2, circleRadius);
		n.setColor("green");
		g.addNode(n);
		drawNode(n);
	}
	
	public static double randInt(int min, int max){
		Random rand = new Random();
		double randomNum = rand.nextInt((max-min)+1)+min;
		return randomNum;
	}
	public static void printArray(ArrayList<double[]> temp){
		for (int i = 0; i < temp.size(); i++){
			double[] rect = temp.get(i);
			System.out.print("x: " + rect[0] + " y: " + rect[1] + " width: " + rect[2] + " height: " + rect[3]);
			System.out.println();
		}
	}
	public static boolean isPointInRectangle(double xcoord, double ycoord, double rectx, double recty, double height, double width){
		if (xcoord >= rectx-width/2 && xcoord <= rectx+width/2){
			if (ycoord >= recty-height/2 && ycoord <= recty+height/2){
				return true;
			}
		}
		return false;
	}
	public static boolean isRadialPointInRectangle(double radius, double xcoord, double ycoord, double rectx, double recty, double height, double width){
//		System.out.println("xcoord: " + xcoord + " ycoord: " + ycoord + " radius: " + radius);
//		System.out.println("rectx: " + rectx + " recty: " + recty);
//		System.out.println("height: " + height + " width: " + width );
//		System.out.println();
		xcoord = xcoord + radius/2;
		ycoord = ycoord - radius/2;
		if (Math.abs(xcoord-rectx) < (width/2 + radius)){
			if (Math.abs(ycoord-recty) < (height/2 + radius)){
				
				return true;
			}
		}
//		if (Math.abs(ycoord-recty) < (height/2 + radius)){
//			if (Math.abs(xcoord-rectx) < (width + radius)){
//				return true;
//			}
//		}
		return false;
	}
	public static boolean clearPathExists(Node n1, Node n2){
		double x1 = n1.getX();
		double x2 = n2.getX();
		double y1 = n1.getY();
		double y2 = n2.getY();
		double radius = n1.getRadius();
		
		double slope = (y2-y1)/(x2-x1);
		//y = slope * x + b
		double b = y2 - slope*x2;
		if (x1 > x2){
			for (double i = x2; i < x1; i+=0.1){
				double newX = i;
				double newY = slope * i + b;
				for (int j = 0; j < rectangleArray.size(); j++){
					double[] rectangle = rectangleArray.get(j);
					if (isRadialPointInRectangle(radius, newX,newY,rectangle[0],rectangle[1],rectangle[3],rectangle[2])){
						return false;
					}
				}
			}
		}else if (x1 < x2){
			for (double i = x1; i < x2; i+=0.1){
				double newX = i;
				double newY = slope*i + b;
				for (int j = 0; j < rectangleArray.size(); j++){
					double[] rectangle = rectangleArray.get(j);
					if (isRadialPointInRectangle(radius, newX,newY,rectangle[0],rectangle[1],rectangle[3],rectangle[2])){
						return false;
					}
				}
			}
		}else{
			return false;
		}
		return true;
	}
	public static Graph findPath(Node n1, Node n2){
		//start node id is 1, end node is 2
		Stack<Node> nodeStack = new Stack<>();
		Graph returnGraph = new Graph();
//		ArrayList<Node> allNodes = returnGraph.getNodes();
		Node currentNode = n1;
		if (n1.getNodeID() == n2.getNodeID()){//catch the same node exception
			returnGraph.addNode(n1);
			return returnGraph;
		}
		if (n1.isIsolated() || n2.isIsolated()){//if either nodes are isolated then they cant be connected
			return null;
		}
		currentNode.setExplored(true);
		nodeStack.push(n1);
		int count = 0;
		loop:
		while (currentNode.getNodeID() != n2.getNodeID()){
			if (count >= 10000){
				System.out.println("No solution found.");
				return null;
			}
			count++;
			ArrayList<Node> neighborNodes = currentNode.getConnectedNodes();
			for (int i = 0; i < neighborNodes.size(); i++){
				if (neighborNodes.get(i).getNodeID() == n2.getNodeID()){//if a neighbor node id is 2 then thats the endpoint
					nodeStack.push(neighborNodes.get(i));
					break loop;
				}
			}
			
			for (int i = 0; i < neighborNodes.size(); i++){
				if (!neighborNodes.get(i).getExplored()){//if there is an unexplored path then take it
					nodeStack.push(neighborNodes.get(i));//push the node to the stack to track path
					neighborNodes.get(i).setExplored(true);//set the node to explored
					currentNode = neighborNodes.get(i);//set the currentnode to the node youve jumped to
					break;
				}
			}
			loop1:
			while(true){
				for (int i = 0; i < neighborNodes.size(); i++){
					if (!neighborNodes.get(i).getExplored()){
						break loop1;
					}
				}
//				System.out.println("test");
				//if reached here then all neighbors are explored, could potentially be end node, if not
				//pop that node and set to current
				if (currentNode.getNodeID() != n2.getNodeID()){
					if (!nodeStack.empty()){
						nodeStack.pop();
					}
					if (!nodeStack.empty()){
						currentNode = nodeStack.peek();
					}
				}else{
					break loop;
				}
				break loop1;
			}
			//if reached here then no neighbors are endnode so keep searching
		}
		//stack of nodes should be created so create graph
		Node prevNode = new Node(-1,-1,-1,-1);
		Node curNode = new Node(-1,-1,-1,-1);
		int num = nodeStack.size();
		for (int i = 0; i < num; i++){
//			if (curNode.getNodeID() == -1){
				curNode = nodeStack.pop();
				Node tempNode = new Node(curNode.getX(), curNode.getY(), curNode.getNodeID(), curNode.getRadius());
				curNode.setColor("dark gray");
				tempNode.setColor("orange");
//				System.out.println(curNode.getNodeID());
//			}
			returnGraph.addNode(tempNode);
//			if (prevNode.getNodeID() == -1){
			if (prevNode.getNodeID() != -1){
				returnGraph.connectExistingNodes(tempNode, prevNode);	
			}
			prevNode = tempNode;
//			}else{
//			}
		}
		
		
		return returnGraph;
	}
}
