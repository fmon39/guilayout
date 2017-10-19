/*
 * Created on 14.12.2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.bitfolge.guilayout.prototype.export.photoshop.imageresources;

import de.bitfolge.guilayout.prototype.export.ExtendedByteArrayOutputStream;


/**
 * @author kb
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class IRCaption extends ImageResource {
	
	private String caption;
	
	public IRCaption(String caption) {
		this.caption = caption;
		setID(1008);
	}

	public byte[] getData() {
		ExtendedByteArrayOutputStream baos = new ExtendedByteArrayOutputStream();
		baos.writePString(caption, true);
		return baos.toByteArray();
	}

}
