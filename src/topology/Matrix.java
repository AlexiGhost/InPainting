package topology;

/** @author Matthieu Cabanes
 * @author Meriam Zekri*/

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Matrix extends BoundingBox{
	//Variables
	public Color[][] val;
	//Constructors
	/**Take every information of an image which its file name has been entered in the parameters, and save them in a Matrix*/ 
	public Matrix(String fileName) throws IOException{
		super(fileName);
		val = new Color[this.width][this.height];
		BufferedImage bfIm = ImageIO.read(new File(fileName));
		int clr, red, green, blue;
		for(int i = 0; i < this.width; i++) {
			for(int j = 0; j < this.height; j++) {
				clr = bfIm.getRGB(i, j); 
				red   = (clr & 0x00ff0000) >> 16;
				green = (clr & 0x0000ff00) >> 8;
				blue  =  clr & 0x000000ff;
				val[i][j] = new Color(blue, green, red);
			}
		}
	}
	//Methods
	/**Create an image with its information and save it in the file name entered in the parameters*/
	public void save(String fName) throws IOException{
		BufferedImage img = new BufferedImage(this.width,this.height, BufferedImage.TYPE_3BYTE_BGR);
		for(int i = 0; i < this.width; i++) {
			for(int j = 0; j < this.height; j++) {
				img.setRGB(i, j, ((-1 << 24) + (val[i][j].val[2] << 16) + (val[i][j].val[1] << 8) + (val[i][j].val[0])));
			}
		}
		File file = new File(fName + ".bmp");
		ImageIO.write(img, "BMP", file);
	}
	/**Return the Color table of the Matrix*/
	 public Color[][] getVal() {
		return val;
	}
	/**Set every pixels of the Mask entered in parameters at black*/
	 public void applyMask(Mask mask){
		 for(int i=0; i<this.width; i++){
			 for(int j=0; j<this.height; j++){
				 if(mask.val[i][j])
					 this.val[i][j].set(new Color(0,0,0));
			 }
		 }
	 }
}
