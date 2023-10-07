/**
 * Dot
 * Problem Set 2
 * @author Tayeb Mohammadi
 */
public class Dot implements Point2D {
	private double x, y;
	public Dot(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String toString() {
		return "("+x+","+y+")";
	}
}
