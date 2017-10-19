package de.bitfolge.guilayout.prototype;

import java.awt.Dimension;

import javax.swing.*;

import de.bitfolge.guilayout.elements.screenarea.*;

public class ToolBarAddNew extends AbstractToolBarPrototype {
	
	class AddNewButtonModel extends JToggleButton.ToggleButtonModel {
		
		private Class className;
		
		AddNewButtonModel(Class c) {
			className = c;
		}
		
		public boolean isSelected() {
			try {
				boolean p = GUILayout._program.drawingPanel.getAddMode().equals(className);
				return p;
			} catch (NullPointerException e) {
				return false;
			}
		}
					
		public void setSelected(boolean b) {
			super.setPressed(b);
			if (b) {
				GUILayout._program.drawingPanel.setAddMode(className);
			}
		}
		
		
	}
	
	public JButton insertScreen;
	public JButton insertScreenArea;
	
	public ToolBarAddNew() {
		super();
		
		this.setName("ScreenAreas");

		insertScreen = (JButton) this.add(new JButton());
		insertScreen.setModel(new AddNewButtonModel(Screen.class));
		insertScreen.setActionCommand("InsertScreen");
		insertScreen.setIcon(Screen.getIcon(false));
		insertScreen.setSelectedIcon(Screen.getIcon(true));
		insertScreen.setRolloverIcon(Screen.getIcon(true));
		insertScreen.addActionListener(GUILayout._program);
		insertScreen.setToolTipText("Insert new Screen");
		
		insertScreenArea = (JButton) this.add(new JButton());
		insertScreenArea.setModel(new AddNewButtonModel(ScreenArea.class));
		insertScreenArea.setActionCommand("InsertScreenArea");
		insertScreenArea.setIcon(ScreenArea.getIcon(false));
		insertScreenArea.setSelectedIcon(ScreenArea.getIcon(true));
		insertScreenArea.setRolloverIcon(ScreenArea.getIcon(true));
		insertScreenArea.addActionListener(GUILayout._program);
		insertScreenArea.setToolTipText("Insert new ScreenArea");
		
		this.addSeparator(new Dimension(8,0));
	}
}
