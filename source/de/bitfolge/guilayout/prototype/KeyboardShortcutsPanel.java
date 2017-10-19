package de.bitfolge.guilayout.prototype;

import java.awt.GridLayout;

import javax.swing.*;

public class KeyboardShortcutsPanel extends JPanel {
	GridLayout layout;
	
	public KeyboardShortcutsPanel() {
		layout = new GridLayout();
		setLayout(layout);
		
		addShortcut("[arrow keys]", "move selected ScreenArea by 1 pixel");
		addShortcut("Ctrl-[arrow keys]", "move selected ScreenArea by 10 pixels");
		addShortcut("Shift-[drag mouse]", "bypass auto-docking");
	}
	
	public void addShortcut(String key, String action) {
		add(new JLabel(key));
		add(new JLabel(action));
		layout.setRows(layout.getRows()+1);
	}
}
