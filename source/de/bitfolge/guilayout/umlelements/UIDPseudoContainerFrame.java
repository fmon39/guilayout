/*
 * Created on 18.11.2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.bitfolge.guilayout.umlelements;

import java.awt.*;

import de.bitfolge.guilayout.drawing.*;

/**
 * @author kb
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UIDPseudoContainerFrame extends UIDComponent {

	public UIDPseudoContainerFrame() {
		this(null);
	}

	public UIDPseudoContainerFrame(AbstractElementView uidc) {
		this(uidc, 0, 0, 200, 200);
	}

	public UIDPseudoContainerFrame(AbstractElementView uidc, int x, int y, int width, int height) {
		super(uidc, x, y, width, height);
		this.setBackground(new Color(0,0,0,0));
		this.setForeground(Color.BLACK);
		this.outlineStroke = new BasicStroke();
	}

	public void update(Graphics g) {
	}

}
