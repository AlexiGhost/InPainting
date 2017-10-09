package topology;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {

	public static void main(String[] args) throws IOException {
		Matrix bb = new Matrix("C:\\Users\\Admin\\Pictures\\fond ecran\\arrow.jpg");
		
		for(int i=0; i<bb.getWidth(); i++){
			for(int j=bb.getHeight()/2; j<bb.getHeight(); j++){
				bb.getVal()[i][j].set(new Color(255,0,0));
			}
		}
		
		bb.save("C:\\Users\\Admin\\Pictures\\fond ecran\\imageMatrix");

	}

}
