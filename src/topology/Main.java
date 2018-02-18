package topology;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) throws IOException {
		JFileChooser jfc = new JFileChooser(new File(".").getCanonicalFile());
		
		jfc.showOpenDialog(null);
		String input = jfc.getSelectedFile().getAbsolutePath();
		String[] colors = JOptionPane.showInputDialog("Couleurs RGB du masque (séparée par des ';')").split(";");
		int halfWidth = Integer.parseInt(JOptionPane.showInputDialog("Largeur du patch",3));
		int searchWidth = Integer.parseInt(JOptionPane.showInputDialog("Largeur de la zone de recherche",50));
		String output = JOptionPane.showInputDialog("Chemin du fichier de sortie", input+"_output");

		Matrix matrix = new Matrix(input);
		Color color = new Color(Integer.parseInt(colors[0]),Integer.parseInt(colors[1]),Integer.parseInt(colors[2]));
		Mask mask = new Mask(matrix,color);
		Inpainting i = new Inpainting(matrix,mask);
		i.restore(halfWidth, searchWidth, output);
		JOptionPane.showMessageDialog(null, "Reconstruction terminé !");
		if(JOptionPane.showConfirmDialog(null, "Open the new image ?") == JOptionPane.YES_OPTION){
			Desktop.getDesktop().open(new File(output+".bmp"));
		}
	}
}
