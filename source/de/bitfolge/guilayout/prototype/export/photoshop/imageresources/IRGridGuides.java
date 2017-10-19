/*
 * Created on 14.12.2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.bitfolge.guilayout.prototype.export.photoshop.imageresources;

import java.util.Vector;

import de.bitfolge.guilayout.prototype.export.ExtendedByteArrayOutputStream;

/**
 * @author kb
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class IRGridGuides extends ImageResource {
	
	private class IRGuide {
		public int location = -1;
		public int direction = -1;
		
		public IRGuide(int location, int direction) {
			this.location = location;
			this.direction = direction;
		}
	}
	
	public static final int GUIDE_DIRECTION_HORIZONTAL = 1;
	public static final int GUIDE_DIRECTION_VERTICAL = 0;
	
	private Vector guides;

	public IRGridGuides() {
		setID(1032);
		guides = new Vector();
	}


	public byte[] getData() {
		ExtendedByteArrayOutputStream baos = new ExtendedByteArrayOutputStream();
		baos.writeInt(1, 4); // version
		baos.writeInt(576, 8); // grid cycle
		baos.writeInt(guides.size(), 4); // guide count
		for (int i=0; i<guides.size(); i++) {
			IRGuide guide = (IRGuide) guides.get(i);
			baos.writeInt(guide.location*32, 4);
			baos.write(guide.direction);
		}
		return baos.toByteArray();
	}
	
	public void addGuide(int location, int direction) {
		guides.add(new IRGuide(location, direction));
	}

}
