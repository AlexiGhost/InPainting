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
		for(int i=0;i<window.width;i++)
			for(int j=0;j<window.height;j++)
				if(_m.getVal()[i][j]) penMask[i][j]=tgv;
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
}