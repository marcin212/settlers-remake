package jsettlers.common.movable;

import jsettlers.common.material.EMaterialType;
import jsettlers.common.player.IPlayerable;
import jsettlers.common.position.ILocatable;
import jsettlers.common.position.ShortPoint2D;
import jsettlers.common.selectable.ISelectable;
import jsettlers.common.sound.ISoundable;

/**
 * Defines a Movable actor that can be drawn by jsettlers.graphics
 * 
 * @author Andreas Eberle
 * 
 */
public interface IMovable extends IPlayerable, ISelectable, ILocatable, ISoundable {
	public EMovableType getMovableType();

	public EAction getAction();

	public EDirection getDirection();

	/**
	 * In general this method returns the progress of doing the action specified by {@link #getAction()}
	 * <p />
	 * for example:<br>
	 * if the movable is walking: this returns the progress of getting from one grid point to the other.<br>
	 * 
	 * @return The value is in the range of [0,1)
	 */
	public float getMoveProgress();

	/**
	 * This method returns the material the IMovable is currently carrying.
	 * <p />
	 * If the movable is just dropping the material ({@link #getAction()} == EAction.DROP) this method has to return the old EMaterialType until
	 * {@link #getAction()} changes to another EAction again (dropping is done)<br>
	 * If the movable is currently taking something, this method already returns the EMaterialType, that the movable want's to take.
	 * 
	 * @return
	 */
	public EMaterialType getMaterial();

	/**
	 * Get Position of a movable
	 * 
	 * @return position on grid
	 */
	@Override
	public ShortPoint2D getPos();

	/**
	 * Used to get health of a movable.
	 * 
	 * @return health of a movable
	 */
	public float getHealth();

	/**
	 * Returns alternating true and false on every step.
	 * 
	 * @return
	 */
	boolean isRightstep();

	/**
	 * Lets this movable stop or start its work.
	 * 
	 * @param stop
	 *            if true this selectable should stop working<br>
	 *            if false, it should stop working.
	 */
	void stopOrStartWorking(boolean stop);
}
