package jsettlers.logic.algorithms.borders;

/**
 * This interface specifies the grid needed by the BordersThread to calculate and set the borders on the grid.
 * 
 * @author Andreas Eberle
 * 
 */
public interface IBordersThreadGrid {

	/**
	 * Gives the player currently occupying the given position.
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @return the player that's currently occupying the given position.
	 */
	byte getPlayer(short x, short y);

	/**
	 * Sets if the given position is a border tile or not.
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param isBorder
	 *            if true, the given position is a border position<br>
	 *            if false, the given position is no border position.
	 */
	void setBorder(short x, short y, boolean isBorder);

}
