package de.bitfolge.guilayout.prototype;

import java.awt.Dimension;

import javax.swing.*;
import javax.swing.event.*;

import de.bitfolge.guilayout.elements.DiagramManager;

public class ToolBarZoom extends AbstractToolBarPrototype implements ChangeListener {
	
	protected JLabel zoomDisplay;
	private JSlider slider;
	
	public ToolBarZoom() {
		super();
		
		this.setName("Zoom");
		
		zoomDisplay = new JLabel("100%");
		zoomDisplay.setBounds(0,0,32,16);
		zoomDisplay.setSize(32,16);
		zoomDisplay.setMinimumSize(new Dimension(32,16));
		zoomDisplay.setMaximumSize(new Dimension(32,16));
		zoomDisplay.setToolTipText("Current zoom level");
		this.add(zoomDisplay);
		
		slider = new JSlider(25,175,100);
		slider.setBorder(BorderFactory.createEtchedBorder());
		slider.setMinorTickSpacing(25);
		Dimension size = new Dimension(150,26); 
		slider.setSize(size);
		slider.setMinimumSize(size);
		slider.setMaximumSize(size);
		slider.addChangeListener(this);
		slider.setFocusable(false);
		slider.setPaintTrack(true);
		slider.setBorder(null);
		slider.setPaintTicks(false);
		slider.setOpaque(false);
		slider.setToolTipText("Drag to change zoom level");
		this.add(slider);
	}
	
	public void setZoomDisplay(double zoom) {
		zoomDisplay.setText(Math.round(zoom*100)+"%");
	}
	
	protected double sliderToZoom(int sliderValue) {
		if (sliderValue>100) {
			int over = sliderValue-100;
			return 1 + 4*over/100.0;
		}
		return sliderValue/100.0;
	}

	public void stateChanged(ChangeEvent e) {
		JSlider slider = (JSlider) e.getSource();

		double zoom = sliderToZoom(slider.getValue());
		setZoomDisplay(zoom);
		DiagramManager.getInstance().setZoom(zoom);
		this.repaint();
	}
	
}
