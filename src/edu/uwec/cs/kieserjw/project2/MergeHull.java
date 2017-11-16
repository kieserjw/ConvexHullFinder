package edu.uwec.cs.kieserjw.project2;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;

public class MergeHull implements ConvexHullFinder {

	private ArrayList<Point2D> pointsList = null;
	public MergeHull(ArrayList<Point2D> points) {
		pointsList = computeHull(points);
	}

	public ArrayList<Point2D> computeHull(ArrayList<Point2D> points) {
		Collections.sort(points, new XCompare());
		//System.out.println("Sorted merge pts: \n"+points);
		points =  recursiveMergeHull(points);
		//System.out.println("Merge hull pts: \n"+points);
		return points;
	}

	private ArrayList<Point2D> recursiveMergeHull(ArrayList<Point2D> points){
		/*
		 * base case check
		 */
		if (points.size()<3){
			return points;
		}

		/*
		 * Split
		 */

		ArrayList<Point2D> pointsLeft = new ArrayList<Point2D>();
		ArrayList<Point2D> pointsRight = new ArrayList<Point2D>();

		pointsLeft.addAll(points.subList(0, points.size()/2));
		pointsRight.addAll(points.subList(points.size()/2,points.size()));
		points.clear();
		pointsLeft = recursiveMergeHull(pointsLeft);
		pointsRight = recursiveMergeHull(pointsRight);

		/*
		 * 
		 * merge the two (find right-most point in left array,
		 * 					find left-most point in right array)
		 */

		int rmL = 0;
		int lmR = 0;
		for (int i = 0;i<pointsLeft.size();i++){
			if (pointsLeft.get(i).getX()>pointsLeft.get(rmL).getX()){
				rmL = i;
			}
		}
		for (int i = 0;i<pointsRight.size();i++){
			if (pointsRight.get(i).getX()<pointsRight.get(lmR).getX()){
				lmR = i;
			}
		}

		Point2D rightmostLeft = pointsLeft.get(rmL);
		Point2D leftmostRight = pointsRight.get(lmR);
		Line2D ab = new Line2D.Double(rightmostLeft, leftmostRight);
		Line2D abBot = new Line2D.Double(leftmostRight, rightmostLeft);

		/*
		 * walk through and find top and bottom indices
		 */

		int[] indicesTop = whileWalker(ab, pointsLeft, pointsRight, rmL, lmR);
		int[] indicesBottom = whileWalker(abBot, pointsRight, pointsLeft, lmR, rmL);

		/*
		 * add points going around in order
		 */

		boolean done = false;
		int i = indicesTop[1];
		while(!done){
			if (i==pointsRight.size()){
				i=0;
			}
			if (i==indicesBottom[0]){
				done = true;
			}else{
				
				points.add(pointsRight.get(i));
			}
			i++;
		}
		points.add(pointsRight.get(indicesBottom[0]));

		done = false;
		i = indicesBottom[1];
		while(!done){
			if (i==pointsLeft.size()){
				i=0;
			}
			if(i==indicesTop[0]){
				done = true;

			}else{
				
				points.add(pointsLeft.get(i));
			}
			i++;
		}
		points.add(pointsLeft.get(indicesTop[0]));

		return points;

	}

	private int[] whileWalker(Line2D ab, ArrayList<Point2D> pointsLeft, ArrayList<Point2D> pointsRight, int rmL, int lmR){
		int[] indices = new int[2];
		while (!tangToHull(ab, pointsLeft, pointsRight, rmL, lmR)){
			while (!tangToHull(ab, pointsLeft, null, rmL, -1)){
				if (rmL!=0){
					rmL = rmL-1;
				}else{
					rmL = pointsLeft.size()-1;
				}
				ab.setLine(pointsLeft.get(rmL),ab.getP2());
			}
			while (!tangToHull(ab, null, pointsRight, -1, lmR)){
				if (lmR!=pointsRight.size()-1){
					lmR = lmR+1;
				}else{
					lmR = 0;
				}
				ab.setLine(ab.getP1(),pointsRight.get(lmR));
			}
		}
		indices[0]=rmL;
		indices[1]=lmR;
		return indices;
	}

	private boolean tangToHull(Line2D tang, ArrayList<Point2D> pointsL, ArrayList<Point2D> pointsR, int rmL, int lmR){
		Point2D rightmostLeft = null;
		Point2D leftmostRight = null;

		if (pointsL != null){
			//Check A-1
			if (rmL!=0){
				rightmostLeft = pointsL.get(rmL-1);
			}else{
				rightmostLeft = pointsL.get(pointsL.size()-1);
			}
			if (tang.relativeCCW(rightmostLeft)<0){
				return false;
			}
			//check A+1
			if (rmL!=pointsL.size()-1){
				rightmostLeft = pointsL.get(rmL+1);
			}else{
				rightmostLeft = pointsL.get(0);
			}
			if (tang.relativeCCW(rightmostLeft)<0){
				return false;
			}



		}
		if (pointsR != null){
			//check B-1
			if (lmR!=0){
				leftmostRight = pointsR.get(lmR-1);
			}else{
				leftmostRight = pointsR.get(pointsR.size()-1);
			}
			if (tang.relativeCCW(leftmostRight)<0){
				return false;
			}
			//check B+1
			if (lmR!=pointsR.size()-1){
				leftmostRight = pointsR.get(lmR+1);
			}else{
				leftmostRight = pointsR.get(0);
			}
			if (tang.relativeCCW(leftmostRight)<0){
				return false;
			}

		}
		return true;
	}

	public void setPointsList(ArrayList<Point2D> pointsList) {
		this.pointsList = pointsList;
	}

	public ArrayList<Point2D> getPointsList() {
		return pointsList;
	}
}
