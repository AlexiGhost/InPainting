package topology;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		Matrix matrix = new Matrix("C:\\Users\\Admin\\Desktop\\IUT Informatique\\2emeAnnee\\S3\\Maths Modélisation\\saut.bmp");
		Color color = new Color(0,255,0);
		Mask mask = new Mask(matrix,color);
		Inpainting i = new Inpainting(matrix,mask);
		i.restore(5, 100);
	}

}
