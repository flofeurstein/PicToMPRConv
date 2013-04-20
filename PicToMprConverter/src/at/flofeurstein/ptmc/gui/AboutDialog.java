/**
 * Author: Florian Feurstein
 * 
 * AboutDialog.java
 */

package at.flofeurstein.ptmc.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblAutor = new JLabel(Messages.getString("AboutDialog.lblAutor.text")); //$NON-NLS-1$
			GridBagConstraints gbc_lblAutor = new GridBagConstraints();
			gbc_lblAutor.insets = new Insets(0, 0, 5, 5);
			gbc_lblAutor.gridx = 1;
			gbc_lblAutor.gridy = 2;
			contentPanel.add(lblAutor, gbc_lblAutor);
		}
		{
			JLabel lblFlorianFeurstein = new JLabel(Messages.getString("AboutDialog.lblFlorianFeurstein.text")); //$NON-NLS-1$
			GridBagConstraints gbc_lblFlorianFeurstein = new GridBagConstraints();
			gbc_lblFlorianFeurstein.insets = new Insets(0, 0, 5, 0);
			gbc_lblFlorianFeurstein.gridx = 4;
			gbc_lblFlorianFeurstein.gridy = 2;
			contentPanel.add(lblFlorianFeurstein, gbc_lblFlorianFeurstein);
		}
		{
			JLabel lblLizenz = new JLabel(Messages.getString("AboutDialog.lblLizenz.text")); //$NON-NLS-1$
			GridBagConstraints gbc_lblLizenz = new GridBagConstraints();
			gbc_lblLizenz.insets = new Insets(0, 0, 0, 5);
			gbc_lblLizenz.gridx = 1;
			gbc_lblLizenz.gridy = 4;
			contentPanel.add(lblLizenz, gbc_lblLizenz);
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
