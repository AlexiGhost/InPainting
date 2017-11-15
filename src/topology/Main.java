package topology;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {

	public static void main(String[] args) throws IOException {
		Matrix matrix = new Matrix("C:\\Users\\Admin\\Desktop\\IUT Informatique\\2emeAnnee\\S3\\Maths Modélisation\\dr.jpg");
		Color color = new Color(0,0,0);
		Mask mask = new Mask(matrix,color);
		matrix.applyMask(mask);
		matrix.save("C:\\Users\\Admin\\Desktop\\IUT Informatique\\2emeAnnee\\S3\\Maths Modélisation\\dr2");

	}

}
