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
public abstract class ImageResource {
	
	protected static final String OSTYPE = "8BIM";
	
	protected int ID = 0;
	protected String name = "";
	private byte[] IRData = null;
	
	protected ExtendedByteArrayOutputStream baos = new ExtendedByteArrayOutputStream();
	
	public byte[] getAsByteArray() {
		baos.writeString(OSTYPE);
		baos.writeInt(ID,2);
		baos.writePString(name, true);
		IRData = getData();
		baos.writeInt(IRData.length, 4);
		baos.write(IRData, 0, IRData.length);
		return baos.toByteArray();
	}
	
	public abstract byte[] getData();

	public int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public void setID(int i) {
		ID = i;
	}

	public void setName(String string) {
		name = string;
	}
	
	public int getLength() {
		return getAsByteArray().length;
	}

}
