package de.bitfolge.guilayout.prototype.export.photoshop;

import de.bitfolge.guilayout.prototype.export.ExtendedByteArrayOutputStream;

public class Channel {
	
	public static final int CHANNEL_ID_RED = 0;
	public static final int CHANNEL_ID_GREEN = 1;
	public static final int CHANNEL_ID_BLUE = 2;
	public static final int CHANNEL_ID_TRANSPARENCY_MASL = -1;
	public static final int CHANNEL_ID_USER_SUPPLIED_LAYER_MASK = -2;
	
	private int channelID;
	private int channelDataSize;
	
	public Channel(int channelID) {
		this.setChannelID(channelID);
	}

	public byte[] getChannelInfoAsByteArray() {
		ExtendedByteArrayOutputStream baos = new ExtendedByteArrayOutputStream();
		baos.writeInt(getChannelID(), 2);
		baos.writeInt(getChannelDataSize(), 4);
		
		return baos.toByteArray();
	}

	public void setChannelID(int channelID) {
		this.channelID = channelID;
	}

	public int getChannelID() {
		return channelID;
	}

	public void setChannelDataSize(int channelDataSize) {
		this.channelDataSize = channelDataSize;
	}

	public int getChannelDataSize() {
		return channelDataSize;
	}
}
