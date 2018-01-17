package topology;

import java.util.List;

public class Component {
	private List<Point> points;
	
	public Component(Tag tag, Point seedPoint) {
		Point point = seedPoint;
		int index;
		
		index = tag.indexActiveOuterEdge(point);
		Edge edge = null;
		Point[] edgePoints = null; 
		
		while(index != -1){
			points.add(point);
			edge = tag.boundary.getEdges().get(index);
			edgePoints = edge.border();
			
			if(edgePoints[0].getI() != point.getI() || edgePoints[0].getJ() != point.getJ())
				point = edgePoints[0];
			else
				point = edgePoints[1];
			
			tag.active[index] = false;
			index = tag.indexActiveOuterEdge(point);
		}
		
		points.add(point);
	}
	
	public String toString(){
		String s = "Liste des points :\n";
		int i = 1;
		
		for (Point point : points) {
			s += i+"-\ti: "+point.getI()+"; j: "+point.getJ();
			i++;
		}
		
		s += "\n----------------------------------------------------------------\n";
		
		return s;
	}
}
