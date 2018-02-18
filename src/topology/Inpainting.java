package topology;

// javac -classpath .:/usr/share/java/* Inpainting.java
// java -classpath .:/usr/share/java/* Inpainting -p 4  -m img/sarah-mask.bmp -i img/sarah.bmp -o img/sarah-restored.bmp
// java -classpath .:/usr/share/java/* Inpainting -p 4 -s 30 -m img/bungee-free-mask.bmp -i img/bungee-free.bmp -o img/bungee-free-restored.bmp
// javadoc -private -classpath .:/usr/share/java/* -d webdoc topology Inpainting.java

import java.io.IOException;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.util.Arrays;
//import org.apache.commons.cli.*;
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
				if(_m.val[i][j]) penMask[i][j]=tgv;
	}
	/** 
		@return a bounding box that enclose the component with a margin of halfSizeBox
	*/
	private BoundingBox searchingBox(Component component,int halfSizeBox){
		Point pt0=component.points.get(0);
		int[] bb=new int[]{pt0.i,pt0.j,pt0.i,pt0.j};
		for(Point pt:component.points){
			bb[0]=Math.min(bb[0],pt.i);
			bb[1]=Math.min(bb[1],pt.j);
			bb[2]=Math.max(bb[2],pt.i);
			bb[3]=Math.max(bb[3],pt.j);
		};
		return new BoundingBox(new int[]{Math.max(bb[0]-halfSizeBox,0),
			Math.max(bb[1]-halfSizeBox,0),
			Math.min(bb[2]+halfSizeBox,window.width),
			Math.min(bb[3]+halfSizeBox,window.height)});
	}
	/** 
		The image is updated by copying the patch at the best_point.
		The mask is updated : the restored pixels are removed. 
	*/
	private void copyPatch(Point best_point,Patch patch){
		int i=best_point.i;
		int j=best_point.j;
		for(int dx=patch.boundingBox.bb[0];dx<patch.boundingBox.bb[2];dx++)
			for(int dy=patch.boundingBox.bb[1];dy<patch.boundingBox.bb[3];dy++)
			{
				int I=patch.point.i+dx;
				int J=patch.point.j+dy;
				if(m.val[I][J])
					image.val[I][J].set(image.val[i+dx][j+dy]);
					m.val[I][J]=false;
			}
	}
	/** @return the index [i,j] such that for all k and l,
		array[i][j] lower or equal to array[k][l] */
	private int[] argmin(double[][] array){
		double currentMin=array[0][0];
		int besti=0;int bestj=0;
		for(int i=0;i<array.length;i++)
			for(int j=0;j<array[i].length;j++)
				if(currentMin>=array[i][j])
				{
					currentMin=array[i][j];
					besti=i;bestj=j;
				}
		return new int[]{besti,bestj};
	}
	/** 
		@return the origin point of the best matching patch
		included in the bounding box Box
		@param patch The patch to match
		@param Box The searching box
	*/
	private Point best_match(Patch patch,BoundingBox Box){
		if(Box==null)
			Box=new BoundingBox(new int[]{0,0,m.width,m.height});
		BoundingBox searchBox=Box.crop(patch);
		// We compute the difference on all the search box, then
		// look at the minimum.
		// It should be a little faster to do it all in one step
		double[][] norms=new double[searchBox.width][searchBox.height];	// filled with zero by default
		for(int i=0;i<searchBox.width;i++)
			for(int j=0;j<searchBox.height;j++) norms[i][j]=0;		// A priori inutile
		for(int dx=patch.boundingBox.bb[0];dx<patch.boundingBox.bb[2];dx++)
			for(int dy=patch.boundingBox.bb[1];dy<patch.boundingBox.bb[3];dy++){
				int I=patch.point.i+dx;			int J=patch.point.j+dy;
				int i_min=searchBox.bb[0]+dx; 	int i_max=searchBox.bb[2]+dx;
				int j_min=searchBox.bb[1]+dy;	int j_max=searchBox.bb[3]+dy;
				// penalization of patch intersectin the mask
				for(int k=0;k<searchBox.width;k++) for(int l=0;l<searchBox.height;l++)
						norms[k][l]+=penMask[i_min+k][j_min+l];
				
				// add contribution to points outside the mask
				if (!m.val[I][J])
					for(int k=0;k<searchBox.width;k++)  for(int l=0;l<searchBox.height;l++)
							norms[k][l]+=Color.dist(image.val[i_min+k][j_min+l],image.val[I][J]);
			}
		int[] bestIndex=argmin(norms);
		return new Point(m,bestIndex[0]+searchBox.bb[0],bestIndex[1]+searchBox.bb[1]);
	}
	/**
		Restore the image obstucted by the mask
		@param halfwidth half width of the patches
		@param searchWidth margin of the seraching box around the connected components.
			If searchWidth=0, the searchingBox is chosen as the whole window.
	*/
	public void restore(int halfwidth, int searchWidth, String output) throws IOException {
		Boundary b=new Boundary(m);
		
		Components C=new Components(b);
		while (C.components.size()!=0){
			System.out.println(C.components.size());
			int k=0;
			for(Component component:C.components){
				k+=1;
				BoundingBox searchBox=null;
				if(searchWidth!=0)
					searchBox=searchingBox(component,searchWidth);

				for(Point point:component.points)
				{ 
					if (m.touchedBy(point)){
						Patch patch=new Patch(point,halfwidth,window);
						Point best_point=best_match(patch,searchBox);
						copyPatch(best_point,patch);	//update image and mask
					}
				}
				image.save(output);
			}
			b=new Boundary(m);
			C=new Components(b);
			if(searchWidth!=0) searchWidth+=halfwidth;
		}
		System.out.println("Done");
	};
	/**
		Main script
		Parse the arguments and save the restored image

		-p halfwidth -s searchWidth -m mask.bmp -i imageToRestore.bmp -o restoredImage.bmp
	*/
//	public static void main(String[] args) throws IOException, ParseException {
//		int halfwidth=4;
//		int searchwidth=0;
//
//
//		////////////////////// Parse arguments ///////////////////////
//		Options options = new Options();
//        Option patch = new Option("p", "patch", true, "half path width (default="+halfwidth+")");
//        patch.setRequired(false);
//        patch.setType(Number.class);
//        options.addOption(patch);
//
//        Option search = new Option("s", "search", true, "half search width (default=None)");
//        search.setType(Number.class);
//        search.setRequired(false);
//        options.addOption(search);
//
//        Option maskName=new Option("m", "mask", true, "Filename of the mask (bmp file 3x8 RGB)");
//        maskName.setRequired(true);
//        options.addOption(maskName);
//
//		Option input=new Option("i", "input", true, "Filename of the input (bmp file 3x8 RGB)");
//        input.setRequired(true);
//        options.addOption(input);
//
//		Option output=new Option("o", "output", true, "Filename of the output (bmp file 3x8 RGB)");
//        output.setRequired(true);
//        options.addOption(output);
//
//        CommandLineParser parser = new GnuParser();
//        HelpFormatter formatter = new HelpFormatter();
//        CommandLine cmd;
//
//        try {
//            cmd = parser.parse(options, args);
//        } catch (ParseException e) {
//            System.out.println(e.getMessage());
//            formatter.printHelp("utility-name", options);
//            System.exit(1);
//            return;
//        }
//
//		if(cmd.hasOption("p")) {
//			halfwidth =  ((Number)cmd.getParsedOptionValue("p")).intValue();
//		}
//        if(cmd.hasOption("s")) {
//			searchwidth = ((Number)cmd.getParsedOptionValue("s")).intValue();
//		}
//
//		String maskFile=cmd.getOptionValue("m");
//		String imageFile=cmd.getOptionValue("i");
//		String outputFile=cmd.getOptionValue("o");
//		
//		//////////////////////////////////////////////////////////////
//
//		// Load Mask
//        System.out.println("load mask");
//        Mask mask=new Mask(maskFile,new Color(0,255,0));
// 		
// 		// Load image and apply mask
//		System.out.println("load image");
//		Matrix image=new Matrix(imageFile);
//		image.applyMask(mask);
//		//image.save("img/bungee-with-mask.bmp");
//
//		// Restore image
//		System.out.println("restore image");
//		Inpainting problem=new Inpainting(image,mask);
//		problem.restore(halfwidth,searchwidth);
//
//		// Save image
//		image.save(outputFile);
//	}
}
