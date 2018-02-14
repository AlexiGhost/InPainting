package topology;

public class Color 			// Color (B,G,R)
{
	/**
		Distance bewteen two colors
		@return (B1-B2)^2+(R1-R2)^2+(G1-G2)^2, wher
			Bi = blue (8 bits)
			Ri = red (8 bits)
			Gi = green (8 bits)
	*/
	public static int dist(Color c1,Color c2){
		return ((int)(c1.val[0]& 0xFF)-(int)(c2.val[0]& 0xFF))*((int)(c1.val[0]& 0xFF)-(int)(c2.val[0]& 0xFF))
		+((int)(c1.val[1]& 0xFF)-(int)(c2.val[1]& 0xFF))*((int)(c1.val[1]& 0xFF)-(int)(c2.val[1]& 0xFF))
		+((int)(c1.val[2]& 0xFF)-(int)(c2.val[2]& 0xFF))*((int)(c1.val[2]& 0xFF)-(int)(c2.val[2]& 0xFF));
		// the code is hugly here, put we perform this operation a lot of times,
		// (it may be faster by unfolding the code... it would be of some interest to check
		// that it is faster than a loop)
	}
	/**
		Definition of the color in BGR format 3x8bits
		val=[B,G,R]
		B,G,R = 8bits (Blue,Green,Red)
	*/
	public byte[] val;
	/**
		Set the color to the given one
	*/
	public void set(Color color){
		val[0]=color.val[0];
		val[1]=color.val[1];
		val[2]=color.val[2];
	}
	/**
		@return true if two colors are identical
	*/
	public boolean isequalto(Color color){
		for(int i=0;i<3;i++) if (val[i]!=color.val[i]) return false;
		return true;
	}
	/**
		Define a color
		@param B Blue (8bits)
		@param G Green (8bits)
		@param R Red (8bits)
	*/
	public Color(byte B,byte G, byte R){
		val=new byte[3];
		val[0]=B;val[1]=G;val[2]=R;
	}
	/**
		Define a color
		@param B Blue (int)
		@param G Green (int)
		@param R Red (int)
	*/
	public Color(int B,int G, int R){
		val=new byte[3];
		val[0]=(byte) B;val[1]=(byte) G;val[2]=(byte) R;
	}
	/**
		@return a string containing the colors value (B,G,R)
	*/
	@Override public String toString(){
		return "("+(val[0]& 0xFF)+","+(val[1]& 0xFF)+","+(val[2]& 0xFF)+")";
	}
}

