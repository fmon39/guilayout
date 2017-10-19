package de.bitfolge.guilayout.prototype;

import java.io.File;
import java.util.*;

import javax.swing.filechooser.FileFilter;

public class ExtensionFileFilter extends FileFilter {
	
	protected Set filteredExtensions;
	protected String description;
	
	public ExtensionFileFilter() {
		filteredExtensions = new HashSet();
		description = "";
	}
	
	public ExtensionFileFilter(Collection extensions) {
		this();
		filteredExtensions.addAll(extensions);
	}
	
	public void addExtension(String extension) {
		filteredExtensions.add(extension);
	}

	public boolean accept(File pathname) {
		String name = pathname.getName();
		if (pathname.isDirectory()) {
			return true;
		}
		if (name.indexOf('.')==-1) {
			return filteredExtensions.contains(""); 
		}
		String fileExtension = name.substring(name.lastIndexOf('.'));
		return filteredExtensions.contains(fileExtension);
	}
	
	public void setDescription(String desc) {
		description = desc;
	}

	public String getDescription() {
		return description;
	}

}
