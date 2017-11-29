package topology;

import java.util.Arrays;

public class Tag {
	Boundary boundary;
	int[] index = new int[boundary.getEdges().size()]; //
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
	
	Point seedPoint(){
		int ind = 0;
		for(boolean bEdge : active){
			if(bEdge){ //return a point if he's in border of boundary
				Edge edge = boundary.getEdges().get(ind);
				Point point = edge.border()[0];
				if(point.onBorder()) return point;
			}
			ind++;
		}
		ind = 0;
		for(boolean bEdge : active){
			if(bEdge){ //Return the first point
				Edge edge = boundary.getEdges().get(ind);
				Point point = edge.border()[0];
				return point;
			}
			ind++;
		}
		return null; //no active edges
	}
	
	int indexActiveOuterEdge(Point point){ //TODO FINIR !!!
		for (Edge edge:point.outerEdges())
		{	
			int k=index[edge.getLabel()];
			if(k!=-1)
				if(boundary.getEdges().get(k).getOrientation==edge.orientation)
					if(active[k]) return k;
		}
		return -1;}
	}
}
