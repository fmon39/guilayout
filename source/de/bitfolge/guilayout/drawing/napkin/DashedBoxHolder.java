package de.bitfolge.guilayout.drawing.napkin;

import java.awt.*;

import napkin.*;
import napkin.BoxHolder;

public class DashedBoxHolder extends BoxHolder {
	
	public boolean dashed = false;
	
	DashedBoxHolder(BoxHolder box) {
		gen = box.gen;
		shape = box.shape;
		width = box.width;
		size = box.size;
		insets = box.insets;
		breakSide = box.breakSide;
		begBreak = box.begBreak;
		endBreak = box.endBreak;
	}

	DashedBoxHolder(BoxGenerator gen) {
		super(gen);
	}

    public void draw(Graphics g) {
        Graphics2D lineG = NapkinUtil.lineGraphics((Graphics2D) g, width, dashed);
        lineG.draw(shape);
    }
}
