/*
 * Created on 14.12.2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.bitfolge.guilayout.prototype.export.photoshop;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.Vector;

import de.bitfolge.guilayout.prototype.export.*;
import de.bitfolge.guilayout.prototype.export.photoshop.imageresources.*;

/**
 * @author kb
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PhotoshopWriter {
	
	protected static final String	HDR_SIGNATURE = "8BPS";
	protected static final int		HDR_VERSION = 1;
	
	public static final int		HDR_MODE_BITMAP = 0;
	public static final int		HDR_MODE_GRAYSCALE = 1;
	public static final int		HDR_MODE_INDEXED = 2;
	public static final int		HDR_MODE_RGB = 3;
	public static final int		HDR_MODE_CMYK = 4;
	public static final int		HDR_MODE_MULTICHANNEL = 7;
	public static final int		HDR_MODE_DUOTONE = 8;
	public static final int		HDR_MODE_LAB = 9;
	public static final int		COMPRESSION_RAW = 0;
	public static final int		COMPRESSION_RLE = 1;
	public static final int		COMPRESSION_ZIP = 2;
	public static final int		COMPRESSION_ZIP_PREDICTION = 3;

	private int mode = -1;
	private int depth = -1;
	private int columns = -1;
	private int rows = -1;
	private int channels = -1;
	private byte[] colorDataArray = null;
	private Vector imageResources;
	private Vector layers;
	private BufferedImage imageData;
	
	private OutputStream os = null;
	private ExtendedByteArrayOutputStream baos = null;
	
	
	public PhotoshopWriter(OutputStream outputStream) {
		os = outputStream;
		baos = new ExtendedByteArrayOutputStream();
		imageResources = new Vector();
		layers = new Vector();
		setMode(HDR_MODE_RGB);
	}
	
	protected void writeHeader() {
		baos.writeString(HDR_SIGNATURE);
		baos.writeInt(HDR_VERSION,2);
		baos.writeInt(0,6);
		baos.writeInt(channels,2);
		baos.writeInt(rows,4);
		baos.writeInt(columns,4);
		baos.writeInt(depth,2);
		baos.writeInt(mode,2);
	}
	
	protected void writeColorModeData() {
		if ((mode==HDR_MODE_INDEXED) || (mode==HDR_MODE_DUOTONE)) {
			baos.writeInt(colorDataArray.length, 4);
			baos.write(colorDataArray, 0, colorDataArray.length);
		} else {
			baos.writeInt(0,4);
		}
	}
	
	protected void writeImageResources() {
		ExtendedByteArrayOutputStream irbs = new ExtendedByteArrayOutputStream();
		for (int i=0; i<imageResources.size(); i++) {
			byte[] data = ((ImageResource) imageResources.get(i)).getAsByteArray();
			irbs.write(data, 0, data.length);
		}
		int IRSize = irbs.toByteArray().length;
		if (IRSize%2==1) {
			IRSize+=1;
			irbs.write(0);
		}
		
		baos.writeInt(IRSize, 4);
		baos.write(irbs.toByteArray(), 0, IRSize);
	}
	
	protected void writeLayerMasks() throws IOException {
		
		baos.writeInt(0,4);
		return;
		
		// buggy area
		/*
		ExtendedByteArrayOutputStream lbs = new ExtendedByteArrayOutputStream();
		
		baos.writeInt(layers.size(), 2);
		for (int i=0; i<layers.size(); i++) {
			Layer l = (Layer) layers.get(i);
			lbs.write(l.getLayerStructureAsByteArray());
		}
		
		
		int liSize = lbs.size();
		// "Length of the layers info section, rounded up to a multiple of 2."
		if (liSize%2==1)
			liSize++;
		baos.writeInt(liSize,4);
		lbs.writeTo(baos);
		*/
	}
	
	protected void writeImageData() {
		boolean dummyData = false;
		
		if (dummyData) {
			baos.writeInt(COMPRESSION_RLE, 2);
			
			ExtendedByteArrayOutputStream rleRow = new ExtendedByteArrayOutputStream();
			int lineLength = 0;
			int b = 0;
			int chunkSize = 0;
			while(b<columns) {
				chunkSize = (byte) Math.max(-127, b-columns);
				rleRow.write(chunkSize);
				rleRow.write((byte) 255);
				b-=chunkSize;
				lineLength+=2;
			}
			
			for (int i=0; i<rows*channels; i++) {
				baos.writeInt(lineLength, 2);
			}
			for (int i=0; i<rows*channels; i++) {
				baos.write(rleRow.toByteArray(), 0, lineLength);
			}
		} else {
			baos.writeInt(COMPRESSION_RAW, 2);
			Raster r = imageData.getData();
			for (int plane=0; plane<this.channels; plane++) {
				for (int y=0; y<r.getHeight(); y++) {
					for (int x=0; x<r.getWidth(); x++) {
						int pixel = r.getSample(x,y,plane);
						baos.write((byte)pixel);
					}
				}
			}
		}
	}
	
	protected void prepareFile() throws IOException {
		writeHeader();
		writeColorModeData();
		writeImageResources();
		writeLayerMasks();
		writeImageData();
	}
	
	public void writeFile() throws IOException {
		prepareFile();
		baos.writeTo(os);
		baos.close();
	}
	
	public void addImageResource(ImageResource ir) {
		imageResources.add(ir);
	}
	
	public static void main(String[] args) {
		PhotoshopWriter psw;
		try {
			psw = new PhotoshopWriter(new FileOutputStream("c:\\temp\\testfile.psd"));
			psw.setChannels(3);
			psw.setColumns(1024);
			psw.setRows(768);
			psw.setDepth(8);
			psw.setMode(HDR_MODE_RGB);
			
			psw.addImageResource(new IRCaption("This is my caption."));
			IRGridGuides guides = new IRGridGuides();
			guides.addGuide(512, IRGridGuides.GUIDE_DIRECTION_VERTICAL);
			guides.addGuide(256, IRGridGuides.GUIDE_DIRECTION_HORIZONTAL);
			guides.addGuide(512, IRGridGuides.GUIDE_DIRECTION_HORIZONTAL);
			psw.addImageResource(guides);
			
			BufferedImage im = new BufferedImage(400, 300, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = (Graphics2D) im.getGraphics();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(Color.WHITE);
			g2.fillRect(0,0,400,300);
			g2.setColor(new Color(255,0,0));
			g2.drawString("Hello world!", 10,20);
			WritableRaster wr = im.getRaster();
			wr.setPixel(0,0,new int[] {0,255,255});
			psw.setImageData(im);
			/*
			Layer l = new Layer();
			im = new BufferedImage(400, 300, BufferedImage.TYPE_INT_RGB);
			g2 = (Graphics2D) im.getGraphics();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(Color.WHITE);
			g2.fillRect(0,0,400,300);
			g2.setColor(new Color(0,255,0));
			g2.drawString("Hello world!", 10,40);
			l.setImage(im);
			psw.addLayer(l);
			*/
		
			psw.writeFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return
	 */
	public int getChannels() {
		return channels;
	}

	/**
	 * @return
	 */
	public int getColumns() {
		return columns;
	}

	/**
	 * @return
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * @return
	 */
	public int getMode() {
		return mode;
	}

	/**
	 * @return
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * @param i
	 */
	public void setChannels(int i) {
		channels = i;
	}

	/**
	 * @param i
	 */
	public void setColumns(int i) {
		columns = i;
	}

	/**
	 * @param i
	 */
	public void setDepth(int i) {
		depth = i;
	}

	/**
	 * @param i
	 */
	public void setMode(int i) {
		mode = i;
	}

	/**
	 * @param i
	 */
	public void setRows(int i) {
		rows = i;
	}

	public void setImageData(BufferedImage imageData) {
		this.imageData = imageData;
		this.setColumns(imageData.getWidth());
		this.setRows(imageData.getHeight());
		this.setChannels(imageData.getColorModel().getNumColorComponents());
		this.setDepth(imageData.getColorModel().getComponentSize(0));
	}

	public BufferedImage getImageData() {
		return imageData;
	}
	
	public void addLayer(Layer layer) {
		layers.add(layer);
	}

}
