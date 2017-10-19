package de.bitfolge.guilayout.prototype.export.photoshop;

import java.awt.image.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;

import de.bitfolge.guilayout.prototype.export.ExtendedByteArrayOutputStream;

public class Layer {
	
	protected static final String BLENDMODE_NORMAL = "norm";
	protected static final String BLENDMODE_DARKEN = "dark";
	protected static final String BLENDMODE_LIGHTEN = "lite";
	protected static final String BLENDMODE_HUE = "hue ";
	protected static final String BLENDMODE_SATURATION = "sat ";
	protected static final String BLENDMODE_COLOR = "colr";
	protected static final String BLENDMODE_LUMINOSITY = "lum ";
	protected static final String BLENDMODE_MULTIPLY = "mul ";
	protected static final String BLENDMODE_SCREEN = "scrn";
	protected static final String BLENDMODE_DISSOLVE = "diss";
	protected static final String BLENDMODE_OVERLAY = "over";
	protected static final String BLENDMODE_HARDLIGHT = "hLit";
	protected static final String BLENDMODE_SOFTLIGHT = "sLit";
	protected static final String BLENDMODE_DIFFERENCE = "diff";
	protected static final String BLENDMODE_EXCLUSION = "smud";
	protected static final String BLENDMODE_COLORDODGE = "div ";
	protected static final String BLENDMODE_COLORBURN = "idiv";
	
	protected static final byte FLAG_TRANSPARENCY_PROTECTED = 0;
	protected static final byte FLAG_VISIBLE = 1;
	protected static final byte FLAG_OBSOLETE = 2;
	protected static final byte FLAG_PS5_INFO = 4;
	protected static final byte FLAG_PIXEL_DATA_IRRELEVANT = 8;
		
	private byte top = 0;
	private byte left = 0; 
	private byte bottom = 0;
	private byte right = 0;
	
	private Vector channels = null;
	
	private final String blendModeSignature = "8BIM";
	private String blendModeKey = BLENDMODE_NORMAL;
	
	private byte opacity = (byte) 255;
	private byte clipping = 0;
	private byte flags = 0;
	
	private String name = "";
	
	protected ExtendedByteArrayOutputStream baos = null;
	protected BufferedImage bi = null;
	
	public Layer() {
		channels = new Vector();
		baos = new ExtendedByteArrayOutputStream();
	}
	
	public String getBlendModeKey() {
		return blendModeKey;
	}
	public void setBlendModeKey(String blendModeKey) {
		this.blendModeKey = blendModeKey;
	}

	public byte getBottom() {
		return bottom;
	}

	public void setBottom(byte bottom) {
		this.bottom = bottom;
	}

	public Vector getChannels() {
		return channels;
	}

	public void setChannels(Vector channels) {
		this.channels = channels;
	}

	public byte getClipping() {
		return clipping;
	}

	public void setClipping(byte clipping) {
		this.clipping = clipping;
	}

	public byte getFlags() {
		return flags;
	}

	public void setFlags(byte flags) {
		this.flags = flags;
	}

	public byte getLeft() {
		return left;
	}

	public void setLeft(byte left) {
		this.left = left;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getOpacity() {
		return opacity;
	}

	public void setOpacity(byte opacity) {
		this.opacity = opacity;
	}

	public byte getRight() {
		return right;
	}

	public void setRight(byte right) {
		this.right = right;
	}

	public byte getTop() {
		return top;
	}

	public void setTop(byte top) {
		this.top = top;
	}
	
	public void setImage(BufferedImage im) {
		bi = im;
		int channelCount = bi.getColorModel().getNumComponents();
		for (int i=0; i<channelCount; i++) {
			Channel ch = new Channel(i);
			ch.setChannelDataSize(bi.getWidth()*bi.getHeight());
			addChannel(ch);
		}
	}
	
	public BufferedImage getImage() {
		return bi;
	}
	
	public byte[] getLayerStructureAsByteArray() {
		ExtendedByteArrayOutputStream baos = new ExtendedByteArrayOutputStream();
		baos.writeInt(top, 4);
		baos.writeInt(left, 4);
		baos.writeInt(bottom, 4);
		baos.writeInt(right, 4);
		baos.writeInt(channels.size(),2);
		for (int i=0; i<channels.size(); i++) {
			try {
				baos.write(((Channel)channels.get(i)).getChannelInfoAsByteArray());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		baos.writeString(blendModeSignature);
		baos.writeString(blendModeKey);
		baos.write(opacity);
		baos.write(clipping);
		baos.write(flags);
		baos.write((byte) 0);
		
		// TODO count "Length of the extra data field. This is the total length of the next five fields."
		baos.writeInt(8, 4);
		baos.writeInt(0, 4); // length of layer mask and adjustment
		baos.writeInt(0, 4); // lenght of layer blending ranges data
		
		baos.writePString(name, 4);
		
		return baos.toByteArray();
	}
	
	public byte[] getAsByteArray() throws IOException {
		ExtendedByteArrayOutputStream data = new ExtendedByteArrayOutputStream();
		
		data.write(getLayerStructureAsByteArray());
		
		Raster raster = bi.getData();
		for (int i=0; i<channels.size(); i++) {
			Channel ch = (Channel) channels.get(i);
			int band = ch.getChannelID();
			switch (ch.getChannelID()) {
				case Channel.CHANNEL_ID_RED:
					break;
				case Channel.CHANNEL_ID_GREEN:
					break;
				case Channel.CHANNEL_ID_BLUE:
					break;
			}
			for (int y=0; y<bi.getHeight(); y++) {
				for (int x=0; x<bi.getWidth(); x++) {
					data.write(raster.getSample(x,y,band));
				}
			}
		}
		
		ExtendedByteArrayOutputStream dataLength = new ExtendedByteArrayOutputStream();
		dataLength.writeInt(data.size(), 4);
		data.writeTo(dataLength);
		return dataLength.toByteArray();
	}
	
	protected void addChannel(Channel ch) {
		channels.add(ch);
	}
}
