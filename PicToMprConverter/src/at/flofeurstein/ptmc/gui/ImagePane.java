/**
 *
 * ImagePane.java
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

package at.flofeurstein.ptmc.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImagePane extends JPanel {
	
	/**
	 * UID for serialization
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Buffered Image that is shown in the panel
	 */
	private BufferedImage m_imgToShow;
	
	public ImagePane(){
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(m_imgToShow, 0, 0, this);
	}
	
	/**
	 * Set image to be shown in the panel, set perferred size according to image size
	 * and repaint the Panel
	 * 
	 * @param img buffered image
	 */
	public void setImage(BufferedImage img){
		setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
		m_imgToShow = img;
		repaint();
	}
	
	/**
	 * Get height of the currently drawn image, return 0 if no image available
	 * 
	 * @return image height or 0 if no image available
	 */
	public int getCurrHeight(){
		if(m_imgToShow != null){
			return m_imgToShow.getHeight();
		}else{
			return 0;
		}
	}
	
	/**
	 * Get width of the currently drawn image, return 0 if no image available
	 * 
	 * @return image width or 0 if no image available
	 */
	public int getCurrWidth(){
		if(m_imgToShow != null){
			return m_imgToShow.getWidth();
		}else{
			return 0;
		}	
	}
	
	/**
	 * Get currently shown image
	 * 
	 * @return buffered image that is currently shown in the panel
	 */
	public BufferedImage getCurrImage(){
		return m_imgToShow;
	}
	
}
