package topology;

public class Color {

	private byte[] val = new byte[3];
	
	public Color(byte B, byte G, byte R) {
		val[0] = B;
		val[1] = G;
		val[2] = R;
	}
	
	public Color(int B, int G, int R) {
		val[0] = (byte) B;
		val[1] = (byte) G;
		val[2] = (byte) R;
	}
	
	public byte[] getVal() {
		return val;
	}

	public void setVal(byte[] val) {
		this.val = val;
	}
	
	public static int dist(Color c1, Color c2) {
		return ((int) Math.pow((c1.getVal()[0] & 0xFF - c2.getVal()[0] & 0xFF), 2)) + ((int) Math.pow((c1.getVal()[1] & 0xFF - c2.getVal()[1] & 0xFF), 2)) + ((int) Math.pow((c1.getVal()[2] & 0xFF - c2.getVal()[2] & 0xFF), 2));
	}
	
	public boolean isequalto(Color color) {
		if(((this.getVal()[0] & 0xFF) == (color.getVal()[0] & 0xFF)) && ((this.getVal()[1] & 0xFF) == (color.getVal()[1] & 0xFF)) && ((this.getVal()[2] & 0xFF) == (color.getVal()[2] & 0xFF)))
			return true;
		else
			return false;
	}
	
	public void set(Color color) {
		for(int i = 0; i < 3; i++) {
			this.getVal()[i] = color.getVal()[i];
		}
	}
	
	public String toString() {
		String resultat = "";
		for(int i = 0; i < 3; i++)
			 resultat += (this.getVal()[i] & 0xFF) + ",";
		
		return resultat;
	}
}
