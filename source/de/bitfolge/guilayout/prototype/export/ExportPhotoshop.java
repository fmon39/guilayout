package de.bitfolge.guilayout.prototype.export;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.swing.SwingUtilities;

import de.bitfolge.guilayout.drawing.DashedStroke;
import de.bitfolge.guilayout.elements.functionality.UIFunctionality;
import de.bitfolge.guilayout.elements.screenarea.*;
import de.bitfolge.guilayout.prototype.export.photoshop.PhotoshopWriter;

public class ExportPhotoshop implements DiagramExport {

	public void exportTo(File file, Screen rootSA) throws Exception {
		FileOutputStream os = new FileOutputStream(file);
		PhotoshopWriter psw = new PhotoshopWriter(os);
		
		int w = rootSA.getWidth();
		int h = rootSA.getHeight();
		BufferedImage im = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) im.getGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.WHITE);
		g2.fillRect(0,0,w,h);
		
		Component[] compsArray = rootSA.getComponents();
		Vector comps = new Vector(Arrays.asList(compsArray));
		
		for (int i=0; i<comps.size(); i++) {
			Stroke solid = new BasicStroke(1);
			Stroke dashed = new DashedStroke(1,2,2);
			g2.setColor(Color.MAGENTA);
			g2.setStroke(solid);
			ScreenArea sa = (ScreenArea) comps.get(i);
			Rectangle r = sa.getBounds();
			r = SwingUtilities.convertRectangle(sa.getParent(), r, rootSA);
			g2.setStroke(sa.isAbstract()? dashed : solid);
			g2.draw(r);
			g2.setStroke(solid);
			if (ContainerScreenArea.class.isInstance(sa)) {
				ContainerScreenArea csa = (ContainerScreenArea) sa;
				comps.addAll(Arrays.asList(csa.getComponents()));
			}
			if (FunctionalScreenArea.class.isInstance(sa)) {
				FunctionalScreenArea fsa = (FunctionalScreenArea) sa;
				Iterator it = fsa.functionalities.iterator();
				while (it.hasNext()) {
					UIFunctionality uif = (UIFunctionality) it.next();
					try {
						Graphics2D g3 = (Graphics2D) g2.create(fsa.getX()+1, fsa.getY()+1, fsa.getWidth()-1, fsa.getHeight()-1);
						uif.draw(g3,new Color(0,155,255));
					} catch (Exception e) {
						// ignore
					}
				}
			}
		}
		
		psw.setImageData(im);
		
		psw.writeFile();
		os.close();
	}

	public String getName() {
		return "Photoshop";
	}

	public String getExtension() {
		return ".psd";
	}

	public String getFileDescription() {
		return "Photoshop files";
	}
}
