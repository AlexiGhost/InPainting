package topology;
// javac -classpath .:/usr/share/java/* Inpainting.java
// java -classpath .:/usr/share/java/* Inpainting -p 4  -m img/sarah-mask.bmp -i img/sarah.bmp -o img/sarah-restored.bmp
// java -classpath .:/usr/share/java/* Inpainting -p 4 -s 30 -m img/bungee-free-mask.bmp -i img/bungee-free.bmp -o img/bungee-free-restored.bmp
// javadoc -private -classpath .:/usr/share/java/* -d webdoc topology Inpainting.java

import topology.*;
import java.io.IOException;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.util.Arrays;
/**
	Inpainting is a class that restore an image
	partially obstructed by a mask
*/
public class Inpainting {
	/** "tres grande valeur" */
	private static  int tgv=3*255*255+1;		
	/** Matrix of the image to treat */
	private Matrix image;
	/** Mask applied to the image */
	private Mask m;
	/** Penalization of the pixels of the initial Mask */
	private int[][] penMask;
	/** Working window */
	private BoundingBox window;
	/** 
		Define  an impainting problem of on image
		obstructed by a mask
	*/
	public Inpainting(Matrix _image,Mask _m){
		assert(Arrays.equals(_image.bb,_m.bb)):"Image and Mask do not have the same dimension";
		image=_image;
		m=_m;
		window=_m;
		penMask=new int[window.width][window.height];
		for(int i=0;i<window.width;i++){
			for(int j=0;j<window.height;j++)
				if(_m.getVal()[i][j]) penMask[i][j]=tgv;
				else penMask[i][j]= 0;
		}
	}
	/** 
		The image is updated by copying the patch at the best_point.
		The mask is updated : the restored pixels are removed. 
	*/
	private void copyPatch(Point best_point,Patch patch){
		int i=best_point.getI();
		int j=best_point.getJ();
		for(int dx=patch.getBoundingBox().bb[0];dx<patch.getBoundingBox().bb[2];dx++) {
			for(int dy=patch.getBoundingBox().bb[1];dy<patch.getBoundingBox().bb[3];dy++) {
				int I=patch.getPoint().getI()+dx;
				int J=patch.getPoint().getJ()+dy;
				if(m.getVal()[I][J]) {
					image.getVal()[I][J].set(image.getVal()[i+dx][j+dy]);
					m.getVal()[I][J]=false;
				}
			}
		}
	}
	
	private int[] argmin(double[][] distances){
		double min = distances[0][0];
		int[] point = new int[2];
		point[0] = 0;
		point[1] = 1;
		for(int i = 0; i<distances.length; i++){
			for (int j = 0; j<distances[0].length; j++) {
				if(distances[i][j] < min){
					min = distances[i][j];
					point[0] = i;
					point[1] = j;
				}
			}
		}
		return point;
	}
	
	public Point bestMatch(Patch p, BoundingBox b){
		b = b.crop(p);
		double[][] distances = new double[b.getWidth()][b.getHeight()];
		Point point = new Point(b,0,0);
		
		for(int i = 0; i < b.getWidth(); i++){
			for(int j = 0; j < b.getHeight(); j++){
				point.setI(i);
				point.setJ(j);
				distances[i][j] = dist(p,point);
			}
		}
		
		int[] ij = argmin(distances);
		point.setI(ij[0]);
		point.setJ(ij[1]);
		
		return point; 
	}
	
	private int dist(Patch p1, Point p2){
		int distance = 0;
		for (int i= p1.getBoundingBox().getBb()[0]; i < p1.getBoundingBox().getBb()[2]; i++){
			for(int j= p1.getBoundingBox().getBb()[1]; j < p1.getBoundingBox().getBb()[3]; j++){
				distance += Color.dist(image.getVal()[i][j], image.getVal()[p2.getI()+i][p2.getJ()+j]) + penMask[p2.getI()+i][p2.getJ()+j];
			}
		}
		return distance;
	}
	
	private BoundingBox searchingBox(Component component, int marge){
		int iMin = component.getPoints().get(0).getI();
		int iMax = component.getPoints().get(0).getI();
		int jMin = component.getPoints().get(0).getJ();
		int jMax = component.getPoints().get(0).getJ();
		
		for (Point point : component.getPoints()) {
			if(point.getI() < iMin)
				iMin = point.getI();
			else if(point.getI() > iMax)
				iMax = point.getI();
			
			if(point.getJ() < jMin)
				jMin = point.getJ();
			else if(point.getJ() > jMax)
				jMax = point.getJ();
		}
		
		iMin = Math.max(0, iMin - marge);
		iMax = Math.min(image.getWidth(), iMax + marge);
		jMin = Math.max(0, jMin - marge);
		jMax = Math.min(image.getHeight(), jMax + marge);
		
		
		int[] bb = {iMin, jMin, iMax, jMax};
		
		return new BoundingBox(bb);
	}
	
	public void restore(int halfwidth, int marge){
		Boundary boundary = new Boundary(m);
		Components components = new Components(boundary);
		Patch patch = null;
		
		for (Component component : components.getComponents()) {
			window = searchingBox(component, marge);
			
			for (Point point : component.getPoints()) {
				if(m.getVal()[point.getI()][point.getJ()]){
					patch = new Patch(point, halfwidth, window);
					Point bestPoint = bestMatch(patch, window);
					copyPatch(bestPoint, patch);
				}
			}
			marge+= patch.getBoundingBox().getHeight()/2;
			patch = null;
		}
	}
}