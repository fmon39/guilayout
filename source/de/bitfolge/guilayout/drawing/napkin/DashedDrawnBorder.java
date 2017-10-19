package de.bitfolge.guilayout.drawing.napkin;

import java.awt.*;

import napkin.*;
import napkin.DrawnBorder;

public class DashedDrawnBorder extends DrawnBorder {
	
	protected boolean dashed = false;
	
    public void paintBorder(Component c, Graphics g1, int x, int y,
            int width, int height) {

        Graphics2D g = (Graphics2D) g1;
        DashedBoxHolder box = new DashedBoxHolder((BoxHolder) borders.get(c));
        box.dashed = isDashed();
        Rectangle passed = new Rectangle(x, y, width, height);
        box.shapeUpToDate(c, passed);

        Rectangle rect = c.getBounds();
        rect.x = rect.y = 0;
        Rectangle clip = g.getClipBounds();
        g.setClip(clip.x - BORDER, clip.y - BORDER, clip.width + 2 * BORDER,
                clip.height + 2 * BORDER);
        box.draw(g);
    }

	/**
	 * @return Returns the dashed.
	 */
	public boolean isDashed() {
		return dashed;
	}
	/**
	 * @param dashed The dashed to set.
	 */
	public void setDashed(boolean dashed) {
		this.dashed = dashed;
	}
}
