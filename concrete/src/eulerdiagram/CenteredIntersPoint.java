package eulerdiagram;

import java.awt.geom.Point2D;
import java.io.Serializable;

public class CenteredIntersPoint extends Point2D.Double implements Comparable<CenteredIntersPoint>, Serializable {

	private static final long serialVersionUID = -4952885070116324303L;
	private double cx;
	private double cy; 
	private Ellipse genEll;
	private Zone zone; 

	public CenteredIntersPoint(double _x, double _y, double _cx, double _cy) {
		super(_x,_y);
		cx = _cx;
		cy = _cy;
		zone = new Zone();
	}

	public CenteredIntersPoint(double _x, double _y, double _cx, double _cy, Ellipse e) {
		super(_x,_y);
		cx = _cx;
		cy = _cy;
		zone = new Zone();
		genEll = e;
	}

	
	public CenteredIntersPoint(PointXY _p) {
		super(_p.getX(),_p.getY());
		zone = new Zone();
	}

	public double getCx() {
		return cx;
	}

	public void setCx(double cx) {
		this.cx = cx;
	}

	public double getCy() {
		return cy;
	}

	public void setCy(double cy) {
		this.cy = cy;
	}

	public Ellipse getGenEll() {
		return genEll;
	}

	public void setGenEll(Ellipse genEll) {
		this.genEll = genEll;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}


	@Override
	public int compareTo(CenteredIntersPoint b) {
		double phi_a = (double) Math.atan2(y - cy, x - cx);
		double phi_b = (double) Math.atan2(b.y - b.cy, b.x - b.cx);
		if (phi_a > phi_b)
			return 1;
		if (phi_a < phi_b)
			return -1;
		return 0;
	}

	public boolean equals(Object _a) {
		CenteredIntersPoint a = (CenteredIntersPoint) _a;
		return (x == a.getX() && y == a.getY() && cx == a.getCx() && cy == a.getCy());
	}

	public int hashCode(){
		java.lang.Double xD = new java.lang.Double(x);
		java.lang.Double yD = new java.lang.Double(y);
		java.lang.Double cxD = new java.lang.Double(cx);
		java.lang.Double cyD = new java.lang.Double(cy);
		return (xD.hashCode()^yD.hashCode()^cxD.hashCode()^cyD.hashCode());
	}
}
