/**
 * 
 * MPR.java
 * 
 * Copyright 2013 Florian Feurstein
 * 
 * This file is part of the PicToMprConverter.
 *
 * PicToMprConverter is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PicToMprConverter is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with PicToMprConverter.  If not, see <http://www.gnu.org/licenses/>.
 * 
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
	 * @param ignoreMin drills/mills are just considered from this value
	 * @param ignoreMax drills/mills are just considered to this value
	 * @param mode is the mode of drilling (LS or SS) or the stepwise mill information
	 * @param squareDrill defines if the drill geometry is a square drill or a rhombus drill
	 * @param img is the image from which the mpr format is generated
	 */
	public abstract void createMPRfromImg(int workpieceLength, int workpieceWidth, 
			float workpieceThickness, float toolDiameter, float minDepth, float maxDepth, 
			float ignoreMin, float ignoreMax, String mode, boolean squareDrill, BufferedImage img);

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
