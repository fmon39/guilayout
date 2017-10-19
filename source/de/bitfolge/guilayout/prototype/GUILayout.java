package de.bitfolge.guilayout.prototype;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.io.*;

import javax.swing.*;

import com.jgoodies.plaf.*;
import com.jgoodies.plaf.plastic.*;

import de.bitfolge.guilayout.elements.DiagramManager;
import de.bitfolge.guilayout.prototype.export.*;

public class GUILayout extends JFrame implements ActionListener {
	
	public static GUILayout _program;
	public DrawingPanel drawingPanel;
	public ToolBarFunctionalities funcToolBar;
	public ToolBarZoom zoomToolBar;
	public ToolBarAddNew addToolBar;
	protected File saveFile = null;
	
	private void createMenu() {
		JMenuBar menuBar = new MenuBarPrototype();
		menuBar.putClientProperty(Options.HEADER_STYLE_KEY, HeaderStyle.BOTH);
		this.setJMenuBar(menuBar);
	}
	
	protected void terminate() {
		this.dispose();
	}

	public GUILayout() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				GUILayout._program.terminate();
			}
			public void windowClosed(WindowEvent event) {
				System.exit(0);
			}
		});
		this.setIconImage(Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("/resources/appicon.gif")));
	}

	private void createToolBar() {
		JToolBar toolbars = new JToolBar();
		toolbars.putClientProperty(Options.HEADER_STYLE_KEY, HeaderStyle.BOTH);
		toolbars.putClientProperty(PlasticLookAndFeel.BORDER_STYLE_KEY, BorderStyle.SEPARATOR);
		toolbars.setBorder(null);
		toolbars.setLayout(new BoxLayout(toolbars,BoxLayout.X_AXIS));
		toolbars.add(new ToolBarFile());
		addToolBar = (ToolBarAddNew) toolbars.add(new ToolBarAddNew());
		funcToolBar = (ToolBarFunctionalities) toolbars.add(new ToolBarFunctionalities());
		toolbars.add(new ToolBarEditing());
		zoomToolBar = (ToolBarZoom) toolbars.add(new ToolBarZoom());
		getContentPane().add(toolbars, BorderLayout.PAGE_START);
	}

	public static void main(String[] args) {
		try {
			if (false) {
				UIManager.setLookAndFeel("de.muntjak.tinylookandfeel.TinyLookAndFeel");
			} else {
				LookAndFeel laf = new PlasticXPLookAndFeel();
				UIManager.setLookAndFeel(laf);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		_program = new GUILayout();
		_program.initialize();
		_program.setTitle("GUILayout Prototype");
		_program.setSize(846, 630);
		int x = (Toolkit.getDefaultToolkit().getScreenSize().width-_program.getWidth())/2;
		int y = (Toolkit.getDefaultToolkit().getScreenSize().height-_program.getHeight())/2;
		_program.setLocation(x,y);

		_program.createDrawingPanel();
		
		_program.show();
		
		if (args.length>0) {
			try {
				Saver.loadFrom(new File(args[0]));
				_program.saveFile = new File(args[0]);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error while loading", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	private void initialize() {
		this.createMenu();
		this.createToolBar();
	}

	private void createDrawingPanel() {
		this.drawingPanel = new DrawingPanel();
		JScrollPane sp = new JScrollPane(drawingPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sp.setBorder(BorderFactory.createLoweredBevelBorder());
		sp.getHorizontalScrollBar().addAdjustmentListener(drawingPanel);
		sp.getVerticalScrollBar().addAdjustmentListener(drawingPanel);
		getContentPane().add(sp, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Exit")) {
			this.terminate();
		}
		if (e.getActionCommand().equals("FileNew")) {
			drawingPanel.clear();
			saveFile = null;
		}
		if (e.getActionCommand().equals("FileOpen")) {
			try {
				JFileChooser fc = new JFileChooser(new java.io.File("."));
				ExtensionFileFilter eff = new ExtensionFileFilter();
				eff.addExtension(".guild");
				eff.setDescription("GUI Layout Diagram files");
				fc.addChoosableFileFilter(eff);
				if (fc.showOpenDialog(this)==JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					actionPerformed(new ActionEvent(this, 0, "FileNew"));
					Saver.loadFrom(f);
					saveFile = f;
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			}
		}
		if (e.getActionCommand().equals("FileSave")) {
			if (saveFile!=null) {
				Saver.saveTo(saveFile);
			} else {
				ActionEvent ae = new ActionEvent(e.getSource(), e.getID(), "FileSaveAs", e.getWhen(), e.getModifiers());
				actionPerformed(ae);
			}
		}
		if (e.getActionCommand().equals("FileSaveAs")) {
			try {
				JFileChooser fc = new JFileChooser(new java.io.File("."));
				ExtensionFileFilter eff = new ExtensionFileFilter();
				eff.addExtension(".guild");
				eff.setDescription("GUI Layout Diagram files");
				fc.addChoosableFileFilter(eff);
				if (fc.showSaveDialog(this)==JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					Saver.saveTo(f);
					saveFile = f;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (e.getActionCommand().equals("FileExport")) {
			JPopupMenu pop = new JPopupMenu("Export");
			Point buttonLocation = ((Component) e.getSource()).getLocation();

			JMenuItem mi = new JMenuItem("XHTML/CSS");
			mi.setActionCommand("FileExportHTML");
			mi.setIcon(new ImageIcon(this.getClass().getResource("/resources/HTML.gif")));
			mi.addActionListener(this);
			pop.add(mi);

			mi = new JMenuItem("Photoshop");
			mi.setActionCommand("FileExportPhotoshop");
			mi.setIcon(new ImageIcon(this.getClass().getResource("/resources/Photoshop.gif")));
			mi.addActionListener(this);
			pop.add(mi);

			pop.show((Component) e.getSource(), 0, buttonLocation.y+((Component) e.getSource()).getHeight());
		}
		if (e.getActionCommand().equals("FileExportPhotoshop")) {
			Saver.export(new ExportPhotoshop());
		}
		if (e.getActionCommand().equals("FileExportHTML")) {
			Saver.export(new ExportHTML());
		}
		if (e.getActionCommand().equals("HelpAbout")) {
			JOptionPane.showMessageDialog(
					this,
					"A Profile for GUI Layout\n" +
					"Master's Thesis by Kai Blankenhorn\n" +
					"Experimental Prototype Application\n\n" +
					"(c) Kai Blankenhorn 2004\n" +
					"Distributed under the GPL (see License.txt)\n" +
					"Includes libraries distributed under other licenses\n" +
					"(see License-*.txt for details)",
					"About this application",
					JOptionPane.INFORMATION_MESSAGE,
					new ImageIcon(this.getClass().getResource("/resources/appicon64.gif"))
			);
		}
		if (e.getActionCommand().equals("HelpKeyboardShortcuts")) {
			JOptionPane.showMessageDialog(
					this,
					new KeyboardShortcutsPanel(),
					"Keyboard shortcuts",
					JOptionPane.INFORMATION_MESSAGE
			);
		}
		if (e.getActionCommand().equals("FilePrint")) {
			try {
				PrintUtilities.printComponent(drawingPanel);
			} catch (PrinterException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage(), "Printer error", JOptionPane.ERROR_MESSAGE);
			}
		}
		if (e.getActionCommand().equals("FilePageSetup")) {
		    PrintUtilities.pageDialog();
		}
		if (e.getActionCommand().equals("Undo")) {
			DiagramManager.getInstance().undo();
		}
		drawingPanel.actionPerformed(e);
		repaint();
	}
	
}
