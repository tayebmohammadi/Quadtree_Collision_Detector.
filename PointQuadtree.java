import java.util.ArrayList;
import java.util.List;

/**
 * PointQuadtree
 * Problem Set 2
 * @author Tayeb Mohammadi
 */
public class PointQuadtree<E extends Point2D>{
	private E point;							// the point anchoring this node
	private int x1, y1;							// upper-left corner of the region
	private int x2, y2;							// bottom-right corner of the region
	private PointQuadtree<E> c1, c2, c3, c4;	// children

	/**
	 * Initializes a leaf quadtree, holding the point in the rectangle
	 */
	public PointQuadtree(E point, int x1, int y1, int x2, int y2) {
		this.point = point;
		this.x1 = x1; this.y1 = y1; this.x2 = x2; this.y2 = y2;
	}

	// Getters
	public E getPoint() {
		return point;
	}

	public int getX1() {
		return x1;
	}

	public int getY1() {
		return y1;
	}

	public int getX2() {
		return x2;
	}

	public int getY2() {
		return y2;
	}

	/**
	 * Returns the child (if any) at the given quadrant, 1-4
	 * @param quadrant	1 through 4
	 */
	public PointQuadtree<E> getChild(int quadrant) {
		if (quadrant==1) return c1;
		if (quadrant==2) return c2;
		if (quadrant==3) return c3;
		if (quadrant==4) return c4;
		return null;
	}

	/**
	 * Returns whether or not there is a child at the given quadrant, 1-4
	 * @param quadrant	1 through 4
	 */
	public boolean hasChild(int quadrant) {
		return (quadrant==1 && c1!=null) || (quadrant==2 && c2!=null) || (quadrant==3 && c3!=null) || (quadrant==4 && c4!=null);
	}

	/**
	 * Inserts the point into the tree
	 */
	public void insert(E p2) {
		// TODO: YOUR CODE HERE

			//If point is in Quadrant 1
			if (p2.getX() > this.point.getX() && p2.getY() < this.point.getY()){
					if(getChild(1)!= null){
						c1.insert(p2); //if there are children in quadrant 1, call insert recursively
					}
					else {
						//set child to a new tree holding just p2
						c1 = new PointQuadtree<>(p2, (int)this.point.getX(), y1, x2, (int)this.point.getY());
					}
				}
			//If point is in Quadrant 2
			else if (p2.getX() < this.point.getX() && p2.getY() < this.point.getY()){
					if(getChild(2)!= null){
						c2.insert(p2); //if there are children in quadrant 2, call insert recursively
					}
					else {
						//set child to a new tree holding just p2
						c2 = new PointQuadtree<>(p2, x1, y1, (int)this.point.getX(), (int)this.point.getY());
					}
			}
			//If point is in Quadrant 3
			else if (p2.getX() < this.point.getX() && p2.getY() > this.point.getY()) {
				if (getChild(3) != null) {
					c3.insert(p2);//if there are children in quadrant 3, call insert recursively
				} else {
					//set child to a new tree holding just p2
					c3 = new PointQuadtree<>(p2, x1, (int) (this.point.getY()), (int) (this.point.getX()), y2);
				}

			}
			//If point is in Quadrant 4
			else if (p2.getX() > this.point.getX() && p2.getY() > this.point.getY()){
				if(getChild(4)!=null){
					c4.insert(p2); //if there are children in quadrant 4, call insert recursively
			}	else {
					//set child to a new tree holding just p2
					c4 = new PointQuadtree<>(p2, (int)this.point.getX(),  (int)this.point.getY(), x2, y2);
			}
		}

	}
	
	/**
	 * Finds the number of points in the quadtree (including its descendants)
	 */
	public int size() {
		// TODO: YOUR CODE HERE
		List<E> listOfAll = allPoints(); //initialize a list to store all points
		return listOfAll.size();//return the size of the list
	}
	
	/**
	 * Builds a list of all the points in the quadtree (including its descendants)
	 */
	public List<E> allPoints() {
		// TODO: YOUR CODE HERE
		List<E> pointsList = new ArrayList<E>(); //initialize a list called to store points from helper func
		addToPointList(pointsList); //call helper func to add points recursively
		return pointsList; //return the list of points
	}	

	/**
	 * Uses the quadtree to find all points within the circle
	 * @param cx	circle center x
	 * @param cy  	circle center y
	 * @param cr  	circle radius
	 * @return    	the points in the circle (and the qt's rectangle)
	 */


	public List<E> findInCircle(double cx, double cy, double cr) {
		// TODO: YOUR CODE HERE
		List<E> pointsInCircle = new ArrayList<E>(); // initialize a list to store points inside a circle of center (cx, cy)
		circleChecker(cx, cy, cr, pointsInCircle); // call circleChecker method to check recursively
	return pointsInCircle;
	}

	// TODO: YOUR CODE HERE for any helper methods.
	public void addToPointList (List<E> listOfPoints){

			listOfPoints.add(point); // add point

			//recursively look for more points in respective quadrants, if any.
			if(hasChild(1)) c1.addToPointList(listOfPoints);
			else if(hasChild(2)) c2.addToPointList(listOfPoints);
			else if(hasChild(3)) c3.addToPointList(listOfPoints);
			else if(hasChild(4)) c4.addToPointList(listOfPoints);

	}

	public void circleChecker(double cx, double cy, double cr, List<E> pointsInCircle){
		//check if circle intersects rectangle
		if (Geometry.circleIntersectsRectangle(cx, cy, cr, this.getX1(), this.getY1(), this.getX2(), this.getY2())){
			//check if points lie in a circle
			if(Geometry.pointInCircle(point.getX(), point.getY(), cx, cy, cr)){
				pointsInCircle.add(this.point); //add points to pointsInCircle list.
			}
			//recursively look for points in respective quadrants if there are any children.
			if(hasChild(1)){
				c1.circleChecker(cx, cy, cr, pointsInCircle);
			}
			if(hasChild(2)){
				c2.circleChecker(cx, cy, cr, pointsInCircle);
			}
			if(hasChild(3)){
				c3.circleChecker(cx, cy, cr, pointsInCircle);
			}
			if(hasChild(4)){
				c4.circleChecker(cx, cy, cr, pointsInCircle);
			}
		}
	}

}
