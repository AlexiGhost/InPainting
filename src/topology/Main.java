package topology;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		Matrix matrix = new Matrix("C:\\Users\\Admin\\Desktop\\IUT Informatique\\2emeAnnee\\S3\\Maths Mod�lisation\\dr.jpg");
		Color color = new Color(0,0,0);
		Mask mask = new Mask(matrix,color);
		matrix.applyMask(mask);
		matrix.save("C:\\Users\\Admin\\Desktop\\IUT Informatique\\2emeAnnee\\S3\\Maths Mod�lisation\\dr2");

	}

}
