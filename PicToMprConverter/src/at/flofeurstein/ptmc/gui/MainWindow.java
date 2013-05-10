/**
 * Author: Florian Feurstein
 * 
 * MainWindow.java
 */

package at.flofeurstein.ptmc.gui;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import at.flofeurstein.ptmc.imgproc.ImgProcessor;
import at.flofeurstein.ptmc.mpr.MPR;
import at.flofeurstein.ptmc.mpr.MPRDrill;
import at.flofeurstein.ptmc.mpr.MPRMill;
import javax.swing.JCheckBox;

public class MainWindow {

	private JFrame frmPictomprconverter;
	private JTextField pictureHeightTf;
	private JTextField pictureWidthTf;
	private JTextField workpieceHeightTf;
	private JTextField workpieceWidthTf;
	private JTextField workpieceThicknessTf;
	private JTextField minDepthTf;
	private JTextField maxDepthTf;
	private JTextField hideFromTf;
	private JTextField hideToTf;
	private JTextField drillDiameterTf;
	private JTextField toolNrTf;
	private JTextField millStepwiseTf;
	private JPanel m_drillPanel;
	private JPanel m_millPanel;
	private JComboBox<String> m_drillModeCombo;
	private final ButtonGroup drillMillRdbtnGroup = new ButtonGroup();
	private JTextField picPathTf;
	private JFileChooser m_fcImgBrowse;
	private JFileChooser m_fcMprBrowse;
	private ImagePane m_imgPanel;
	private JTextField mprPathTf;
	private ImgProcessor m_imgProc;
	private JCheckBox m_negativeChkBx;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmPictomprconverter.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m_imgProc = new ImgProcessor();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPictomprconverter = new JFrame();
		frmPictomprconverter.setTitle(Messages.getString("MainWindow.frmPictomprconverter.title")); //$NON-NLS-1$
		frmPictomprconverter.setBounds(100, 100, 843, 700);
		frmPictomprconverter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmPictomprconverter.setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu(Messages.getString("MenuFile")); //$NON-NLS-1$
		menuBar.add(fileMenu);
		
		JMenuItem loadPicMenuItem = new JMenuItem(Messages.getString("MenuItemOpenPic")); //$NON-NLS-1$
		loadPicMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadNewImgEvent();
			}
		});
		fileMenu.add(loadPicMenuItem);
		
		JMenuItem exitMenuItem = new JMenuItem(Messages.getString("MenuItemExit")); //$NON-NLS-1$
		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmPictomprconverter.dispose();
			}
		});
		fileMenu.add(exitMenuItem);
		
		JMenu helpMenu = new JMenu(Messages.getString("MainWindow.mnHilfe.text")); //$NON-NLS-1$
		menuBar.add(helpMenu);
		
		JMenuItem aboutMenuItem = new JMenuItem(Messages.getString("MainWindow.mntmberDasProgramm.text")); //$NON-NLS-1$
		aboutMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog aboutDialog = new AboutDialog();
				aboutDialog.setModal(true);
				aboutDialog.setVisible(true);
			}
		});
		helpMenu.add(aboutMenuItem);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{827, 0};
		gridBagLayout.rowHeights = new int[]{701, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		frmPictomprconverter.getContentPane().setLayout(gridBagLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		frmPictomprconverter.getContentPane().add(scrollPane, gbc_scrollPane);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setViewportView(mainPanel);
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_mainPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_mainPanel.columnWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_mainPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		mainPanel.setLayout(gbl_mainPanel);
		
		JPanel settingsPanel = new JPanel();
		settingsPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), Messages.getString("JPanelTitle1"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_settingsPanel = new GridBagConstraints();
		gbc_settingsPanel.gridwidth = 3;
		gbc_settingsPanel.fill = GridBagConstraints.BOTH;
		gbc_settingsPanel.gridheight = 3;
		gbc_settingsPanel.insets = new Insets(0, 0, 5, 5);
		gbc_settingsPanel.gridx = 0;
		gbc_settingsPanel.gridy = 0;
		mainPanel.add(settingsPanel, gbc_settingsPanel);
		GridBagLayout gbl_settingsPanel = new GridBagLayout();
		gbl_settingsPanel.columnWidths = new int[]{0, 0, 29, 0, 0, 0, 0, 0, 0};
		gbl_settingsPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_settingsPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_settingsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		settingsPanel.setLayout(gbl_settingsPanel);
		
		JRadioButton drillRdbtn = new JRadioButton(Messages.getString("MainWindow.rdbtnBohren.text"));
		drillMillRdbtnGroup.add(drillRdbtn);
		drillRdbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enableDrillPanel();
				disableMillPanel();
			}
		});

		GridBagConstraints gbc_drillRdbtn = new GridBagConstraints();
		gbc_drillRdbtn.insets = new Insets(0, 0, 5, 5);
		gbc_drillRdbtn.gridx = 0;
		gbc_drillRdbtn.gridy = 0;
		settingsPanel.add(drillRdbtn, gbc_drillRdbtn);
		
		JRadioButton millRdbtn = new JRadioButton(Messages.getString("MainWindow.rdbtnFrsen.text"));
		drillMillRdbtnGroup.add(millRdbtn);
		
		/*TODO remove the following line when mill function is implemented*/
		millRdbtn.setEnabled(false);
		millRdbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enableMillPanel();
				disableDrillPanel();
			}
		});

		GridBagConstraints gbc_millRdbtn = new GridBagConstraints();
		gbc_millRdbtn.insets = new Insets(0, 0, 5, 5);
		gbc_millRdbtn.gridx = 1;
		gbc_millRdbtn.gridy = 0;
		settingsPanel.add(millRdbtn, gbc_millRdbtn);
		
		JPanel scalePanel = new JPanel();
		scalePanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), Messages.getString("JPanelTitle2"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_scalePanel = new GridBagConstraints();
		gbc_scalePanel.fill = GridBagConstraints.BOTH;
		gbc_scalePanel.gridheight = 2;
		gbc_scalePanel.gridwidth = 2;
		gbc_scalePanel.insets = new Insets(0, 0, 5, 5);
		gbc_scalePanel.gridx = 0;
		gbc_scalePanel.gridy = 1;
		settingsPanel.add(scalePanel, gbc_scalePanel);
		GridBagLayout gbl_scalePanel = new GridBagLayout();
		gbl_scalePanel.columnWidths = new int[]{0, 0, 48, 0};
		gbl_scalePanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_scalePanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_scalePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		scalePanel.setLayout(gbl_scalePanel);
		
		JLabel pictureHeightLbl = new JLabel(Messages.getString("MainWindow.lblHhe.text"));
		GridBagConstraints gbc_pictureHeightLbl = new GridBagConstraints();
		gbc_pictureHeightLbl.anchor = GridBagConstraints.EAST;
		gbc_pictureHeightLbl.insets = new Insets(0, 0, 5, 5);
		gbc_pictureHeightLbl.gridx = 0;
		gbc_pictureHeightLbl.gridy = 0;
		scalePanel.add(pictureHeightLbl, gbc_pictureHeightLbl);
		
		pictureHeightTf = new JTextField();
		pictureHeightTf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				setNewWidthToGivenHeightPx();
			}
		});
		
	
		GridBagConstraints gbc_pictureHeightTf = new GridBagConstraints();
		gbc_pictureHeightTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_pictureHeightTf.insets = new Insets(0, 0, 5, 5);
		gbc_pictureHeightTf.gridx = 1;
		gbc_pictureHeightTf.gridy = 0;
		scalePanel.add(pictureHeightTf, gbc_pictureHeightTf);
		pictureHeightTf.setHorizontalAlignment(SwingConstants.RIGHT);
		pictureHeightTf.setText(Messages.getString("MainWindow.textField.text"));
		pictureHeightTf.setColumns(10);
		
		JLabel px1Lbl = new JLabel(Messages.getString("MainWindow.lblPx.text"));
		GridBagConstraints gbc_px1Lbl = new GridBagConstraints();
		gbc_px1Lbl.anchor = GridBagConstraints.WEST;
		gbc_px1Lbl.insets = new Insets(0, 0, 5, 0);
		gbc_px1Lbl.gridx = 2;
		gbc_px1Lbl.gridy = 0;
		scalePanel.add(px1Lbl, gbc_px1Lbl);
		
		JLabel workpieceHeightLbl = new JLabel(Messages.getString("MainWindow.lblPlattenhhe.text")); //$NON-NLS-1$
		GridBagConstraints gbc_workpieceHeightLbl = new GridBagConstraints();
		gbc_workpieceHeightLbl.insets = new Insets(0, 0, 5, 5);
		gbc_workpieceHeightLbl.anchor = GridBagConstraints.EAST;
		gbc_workpieceHeightLbl.gridx = 0;
		gbc_workpieceHeightLbl.gridy = 1;
		scalePanel.add(workpieceHeightLbl, gbc_workpieceHeightLbl);
		
		workpieceHeightTf = new JTextField();
		workpieceHeightTf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				setNewWidthToGivenHeightMm();
			}
		});
		GridBagConstraints gbc_workpieceHeightTf = new GridBagConstraints();
		gbc_workpieceHeightTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_workpieceHeightTf.insets = new Insets(0, 0, 5, 5);
		gbc_workpieceHeightTf.gridx = 1;
		gbc_workpieceHeightTf.gridy = 1;
		scalePanel.add(workpieceHeightTf, gbc_workpieceHeightTf);
		workpieceHeightTf.setHorizontalAlignment(SwingConstants.RIGHT);
		workpieceHeightTf.setText(Messages.getString("MainWindow.textField_2.text"));
		workpieceHeightTf.setColumns(10);
		
		JLabel mm1Lbl = new JLabel(Messages.getString("MainWindow.lblMm.text"));
		GridBagConstraints gbc_mm1Lbl = new GridBagConstraints();
		gbc_mm1Lbl.anchor = GridBagConstraints.WEST;
		gbc_mm1Lbl.insets = new Insets(0, 0, 5, 0);
		gbc_mm1Lbl.gridx = 2;
		gbc_mm1Lbl.gridy = 1;
		scalePanel.add(mm1Lbl, gbc_mm1Lbl);
		
		JLabel pictureWidthLbl = new JLabel(Messages.getString("MainWindow.lblBreite.text"));
		GridBagConstraints gbc_pictureWidthLbl = new GridBagConstraints();
		gbc_pictureWidthLbl.anchor = GridBagConstraints.EAST;
		gbc_pictureWidthLbl.insets = new Insets(0, 0, 5, 5);
		gbc_pictureWidthLbl.gridx = 0;
		gbc_pictureWidthLbl.gridy = 2;
		scalePanel.add(pictureWidthLbl, gbc_pictureWidthLbl);
		
		pictureWidthTf = new JTextField();
		pictureWidthTf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				setNewHeightToGivenWidthPx();
			}
		});

		GridBagConstraints gbc_pictureWidthTf = new GridBagConstraints();
		gbc_pictureWidthTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_pictureWidthTf.insets = new Insets(0, 0, 5, 5);
		gbc_pictureWidthTf.gridx = 1;
		gbc_pictureWidthTf.gridy = 2;
		scalePanel.add(pictureWidthTf, gbc_pictureWidthTf);
		pictureWidthTf.setHorizontalAlignment(SwingConstants.RIGHT);
		pictureWidthTf.setText(Messages.getString("MainWindow.textField_1.text"));
		pictureWidthTf.setColumns(10);
		
		JLabel px2Lbl = new JLabel(Messages.getString("MainWindow.lblPx_1.text"));
		GridBagConstraints gbc_px2Lbl = new GridBagConstraints();
		gbc_px2Lbl.anchor = GridBagConstraints.WEST;
		gbc_px2Lbl.insets = new Insets(0, 0, 5, 0);
		gbc_px2Lbl.gridx = 2;
		gbc_px2Lbl.gridy = 2;
		scalePanel.add(px2Lbl, gbc_px2Lbl);
		
		JLabel workpieceWidthLbl = new JLabel(Messages.getString("MainWindow.lblPlattenbreite.text")); //$NON-NLS-1$
		GridBagConstraints gbc_workpieceWidthLbl = new GridBagConstraints();
		gbc_workpieceWidthLbl.insets = new Insets(0, 0, 5, 5);
		gbc_workpieceWidthLbl.anchor = GridBagConstraints.EAST;
		gbc_workpieceWidthLbl.gridx = 0;
		gbc_workpieceWidthLbl.gridy = 3;
		scalePanel.add(workpieceWidthLbl, gbc_workpieceWidthLbl);
		
		workpieceWidthTf = new JTextField();
		workpieceWidthTf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				setNewHeightToGivenWidthMm();
			}
		});
		GridBagConstraints gbc_workpieceWidthTf = new GridBagConstraints();
		gbc_workpieceWidthTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_workpieceWidthTf.insets = new Insets(0, 0, 5, 5);
		gbc_workpieceWidthTf.gridx = 1;
		gbc_workpieceWidthTf.gridy = 3;
		scalePanel.add(workpieceWidthTf, gbc_workpieceWidthTf);
		workpieceWidthTf.setHorizontalAlignment(SwingConstants.RIGHT);
		workpieceWidthTf.setText(Messages.getString("MainWindow.textField_3.text"));
		workpieceWidthTf.setColumns(10);
		
		JLabel mm2Lbl = new JLabel(Messages.getString("MainWindow.lblMm_1.text"));
		GridBagConstraints gbc_mm2Lbl = new GridBagConstraints();
		gbc_mm2Lbl.anchor = GridBagConstraints.WEST;
		gbc_mm2Lbl.insets = new Insets(0, 0, 5, 0);
		gbc_mm2Lbl.gridx = 2;
		gbc_mm2Lbl.gridy = 3;
		scalePanel.add(mm2Lbl, gbc_mm2Lbl);
		
		JLabel workpieceThicknessLbl = new JLabel(Messages.getString("MainWindow.lblPlattendicke.text"));
		GridBagConstraints gbc_workpieceThicknessLbl = new GridBagConstraints();
		gbc_workpieceThicknessLbl.anchor = GridBagConstraints.EAST;
		gbc_workpieceThicknessLbl.insets = new Insets(0, 0, 0, 5);
		gbc_workpieceThicknessLbl.gridx = 0;
		gbc_workpieceThicknessLbl.gridy = 4;
		scalePanel.add(workpieceThicknessLbl, gbc_workpieceThicknessLbl);
		
		workpieceThicknessTf = new JTextField();
		workpieceThicknessTf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(!isPosNr(Integer.parseInt(workpieceThicknessTf.getText()))){
					errorPopUp(Messages.getString("valBtZero"));
					workpieceThicknessTf.setText("0");
				}
				
			}
		});
		GridBagConstraints gbc_workpieceThicknessTf = new GridBagConstraints();
		gbc_workpieceThicknessTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_workpieceThicknessTf.insets = new Insets(0, 0, 0, 5);
		gbc_workpieceThicknessTf.gridx = 1;
		gbc_workpieceThicknessTf.gridy = 4;
		scalePanel.add(workpieceThicknessTf, gbc_workpieceThicknessTf);
		workpieceThicknessTf.setHorizontalAlignment(SwingConstants.RIGHT);
		workpieceThicknessTf.setText(Messages.getString("MainWindow.textField_4.text"));
		workpieceThicknessTf.setColumns(10);
		
		JLabel mm3Lbl = new JLabel(Messages.getString("MainWindow.lblMm_2.text"));
		GridBagConstraints gbc_mm3Lbl = new GridBagConstraints();
		gbc_mm3Lbl.anchor = GridBagConstraints.WEST;
		gbc_mm3Lbl.gridx = 2;
		gbc_mm3Lbl.gridy = 4;
		scalePanel.add(mm3Lbl, gbc_mm3Lbl);
		
		JPanel workingDepthPanel = new JPanel();
		workingDepthPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), Messages.getString("JPanelTitle3"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_workingDepthPanel = new GridBagConstraints();
		gbc_workingDepthPanel.fill = GridBagConstraints.BOTH;
		gbc_workingDepthPanel.gridheight = 2;
		gbc_workingDepthPanel.insets = new Insets(0, 0, 5, 5);
		gbc_workingDepthPanel.gridx = 4;
		gbc_workingDepthPanel.gridy = 1;
		settingsPanel.add(workingDepthPanel, gbc_workingDepthPanel);
		GridBagLayout gbl_workingDepthPanel = new GridBagLayout();
		gbl_workingDepthPanel.columnWidths = new int[]{29, 0, 23, 0};
		gbl_workingDepthPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_workingDepthPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_workingDepthPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		workingDepthPanel.setLayout(gbl_workingDepthPanel);
		
		JLabel minDepthLbl = new JLabel(Messages.getString("MainWindow.lblMinimaleTiefe.text"));
		GridBagConstraints gbc_minDepthLbl = new GridBagConstraints();
		gbc_minDepthLbl.anchor = GridBagConstraints.EAST;
		gbc_minDepthLbl.insets = new Insets(0, 0, 5, 5);
		gbc_minDepthLbl.gridx = 0;
		gbc_minDepthLbl.gridy = 0;
		workingDepthPanel.add(minDepthLbl, gbc_minDepthLbl);
		
		minDepthTf = new JTextField();
		minDepthTf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(!isPosNr(Integer.parseInt(minDepthTf.getText()))){
					errorPopUp(Messages.getString("valBtZero"));
					minDepthTf.setText("0");
				}
			}
		});
		GridBagConstraints gbc_minDepthTf = new GridBagConstraints();
		gbc_minDepthTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_minDepthTf.insets = new Insets(0, 0, 5, 5);
		gbc_minDepthTf.gridx = 1;
		gbc_minDepthTf.gridy = 0;
		workingDepthPanel.add(minDepthTf, gbc_minDepthTf);
		minDepthTf.setHorizontalAlignment(SwingConstants.RIGHT);
		minDepthTf.setText(Messages.getString("MainWindow.textField_5.text"));
		minDepthTf.setColumns(10);
		
		JLabel mm4Lbl = new JLabel(Messages.getString("MainWindow.lblMm_3.text"));
		GridBagConstraints gbc_mm4Lbl = new GridBagConstraints();
		gbc_mm4Lbl.anchor = GridBagConstraints.WEST;
		gbc_mm4Lbl.insets = new Insets(0, 0, 5, 0);
		gbc_mm4Lbl.gridx = 2;
		gbc_mm4Lbl.gridy = 0;
		workingDepthPanel.add(mm4Lbl, gbc_mm4Lbl);
		
		JLabel maxDepthLbl = new JLabel(Messages.getString("MainWindow.lblMaximaleTiefe.text"));
		GridBagConstraints gbc_maxDepthLbl = new GridBagConstraints();
		gbc_maxDepthLbl.anchor = GridBagConstraints.EAST;
		gbc_maxDepthLbl.insets = new Insets(0, 0, 5, 5);
		gbc_maxDepthLbl.gridx = 0;
		gbc_maxDepthLbl.gridy = 1;
		workingDepthPanel.add(maxDepthLbl, gbc_maxDepthLbl);
		
		maxDepthTf = new JTextField();
		maxDepthTf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!isPosNr(Integer.parseInt(maxDepthTf.getText()))){
					errorPopUp(Messages.getString("valBtZero"));
					maxDepthTf.setText("0");
				}
			}
		});
		GridBagConstraints gbc_maxDepthTf = new GridBagConstraints();
		gbc_maxDepthTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_maxDepthTf.insets = new Insets(0, 0, 5, 5);
		gbc_maxDepthTf.gridx = 1;
		gbc_maxDepthTf.gridy = 1;
		workingDepthPanel.add(maxDepthTf, gbc_maxDepthTf);
		maxDepthTf.setHorizontalAlignment(SwingConstants.RIGHT);
		maxDepthTf.setText(Messages.getString("MainWindow.textField_6.text"));
		maxDepthTf.setColumns(10);
		
		JLabel mm5Lbl = new JLabel(Messages.getString("MainWindow.lblMm_4.text"));
		GridBagConstraints gbc_mm5Lbl = new GridBagConstraints();
		gbc_mm5Lbl.anchor = GridBagConstraints.WEST;
		gbc_mm5Lbl.insets = new Insets(0, 0, 5, 0);
		gbc_mm5Lbl.gridx = 2;
		gbc_mm5Lbl.gridy = 1;
		workingDepthPanel.add(mm5Lbl, gbc_mm5Lbl);
		
		JLabel hideDepthsLbl = new JLabel(Messages.getString("MainWindow.lblTiefenAusblenden.text"));
		GridBagConstraints gbc_hideDepthsLbl = new GridBagConstraints();
		gbc_hideDepthsLbl.insets = new Insets(0, 0, 5, 5);
		gbc_hideDepthsLbl.gridx = 0;
		gbc_hideDepthsLbl.gridy = 2;
		workingDepthPanel.add(hideDepthsLbl, gbc_hideDepthsLbl);
		
		JLabel hideFromLbl = new JLabel(Messages.getString("MainWindow.lblVon.text"));
		GridBagConstraints gbc_hideFromLbl = new GridBagConstraints();
		gbc_hideFromLbl.anchor = GridBagConstraints.EAST;
		gbc_hideFromLbl.insets = new Insets(0, 0, 5, 5);
		gbc_hideFromLbl.gridx = 0;
		gbc_hideFromLbl.gridy = 3;
		workingDepthPanel.add(hideFromLbl, gbc_hideFromLbl);
		
		hideFromTf = new JTextField();
		hideFromTf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!isPosNr(Integer.parseInt(hideFromTf.getText()))){
					errorPopUp(Messages.getString("valBtZero"));
					hideFromTf.setText("0");
				}
			}
		});
		GridBagConstraints gbc_hideFromTf = new GridBagConstraints();
		gbc_hideFromTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_hideFromTf.insets = new Insets(0, 0, 5, 5);
		gbc_hideFromTf.gridx = 1;
		gbc_hideFromTf.gridy = 3;
		workingDepthPanel.add(hideFromTf, gbc_hideFromTf);
		hideFromTf.setHorizontalAlignment(SwingConstants.RIGHT);
		hideFromTf.setText(Messages.getString("MainWindow.textField_7.text"));
		hideFromTf.setColumns(10);
		
		JLabel mm6Lbl = new JLabel(Messages.getString("MainWindow.lblMm_5.text"));
		GridBagConstraints gbc_mm6Lbl = new GridBagConstraints();
		gbc_mm6Lbl.anchor = GridBagConstraints.WEST;
		gbc_mm6Lbl.insets = new Insets(0, 0, 5, 0);
		gbc_mm6Lbl.gridx = 2;
		gbc_mm6Lbl.gridy = 3;
		workingDepthPanel.add(mm6Lbl, gbc_mm6Lbl);
		
		JLabel hideToLbl = new JLabel(Messages.getString("MainWindow.lblBis.text"));
		GridBagConstraints gbc_hideToLbl = new GridBagConstraints();
		gbc_hideToLbl.anchor = GridBagConstraints.EAST;
		gbc_hideToLbl.insets = new Insets(0, 0, 0, 5);
		gbc_hideToLbl.gridx = 0;
		gbc_hideToLbl.gridy = 4;
		workingDepthPanel.add(hideToLbl, gbc_hideToLbl);
		
		hideToTf = new JTextField();
		hideToTf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!isPosNr(Integer.parseInt(hideToTf.getText()))){
					errorPopUp(Messages.getString("valBtZero"));
					hideToTf.setText("0");
				}
			}
		});
		GridBagConstraints gbc_hideToTf = new GridBagConstraints();
		gbc_hideToTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_hideToTf.insets = new Insets(0, 0, 0, 5);
		gbc_hideToTf.gridx = 1;
		gbc_hideToTf.gridy = 4;
		workingDepthPanel.add(hideToTf, gbc_hideToTf);
		hideToTf.setHorizontalAlignment(SwingConstants.RIGHT);
		hideToTf.setText(Messages.getString("MainWindow.textField_8.text"));
		hideToTf.setColumns(10);
		
		JLabel mm7Lbl = new JLabel(Messages.getString("MainWindow.lblMm_6.text"));
		GridBagConstraints gbc_mm7Lbl = new GridBagConstraints();
		gbc_mm7Lbl.anchor = GridBagConstraints.WEST;
		gbc_mm7Lbl.gridx = 2;
		gbc_mm7Lbl.gridy = 4;
		workingDepthPanel.add(mm7Lbl, gbc_mm7Lbl);
		
		m_drillPanel = new JPanel();
		m_drillPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), Messages.getString("JPanelTitle4"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_m_drillPanel = new GridBagConstraints();
		gbc_m_drillPanel.fill = GridBagConstraints.BOTH;
		gbc_m_drillPanel.insets = new Insets(0, 0, 5, 0);
		gbc_m_drillPanel.gridx = 7;
		gbc_m_drillPanel.gridy = 1;
		settingsPanel.add(m_drillPanel, gbc_m_drillPanel);
		GridBagLayout gbl_m_drillPanel = new GridBagLayout();
		gbl_m_drillPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_m_drillPanel.rowHeights = new int[]{0, 0, 0};
		gbl_m_drillPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_m_drillPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		m_drillPanel.setLayout(gbl_m_drillPanel);
		
		JLabel drillDiameterLbl = new JLabel(Messages.getString("MainWindow.lblBohrdurchmesser.text"));
		GridBagConstraints gbc_drillDiameterLbl = new GridBagConstraints();
		gbc_drillDiameterLbl.anchor = GridBagConstraints.EAST;
		gbc_drillDiameterLbl.insets = new Insets(0, 0, 5, 5);
		gbc_drillDiameterLbl.gridx = 0;
		gbc_drillDiameterLbl.gridy = 0;
		m_drillPanel.add(drillDiameterLbl, gbc_drillDiameterLbl);
		
		drillDiameterTf = new JTextField();
		drillDiameterTf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!isPosNr(Integer.parseInt(drillDiameterTf.getText()))){
					errorPopUp(Messages.getString("valBtZero"));
					drillDiameterTf.setText("0");
				}
			}
		});
		GridBagConstraints gbc_drillDiameterTf = new GridBagConstraints();
		gbc_drillDiameterTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_drillDiameterTf.insets = new Insets(0, 0, 5, 5);
		gbc_drillDiameterTf.gridx = 1;
		gbc_drillDiameterTf.gridy = 0;
		m_drillPanel.add(drillDiameterTf, gbc_drillDiameterTf);
		drillDiameterTf.setHorizontalAlignment(SwingConstants.RIGHT);
		drillDiameterTf.setText(Messages.getString("MainWindow.textField_9.text"));
		drillDiameterTf.setColumns(10);
		
		JLabel mm8Lbl = new JLabel(Messages.getString("MainWindow.lblMm_7.text"));
		GridBagConstraints gbc_mm8Lbl = new GridBagConstraints();
		gbc_mm8Lbl.anchor = GridBagConstraints.WEST;
		gbc_mm8Lbl.insets = new Insets(0, 0, 5, 0);
		gbc_mm8Lbl.gridx = 2;
		gbc_mm8Lbl.gridy = 0;
		m_drillPanel.add(mm8Lbl, gbc_mm8Lbl);
		
		JLabel drillModeLbl = new JLabel(Messages.getString("MainWindow.lblBohrmodus.text"));
		GridBagConstraints gbc_drillModeLbl = new GridBagConstraints();
		gbc_drillModeLbl.anchor = GridBagConstraints.EAST;
		gbc_drillModeLbl.insets = new Insets(0, 0, 0, 5);
		gbc_drillModeLbl.gridx = 0;
		gbc_drillModeLbl.gridy = 1;
		m_drillPanel.add(drillModeLbl, gbc_drillModeLbl);
		
		m_drillModeCombo = new JComboBox<String>();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		m_drillPanel.add(m_drillModeCombo, gbc_comboBox);
		m_drillModeCombo.setModel(new DefaultComboBoxModel<String>(new String[] {"LS", "SS"}));
		
		m_millPanel = new JPanel();
		m_millPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), Messages.getString("JPanelTitle5"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_m_millPanel = new GridBagConstraints();
		gbc_m_millPanel.insets = new Insets(0, 0, 5, 0);
		gbc_m_millPanel.fill = GridBagConstraints.BOTH;
		gbc_m_millPanel.gridx = 7;
		gbc_m_millPanel.gridy = 2;
		settingsPanel.add(m_millPanel, gbc_m_millPanel);
		GridBagLayout gbl_m_millPanel = new GridBagLayout();
		gbl_m_millPanel.columnWidths = new int[]{0, 0, 0};
		gbl_m_millPanel.rowHeights = new int[]{0, 0, 0};
		gbl_m_millPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_m_millPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		m_millPanel.setLayout(gbl_m_millPanel);
		
		JLabel toolNrLbl = new JLabel(Messages.getString("MainWindow.lblWerkzeugnummer.text"));
		GridBagConstraints gbc_toolNrLbl = new GridBagConstraints();
		gbc_toolNrLbl.anchor = GridBagConstraints.EAST;
		gbc_toolNrLbl.insets = new Insets(0, 0, 5, 5);
		gbc_toolNrLbl.gridx = 0;
		gbc_toolNrLbl.gridy = 0;
		m_millPanel.add(toolNrLbl, gbc_toolNrLbl);
		
		toolNrTf = new JTextField();
		GridBagConstraints gbc_toolNrTf = new GridBagConstraints();
		gbc_toolNrTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_toolNrTf.insets = new Insets(0, 0, 5, 0);
		gbc_toolNrTf.gridx = 1;
		gbc_toolNrTf.gridy = 0;
		m_millPanel.add(toolNrTf, gbc_toolNrTf);
		toolNrTf.setHorizontalAlignment(SwingConstants.RIGHT);
		toolNrTf.setText(Messages.getString("MainWindow.textField_10.text"));
		toolNrTf.setColumns(10);
		
		JLabel millStepwiseLbl = new JLabel(Messages.getString("MainWindow.lblStufenweiseFrsen.text"));
		GridBagConstraints gbc_millStepwiseLbl = new GridBagConstraints();
		gbc_millStepwiseLbl.anchor = GridBagConstraints.EAST;
		gbc_millStepwiseLbl.insets = new Insets(0, 0, 0, 5);
		gbc_millStepwiseLbl.gridx = 0;
		gbc_millStepwiseLbl.gridy = 1;
		m_millPanel.add(millStepwiseLbl, gbc_millStepwiseLbl);
		
		millStepwiseTf = new JTextField();
		GridBagConstraints gbc_millStepwiseTf = new GridBagConstraints();
		gbc_millStepwiseTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_millStepwiseTf.gridx = 1;
		gbc_millStepwiseTf.gridy = 1;
		m_millPanel.add(millStepwiseTf, gbc_millStepwiseTf);
		millStepwiseTf.setText(Messages.getString("MainWindow.textField_11.text"));
		millStepwiseTf.setColumns(10);
		
		/*
		 * Set initial state for radiobuttons (drill/bohren and mill/fräsen)
		 */
		drillRdbtn.setSelected(true);
		disableMillPanel();
		
		JButton applyChangesBtn = new JButton(Messages.getString("MainWindow.btnAnwenden.text")); //$NON-NLS-1$
		applyChangesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File file = new File(picPathTf.getText());
				try {
					applyImgSettings(file);
				} catch (IOException e1) {
					errorPopUp(Messages.getString("imgIOError"));
					e1.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_applyChangesBtn = new GridBagConstraints();
		gbc_applyChangesBtn.insets = new Insets(0, 0, 0, 5);
		gbc_applyChangesBtn.gridx = 0;
		gbc_applyChangesBtn.gridy = 3;
		settingsPanel.add(applyChangesBtn, gbc_applyChangesBtn);
		
		JLabel mprPathLbl = new JLabel(Messages.getString("MainWindow.lblVerzeichnis.text")); //$NON-NLS-1$
		GridBagConstraints gbc_mprPathLbl = new GridBagConstraints();
		gbc_mprPathLbl.anchor = GridBagConstraints.EAST;
		gbc_mprPathLbl.gridwidth = 3;
		gbc_mprPathLbl.insets = new Insets(0, 0, 0, 5);
		gbc_mprPathLbl.gridx = 1;
		gbc_mprPathLbl.gridy = 3;
		settingsPanel.add(mprPathLbl, gbc_mprPathLbl);
		
		mprPathTf = new JTextField();
		mprPathTf.setText(Messages.getString("MainWindow.textField_13.text")); //$NON-NLS-1$
		GridBagConstraints gbc_mprPathTf = new GridBagConstraints();
		gbc_mprPathTf.gridwidth = 3;
		gbc_mprPathTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_mprPathTf.insets = new Insets(0, 0, 0, 5);
		gbc_mprPathTf.gridx = 4;
		gbc_mprPathTf.gridy = 3;
		settingsPanel.add(mprPathTf, gbc_mprPathTf);
		mprPathTf.setColumns(10);
		
		m_fcMprBrowse = new JFileChooser();
		JButton mprPathBrowseBtn = new JButton(Messages.getString("MainWindow.btnNewButton.text_1")); //$NON-NLS-1$
		mprPathBrowseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = m_fcMprBrowse.showOpenDialog(frmPictomprconverter);
				
				if(returnVal == JFileChooser.APPROVE_OPTION){
					File file = m_fcMprBrowse.getSelectedFile();
					
					mprPathTf.setText(file.getPath());
				}
			}
		});
		GridBagConstraints gbc_mprPathBrowseBtn = new GridBagConstraints();
		gbc_mprPathBrowseBtn.anchor = GridBagConstraints.WEST;
		gbc_mprPathBrowseBtn.gridx = 7;
		gbc_mprPathBrowseBtn.gridy = 3;
		settingsPanel.add(mprPathBrowseBtn, gbc_mprPathBrowseBtn);
		
		JPanel picturePanel = new JPanel();
		picturePanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), Messages.getString("JPanelTitle6"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_picturePanel = new GridBagConstraints();
		gbc_picturePanel.gridwidth = 3;
		gbc_picturePanel.fill = GridBagConstraints.BOTH;
		gbc_picturePanel.gridx = 0;
		gbc_picturePanel.gridy = 3;
		mainPanel.add(picturePanel, gbc_picturePanel);
		GridBagLayout gbl_picturePanel = new GridBagLayout();
		gbl_picturePanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_picturePanel.rowHeights = new int[]{0, 0, 0};
		gbl_picturePanel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_picturePanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		picturePanel.setLayout(gbl_picturePanel);
		
		picPathTf = new JTextField();
		GridBagConstraints gbc_picPathTf = new GridBagConstraints();
		gbc_picPathTf.gridwidth = 5;
		gbc_picPathTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_picPathTf.insets = new Insets(0, 0, 5, 5);
		gbc_picPathTf.gridx = 0;
		gbc_picPathTf.gridy = 0;
		picturePanel.add(picPathTf, gbc_picPathTf);
		picPathTf.setText(Messages.getString("MainWindow.textField_12.text"));
		picPathTf.setColumns(10);
		
		m_fcImgBrowse = new JFileChooser();
		
		JButton picPathBtn = new JButton(Messages.getString("MainWindow.button.text")); //$NON-NLS-1$
		GridBagConstraints gbc_picPathBtn = new GridBagConstraints();
		gbc_picPathBtn.insets = new Insets(0, 0, 5, 5);
		gbc_picPathBtn.gridx = 5;
		gbc_picPathBtn.gridy = 0;
		picturePanel.add(picPathBtn, gbc_picPathBtn);
		picPathBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadNewImgEvent();
			}
		});
		
		JButton createMPRBtn = new JButton(Messages.getString("MainWindow.btnMprErstellen.text")); //$NON-NLS-1$
		createMPRBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createAndStoreMPR();
			}
		});
		
		m_negativeChkBx = new JCheckBox(Messages.getString("MainWindow.chckbxNegativ.text")); //$NON-NLS-1$
		m_negativeChkBx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*
				 * Creates negative of the picture
				 */
				m_imgPanel.setImage(m_imgProc.createGrayNegative(m_imgPanel.getCurrImage()));
			}
		});
		GridBagConstraints gbc_negativChkBx = new GridBagConstraints();
		gbc_negativChkBx.insets = new Insets(0, 0, 5, 5);
		gbc_negativChkBx.gridx = 7;
		gbc_negativChkBx.gridy = 0;
		picturePanel.add(m_negativeChkBx, gbc_negativChkBx);
		GridBagConstraints gbc_createMPRBtn = new GridBagConstraints();
		gbc_createMPRBtn.anchor = GridBagConstraints.WEST;
		gbc_createMPRBtn.gridwidth = 4;
		gbc_createMPRBtn.insets = new Insets(0, 0, 5, 5);
		gbc_createMPRBtn.gridx = 12;
		gbc_createMPRBtn.gridy = 0;
		picturePanel.add(createMPRBtn, gbc_createMPRBtn);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridwidth = 19;
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 1;
		picturePanel.add(scrollPane_1, gbc_scrollPane_1);
		
		m_imgPanel = new ImagePane();
		scrollPane_1.setViewportView(m_imgPanel);
		m_imgPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		/*
		 * Set height and width textfields to value of currently loaded picture
		 */
		pictureHeightTf.setText(Integer.toString(m_imgPanel.getCurrHeight()));
		pictureWidthTf.setText(Integer.toString(m_imgPanel.getCurrWidth()));
	}
	
	/**
	 * Enables the drill Panel in the GUI
	 */
	private void enableDrillPanel(){
		m_drillPanel.setEnabled(true);

		for(Component c:m_drillPanel.getComponents()){
			c.setEnabled(true);
		}
	}
	
	/**
	 * Disables the drill Panel in the GUI
	 */
	private void disableDrillPanel(){
		m_drillPanel.setEnabled(false);
		
		for(Component c:m_drillPanel.getComponents()){
			c.setEnabled(false);
		}
	}

	/**
	 * Enables the mill Panel in the GUI
	 */
	private void enableMillPanel(){
		m_millPanel.setEnabled(true);
		
		for(Component c:m_millPanel.getComponents()){
			c.setEnabled(true);
		}
	}
	
	/**
	 * Disables the mill Panel in the GUI
	 */
	private void disableMillPanel(){
		m_millPanel.setEnabled(false);
		
		for(Component c:m_millPanel.getComponents()){
			c.setEnabled(false);
		}
	}
	
	/**
	 * Reaction to the event when a new image is loaded.
	 * Filechooser is opened, when the file is selected, a file object is created
	 * and the loadImg function is invoked. Pops up an error message when no 
	 * valid image was selected.
	 */
	private void loadNewImgEvent(){
		int returnVal = m_fcImgBrowse.showOpenDialog(frmPictomprconverter);
		
		if(returnVal == JFileChooser.APPROVE_OPTION){
			File file = m_fcImgBrowse.getSelectedFile();
			
			picPathTf.setText(file.getPath());
			
			try {
				loadImg(file);
			} catch (IOException e) {
				errorPopUp(Messages.getString("imgIOError"));
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Loads image from a file, sets height and width textfields in the GUI
	 * to the values of the image, unchecks "negative"-checkbox 
	 * 
	 * @param file is the file to be loaded
	 * @throws IOException when file not valid
	 */
	private void loadImg(File file) throws IOException{
		BufferedImage img = ImageIO.read(file);
		
		m_imgPanel.setImage(m_imgProc.preProcImage(img, img.getWidth(), img.getHeight()));
		pictureHeightTf.setText(Integer.toString(img.getHeight()));
		pictureWidthTf.setText(Integer.toString(img.getWidth()));
		m_negativeChkBx.setSelected(false);
	}
	
	/**
	 * Apply the settings from the GUI to the image, keeps the old settings
	 * when width or height are zero, creates the negative of the file if
	 * the negative checkbox is selected
	 * 
	 * @param file is the file where the image is stored
	 * @throws IOException when file not valid
	 */
	private void applyImgSettings(File file) throws IOException{
		int width = Integer.parseInt(pictureWidthTf.getText());
		int height = Integer.parseInt(pictureHeightTf.getText());
		BufferedImage img = ImageIO.read(file);
		if(width == 0 || height == 0){
			pictureHeightTf.setText(Integer.toString(img.getHeight()));
			pictureWidthTf.setText(Integer.toString(img.getWidth()));
			width = img.getWidth();
			height = img.getHeight();
		}
		
		m_imgPanel.setImage(m_imgProc.preProcImage(img, width, height));
		
		if(m_negativeChkBx.isSelected()){
			m_imgPanel.setImage(m_imgProc.createGrayNegative(m_imgPanel.getCurrImage()));
		}
	}
	
	/**
	 * Pops up an error message
	 * 
	 * @param errorMsg is the error message that is shown
	 */
	private void errorPopUp(String errorMsg){
		JOptionPane.showMessageDialog(frmPictomprconverter, errorMsg, Messages.getString("errorMessage"), JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Pops up an warning message
	 * 
	 * @param warningMsg is the warning message that is shown
	 */
	private void warningPopUp(String warningMsg){
		JOptionPane.showMessageDialog(frmPictomprconverter, warningMsg, Messages.getString("warningMessage"), JOptionPane.WARNING_MESSAGE);
	}
	
	/**
	 * Calculates and sets the height to the given width of the pictureWidthTf
	 * according to the ratio of the picture in the m_imgPanel
	 */
	private void setNewHeightToGivenWidthPx(){
		
		try {
			int width = Integer.parseInt(pictureWidthTf.getText());
			
			if(width >= 0){
				int height = width * m_imgPanel.getCurrHeight()/m_imgPanel.getCurrWidth();
				pictureHeightTf.setText(height + "");
			}else{
				errorPopUp(Messages.getString("valBtZero"));
				pictureWidthTf.setText("0");				
			}
		} catch (NumberFormatException e) {
			//errorPopUp(Messages.getString("nanMessage"));
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Calculates and sets the width to the given height of the pictureHeightTf
	 * according to the ratio of the picture in the m_imgPanel
	 */
	private void setNewWidthToGivenHeightPx(){
		
		try {
			int height = Integer.parseInt(pictureHeightTf.getText());
			
			if(height >= 0){
				int width = height * m_imgPanel.getCurrWidth()/m_imgPanel.getCurrHeight();
				pictureWidthTf.setText(width + "");
			}else{
				errorPopUp(Messages.getString("valBtZero"));
				pictureHeightTf.setText("0");
			}
		} catch (NumberFormatException e) {
			//errorPopUp(Messages.getString("nanMessage"));
			e.printStackTrace();
		}
	}
	
	/**
	 * Calculates and sets the height to the given width of the workpieceWidthTf
	 * according to the ratio of the picture in the m_imgPanel
	 */
	private void setNewHeightToGivenWidthMm(){
		
		try {
			int width = Integer.parseInt(workpieceWidthTf.getText());
			
			if(width >= 0){
				int height = width * m_imgPanel.getCurrHeight()/m_imgPanel.getCurrWidth();
				workpieceHeightTf.setText(height + "");
			}else{
				errorPopUp(Messages.getString("valBtZero"));
				workpieceWidthTf.setText("0");
			}
		} catch (NumberFormatException e) {
			//errorPopUp(Messages.getString("nanMessage"));
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Calculates and sets the width to the given height of the workpieceHeightTf
	 * according to the ratio of the picture in the m_imgPanel
	 */
	private void setNewWidthToGivenHeightMm(){
		
		try {
			int height = Integer.parseInt(workpieceHeightTf.getText());
			
			if(height >= 0){
				int width = height * m_imgPanel.getCurrWidth()/m_imgPanel.getCurrHeight();
				workpieceWidthTf.setText(width + "");
			}else{
				errorPopUp(Messages.getString("valBtZero"));
				workpieceHeightTf.setText("0");
			}
		} catch (NumberFormatException e) {
			//errorPopUp(Messages.getString("nanMessage"));
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a MPR from loaded image and the settings in the GUI and stores it to the file specified in the mprPathTf Textfield.
	 * The MPR file is created according to the drilling or milling mode selected in the GUI. Invokes a success message or
	 * an error message depending on the success state of the function
	 */
	private void createAndStoreMPR(){
		
		if(m_imgPanel.getCurrImage() != null){
			
			MPR mpr;
			
			if(m_drillPanel.isEnabled()){
				mpr = new MPRDrill();

				try {
					mpr.createMPRfromImg(Integer.parseInt(workpieceWidthTf.getText()), Integer.parseInt(workpieceHeightTf.getText()), 
							Integer.parseInt(workpieceThicknessTf.getText()), Integer.parseInt(drillDiameterTf.getText()), 
							Float.parseFloat(minDepthTf.getText()), Float.parseFloat(maxDepthTf.getText()), Float.parseFloat(hideFromTf.getText()),
							Float.parseFloat(hideToTf.getText()), (String)m_drillModeCombo.getSelectedItem(), m_imgPanel.getCurrImage());
					
					mpr.writeMPRToFile(mprPathTf.getText());
					
					/*
					 * Success pop up
					 */
					JOptionPane.showMessageDialog(frmPictomprconverter, Messages.getString("successTitle"), Messages.getString("successMessage"), JOptionPane.INFORMATION_MESSAGE);
					
				} catch (IOException e) {
					errorPopUp(Messages.getString("invalidPath"));
					e.printStackTrace();
				} catch (NumberFormatException e){
					errorPopUp(Messages.getString("nanMessage"));
					e.printStackTrace();
				}
			}else{
				mpr = new MPRMill();
				warningPopUp(Messages.getString("millingNotYetImplemented"));
			}
		}else{
			errorPopUp(Messages.getString("imgIOError"));
		}

	}
	
	private boolean isPosNr(int val){
		if(val >= 0){
			return true;
		}else{
			return false;
		}
	}
}
