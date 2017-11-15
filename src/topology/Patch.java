package topology;

public class Patch{
	private Point point;	
	private BoundingBox boundingBox;
	
	public Patch(Point _point, int halfwidth, BoundingBox window) {		
	point = _point;
	int[] tab = new int[4];
	tab[0] = Math.max(0, point.getI() - halfwidth) - point.getI();
	tab[1] = Math.max(0, point.getJ() - halfwidth) - point.getJ();
	tab[2] = Math.min(window.getWidth(), point.getJ() + halfwidth) - point.getI();
	tab[3] = Math.min(window.getHeight(), point.getJ() + halfwidth) - point.getJ();
	boundingBox = new BoundingBox(tab);	
	}
	
	public Point getPoint() {
		return point;
	}
	
	public BoundingBox getBoundingBox() {
		return boundingBox;
	}
}
