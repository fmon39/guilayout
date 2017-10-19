package de.bitfolge.guilayout.prototype;

import javax.swing.*;

import com.jgoodies.plaf.*;
import com.jgoodies.plaf.plastic.PlasticLookAndFeel;

public class AbstractToolBarPrototype extends JToolBar {
	
	public AbstractToolBarPrototype() {
		this.putClientProperty(Options.HEADER_STYLE_KEY, HeaderStyle.BOTH);
		this.putClientProperty(PlasticLookAndFeel.BORDER_STYLE_KEY, BorderStyle.SEPARATOR);
		this.setBorder(BorderFactory.createEmptyBorder());
	}
}
