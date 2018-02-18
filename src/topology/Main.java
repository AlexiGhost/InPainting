package topology;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		Matrix matrix = new Matrix("tests/TestCouleur2.bmp");
		Color color = new Color(255,0,255);
		Mask mask = new Mask(matrix,color);
		Inpainting i = new Inpainting(matrix,mask);
		i.restore(3, 50);
	}
}
