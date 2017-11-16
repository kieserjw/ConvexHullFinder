package edu.uwec.cs.kieserjw.project2;

import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Collections;

public class QuickHull implements ConvexHullFinder {
	private ArrayList<Point2D> ptsList = null;
	
	public QuickHull(ArrayList<Point2D> pointsList) {
		this.ptsList = computeHull(pointsList);
	}
	
	public ArrayList<Point2D> computeHull(ArrayList<Point2D> pointsList) {

		Collections.sort(pointsList, new XCompare());
		//System.out.println("Sorted quick pts: \n"+pointsList);
		Point2D left = pointsList.get(0);
		Point2D right = pointsList.get(pointsList.size()-1);
		Line2D half = new Line2D.Double(left,right);
		Line2D reverseHalf = new Line2D.Double(right,left); 
		ArrayList<Point2D> pointsAbove = new ArrayList<Point2D>();
		ArrayList<Point2D> pointsBelow = new ArrayList<Point2D>();

		for (int i =0;i<pointsList.size();i++){
			if (half.relativeCCW(pointsList.get(i))<0){
				pointsAbove.add(pointsList.get(i));
			}else if (half.relativeCCW(pointsList.get(i))>0){
				pointsBelow.add(pointsList.get(i));
			}
		}
		pointsAbove = recursiveQuickHull(half, pointsAbove);
		pointsBelow = recursiveQuickHull(reverseHalf, pointsBelow);
		pointsList.clear();
		pointsList.addAll(pointsAbove);
		if (pointsBelow.size()!=0){
			pointsBelow.remove(0);
		}
		pointsList.addAll(pointsBelow);
		pointsList.remove(pointsList.size()-1);
		//System.out.println("QuickHull Points: \n"+pointsList);
		return pointsList;
	}

	private ArrayList<Point2D> recursiveQuickHull(Line2D lineAB, ArrayList<Point2D> pointsAB){

		if (pointsAB.size()<2){
			pointsAB.add(0,lineAB.getP1());
			pointsAB.add(lineAB.getP2());

			return pointsAB;
		}


		double dist = 0;
		Point2D furthest = null;
		for (int i = 0;i<pointsAB.size();i++){
			if (lineAB.ptLineDist(pointsAB.get(i))>dist){
				dist = lineAB.ptLineDist(pointsAB.get(i));
				furthest = pointsAB.get(i);
			}
		}
		Line2D left = new Line2D.Double(lineAB.getP1(),furthest);
		Line2D right = new Line2D.Double(furthest,lineAB.getP2());
		ArrayList<Point2D> pointsLeft = new ArrayList<Point2D>();
		ArrayList<Point2D> pointsRight = new ArrayList<Point2D>();

		for (int i = 0;i<pointsAB.size();i++){
			if (left.relativeCCW(pointsAB.get(i))<0){
				pointsLeft.add(pointsAB.get(i));
			}
			if (right.relativeCCW(pointsAB.get(i))<0){
				pointsRight.add(pointsAB.get(i));
			}
		}
		pointsLeft = recursiveQuickHull(left,pointsLeft);
		pointsRight = recursiveQuickHull(right,pointsRight);

		pointsAB.clear();
		pointsAB.addAll(pointsLeft);
		if (pointsRight.size()!=0){
			pointsRight.remove(0);
		}
		pointsAB.addAll(pointsRight);


		return pointsAB;

	}

	public void setPointsList(ArrayList<Point2D> pointsList) {
		this.ptsList = pointsList;
	}

	public ArrayList<Point2D> getPointsList() {
		return ptsList;
	}


}
