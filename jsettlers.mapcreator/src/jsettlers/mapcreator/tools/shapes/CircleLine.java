package jsettlers.mapcreator.tools.shapes;

import jsettlers.common.map.shapes.MapCircle;
import jsettlers.common.position.ShortPoint2D;

/**
 * This is a line that ends in two circles.
 * 
 * @author michael
 */
public class CircleLine {

	private final short startx;
	private final short starty;
	private final short endx;
	private final short endy;
	private final double directionx;
	private final double directiony;
	private final double length;

	public CircleLine(ShortPoint2D start, ShortPoint2D end) {
		this.startx = start.x;
		this.starty = start.y;
		this.endx = end.x;
		this.endy = end.y;

		int nx = endx - startx;
		int ny = endy - starty;
		this.length = Math.hypot(nx, ny);
		// vector pointing in the direction of the line
		this.directionx = nx / length;
		this.directiony = ny / length;
	}

	/**
	 * Gets the distance to the center Line.
	 * 
	 * @param x
	 *            The x position
	 * @param y
	 *            THe y position.
	 */
	public double getDistanceOf(int x, int y) {
		int dx = x - startx;
		int dy = y - starty;

		double t = directionx * dx + directiony * dy;
		if (t < 0) {
			// check distance to start circle
			return MapCircle.getDistance(x, y, startx, starty);
		} else if (t > length) {
			// after end circle
			return MapCircle.getDistance(x, y, endx, endy);
		} else {
			int cx = startx + (int) (directionx * t);
			int cy = starty + (int) (directiony * t);
			return MapCircle.getDistance(x, y, cx, cy);
		}
	}
}
