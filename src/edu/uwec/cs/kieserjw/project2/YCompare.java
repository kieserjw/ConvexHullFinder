package edu.uwec.cs.kieserjw.project2;

import java.awt.geom.Point2D;
import java.util.Comparator;

public class YCompare implements Comparator<Point2D> {

	public int compare(Point2D pt1, Point2D pt2) {
		if (pt1.getX()<pt2.getX()) return -1;
		if (pt1.getX()>pt2.getX()) return 1;
		return 0;
	}

}