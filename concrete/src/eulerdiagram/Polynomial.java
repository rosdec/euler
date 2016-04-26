package eulerdiagram;

import java.util.ArrayList;

public class Polynomial {

	private static final double TOLERANCE = 1e-6;
	private ArrayList<Double> coefs;

	public Polynomial(ArrayList<Double> _coefs) {
		coefs = _coefs;
	}

	public Polynomial(double a1, double a2, double a3, double a4, double a5) {
		coefs = new ArrayList<Double>();
		coefs.add(a5);
		coefs.add(a4);
		coefs.add(a3);
		coefs.add(a2);
		coefs.add(a1);
	}

	public Polynomial(double a1, double a2, double a3, double a4) {
		coefs = new ArrayList<Double>();
		coefs.add(a4);
		coefs.add(a3);
		coefs.add(a2);
		coefs.add(a1);
	}

	public Polynomial(double a1, double a2, double a3) {
		coefs = new ArrayList<Double>();
		coefs.add(a3);
		coefs.add(a2);
		coefs.add(a1);
	}

	public void simplify() {
		for (int i = getDegree(); i >= 0; i--) {
			if (Math.abs(coefs.get(i)) <= TOLERANCE)
				coefs.remove(i);
			else
				break;
		}
	}

	public int getDegree() {
		return coefs.size() - 1;
	}

	public ArrayList<Double> getRoots() {
		ArrayList<Double> result = null;
		simplify();
		switch (getDegree()) {
		case 0:
			result = new ArrayList<Double>();
			break;
		case 1:
			result = this.getLinearRoot();
			break;
		case 2:
			result = this.getQuadraticRoots();
			break;
		case 3:
			result = this.getCubicRoots();
			break;
		case 4:
			result = this.getQuarticRoots();
			break;
		default:
			result = new ArrayList<Double>(); // should try Newton's method
		// and/or bisection
		}
		return result;
	}

	public ArrayList<Double> getLinearRoot() {
		ArrayList<Double> result = new ArrayList<Double>();
		double a = coefs.get(1);
		if (a != 0)
			result.add(-coefs.get(0) / a);
		return result;
	}

	public ArrayList<Double> getQuadraticRoots() {
		ArrayList<Double> results = new ArrayList<Double>();
		if (getDegree() == 2) {
			double a = coefs.get(2);
			double b = coefs.get(1) / a;
			double c = coefs.get(0) / a;
			double d = b * b - 4 * c;
			if (d > 0) {
				double e = Math.sqrt(d);
				results.add(0.5 * (-b + e));
				results.add(0.5 * (-b - e));
			} else if (d == 0) {
				// really two roots with same value, but we only return one
				results.add((0.5 * -b));
			}
		}
		return results;
	}

	public ArrayList<Double> getCubicRoots() {
		ArrayList<Double> results = new ArrayList<Double>();
		if (getDegree() == 3) {
			double c3 = coefs.get(3);
			double c2 = coefs.get(2) / c3;
			double c1 = coefs.get(1) / c3;
			double c0 = coefs.get(0) / c3;
			double a = (3.0 * c1 - c2 * c2) / 3.0;
			double b = (2.0 * c2 * c2 * c2 - 9.0 * c1 * c2 + 27.0 * c0) / 27.0;
			double offset = c2 / 3.0;
			double discrim = b * b / 4.0 + a * a * a / 27.0;
			double halfB = b / 2.0;
			if (Math.abs(discrim) <= TOLERANCE)
				discrim = 0.0;
			if (discrim > 0.0) { // 1 real, 2 complex roots
				discrim = Math.sqrt(discrim);
				double tmp = discrim - halfB;
				double root = 0.0;
				if (tmp >= 0.0)
					root = Math.pow(tmp, 1 / 3.0);
				else
					root = -Math.pow(-tmp, 1 / 3.0);
				tmp = -halfB - discrim;
				if (tmp >= 0.0)
					root += Math.pow(tmp, 1 / 3.0);
				else
					root -= Math.pow(-tmp, 1 / 3.0);
				results.add((root - offset));
			} else if (discrim < 0.0) {
				double distance = Math.sqrt(-a / 3.0);
				double angle = Math.atan2(Math.sqrt(-discrim), -halfB) / 3.0;
				double cos = Math.cos(angle);
				double sin = Math.sin(angle);
				double sqrt3 = Math.sqrt(3);
				results.add((2 * distance * cos - offset));
				results.add((-distance * (cos + sqrt3 * sin) - offset));
				results.add((-distance * (cos - sqrt3 * sin) - offset));
			} else {
				double tmp;
				if (halfB >= 0.0)
					tmp = -Math.pow(halfB, 1 / 3.0);
				else
					tmp = Math.pow(-halfB, 1 / 3.0);
				results.add((2 * tmp - offset));
				// really should return next root twice, but we return only one
				results.add((-tmp - offset));
			}
		}
		return results;
	}

	public ArrayList<Double> getQuarticRoots() {
		ArrayList<Double> results = new ArrayList<Double>();
		if (getDegree() == 4) {
			double c4 = coefs.get(4);
			double c3 = coefs.get(3) / c4;
			double c2 = coefs.get(2) / c4;
			double c1 = coefs.get(1) / c4;
			double c0 = coefs.get(0) / c4;
			ArrayList<Double> resolveRoots = new Polynomial(1.0, -c2, c3 * c1
					- 4 * c0, -c3 * c3 * c0 + 4 * c2 * c0 - c1 * c1)
			.getCubicRoots();
			double y = resolveRoots.get(0);
			double discrim = c3 * c3 / 4.0 - c2 + y;
			if (Math.abs(discrim) <= TOLERANCE)
				discrim = 0.0;
			if (discrim > 0.0) {
				double e = Math.sqrt(discrim);
				double t1 = 3 * c3 * c3 / 4 - e * e - 2 * c2;
				double t2 = (4 * c3 * c2 - 8 * c1 - c3 * c3 * c3) / (4 * e);
				double plus = t1 + t2;
				double minus = t1 - t2;
				if (Math.abs(plus) <= TOLERANCE)
					plus = 0.0;
				if (Math.abs(minus) <= TOLERANCE)
					minus = 0.0;
				if (plus >= 0.0) {
					double f = Math.sqrt(plus);
					results.add((-c3 / 4 + (e + f) / 2));
					results.add((-c3 / 4 + (e - f) / 2));
				}
				if (minus >= 0.0) {
					double f = Math.sqrt(minus);
					results.add((-c3 / 4 + (f - e) / 2));
					results.add((-c3 / 4 - (f + e) / 2));
				}
			} else if (discrim < 0.0) {
				//System.out.println("discrim " + discrim);
			} else {
				double t2 = y * y - 4 * c0;
				if (t2 >= -TOLERANCE) {
					if (t2 < 0.0)
						t2 = 0.0;
					t2 = 2 * Math.sqrt(t2);
					double t1 = 3 * c3 * c3 / 4 - 2 * c2;
					if (t1 + t2 >= TOLERANCE) {
						double d = Math.sqrt(t1 + t2);
						results.add((-c3 / 4 + d / 2));
						results.add((-c3 / 4 - d / 2));
					}
					if (t1 - t2 >= TOLERANCE) {
						double d = Math.sqrt(t1 - t2);
						results.add((-c3 / 4 + d / 2));
						results.add((-c3 / 4 - d / 2));
					}
				}
			}
		}
		return results;
	}
}
