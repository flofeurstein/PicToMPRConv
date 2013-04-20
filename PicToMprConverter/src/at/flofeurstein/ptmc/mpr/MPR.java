/**
 * Author: Florian Feurstein
 * 
 * MPR.java
 */

package at.flofeurstein.ptmc.mpr;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public abstract class MPR {
	
	/**
	 * String where the textdata for the mpr file is stored
	 */
	protected StringBuilder m_mprString = new StringBuilder();
	
	/**
	 * Header string for the mpr file
	 */
	protected StringBuilder m_mprHeader = new StringBuilder("[H\n" + 
															"VERSION=\"4.0 Alpha\"\n" + 
															"HP=\"1\"\n" + 
															"IN=\"0\"\n" + 
															"GX=\"0\"\n" + 
															"BFS=\"1\"\n" + 
															"GY=\"1\"\n" + 
															"GXY=\"0\"\n" + 
															"UP=\"0\"\n" + 
															"FM=\"1\"\n" + 
															"FW=\"800\"\n" + 
															"HS=\"0\"\n" + 
															"OP=\"0\"\n" + 
															"MAT=\"WEEKE\"\n" + 
															"INCH=\"0\"\n" + 
															"VIEW=\"NOMIRROR\"\n" + 
															"ANZ=\"1\"\n\n"
															);
	
	/**
	 * Creates mpr format from image, writes it to m_mprString
	 * 
	 * @param workpieceLength is the length of the workpiece, ATTENTION: width in the GUI (Plattenbreite)
	 * @param workpieceWidth is the width of the workpiece, ATTENTION: height in the GUI (Plattenhöhe)
	 * @param workpieceThickness is the thickness of the workpiece
	 * @param toolDiameter is the diameter of the drill or the thickness of the mill
	 * @param minDepth is the minimum drill/mill depth
	 * @param maxDepth is the maximum drill/mill depth
	 * @param mode is the mode of drilling (LS or SS) or the stepwise mill information
	 * @param img is the image from which the mpr format is generated
	 */
	public abstract void createMPRfromImg(int workpieceLength, int workpieceWidth, int workpieceThickness, 
			int toolDiameter, float minDepth, float maxDepth, String mode, BufferedImage img);

	/**
	 * Writes the mpr format from the m_mprString to a file
	 * 
	 * @param path of the file where the data is written to
	 * @throws IOException 
	 */
	public void writeMPRToFile(String path) throws IOException {
		FileWriter output = new FileWriter(path);
		BufferedWriter writer = new BufferedWriter(output);
		
		writer.write(m_mprString.toString());
		
		writer.close();
	}
}
