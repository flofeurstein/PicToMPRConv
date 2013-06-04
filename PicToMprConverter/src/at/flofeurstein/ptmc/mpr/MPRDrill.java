/**
 * 
 * MPRDrill.java
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
import java.awt.image.Raster;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MPRDrill extends MPR{

	public final int MAX_GRAYVAL = 255;
	public final int MAX_FRACT_DIGITS = 2;
	
	public void createMPRfromImg(int workpieceLength, int workpieceWidth, 
			float workpieceThickness, float drillDiameter, float minDrillDepth, float maxDrillDepth, 
			float ignoreMin, float ignoreMax, String drillMode, boolean squareDrill, BufferedImage img) {
		
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat decFormat = (DecimalFormat)nf;
		//decFormat.applyPattern("0.00");
		decFormat.setMaximumFractionDigits(MAX_FRACT_DIGITS);
		float wpToResFactor = (float)workpieceLength/(float)img.getWidth();//workpiece to resolution factor is needed if the workpiece is larger or smaller than the image resolution
		float mappedDrillDepth = 0;
		Calendar nowCal = new GregorianCalendar();
		nowCal.setTime(new Date());
		String nowDate = nowCal.get(Calendar.DAY_OF_MONTH) + "." + (nowCal.get(Calendar.MONTH) + 1) + "." 
					+ nowCal.get(Calendar.YEAR) + " " + nowCal.get(Calendar.HOUR_OF_DAY) + "Uhr" + nowCal.get(Calendar.MINUTE);
		
		StringBuilder commentField001 = new StringBuilder("[001\n" + 
														"laenge=\"" + workpieceLength + "\"\n" +
														"KM=\"Laenge in X\"\n" +
														"breite=\"" + workpieceWidth + "\"\n" +
														"KM=\"Breite in Y\"\n" +
														"staerke=\"" + decFormat.format(workpieceThickness) + "\"\n" +
														"KM=\"Dicke des Werkstücks in Z\"\n" +
														"anzeige=\"1\"\n" +
														"KM=\"BOOL es werden die Durchmesser angezeigt\"\n" +
														"dm=\"" + decFormat.format(drillDiameter) + "\"\n" +
														"KM=\"Durchmesser Bohrer  \"\n" +
														"faktorgr=\"1\"\n" +
														"KM=\"Faktor in der Groesse veraendern\"\n" +
														"faktorti=\"1\"\n" +
														"KM=\"Faktor in der Tiefe verandern\"\n\n"
														);
		
		StringBuilder workpiece = new StringBuilder("<100 \\WerkStck\\\n" +
														"LA=\"laenge*faktorgr\"\n" +
														"BR=\"breite*faktorgr\"\n" +
														"DI=\"staerke\"\n" +
														"FNX=\"0\"\n" +
														"FNY=\"0\"\n" +
														"AX=\"0\"\n" +
														"AY=\"0\"\n\n"
														);
		
		StringBuilder commentField101 = new StringBuilder("<101 \\Kommentar\\\n" +
														"KM=\"Bildpunkte bohren\"\n" +
														"KM=\"erstellt am " + nowDate + " von ptmc\"\n" +
														"KM=\"\"\n" +
														"KM=\"mindestbohrtiefe= " + decFormat.format(minDrillDepth) + "mm\"\n" +
														"KM=\"maximalbohrtiefe =" + decFormat.format(maxDrillDepth) + "mm\"\n\n"
														);
		
		m_mprString.append(m_mprHeader.append(commentField001.append(workpiece.append(commentField101.toString()))));	
		
		Raster imgRaster = img.getData();
		
		if(squareDrill){

			/*
			 * Go through all the pixels in the raster, beginning at the right upper corner
			 */
			for(int line = 0; line < imgRaster.getHeight(); line++){//loop for all the lines
				for(int col = 0; col < imgRaster.getWidth(); col++){//loop for all the columns
					
					/*
					 * Map grayvalue to value between maxDrillDepth and minDrillDepth.
					 * Grayvalue: 255 is white and 0 is black
					 * To get the drill deeper, the darker the grayvalue is, I have to subtract the actual
					 * grayvalue from the maximum grayvalue (255)
					 * 
					 * For XA I calculate the value by subtracting the current column from the 
					 * overall height of the raster, that is because the mpr should start
					 * at the top right corner (if I don't do this, the image is mirrored in the mpr file)
					 * 
					 * For YA I have to add 1 to the current line number because
					 * the Raster is zero based but the mpr is 1 based
					 */
					mappedDrillDepth = mapDrillDepth(maxDrillDepth, minDrillDepth, (MAX_GRAYVAL - imgRaster.getSample(col, line, 0)));
					
					if(mappedDrillDepth > ignoreMin && mappedDrillDepth < ignoreMax){
						m_mprString.append("<102 \\BohrVert\\\n" +
											"XA=\"faktorgr*" + wpToResFactor * (float)(imgRaster.getWidth() - col) + "\"\n" +
											"YA=\"faktorgr*" + wpToResFactor * (float)(line + 1) + "\"\n" +
											"BM=\"" + drillMode + "\"\n" +
											"TI=\"faktorti*" + decFormat.format(mappedDrillDepth) + "\"\n" +
											"DU=\"IF anzeige=0 THEN dm ELSE faktorti*" + decFormat.format(mappedDrillDepth) + "\"\n\n"
											);
					}
				}
			}
		}else{
			/*
			 * Go through all the pixels in the raster, beginning at the right upper corner
			 */
			int col = 1;
			for(int line = 0; line < imgRaster.getHeight(); line++){//loop for all the lines
				if(line%2 == 0){
					col = 1;
				}else{
					col = 0;
				}
				while(col < imgRaster.getWidth()){//loop for all the columns
					
					/*
					 * Map grayvalue to value between maxDrillDepth and minDrillDepth.
					 * Grayvalue: 255 is white and 0 is black
					 * To get the drill deeper, the darker the grayvalue is, I have to subtract the actual
					 * grayvalue from the maximum grayvalue (255)
					 * 
					 * For XA I calculate the value by subtracting the current column from the 
					 * overall height of the raster, that is because the mpr should start
					 * at the top right corner (if I don't do this, the image is mirrored in the mpr file)
					 * 
					 * For YA I have to add 1 to the current line number because
					 * the Raster is zero based but the mpr is 1 based
					 */
					mappedDrillDepth = mapDrillDepth(maxDrillDepth, minDrillDepth, (MAX_GRAYVAL - imgRaster.getSample(col, line, 0)));
					
					if(mappedDrillDepth > ignoreMin && mappedDrillDepth < ignoreMax){
						m_mprString.append("<102 \\BohrVert\\\n" +
											"XA=\"faktorgr*" + wpToResFactor * (float)(imgRaster.getWidth() - col) + "\"\n" +
											"YA=\"faktorgr*" + wpToResFactor * (float)(line + 1) + "\"\n" +
											"BM=\"" + drillMode + "\"\n" +
											"TI=\"faktorti*" + decFormat.format(mappedDrillDepth) + "\"\n" +
											"DU=\"IF anzeige=0 THEN dm ELSE faktorti*" + decFormat.format(mappedDrillDepth) + "\"\n\n"
											);
					}
					col+=2;
				}
			}
		}
		
		
		/*
		 * Append end marker to the mpr
		 */
		m_mprString.append("!");
		
	}
	
	/**
	 * Map the color information of a pixel to a value in the drill depth interval from maxDepth to minDepth
	 * See Arduino Map function ((x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min)
	 * 
	 * @param maxDepth maximum depth the machine is allowed to drill
	 * @param minDepth minimum depth the machine should drill
	 * @param grayValue the gray value (0 - 255) of a single pixel
	 * @return
	 */
	private float mapDrillDepth(float maxDepth, float minDepth, int grayValue){
		return (grayValue - 0) * (maxDepth - minDepth) / (255 - 0) + minDepth;
	}
	
}
