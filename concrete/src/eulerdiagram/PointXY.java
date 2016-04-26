package eulerdiagram;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.Serializable;

public class PointXY extends Point2D.Double  implements Serializable {

	private static final long serialVersionUID = 8296935547763986720L;

	public PointXY(PointXY p) {
		super(p.getX(), p.getY());
	}

	public PointXY(Point p) {
		super(p.getX(), p.getY());
	}

	public PointXY(Point2D.Double p) {
		super(p.getX(), p.getY());
	}

	public PointXY(CenteredIntersPoint p) {
		super(p.getX(), p.getY());
	}

	public PointXY(double _x,  double _y) {
		super(_x, _y);
	}

	public double length() {
		return Math.sqrt(x*x + y*y);
	}

	public double dot(PointXY p) {
		return x*p.x + y*p.y;
	}

	public double cross(PointXY p) {
		return x*p.y - y*p.x;
	}

	public PointXY unit(){
		return this.divide( this.length() );
	}

	public PointXY divide(double scalar) {
		return new PointXY(x / scalar, y / scalar);
	}

	public PointXY subtract(PointXY p) {
		return new PointXY(x - p.x, y - p.y);
	}

	public static PointXY fromPoints(PointXY p1, PointXY  p2) {
		return new PointXY(p2.x - p1.x,  p2.y - p1.y);
	}

	public PointXY lerp(PointXY p, double t) {
		return new PointXY(x + (p.x - x) * t, y + (p.y - y) * t );
	}

	public PointXY rotate(double _t) {
		double m = Math.cos(_t);
		double n = Math.sin(_t);

		return new PointXY(x * m - y * n, x * n + y *m);
	}

	public PointXY rotate(double _t, PointXY p) {
		PointXY q = new PointXY(x - p.x, y - p.y).rotate(_t);

		q.x += p.x;
		q.y += p.y;

		return q;
	}


	//	/*****
	//	*
	//	*   unitEquals
	//	*
	//	*****/
	//	Vector2D.prototype.unitEquals = function() {
	//	    this.divideEquals( this.length() );
	//
	//	    return this;
	//	};


	//	/*****
	//	*
	//	*   add
	//	*
	//	*****/
	//	Vector2D.prototype.add = function(that) {
	//	    return new Vector2D(this.x + that.x, this.y + that.y);
	//	};


	//	/*****
	//	*
	//	*   addEquals
	//	*
	//	*****/
	//	Vector2D.prototype.addEquals = function(that) {
	//	    this.x += that.x;
	//	    this.y += that.y;
	//
	//	    return this;
	//	};



	//	/*****
	//	*
	//	*   subtractEquals
	//	*
	//	*****/
	//	Vector2D.prototype.subtractEquals = function(that) {
	//	    this.x -= that.x;
	//	    this.y -= that.y;
	//
	//	    return this;
	//	};


	//	/*****
	//	*
	//	*   multiply
	//	*
	//	*****/
	//	Vector2D.prototype.multiply = function(scalar) {
	//	    return new Vector2D(this.x * scalar, this.y * scalar);
	//	};


	//	/*****
	//	*
	//	*   multiplyEquals
	//	*
	//	*****/
	//	Vector2D.prototype.multiplyEquals = function(scalar) {
	//	    this.x *= scalar;
	//	    this.y *= scalar;
	//
	//	    return this;
	//	};


	//	/*****
	//	*
	//	*   divideEquals
	//	*
	//	*****/
	//	Vector2D.prototype.divideEquals = function(scalar) {
	//	    this.x /= scalar;
	//	    this.y /= scalar;
	//
	//	    return this;
	//	};


	//	/*****
	//	*
	//	*   perp
	//	*
	//	*****/
	//	Vector2D.prototype.perp = function() {
	//	    return new Vector2D(-this.y, this.x);
	//	};


	//	/*****
	//	*
	//	*   perpendicular
	//	*
	//	*****/
	//	Vector2D.prototype.perpendicular = function(that) {
	//	    return this.subtract(this.project(that));
	//	};


	//	/*****
	//	*
	//	*   project
	//	*
	//	*****/
	//	Vector2D.prototype.project = function(that) {
	//	    var percent = this.dot(that) / that.dot(that);
	//
	//	    return that.multiply(percent);
	//	};


	//	/*****
	//	*
	//	*   toString
	//	*
	//	*****/
	//	Vector2D.prototype.toString = function() {
	//	    return this.x + "," + this.y;
	//	};

}
