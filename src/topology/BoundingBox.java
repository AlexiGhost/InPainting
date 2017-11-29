package topology;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BoundingBox {
	protected int[] bb;
	protected int width, height, size;
	protected int nbEdges, nbEdgesHorizontal, nbEdgesVertical;
	
	public BoundingBox (int[] bb){
		this.bb = bb;
		width = (bb[2] - bb[0]);
		height = (bb[3] - bb[1]);
		size = width * height;
		nbEdgesHorizontal = (height+1) * width;
		nbEdgesVertical = (width+1) * height;
		nbEdges = nbEdgesHorizontal + nbEdgesVertical;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
		size = width*height;
		nbEdgesHorizontal = (height+1) * width;
		nbEdgesVertical = (width+1) * height;
		nbEdges = nbEdgesHorizontal + nbEdgesVertical;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
		size = width*height;
		nbEdgesHorizontal = (height+1) * width;
		nbEdgesVertical = (width+1) * height;
		nbEdges = nbEdgesHorizontal + nbEdgesVertical;
	}
	
	public int[] getBb() {
		return bb;
	}

	public int getNbEdgesHorizontal() {
		return nbEdgesHorizontal;
	}
	
	public int getNbEdgesVertical() {
		return nbEdgesVertical;
	}
	
	public BoundingBox (BufferedImage bi){
		this(new int[]{0,0,bi.getWidth(),bi.getHeight()});
	}
	
	public BoundingBox (String fName) throws IOException{
		this(ImageIO.read(new File(fName)));
	}
	
	public BoundingBox (BoundingBox bdb){
		this.bb = bdb.bb;
		this.width = bdb.width;
		this.height = bdb.height;
		this.size = bdb.size;
		this.nbEdges = bdb.nbEdges;
		this.nbEdgesHorizontal = bdb.nbEdgesHorizontal;
		this.nbEdgesVertical = bdb.nbEdgesVertical;
	}
	
	public String toString(){
		String resume = "bb : ";
		for (int i : bb) {
			resume += i+"\t";
		}
		resume += "\nwidth : "+width+"\nheight : "+height+"\nsize"+size+"\nnbEdges : "+nbEdges+"\nnbEdgesHorizontal : "+nbEdgesHorizontal+"\nnbEdgesVertical : "+nbEdgesVertical;
		return resume;
	}
}
