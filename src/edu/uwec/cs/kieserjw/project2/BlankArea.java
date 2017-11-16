package edu.uwec.cs.kieserjw.project2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import javax.swing.JPanel;

public class BlankArea extends JPanel implements MouseListener {
	static boolean hullComputed = false;
	static final int xRange = 16;
	static final int yRange = 20;
	private static final int dotSize = 5;


	private static final long serialVersionUID = 1L;
	public BlankArea (){
		this.setBackground(Color.WHITE);
		this.addMouseListener(this);
	}

	public void mouseClicked(MouseEvent arg0) {
		double newX = ((double)arg0.getX()/this.getWidth()-.5)*xRange*2;
		double newY = (.5-(double)arg0.getY()/this.getHeight())*yRange*2;
		PointsView.points.addPoint(new Point2D.Double(newX , newY));
		hullComputed = false;
		this.repaint();

	}

	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		//System.out.println("Number of points: "+PointsView.points.getSize());
		
		g.drawLine(this.getWidth()/2, 0, this.getWidth()/2, this.getHeight());
		g.drawLine(0, this.getHeight()/2, this.getWidth(),this.getHeight()/2);
		g.drawString(""+xRange, this.getWidth()-20, this.getHeight()/2-10);
		g.drawString("-"+xRange, 10, this.getHeight()/2-10);
		g.drawString(""+yRange, this.getWidth()/2+10, 15);
		g.drawString("-"+yRange, this.getWidth()/2+10, this.getHeight()-15);
		
		if (hullComputed){
			if (PointsView.quickHullSet){
				g.setColor(Color.RED);
			}else{
				g.setColor(Color.BLUE);
			}
			Graphics2D g2 = (Graphics2D) g;
	        g2.setStroke(new BasicStroke(2));
			int size = PointsView.hullPoints.getSize();
			for (int i = 0;i<size-1;i++){
				int x1 = (int) ((PointsView.hullPoints.getPointAt(i).getX()+xRange)*this.getWidth()/xRange/2);
				int x2 = (int) ((PointsView.hullPoints.getPointAt(i+1).getX()+xRange)*this.getWidth()/xRange/2);
				int y1 = (int) ((-PointsView.hullPoints.getPointAt(i).getY()+yRange)*this.getHeight()/yRange/2);
				int y2 = (int) ((-PointsView.hullPoints.getPointAt(i+1).getY()+yRange)*this.getHeight()/yRange/2);
				g2.drawLine(x1, y1, x2, y2);
			}
			int x1 = (int) ((PointsView.hullPoints.getPointAt(size-1).getX()+xRange)*this.getWidth()/xRange/2);
			int x2 = (int) ((PointsView.hullPoints.getPointAt(0).getX()+xRange)*this.getWidth()/xRange/2);
			int y1 = (int) ((-PointsView.hullPoints.getPointAt(size-1).getY()+yRange)*this.getHeight()/yRange/2);
			int y2 = (int) ((-PointsView.hullPoints.getPointAt(0).getY()+yRange)*this.getHeight()/yRange/2);
			g2.drawLine(x1, y1, x2, y2);
		}
		
		g.setColor(Color.BLACK);
		for (int i = 0 ; i<PointsView.points.getSize();i++){
			int xPrime = (int) ((PointsView.points.getPointAt(i).getX()+xRange)*this.getWidth()/xRange/2-dotSize/2);
			int yPrime = (int) ((-PointsView.points.getPointAt(i).getY()+yRange)*this.getHeight()/yRange/2-dotSize/2);
			g.fillOval(xPrime,yPrime,dotSize,dotSize);
		}

	}

	public void mouseEntered(MouseEvent arg0) {
	}
	public void mouseExited(MouseEvent arg0) {
	}
	public void mousePressed(MouseEvent arg0) {
	}
	public void mouseReleased(MouseEvent arg0) {
	}



}
