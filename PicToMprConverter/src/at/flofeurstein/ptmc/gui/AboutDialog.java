/**
 * 
 * AboutDialog.java
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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AboutDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AboutDialog dialog = new AboutDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AboutDialog() {
		setTitle(Messages.getString("AboutDialog.this.title")); //$NON-NLS-1$
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblCopyright = new JLabel(Messages.getString("AboutDialog.lblCopyright.text")); //$NON-NLS-1$
			GridBagConstraints gbc_lblCopyright = new GridBagConstraints();
			gbc_lblCopyright.gridheight = 3;
			gbc_lblCopyright.gridwidth = 5;
			gbc_lblCopyright.insets = new Insets(0, 0, 5, 0);
			gbc_lblCopyright.gridx = 0;
			gbc_lblCopyright.gridy = 0;
			contentPanel.add(lblCopyright, gbc_lblCopyright);
		}

		{
			ImagePane panelGPLv3 = new ImagePane();
			GridBagConstraints gbc_panelGPLv3 = new GridBagConstraints();
			gbc_panelGPLv3.gridx = 4;
			gbc_panelGPLv3.gridy = 4;
			try {
				panelGPLv3.setImage(ImageIO.read(new File("../PicToMprConverter/src/at/flofeurstein/ptmc/gui/gplv3-127x51.png")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			{
				JLabel lblLizenz = new JLabel(Messages.getString("AboutDialog.lblLizenz.text")); //$NON-NLS-1$
				GridBagConstraints gbc_lblLizenz = new GridBagConstraints();
				gbc_lblLizenz.insets = new Insets(0, 0, 5, 0);
				gbc_lblLizenz.gridwidth = 8;
				gbc_lblLizenz.gridx = 0;
				gbc_lblLizenz.gridy = 3;
				contentPanel.add(lblLizenz, gbc_lblLizenz);
			}
			contentPanel.add(panelGPLv3, gbc_panelGPLv3);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton(Messages.getString("OkButton")); //$NON-NLS-1$
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						closeDialog();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton(Messages.getString("CancelButton")); //$NON-NLS-1$
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						closeDialog();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		
	}
	
	private void closeDialog(){
		this.dispose();
	}

}
