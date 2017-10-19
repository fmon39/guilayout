/*
 * Created on 16.11.2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.bitfolge.guilayout.drawing;

import java.awt.BasicStroke;

/**
 * @author kb
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DashedStroke extends BasicStroke implements java.io.Serializable {
	
	public DashedStroke(float width, float dashLength, float blankLength) {
		//w, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND
		super(width, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1, new float[] { dashLength, blankLength }, 0);
	}

}
