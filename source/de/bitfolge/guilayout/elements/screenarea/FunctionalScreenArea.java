package de.bitfolge.guilayout.elements.screenarea;

import java.awt.Container;
import java.util.*;

import de.bitfolge.guilayout.elements.DiagramManager;
import de.bitfolge.guilayout.elements.functionality.UIFunctionality;

public class FunctionalScreenArea extends ScreenArea {
	public Set functionalities;
	
	public FunctionalScreenArea() {
		functionalities = Collections.synchronizedSet(new HashSet());
	}
	
	public FunctionalScreenArea(ScreenArea sa) {
		super(sa);
		functionalities = Collections.synchronizedSet(new HashSet());
	}
	
	public synchronized void setFunctionality(Class functionality, boolean enabled) {
		try {
			if (enabled && !hasFunctionality(functionality)) {
				UIFunctionality funct = (UIFunctionality) functionality.newInstance();
				funct.setParent(this);
				functionalities.add(funct);
			}
			UIFunctionality deletableFunc = null;
			if (!enabled && (deletableFunc = getFunctionality(functionality))!=null) {
				synchronized(functionalities) {
					functionalities.remove(deletableFunc);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected UIFunctionality getFunctionality(Class functionality) {
		synchronized (functionalities) {
			Iterator it = functionalities.iterator();
			while (it.hasNext()) {
				Object funct = (Object) it.next();
				if (functionality.isInstance(funct)) {
					return (UIFunctionality) funct;
				}
			}
			return null;
		}
	}
	
	public synchronized boolean hasFunctionality(Class functionality) {
		return getFunctionality(functionality)!=null;
	}

	public static FunctionalScreenArea replace(ScreenArea sa) throws InstantiationException {
		Container cont = sa.getParent();
		FunctionalScreenArea fsa = new FunctionalScreenArea(sa);
		cont.remove(sa);
		cont.add(fsa);
		DiagramManager.getInstance().setSelectedScreenArea(fsa);
		return fsa;
	}
	
	
	public synchronized void paint(java.awt.Graphics g) {
		synchronized (functionalities) {
			Iterator it = functionalities.iterator();
			while (it.hasNext()) {
				UIFunctionality funct = (UIFunctionality) it.next();
				funct.draw(g, getForeground());
			}
		}
		super.paint(g);
	}
	
	public boolean isConvertible() {
		synchronized (functionalities) {
			return functionalities.isEmpty();
		}
	}
	
	public boolean isFunctional() {
		return true;
	}
	
	public Object clone() {
		FunctionalScreenArea fsa = new FunctionalScreenArea(this);
		//fsa.setName("Copy of "+getName());
		Iterator it = functionalities.iterator();
		while (it.hasNext()) {
			UIFunctionality uif = (UIFunctionality) it.next();
			fsa.setFunctionality(uif.getClass(), true);
		}
		return fsa;
	}

	public void updateZoom(double newZoom) {
		super.updateZoom(newZoom);
		synchronized (functionalities) {
			Iterator it = functionalities.iterator();
			while (it.hasNext()) {
				UIFunctionality funct = (UIFunctionality) it.next();
				funct.setZoom(newZoom);
			}
		}
	}
}
