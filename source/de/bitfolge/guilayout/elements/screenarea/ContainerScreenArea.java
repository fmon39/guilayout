package de.bitfolge.guilayout.elements.screenarea;

import java.awt.Component;
import java.awt.Container;

public class ContainerScreenArea extends ScreenArea {
	
	public ContainerScreenArea(ContainerScreenArea csa) {
		super(csa);
		for (int i=0; i<csa.getComponentCount(); i++) {
			ScreenArea sa = (ScreenArea) csa.getComponent(i);
			add((ScreenArea) sa.clone());
		}
	}
		
	public ContainerScreenArea(ScreenArea sa) {
		super(sa);
	}
	
	public ContainerScreenArea() {
		super();
	}
	
	public boolean isContainer() {
		return true;
	}
	
	public boolean isConvertible() {
		return getComponents().length==0;
	}
	
	
	public boolean isDeletable() {
		return getComponents().length==0;
	}

	public static ContainerScreenArea replace(ScreenArea sa) throws InstantiationException {
		Container cont = sa.getParent();
		ContainerScreenArea csa = new ContainerScreenArea(sa);
		if (cont!=null) {
			cont.remove(sa);
			cont.add(csa);
		}
		return csa;
	}

	protected void addImpl(Component comp, Object constraints, int index) {
		super.addImpl2(comp, constraints, index);
	}
	
	public void updateZoom(double newZoom) {
		super.updateZoom(newZoom);
		Component[] comps = getComponents();
		for (int i=0; i<comps.length; i++) {
			ScreenArea sa = (ScreenArea) comps[i];
			sa.updateZoom(newZoom);
		}
	}
	
	public Object clone() {
		ContainerScreenArea csa = new ContainerScreenArea(this);
		//csa.setName("Copy of "+getName());
		return csa;
	}
}
