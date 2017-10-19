package de.bitfolge.guilayout.prototype;

import java.awt.event.*;

import javax.swing.*;

import de.bitfolge.guilayout.elements.DiagramManager;
import de.bitfolge.guilayout.elements.functionality.*;
import de.bitfolge.guilayout.elements.screenarea.*;

public class MenuBarPrototype extends JMenuBar {
	
	public MenuBarPrototype() {
		JMenu menu = this.add(new JMenu("&File"));
		setMnemonicFor(menu);
		createMenuItem(menu, "&New", "FileNew", new ImageIcon(this.getClass().getResource("/resources/New16.gif")), KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		createMenuItem(menu, "&Open...", "FileOpen", new ImageIcon(this.getClass().getResource("/resources/Open16.gif")), KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		createMenuItem(menu, "&Save", "FileSave", new ImageIcon(this.getClass().getResource("/resources/Save16.gif")), KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		createMenuItem(menu, "Save &as...", "FileSaveAs", new ImageIcon(this.getClass().getResource("/resources/SaveAs16.gif")), KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK+InputEvent.SHIFT_DOWN_MASK));
		
		JMenu export = (JMenu) menu.add(new JMenu("&Export"));
		setMnemonicFor(export);
		export.setIcon(new ImageIcon(this.getClass().getResource("/resources/Export16.gif")));
		createMenuItem(export, "to X&HTML/CSS", "FileExportHTML", new ImageIcon(this.getClass().getResource("/resources/HTML.gif")), null);
		createMenuItem(export, "to Adobe &Photoshop", "FileExportPhotoshop", new ImageIcon(this.getClass().getResource("/resources/Photoshop.gif")), null);
		
		createMenuItem(menu, "&Print...", "FilePrint", new ImageIcon(this.getClass().getResource("/resources/Print16.gif")), null);
		createMenuItem(menu, "Pa&ge setup...", "FilePageSetup", new ImageIcon(this.getClass().getResource("/resources/PageSetup16.gif")), null);
		
		menu.addSeparator();
		createMenuItem(menu, "E&xit", "Exit", null, null);
		this.add(menu);

		menu = this.add(new JMenu("&Edit"));
		setMnemonicFor(menu);
		createMenuItem(menu, "&Copy", "EditCopy", new ImageIcon(this.getClass().getResource("/resources/Copy16.gif")), KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		createMenuItem(menu, "C&ut", "EditCut", new ImageIcon(this.getClass().getResource("/resources/Cut16.gif")), KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
		createMenuItem(menu, "&Paste", "EditPaste", new ImageIcon(this.getClass().getResource("/resources/Paste16.gif")), KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
		menu.addSeparator();
		createMenuItem(menu, "Dup&licate", "EditDuplicate", null, KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK, false));
		JMenuItem del = createMenuItem(menu, "&Delete ScreenArea", "DeleteScreenArea", new ImageIcon(this.getClass().getResource("/resources/Delete16.gif")), null);
		del.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0, true));
		del.setModel(DiagramManager.getInstance().getDeleteButtonModel());
		
		menu = this.add(new JMenu("&Diagram"));
		setMnemonicFor(menu);
		//createMenuItem(menu, "&Undo", "Undo", null, KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK, false));
		createMenuItem(menu, "Insert &Screen", "InsertScreen", Screen.getIcon(false), KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, KeyEvent.SHIFT_DOWN_MASK));
		createMenuItem(menu, "Insert Screen&Area", "InsertScreenArea", ScreenArea.getIcon(false), KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, KeyEvent.CTRL_DOWN_MASK));
		menu.addSeparator();
		
		createContentMenu(menu);
		
		this.add(Box.createHorizontalGlue());
		menu = this.add(new JMenu("&Help"));
		setMnemonicFor(menu);
		createMenuItem(menu, "Additional &keyboard shortcuts...", "HelpKeyboardShortcuts", new ImageIcon(this.getClass().getResource("/resources/Information16.gif")), null);
		createMenuItem(menu, "&About...", "HelpAbout", new ImageIcon(this.getClass().getResource("/resources/About16.gif")), null);
	}
	
	protected static void setMnemonicFor(AbstractButton ab) {
		String label = ab.getText();
		int memnIndex = label.indexOf('&'); 
		if (memnIndex>-1) {
			label = label.replaceAll("&([^&])","$1");
			ab.setText(label);
			ab.setMnemonic(label.charAt(memnIndex));
			ab.setDisplayedMnemonicIndex(memnIndex);
		}
	}
	
	public static JMenuItem createMenuItem(JMenu menu, String label, String actionCommand, Icon icon, KeyStroke keystroke) {
		JMenuItem item = new JMenuItem();
		item.setText(label);
		setMnemonicFor(item);
		if (icon!=null) {
			item.setIcon(icon);
		}
		if (keystroke!=null) {
			item.setAccelerator(keystroke);
		}
		item.setActionCommand(actionCommand);
		item.addActionListener(GUILayout._program);
		return menu.add(item);
	}
	
	private static void createMenuCheckBoxItemForClass(JMenu menu, Class c, KeyStroke keystroke) {
		JCheckBoxMenuItem cbmi;
		cbmi = new JCheckBoxMenuItem("Toggle "+c.getName().substring(c.getName().lastIndexOf('.')+1));
		cbmi.setIcon(AbstractUIFunctionality.getIconForClassAndState(c,AbstractUIFunctionality.STATE_DEPRESSED));
		cbmi.setSelectedIcon(AbstractUIFunctionality.getIconForClassAndState(c,AbstractUIFunctionality.STATE_PRESSED));
		cbmi.setDisabledIcon(AbstractUIFunctionality.getIconForClassAndState(c,AbstractUIFunctionality.STATE_DISABLED));
		cbmi.setActionCommand(AbstractUIFunctionality.getActionCommandForClass(c));
		cbmi.setModel(DiagramManager.getInstance().getFunctionalityButtonModel(c));
		if (keystroke!=null) {
			cbmi.setAccelerator(keystroke);
		}
		menu.add(cbmi);
	}
	
	public static void createContentMenu(JMenu menu) {
		createMenuCheckBoxItemForClass(menu, Image.class, KeyStroke.getKeyStroke(KeyEvent.VK_1,0));
		createMenuCheckBoxItemForClass(menu, Text.class, KeyStroke.getKeyStroke(KeyEvent.VK_2,0));
		createMenuCheckBoxItemForClass(menu, Heading.class, KeyStroke.getKeyStroke(KeyEvent.VK_3,0));
		createMenuCheckBoxItemForClass(menu, Logo.class, KeyStroke.getKeyStroke(KeyEvent.VK_4,0));
		createMenuCheckBoxItemForClass(menu, Link.class, KeyStroke.getKeyStroke(KeyEvent.VK_5,0));
		createMenuCheckBoxItemForClass(menu, Form.class, KeyStroke.getKeyStroke(KeyEvent.VK_6,0));
		createMenuCheckBoxItemForClass(menu, Navigation.class, KeyStroke.getKeyStroke(KeyEvent.VK_7,0));
		createMenuCheckBoxItemForClass(menu, Workspace.class, KeyStroke.getKeyStroke(KeyEvent.VK_8,0));
	}
}
