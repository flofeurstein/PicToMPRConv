/**
 * Author: Florian Feurstein
 * 
 * ImgProcessor.java
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
