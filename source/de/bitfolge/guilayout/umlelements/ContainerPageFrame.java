package de.bitfolge.guilayout.umlelements;

import java.awt.*;
import java.awt.event.*;

import de.bitfolge.guilayout.drawing.*;

public class ContainerPageFrame extends AbstractElementView {
	
	private int _labelDistance = 5;
	
	public ContainerPageFrame(int width) {
		super();
		this.setSize(width, width/4 * 3);
		//this.setBackground(new Color(255,255,255,128));
		this.setBackground(Color.WHITE);
		this.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				ContainerPageFrame cpf = (ContainerPageFrame) e.getSource();
				cpf.mouseOver();
				cpf.repaint();
			}

			public void mouseExited(MouseEvent e) {
				ContainerPageFrame cpf = (ContainerPageFrame) e.getSource();
				cpf.mouseOut();
				cpf.repaint();
			}
		});
	}
	
	public void update(Graphics g) {
		//super.update(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		Font nameFont = new Font("Arial Narrow",Font.BOLD,10);
		g2.setFont(nameFont);
		FontMetrics fm = g2.getFontMetrics(nameFont);
		//g2.drawString(this.getName(), 0, _labelDistance);
	}
	
	public void mouseOver() {
		setOutlineWidth(1);
	}
	
	public void mouseOut() {
		setOutlineWidth(1);
	}

}
