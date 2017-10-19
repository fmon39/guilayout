package de.bitfolge.guilayout.prototype;

import java.awt.Dimension;

import javax.swing.*;

public class ToolBarFile extends AbstractToolBarPrototype {
	
	public ToolBarFile() {
		super();
		
		JButton button;
	
		this.setName("ScreenAreas");

		button = (JButton) this.add(new JButton());
		button.setActionCommand("FileNew");
		button.setIcon(new ImageIcon(this.getClass().getResource("/resources/New16.gif")));
		button.addActionListener(GUILayout._program);
		button.setToolTipText("Create a new diagram");

		button = (JButton) this.add(new JButton());
		button.setActionCommand("FileOpen");
		button.setIcon(new ImageIcon(this.getClass().getResource("/resources/Open16.gif")));
		button.addActionListener(GUILayout._program);
		button.setToolTipText("Open an existing diagram");

		button = (JButton) this.add(new JButton());
		button.setActionCommand("FileSave");
		button.setIcon(new ImageIcon(this.getClass().getResource("/resources/Save16.gif")));
		button.addActionListener(GUILayout._program);
		button.setToolTipText("Save the current diagram");

		button = (JButton) this.add(new JButton());
		button.setActionCommand("FileSaveAs");
		button.setIcon(new ImageIcon(this.getClass().getResource("/resources/SaveAs16.gif")));
		button.addActionListener(GUILayout._program);
		button.setToolTipText("Save the current diagram under a new filename");

		button = (JButton) this.add(new JButton());
		button.setActionCommand("FileExport");
		button.setIcon(new ImageIcon(this.getClass().getResource("/resources/Export16.gif")));
		button.addActionListener(GUILayout._program);
		button.setToolTipText("Export a Screen");
		
		this.addSeparator(new Dimension(8,0));
	}
}
