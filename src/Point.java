package topology;

import java.io.IOException;

/** @author Alexi Courieux
 * @author Nathan Fournier*/
public class Point {
//Variables
	private BoundingBox bb;
	private int i, j;
//Constructors	
	public Point(BoundingBox _bb, int _i, int _j){
		setBb(_bb);
		setI(_i);
		setJ(_j);
	}
//Methods
	/**Check if this point is equal to another one*/
	public boolean isEqualTo(Point p){
		if(this.equals(p)){
			return true;
		}
		return false;
	}
	/**Check if this point is on the border of his BoundingBox*/
	public boolean onBorder(){
		if(this.i == this.bb.getBb()[0] //left
				|| this.i == (this.bb.getBb()[0]+this.bb.getWidth()) //right
				|| this.j == this.bb.getBb()[1] //bottom
				|| this.j == (this.bb.getBb()[1]+this.bb.getHeight())){ //top
			return true;
		}
		return false;
	}
	/**Check if this point is on the corner of his BoundingBox*/
	public boolean onCorner(){
		if(this.onBorder()){ //if the point isn't a border, it cannot be a corner
			//Check if the point is on the left/right of the border
			if(this.i == this.bb.getBb()[0]
					|| this.i == (this.bb.getBb()[0]+this.bb.getWidth())){
				//Check if the point is on the top/bottom of the border
				if(this.j == this.bb.getBb()[1]
				|| this.j == (this.bb.getBb()[1]+this.bb.getHeight())){
					return true;
				}
			}
		}
		return false;
	}
/*	public Edge[] outerEdges(){
		//TODO Point - outerEdge()
	}*/
	@Override
	public String toString() {
		return super.toString();
	}
	public boolean equals(Point p) {
		if(this.bb == p.getBb() 
				&& this.i == p.getI() 
				&& this.j == p.getJ()){
			return true;
		}
		return false;
	}
//Getters & Setters
	public BoundingBox getBb() {
		return bb;
	}
	public void setBb(BoundingBox bb) {
		this.bb = bb;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		if (i < this.bb.getBb()[0]
				&& i > this.bb.getBb()[0]+this.bb.getWidth()){
			System.err.println("This point isn't in the BoundingBox");
		}
		this.i = i;			
	}
	public int getJ() {
		return j;
	}
	public void setJ(int j) {
		if (j < this.bb.getBb()[1]
				&& j > this.bb.getBb()[1]+this.bb.getHeight()){
			System.err.println("This point isn't in the BoundingBox");
		}
		this.j = j;
	}
	
}