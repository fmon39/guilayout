package de.bitfolge.guilayout.prototype;

import java.awt.Component;
import java.util.*;

import javax.swing.*;

import com.jgoodies.plaf.*;
import com.jgoodies.plaf.plastic.PlasticLookAndFeel;

import de.bitfolge.guilayout.elements.DiagramManager;
import de.bitfolge.guilayout.elements.functionality.*;

public class ToolBarFunctionalities extends JToolBar implements Observer {
	
	protected void addButton(Class c) {
		JToggleButton toggleButton;
		toggleButton = (JToggleButton) this.add(new JToggleButton());
		toggleButton.setIcon(AbstractUIFunctionality.getIconForClassAndState(c, AbstractUIFunctionality.STATE_DEPRESSED));
		toggleButton.setSelectedIcon(AbstractUIFunctionality.getIconForClassAndState(c, AbstractUIFunctionality.STATE_PRESSED));
		toggleButton.setDisabledIcon(AbstractUIFunctionality.getIconForClassAndState(c, AbstractUIFunctionality.STATE_DISABLED));
		toggleButton.setToolTipText(c.getName().substring(c.getName().lastIndexOf('.')+1));
		toggleButton.setActionCommand(AbstractUIFunctionality.getActionCommandForClass(c));
		toggleButton.setModel(DiagramManager.getInstance().getFunctionalityButtonModel(c));
	}

	public ToolBarFunctionalities() {
		JToggleButton toggleButton;
		
		this.setName("Functionalities");
		this.putClientProperty(Options.HEADER_STYLE_KEY, HeaderStyle.BOTH);
		this.putClientProperty(PlasticLookAndFeel.BORDER_STYLE_KEY, BorderStyle.SEPARATOR);
		this.setBorder(BorderFactory.createEmptyBorder());

		// StaticUIFunctionalities
		addButton(Image.class);
		addButton(Text.class);
		addButton(Heading.class);
		addButton(Logo.class);

		// ActivatableUIFunctionalities
		addButton(Link.class);
		addButton(Form.class);
		addButton(Navigation.class);
		addButton(Workspace.class);

		DiagramManager.getInstance().addObserver(this);
	}
	
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		int i = 0;
		Component c;
		while ((c = getComponentAtIndex(i++)) != null) {
			c.setEnabled(enabled);
		}
	}

	public void update(Observable o, Object arg) {
		this.repaint();
	}
}
