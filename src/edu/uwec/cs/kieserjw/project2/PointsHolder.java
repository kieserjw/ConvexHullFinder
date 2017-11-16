package edu.uwec.cs.kieserjw.project2;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class PointsHolder {
	private ArrayList<Point2D> points = new ArrayList<Point2D>();
	public PointsHolder (){
		/*
		 * if you want to input pts manually, uncomment and copy/paste the following line to add the desired pts
		 * (ensure these points are within the x and y range static variables specified in the BlankArea class)
		 */
		//points.add(new Point2D.Double(0,0));
	}
	
	public ArrayList<Point2D> getPoints(){
		return this.points;
	}
	
	public void setPoints(ArrayList<Point2D> ptList){
		this.points = ptList;
	}
	
	public void addPoint (Point2D point){
		this.points.add(point);
	}
	
	public Point2D getLastPoint (){
		return this.points.get(this.points.size()-1);
	}
	
	public int getSize() {
		return this.points.size();
	}
	
	public Point2D getPointAt(int i) {
		return this.points.get(i);
	}
	
	public void reGen(){
		PointsView.hullPoints.getPoints().clear();
		this.points.clear();
		for (int i = 0;i<10;i++){
			this.points.add(new Point2D.Double(BlankArea.xRange*(1-Math.random()*2), BlankArea.yRange*(1-Math.random()*2)));
		}
	}
	
	public void reGen(int z){
		this.points.clear();
		for (int i = 0;i<z;i++){
			this.points.add(new Point2D.Double(BlankArea.xRange*(1-Math.random()*2), BlankArea.yRange*(1-Math.random()*2)));
		}
	}
	
	
}
