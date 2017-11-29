package topology;

import java.util.ArrayList;
import java.util.List;

public class Boundary {
	private List<Edge> edges = new ArrayList<>();
	BoundingBox bb;
	
	public Boundary(Mask mask) {
		this.bb = mask; //Mask extends of BoundingBox
		//Horizontal edges
		for(int h = 0; h<bb.getHeight()-1;h++){
			for(int w=0; w<bb.getWidth();w++){
				if((mask.getVal()[w][h])&&(!mask.getVal()[w][h+1])){
					edges.add(new Edge(mask, 0, w+1, h+1, -1)); //Left
				}
				if((!mask.getVal()[w][h])&&(mask.getVal()[w][h+1])){
					edges.add(new Edge(mask, 0, w, h+1, 1)); //Right
				}
			}
		}
		//Vertical edges
		for(int w = 0; w<bb.width-1;w++){
			for(int h=0; h<bb.height;h++){
				if((mask.getVal()[w][h])&&(!mask.getVal()[w+1][h])){
					edges.add(new Edge(mask, 1,w,h,1)); //Bottom
				}
				if((!mask.getVal()[w][h])&&(mask.getVal()[w+1][h])){
					edges.add(new Edge(mask, 1,w+1,h,-1)); //Top
				}
			}
		}
	}
	
	@Override
	public String toString(){
		String s = "";
		for (Edge edge : edges) {
			s += edge+"\n";
		}
		return s;
	}
	
	public List<Edge> getEdges(){
		return edges;
	}
}
