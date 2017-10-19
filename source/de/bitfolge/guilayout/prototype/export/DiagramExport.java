package de.bitfolge.guilayout.prototype.export;

import java.io.File;

import de.bitfolge.guilayout.elements.screenarea.Screen;

public interface DiagramExport {
	
	public void exportTo(File file, Screen screen) throws Exception;
	
	public String getName();
	
	public String getExtension();
	
	public String getFileDescription();
}
