/*
 * Created on 14.12.2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.bitfolge.guilayout.prototype.export;

import java.io.ByteArrayOutputStream;

/**
 * @author kb
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ExtendedByteArrayOutputStream extends ByteArrayOutputStream {
	
	public ExtendedByteArrayOutputStream() {
		super();
	}

	public ExtendedByteArrayOutputStream(int size) {
		super(size);
	}


	public void writeString(String s) {
		for(int i=0; i<s.length(); i++) {
			write((byte) s.charAt(i));
		}
	}
	
	public void writePString(String s) {
		writePString(s, false);
	}
	
	public void writePString(String s, int padTo) {
		write(s.length());
		writeString(s);
		for (int i=(s.length()+1)%padTo; i%padTo!=0; i++) {
			write((byte) 0);
		}
		/*
		if(padEvenLength && ((s.length()+1)%padTo==1)) {
			write(0);
		}
		*/
	}

	public void writePString(String s, boolean padEvenLength) {
		writePString(s, 2);
	}
	
	public void write(byte b) {
		//System.out.println(b);
		super.write(b);
	}
	
	
	public void writeInt(int i, int paddingBytes) {
		for (int j=paddingBytes; j>0; j--) {
			write((i >> (j-1)*8) % 256);
			//this.write((byte) (i % 256));
			//i = i >> 8;
		}
	}
}
