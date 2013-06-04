/**
 * 
 * ImgProcessor.java
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

package at.flofeurstein.ptmc.imgproc;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.RescaleOp;

public class ImgProcessor {
		
	public ImgProcessor(){
	}
	
	/**
	 * Creates a gray image of the input parameter img with a chosen width and height
	 * 
	 * @param img the image that is processed
	 * @param width is the width of the newly created image
	 * @param height is the height of the newly created image
	 * @return the newly created image
	 */
	public BufferedImage preProcImage(BufferedImage img, int width, int height){
		BufferedImage resizedGray = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D g = resizedGray.createGraphics();
	    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g.drawImage(img, 0, 0, width, height, 0, 0, img.getWidth(), img.getHeight(), null);
	    g.dispose();
	    
		return resizedGray;
	}
	
	/**
	 * Creates the gray negative of an image
	 * 
	 * @param img the image that is processed
	 * @return the gray and negative version of the input image
	 */
	public BufferedImage createGrayNegative(BufferedImage img){
		BufferedImage negative = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		
		BufferedImageOp biOp = new RescaleOp(-1.0f, 255f, null);
		biOp.filter(img, negative);
		
		return negative;
	}

}
