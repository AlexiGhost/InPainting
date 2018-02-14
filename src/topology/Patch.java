package topology;

public class Patch{
	public Point point;
	public BoundingBox boundingBox;
	public Patch(Point _point,int halfwidth,BoundingBox window)
	{
		point=_point;
		boundingBox=new BoundingBox(new int[]{Math.max(0,point.i-halfwidth)-point.i,
			Math.max(0,point.j-halfwidth)-point.j,
			Math.min(window.width,point.i+halfwidth)-point.i,
			Math.min(window.height,point.j+halfwidth)-point.j});
	}
}
