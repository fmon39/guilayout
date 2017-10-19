package de.bitfolge.guilayout.prototype;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.undo.StateEdit;

import de.bitfolge.guilayout.drawing.DashedStroke;
import de.bitfolge.guilayout.elements.DiagramManager;
import de.bitfolge.guilayout.elements.functionality.AbstractUIFunctionality;
import de.bitfolge.guilayout.elements.screenarea.ScreenArea;

public class ToolBarEditing extends AbstractToolBarPrototype implements Observer, KeyListener, KeyEventDispatcher {
	private JTextField nameField;
	
	protected Icon createAbstractIcon(final Integer state) {
		Icon icon = new Icon() {
			public int getIconHeight() {
				return 16;
			}
	
			public int getIconWidth() {
				return 16;
			}
	
			public void paintIcon(Component c, Graphics g, int x, int y) {
				Graphics2D g2 = (Graphics2D) g;
				Color col = AbstractUIFunctionality.getColorForState(state);
				g2.setColor(col);
				g2.setStroke(new DashedStroke(2,4,4));
				g2.drawRect(x,y,getIconWidth(),getIconHeight());
			}
		};
		return icon;
	}
	
	public ToolBarEditing() {
		super();
		
		JButton button;

		this.setName("Editing");
		
		this.addSeparator(new Dimension(8,0));
		
		JToggleButton toggleButton;
		toggleButton = (JToggleButton) this.add(new JToggleButton());
		toggleButton.setActionCommand("ToggleAbstract");
		toggleButton.setEnabled(false);
		toggleButton.setToolTipText("Toggle visible borders");
		toggleButton.setIcon(createAbstractIcon(AbstractUIFunctionality.STATE_DEPRESSED));
		toggleButton.setSelectedIcon(createAbstractIcon(AbstractUIFunctionality.STATE_PRESSED));
		toggleButton.setDisabledIcon(createAbstractIcon(AbstractUIFunctionality.STATE_DISABLED));
		toggleButton.setModel(new JToggleButton.ToggleButtonModel() {
			
			public boolean isEnabled() {
				return DiagramManager.getInstance().getSelectedScreenArea()!=null;
			}
			
			public boolean isSelected() {
				ScreenArea sa = DiagramManager.getInstance().getSelectedScreenArea();
				if (sa==null) {
					return false;
				} else {
					return sa.isAbstract();	
				}
			}
			
			public void setSelected(boolean selected) {
				DiagramManager dm = DiagramManager.getInstance(); 
				ScreenArea sa = dm.getSelectedScreenArea();
				if (sa!=null) {
					StateEdit se = dm.getUndoState(sa);
					sa.setAbstract(selected);
					dm.addUndo(se);
				}
			}
		});
		
		this.addSeparator(new Dimension(8,0));

		button = (JButton) this.add(new JButton());
		button.setActionCommand("DeleteScreenArea");
		button.setIcon(new ImageIcon(this.getClass().getResource("/resources/Delete16.gif")));
		button.setModel(DiagramManager.getInstance().getDeleteButtonModel());
		button.setToolTipText("Delete the selected ScreenArea");
		
		this.addSeparator(new Dimension(16,0));
		
		nameField = new JTextField();
		nameField.addCaretListener(DiagramManager.getInstance());
		nameField.setColumns(15);
		Dimension size = new Dimension(100,20);
		nameField.setMinimumSize(size);
		nameField.setMaximumSize(size);
		nameField.setActionCommand("SetName");
		nameField.setEnabled(false);
		nameField.addKeyListener(this);
		DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this); 
		JLabel nameLabel = new JLabel("Name: ");
		nameLabel.setLabelFor(nameField);
		nameLabel.setEnabled(false);
		nameLabel.setToolTipText("Enter the new name for the selected ScreenArea");
		this.add(nameLabel);
		this.add(nameField);

		DiagramManager.getInstance().addObserver(this);
	}

	public void update(Observable o, Object arg) {
		Component[] c = getComponents();
		boolean enabled = DiagramManager.getInstance().getSelectedScreenArea()!=null;
		for (int i=0; i<c.length; i++) {
			c[i].setEnabled(enabled);
		}
		if (enabled) {
			nameField.setText(DiagramManager.getInstance().getSelectedScreenArea().getName());
		} else {
			nameField.setText("");
		}
		this.repaint();
	}

	public void keyPressed(KeyEvent e) {
		e.consume();
	}

	public void keyReleased(KeyEvent e) {
		e.consume();
	}

	public void keyTyped(KeyEvent e) {
	}

	public boolean dispatchKeyEvent(KeyEvent e) {
		return false;
	}
}
