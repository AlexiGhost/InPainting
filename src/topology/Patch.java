package topology;

public class Patch{
	private Point point;	
	private BoundingBox boundingBox;
	
	public Patch(Point _point,int halfwidth,BoundingBox window){		
	point = _point;
	boundingBox = new BoundingBox(new int[]{
		Math.max(0, point.getI() - halfwidth) - point.getI(),
		Math.max(0, point.getJ() - halfwidth) - point.getJ(),
		Math.min(window.getWidth(), point.getJ() + halfwidth) - point.getI(),
		Math.min(window.getHeight(), point.getJ() + halfwidth) - point.getJ()
		});
	}
	
	public Point getPoint() {
		return point;
	}
	
	public BoundingBox getBoundingBox() {
		return boundingBox;
	}
}
