/*
 * Created on 14.12.2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.bitfolge.guilayout.prototype.export;

/**
 * @author kb
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BigEndianByteArrayOutputStream extends ExtendedByteArrayOutputStream {

	public BigEndianByteArrayOutputStream() {
		super();
	}

	public BigEndianByteArrayOutputStream(int size) {
		super(size);
	}

	public void writeInt(int i, int paddingBytes) {
		for (int j=0; j<paddingBytes; j++) {
			this.write((byte) (i % 256));
			i = i >> 8;
		}
	}
	
}
