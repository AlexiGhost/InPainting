package topology;

/** @author Matthieu Cabanes
 * @author Meriam Zekri*/

public class Edge {
	//Variables
	private static int[][] v = {{1,0},{0,1}}; //Axes used to determine a point with an edge and its other point
	private BoundingBox bb;
	private int orientation, i, j;
	private int direction;
	private int label; //not used for now
	//Constructors
	public Edge(BoundingBox bb, int direction, int i, int j, int orientation) {
		this.bb = bb;
		this.direction = direction;
		this.i = i;
		this.j = j;
		this.orientation = orientation;
		
		int[][] = this.border();
		
		if(orientation == -1){
			Point tmp = this.border()[0];
			this.border()[0] = this.border()[1];
			this.border()[1] = tmp; 
		}
		
		String labelTmp = '1'+ String.valueOf(this.border()[0].getI()) + String.valueOf(this.border()[0].getJ()) + String.valueOf(this.border()[1].getI()) + String.valueOf(this.border()[1].getJ());
		this.label = Integer.valueOf(labelTmp);
	}
	//Methods
	/**Return a table of the two points at the ends of the edge*/
	public Point[] border(){
		Point[] pointTab = new Point[2];
		pointTab[0] = new Point(this.bb, this.i, this.j);
		pointTab[1] = new Point(this.bb, this.i + this.orientation * v[this.direction][0], this.j + this.orientation * v[this.direction][1]);
		return pointTab;
	}
	/**Return the description of the edge (origin point, direction, orientation)*/
	public String toString(){
		String str = "Mon point d'origine a pour coordonnees i: "+this.i+" et j: "+this.j+".\n";
		if(direction == 1){
			str += "Je suis oriente verticalement, ";
			if(orientation == 1){
				str += "vers le bas.";
			}else{
				str += "vers le haut.";
			}
		}else{
			str += "Je suis oriente horizontalement, ";
			if(orientation == 1){
				str += "vers la droite.";
			}else{
				str += "vers la gauche.";
			}
		}
		return str;
	}
	
}
