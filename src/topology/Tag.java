package topology;

import java.util.Arrays;

public class Tag {
	Boundary boundary;
	int[] index = new int[boundary.bb.nbEdges]; //
	boolean[] active = new boolean[boundary.getEdges().size()]; //True : edge "to do"
	int nbActive; //the number of active edges
	
	public Tag(Boundary boundary){
		Arrays.fill(index, -1);
		int ind=0;
		for(Edge edge : boundary.getEdges()){
			index[edge.getLabel()]=ind;
			ind++;
		}
		Arrays.fill(active,  true);
		nbActive = boundary.getEdges().size(); //ou ind ???
	}
	
	/**Found the seed point of the boundary*/
	Point seedPoint(){
		int ind = 0;
		for(boolean bEdge : active){ //return a point if he's in border of boundary
			if(bEdge){
				Edge edge = boundary.getEdges().get(ind);
				Point point = edge.border()[0];
				if(point.onBorder()) return point;
			}
			ind++;
		}
		ind = 0;
		for(boolean bEdge : active){ //return the first point into the boundary
			if(bEdge){
				Edge edge = boundary.getEdges().get(ind);
				Point point = edge.border()[0];
				return point;
			}
			ind++;
		}
		return null; //no active edges
	}
	/**Return the index of the first active edge (-1 if null)*/
	int indexActiveOuterEdge(Point point){
		for (Edge edge : point.outerEdges())
		{	
			int ind = index[edge.getLabel()];
			if(ind != -1) {//if this index is in the boundary
				if(boundary.getEdges().get(ind).getOrientation()==edge.getOrientation()){
					if(active[ind]) return ind; //edge "to do"
				}
			}
		}
		return -1;
	}
}
