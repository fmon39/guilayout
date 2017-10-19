package de.bitfolge.guilayout.drawing;

import java.awt.*;
import java.util.*;

public class Turtle {
	private Component component;
	private Graphics2D g;
	private int originX, originY, x, y;
	private double angle;
	private Stroke stroke;
	private boolean drawing = true;
	private Map positions;
	
	public Turtle(Graphics g, int x, int y) {
		this.g = (Graphics2D) g;
		this.originX = x;
		this.originY = y;
		this.x = this.originX;
		this.y = this.originY;
		this.angle = 0;
		this.stroke = new BasicStroke(1);
		this.positions = new HashMap();
	}
	
	public void setStroke(Stroke s) {
		stroke = s;
		g.setStroke(stroke);
	}
	
	protected double ensureAngleValid(double angle) {
		while (angle>=2*Math.PI) {
			angle-=2*Math.PI;
		}
		while (angle<0) {
			angle+=2*Math.PI;
		}
		return angle;
	}
	
	public double convertAngleDegreeToRad(double degree) {
		double angle = (degree/360.0)*2*Math.PI;
		return ensureAngleValid(angle);
	}
	
	public void turn(double degree) {
		angle+=convertAngleDegreeToRad(degree);
		ensureAngleValid(angle);
	}
	
	public void setDrawing(boolean enabled) {
		drawing = enabled;
	}
	
	public void setPosition(double x, double y) {
		this.x = (int) Math.round(originX+x);
		this.y = (int) Math.round(originY+y);
	}
	
	public void moveRelative(double x, double y) {
		this.x = (int) Math.round(this.x+x);
		this.y = (int) Math.round(this.y+y);
	}
	
	public void setAngle(double angle) {
		this.angle = convertAngleDegreeToRad(angle);
		ensureAngleValid(angle);
	}
	
	public void forward(double distance) {
		int dX, dY;
		dX = (int) Math.round(Math.cos(angle)*distance);
		dY = (int) Math.round(Math.sin(angle)*distance);
		if (drawing) g.drawLine(x,y,x+dX,y+dY);
		x+=dX;
		y+=dY;
	}
	
	public void storePosition(Object name) {
		positions.put(name, new Point(x,y));
	}
	
	public void restorePosition(Object name) {
		Point p = (Point) positions.get(name);
		x = p.x;
		y = p.y;
	}
	
	public Point getPosition() {
		return new Point(this.x,this.y);
	}
}
