package edu.uwec.cs.kieserjw.project2;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public interface ConvexHullFinder {
	public ArrayList<Point2D> computeHull(ArrayList<Point2D> points);
	
}

