package eulerdiagram;

import java.awt.Color;
import java.awt.geom.GeneralPath;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeSet;

public class Ellipse implements Comparable<Ellipse>, Serializable {

	private static final long serialVersionUID = -7347518846438929487L;
	
	
	private double a;
	private double b;
	private double c;
	private double d;
	private double phi;
	private String name;
	private Color color;
	private boolean selected;
	
	private IntersPoint fakeIntersPoint=null;
	private TreeSet<CenteredIntersPoint> intPoints;

	public Ellipse(double _a, double _b, double w, double h, double _phi) {
		a = _a;
		b = _b;
		c = w ;
		d = h ;
		setPhi(_phi);
		
		color = ColorUtils.getColor();
	}

		
	public Ellipse(Ellipse e) {
		a = e.a;
		b = e.b;
		c = e.c ;
		d = e.d ;
		setPhi(e.phi);
		color = e.color;
		name=e.name;
		fakeIntersPoint=e.fakeIntersPoint;
		selected=e.selected;
	}

	public Ellipse() {
		c = 10.0;
		d = 15.0;
		setPhi(0.0);
		color =  ColorUtils.getColor();
	}

	public void setAll(double _a, double _b, double w, double h, double _phi) {
		a = _a;
		b = _b;
		c = w ;
		d = h ;
		setPhi(_phi);
	}

	public void setCenter(double _a, double _b) {
		a = _a;
		b = _b;
	}
	
	
	public TreeSet<IntersPoint> findIntersectionsEllipseEllipse(Ellipse fEllipse) {
		double a1 = a;
		double b1 = b;
		double c1 = c;
		double d1 = d;
		double m1 = Math.cos(phi);
		double n1 = Math.sin(phi);

		double a2 = fEllipse.getA();
		double b2 = fEllipse.getB();
		double c2 = fEllipse.getC();
		double d2 = fEllipse.getD();
		double m2 = Math.cos(fEllipse.getPhi());
		double n2 = Math.sin(fEllipse.getPhi());

		/*
		 * cx2= ddmm+ccnn; cxy= 2mn(dd-cc); cy2= ddnn+ccmm; cx=
		 * -2(ddm(ma+nb)+ccn(na-mb)); cy = -2(ddn(ma+nb)-ccm(na-mb)); cn =
		 * dd(mmaa+nnbb+2mnab-cc)+cc(nnaa+mmbb-2mnab);
		 */

		double[] a = {
				d1 * d1 * m1 * m1 + c1 * c1 * n1 * n1,
				2 * m1 * n1 * (d1 * d1 - c1 * c1),
				d1 * d1 * n1 * n1 + c1 * c1 * m1 * m1,
				-2
				* (d1 * d1 * m1 * (m1 * a1 + n1 * b1) + c1 * c1 * n1
						* (n1 * a1 - m1 * b1)),
						-2
						* (d1 * d1 * n1 * (m1 * a1 + n1 * b1) - c1 * c1 * m1
								* (n1 * a1 - m1 * b1)),
								d1
								* d1
								* (m1 * m1 * a1 * a1 + n1 * n1 * b1 * b1 + 2 * m1 * n1
										* a1 * b1 - c1 * c1)
										+ c1
										* c1
										* (n1 * n1 * a1 * a1 + m1 * m1 * b1 * b1 - 2 * m1 * n1
												* a1 * b1) };
		double[] b = {
				d2 * d2 * m2 * m2 + c2 * c2 * n2 * n2,
				2 * m2 * n2 * (d2 * d2 - c2 * c2),
				d2 * d2 * n2 * n2 + c2 * c2 * m2 * m2,
				-2
				* (d2 * d2 * m2 * (m2 * a2 + n2 * b2) + c2 * c2 * n2
						* (n2 * a2 - m2 * b2)),
						-2
						* (d2 * d2 * n2 * (m2 * a2 + n2 * b2) - c2 * c2 * m2
								* (n2 * a2 - m2 * b2)),
								d2
								* d2
								* (m2 * m2 * a2 * a2 + n2 * n2 * b2 * b2 + 2 * m2 * n2
										* a2 * b2 - c2 * c2)
										+ c2
										* c2
										* (n2 * n2 * a2 * a2 + m2 * m2 * b2 * b2 - 2 * m2 * n2
												* a2 * b2) };

		Polynomial yPoly = bezout(a, b);
		ArrayList<java.lang.Double> yRoots = yPoly.getRoots();

		double epsilon = 0.0005;
		double norm0 = (a[0] * a[0] + 2 * a[1] * a[1] + a[2] * a[2]) * epsilon;
		double norm1 = (b[0] * b[0] + 2 * b[1] * b[1] + b[2] * b[2]) * epsilon;
		TreeSet<IntersPoint> results = new TreeSet<IntersPoint>();
		for (int y = 0; y < yRoots.size(); y++) {
			Polynomial xPoly = new Polynomial(a[0],
					a[3] + yRoots.get(y) * a[1], a[5] + yRoots.get(y)
					* (a[4] + yRoots.get(y) * a[2]));
			ArrayList<java.lang.Double> xRoots = xPoly.getRoots();

			for (int x = 0; x < xRoots.size(); x++) {
				double test = (a[0] * xRoots.get(x) + a[1] * yRoots.get(y) + a[3])
				* xRoots.get(x)
				+ (a[2] * yRoots.get(y) + a[4])
				* yRoots.get(y) + a[5];
				if (Math.abs(test) < norm0) {
					test = (b[0] * xRoots.get(x) + b[1] * yRoots.get(y) + b[3])
					* xRoots.get(x) + (b[2] * yRoots.get(y) + b[4])
					* yRoots.get(y) + b[5];
					if (Math.abs(test) < norm1) {
						results.add(new IntersPoint(xRoots.get(x), yRoots
								.get(y),null,null));
					}
				}
			}
		}
		return results;
	}
	
	

	private Polynomial bezout(double[] e1, double[] e2) {
		double AB = e1[0] * e2[1] - e2[0] * e1[1];
		double AC = e1[0] * e2[2] - e2[0] * e1[2];
		double AD = e1[0] * e2[3] - e2[0] * e1[3];
		double AE = e1[0] * e2[4] - e2[0] * e1[4];
		double AF = e1[0] * e2[5] - e2[0] * e1[5];
		double BC = e1[1] * e2[2] - e2[1] * e1[2];
		double BE = e1[1] * e2[4] - e2[1] * e1[4];
		double BF = e1[1] * e2[5] - e2[1] * e1[5];
		double CD = e1[2] * e2[3] - e2[2] * e1[3];
		double DE = e1[3] * e2[4] - e2[3] * e1[4];
		double DF = e1[3] * e2[5] - e2[3] * e1[5];
		double BFpDE = BF + DE;
		double BEmCD = BE - CD;

		return new Polynomial(AB * BC - AC * AC, AB * BEmCD + AD * BC - 2 * AC
				* AE, AB * BFpDE + AD * BEmCD - AE * AE - 2 * AC * AF, AB * DF
				+ AD * BFpDE - 2 * AE * AF, AD * DF - AF * AF);
	}


	public boolean hasContourPoint(PointXY p) {
		double cx2, cy2, cxy;
		double m = Math.cos(phi);
		double n = Math.sin(phi);
		double epsilon = 0.03;
		/*
		 * cx2= ddmm+ccnn; cxy= 2mn(dd-cc); cy2= ddnn+ccmm; cx=
		 * -2(ddm(ma+nb)+ccn(na-mb)); cy = -2(ddn(ma+nb)-ccm(na-mb)); cn =
		 * dd(mmaa+nnbb+2mnab-cc)+cc(nnaa+mmbb-2mnab);
		 */

		cx2 = d * d * m * m + c * c * n * n;
		cxy = 2 * m * n * (d * d - c * c);
		cy2 = d * d * n * n + c * c * m * m;
		
		double norm = (cx2 * cx2 + 2 * cxy * cxy + cy2 * cy2) * epsilon;
		if (distance(p) < norm)
			return true;
		else
			return false;
	}

	public double distance(PointXY p) {
		double cx2, cy2, cx, cy, cxy, cn, eval;
		double m = Math.cos(phi);
		double n = Math.sin(phi);
		/*
		 * cx2= ddmm+ccnn; cxy= 2mn(dd-cc); cy2= ddnn+ccmm; cx=
		 * -2(ddm(ma+nb)+ccn(na-mb)); cy = -2(ddn(ma+nb)-ccm(na-mb)); cn =
		 * dd(mmaa+nnbb+2mnab-cc)+cc(nnaa+mmbb-2mnab);
		 */

		cx2 = d * d * m * m + c * c * n * n;
		cxy = 2 * m * n * (d * d - c * c);
		cy2 = d * d * n * n + c * c * m * m;
		cx = -2 * (d * d * m * (m * a + n * b) + c * c * n * (n * a - m * b));
		cy = -2 * (d * d * n * (m * a + n * b) - c * c * m * (n * a - m * b));
		cn = d * d
		* (m * m * a * a + n * n * b * b + 2 * m * n * a * b - c * c)
		+ c * c * (n * n * a * a + m * m * b * b - 2 * m * n * a * b);

		eval = (cx2 * p.getX() * p.getX()) + (cxy * p.getY() * p.getX())
		+ (cy2 * p.getY() * p.getY()) + (cx * p.getX())
		+ (cy * p.getY()) + cn;

		return Math.abs(eval);
	}
	
	public boolean containsPoint(IntersPoint p){
		return containsPoint(p.getX(),p.getY());
	}
	
	public boolean containsPoint(PointXY p){
		return containsPoint(p.getX(),p.getY());
	}
	
	public boolean containsPoint(double x, double y) {
		double cx2, cy2, cx, cy, cxy, cn, eval;
		double m = Math.cos(phi);
		double n = Math.sin(phi);

		/*
		 * cx2= ddmm+ccnn; cxy= 2mn(dd-cc); cy2= ddnn+ccmm; cx=
		 * -2(ddm(ma+nb)+ccn(na-mb)); cy = -2(ddn(ma+nb)-ccm(na-mb)); cn =
		 * dd(mmaa+nnbb+2mnab-cc)+cc(nnaa+mmbb-2mnab);
		 */

		cx2 = d * d * m * m + c * c * n * n;
		cxy = 2 * m * n * (d * d - c * c);
		cy2 = d * d * n * n + c * c * m * m;
		cx = -2 * (d * d * m * (m * a + n * b) + c * c * n * (n * a - m * b));
		cy = -2 * (d * d * n * (m * a + n * b) - c * c * m * (n * a - m * b));
		cn = d * d
		* (m * m * a * a + n * n * b * b + 2 * m * n * a * b - c * c)
		+ c * c * (n * n * a * a + m * m * b * b - 2 * m * n * a * b);
		eval = (cx2 * x * x) + (cxy * y * x)
		+ (cy2 * y * y) + (cx * x)
		+ (cy * y) + cn;

		if (eval > 0)
			return false;
		else
			return true;
	}



	/*
	 * getPoint = getT^(-1)
	 *  p = getPoint(getT(p)) and   t= getT(getPoint(t)) 
	 */

	public double getT(PointXY p) {
		return getT(p.getX(),p.getY());
	}
	
	public double getT(CenteredIntersPoint p) {
		return getT(p.getX(),p.getY());
	}

	public double getT(double x, double y) {
		return Math.atan2(y - b, x - a);
	}

	
	public PointXY getEllipseLabelPoint(){
		PointXY p = getPoint(11.0*Math.PI/6.0);
		p.x+=6;
		p.y-=6;
		return p;
	}
	
	public PointXY getPoint(double _t) {
		double max = d * 4;
		if (c > d)
			max = c * 4;
		PointXY a1 = new PointXY(a, b);
		PointXY a2 = new Ellipse(a, b, max, max, 0.0).getPointC(_t - phi);

		PointXY origin = new PointXY(a, b);
		PointXY dir = PointXY.fromPoints(new PointXY(a, b), a2);
		PointXY center = new PointXY(a, b);
		PointXY diff = origin.subtract(center);
		PointXY mDir = new PointXY(dir.x / (c * c), dir.y / (d * d));
		PointXY mDiff = new PointXY(diff.x / (c * c), diff.y / (d * d));

		double a = dir.dot(mDir);
		double b = dir.dot(mDiff);
		double c = diff.dot(mDiff) - 1.0;
		double d = b * b - a * c;

		if (d > 0) {
			double root = Math.sqrt(d);
			double t_b = (-b + root) / a;

			return new PointXY(a1.lerp(a2, t_b)).rotate(phi, a1);
		}

		System.out.print("Kinky error in getPoint.");
		return null;
	}

	public GeneralPath ellipsePolygon() {
		GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD,  (int) Math.PI*20);
		polygon.moveTo(getPointC(0.0).getX(), getPointC(0.0).getY());
		for (double t = 0.1; t < 6.28; t += 0.1) {
			polygon.lineTo(getPointC(t).getX(), getPointC(t).getY());
		}
		polygon.closePath();
		return polygon;
	}
	
	public PointXY getPointC(double t) {
		PointXY p = new PointXY(a + c * Math.cos(t)
				* Math.cos(phi) - d * Math.sin(t) * Math.sin(phi), b + d
				* Math.sin(t) * Math.cos(phi) + c * Math.cos(t) * Math.sin(phi));
		return p;
	}

	public void assignSecondEdge(double startX, double startY,  double endX, double endY) {
		double tmpw = Math.sqrt((double) (endX - startX) * (endX - startX)
				+ (endY - startY) * (endY - startY));

		double phi = (double) Math.atan2(endY - startY, endX - startX);
		setAll((startX + endX) * 0.5, (startY + endY) * 0.5, tmpw/2.0, d, phi);
			
	}
	
	public void assignFirstEdge(Double _x1, Double _y1) {
		a = _x1;
		b = _y1;
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public double getC() {
		return c;
	}

	public void setC(double c) {
		this.c = c;
	}

	public double getD() {
		return d;
	}

	public void setD(double d) {
		this.d = d;
	}

	public double getPhi() {
		return phi;
	}

	public void setPhi(double _phi) {
		phi = _phi;
		if (phi < 0)
			phi = phi + Math.PI;	
	}

	public TreeSet<CenteredIntersPoint> getIntPoints() {
		return intPoints;
	}

	public void setIntPoints(TreeSet<CenteredIntersPoint> intPoints) {
		this.intPoints = intPoints;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Color getCol() {
		return color;
	}

	public void setCol(Color col) {
		this.color = col;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public IntersPoint getFakeIntersPoint() {
		return fakeIntersPoint;
	}

	public void setFakeIntersPoint(IntersPoint fakeIntersPoint) {
		this.fakeIntersPoint = fakeIntersPoint;
	}
	
	@Override
	public int compareTo(Ellipse f) {
		if (Math.round(f.a) - Math.round(a) > 0)
			return 1;
		if (Math.round(f.a) - Math.round(a) < 0)
			return -1;
		if (Math.round(f.b) - Math.round(b) > 0)
			return 1;
		if (Math.round(f.b) - Math.round(b) < 0)
			return -1;
		if (Math.round(f.c) - Math.round(c) > 0)
			return 1;
		if (Math.round(f.c) - Math.round(c) < 0)
			return -1;
		if (Math.round(f.d) - Math.round(d) > 0)
			return 1;
		if (Math.round(f.d) - Math.round(d) < 0)
			return -1;
		if (Math.round(f.phi) - Math.round(phi) > 0)
			return 1;
		if (Math.round(f.phi) - Math.round(phi) < 0)
			return -1;
		return 0;
	}

	public boolean equals(Object _a) {
		Ellipse f = (Ellipse) _a;
		return (Math.round(f.a) == Math.round(a) && Math.round(f.b) == Math.round(b) 
				&& Math.round(f.c) == Math.round(c) && Math.round(f.d) == Math.round(d)
				&& Math.round(f.phi) == Math.round(phi));
	}

	public int hashCode() {
		Double aD = new Double(a);
		Double bD = new Double(b);
		Double cD = new Double(c);
		Double dD = new Double(d);
		Double phiD = new Double(phi);
		return (aD.hashCode() ^ bD.hashCode() ^ cD.hashCode() ^ dD.hashCode() ^ phiD
				.hashCode());
	}
}
