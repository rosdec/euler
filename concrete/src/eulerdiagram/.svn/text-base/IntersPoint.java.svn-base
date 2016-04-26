package eulerdiagram;

import java.awt.geom.Point2D;
import java.io.Serializable;

public class IntersPoint extends Point2D.Double implements Comparable<IntersPoint>, Serializable {

	private static final long serialVersionUID = 34284634391824027L;
	private static final double TOLERANCE = 1.0;
	
	private Ellipse a;
	private Ellipse b;

	public IntersPoint(double _x, double _y, Ellipse _a, Ellipse _b) {
		super(_x,_y);
		a=_a;
		b=_b;
	}
	
	public IntersPoint(PointXY p, Ellipse _a, Ellipse _b) {
		super(p.getX(), p.getY());
		a=_a;
		b=_b;
	}

	public IntersPoint(CenteredIntersPoint p, Ellipse e){
		super(p.getX(),p.getY());
		a=e;
		b=p.getGenEll();
	}
		
	public IntersPoint(double _x, double _y) {
		super(_x,_y);
		a=null;
		b=null;
	}

	public IntersPoint(PointXY p) {
		super(p.getX(), p.getY());
		a=null;
		b=null;
	}

	public IntersPoint(Point2D.Double p) {
		super(p.getX(), p.getY());
		a=null;
		b=null;
	}

	@Override
	public int compareTo(IntersPoint b) {
		if (Math.abs(b.x - x) > TOLERANCE) {
			if (b.x - x > 0)
				return 1;
			else
				return -1;
		}
		if (Math.abs(b.y - y) > TOLERANCE) {
			if (b.y - y > 0)
				return 1;
			else
				return -1;
		}
		return 0;
	}

	public boolean equals(Object _a) {
		IntersPoint b = (IntersPoint) _a;
		return (Math.abs(b.y - y) < TOLERANCE && Math.abs(b.x - x) < TOLERANCE);
	}

	public int hashCode(){
		int ix = (int) x;
		Integer xD = new Integer(ix);
		int iy = (int) y;
		Integer yD = new Integer(iy);
		return (xD.hashCode()^yD.hashCode());
	}

	public Ellipse getA() {
		return a;
	}

	public void setA(Ellipse a) {
		this.a = a;
	}

	public Ellipse getB() {
		return b;
	}

	public void setB(Ellipse b) {
		this.b = b;
	}

	
}
