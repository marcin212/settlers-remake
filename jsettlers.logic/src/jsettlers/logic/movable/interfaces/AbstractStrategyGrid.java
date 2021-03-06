package jsettlers.logic.movable.interfaces;

import java.io.Serializable;

import jsettlers.algorithms.path.IPathCalculatable;
import jsettlers.common.landscape.ELandscapeType;
import jsettlers.common.landscape.EResourceType;
import jsettlers.common.material.EMaterialType;
import jsettlers.common.material.ESearchType;
import jsettlers.common.movable.EDirection;
import jsettlers.common.position.ShortPoint2D;
import jsettlers.logic.map.newGrid.partition.manager.manageables.IManageableBearer;
import jsettlers.logic.map.newGrid.partition.manager.manageables.IManageableBricklayer;
import jsettlers.logic.map.newGrid.partition.manager.manageables.IManageableDigger;
import jsettlers.logic.map.newGrid.partition.manager.manageables.IManageableWorker;
import jsettlers.logic.movable.MovableStrategy;
import jsettlers.logic.player.Player;

/**
 * Defines methods needed by the {@link MovableStrategy}.
 * 
 * @author Andreas Eberle
 * 
 */
public abstract class AbstractStrategyGrid implements Serializable {
	private static final long serialVersionUID = 5560951888790865783L;

	public abstract void addJobless(IManageableBearer bearer);

	public abstract void removeJobless(IManageableBearer bearer);

	public abstract void addJobless(IManageableWorker worker);

	public abstract void removeJobless(IManageableWorker worker);

	public abstract void addJobless(IManageableDigger digger);

	public abstract void removeJobless(IManageableDigger digger);

	public abstract void addJobless(IManageableBricklayer bricklayer);

	public abstract void removeJobless(IManageableBricklayer bricklayer);

	/**
	 * Take a material from the stack at given position of given {@link EMaterialType}.
	 * 
	 * @param pos
	 * @param materialType
	 * @return true if the material was available<br>
	 *         false otherwise.
	 */
	public abstract boolean takeMaterial(ShortPoint2D pos, EMaterialType materialType);

	/**
	 * Drop a material of given type at given position.
	 * 
	 * @param pos
	 * @param materialType
	 */
	public abstract boolean dropMaterial(ShortPoint2D pos, EMaterialType materialType, boolean offer);

	public abstract float getResourceProbabilityAround(short x, short y, EResourceType type, int radius);

	public abstract void decreaseResourceAround(short x, short y, EResourceType resourceType, int radius, int amount);

	/**
	 * 
	 * @param position
	 * @param searchType
	 * @return in what direction you have to look from the given position to directly look at the given search type<br>
	 *         or null if the search type isn't a neighbor of the given position.
	 */
	public abstract EDirection getDirectionOfSearched(ShortPoint2D position, ESearchType searchType);

	/**
	 * 
	 * @param pos
	 * @param searchType
	 * @return true if the given position can be used to execute the search type.<br>
	 *         false if it can not
	 */
	public abstract boolean executeSearchType(ShortPoint2D pos, ESearchType searchType);

	/**
	 * Checks if the given position fits the given search type.
	 * 
	 * @param pathCalculateable
	 *            path requester
	 * @param pos
	 *            position
	 * @param searchType
	 *            search type to be checked
	 * @return true if the search type fits the given position.
	 */
	public abstract boolean fitsSearchType(IPathCalculatable pathCalculateable, ShortPoint2D pos, ESearchType searchType);

	public abstract EMaterialType popToolProductionRequest(ShortPoint2D pos);

	public abstract void placePigAt(ShortPoint2D pos, boolean place);

	/**
	 * 
	 * @param position
	 * @return true if there is a pig at given pos<br>
	 *         false otherwise.
	 */
	public abstract boolean hasPigAt(ShortPoint2D position);

	/**
	 * 
	 * @param position
	 * @return true if there is a pig on given position.
	 */
	public abstract boolean isPigAdult(ShortPoint2D position);

	/**
	 * Show smoke or remove it at the given position.
	 * 
	 * @param position
	 *            position to let the smoke appear.
	 * @param smokeOn
	 *            if true, smoke will be turned on, <br>
	 *            if false, it will be turned of.
	 */
	public abstract void placeSmoke(ShortPoint2D position, boolean smokeOn);

	/**
	 * checks if there can be put any more materials on the given position.
	 * 
	 * @param position
	 * @return
	 */
	public abstract boolean canPushMaterial(ShortPoint2D position);

	/**
	 * Checks if the given {@link EMaterialType} can be popped from the given position.
	 * 
	 * @param position
	 * @param material
	 * @return
	 */
	public abstract boolean canPop(ShortPoint2D position, EMaterialType material);

	public abstract byte getHeightAt(ShortPoint2D position);

	public abstract boolean isMarked(ShortPoint2D position);

	public abstract void setMarked(ShortPoint2D position, boolean marked);

	/**
	 * Changes the height of the given position towards the given targetHeight and changes the landscape type to {@link ELandscapeType}.FLATTENED
	 * 
	 * @param position
	 * @param targetHeight
	 */
	public abstract void changeHeightTowards(short x, short y, byte targetHeight);

	/**
	 * Changes the player at the given position to the given player.
	 * 
	 * @param pos
	 * @param player
	 */
	public abstract void changePlayerAt(ShortPoint2D pos, Player player);

	/**
	 * Gets the landscape type at the given position.
	 * 
	 * @param x
	 *            x coordinate of the position to get the landscape type.
	 * @param y
	 *            y coordinate of the position to get the landscape type.
	 * @return {@link ELandscapeType} at the given position.
	 */
	public abstract ELandscapeType getLandscapeTypeAt(short x, short y);

	/**
	 * Searches for an enemy around the position of the given movable in it's search radius.
	 * 
	 * @param centerPos
	 *            The center position to start the search.
	 * @param movable
	 *            The movable searching an enemy.
	 * @param searchRadius
	 *            The radius of the search for enemy attackables.
	 * @param includeTowers
	 *            If true, towers are included in the search, if false, only movables are searched.
	 * @return The closest enemy or null if none exists in the search radius.
	 */
	public abstract IAttackable getEnemyInSearchArea(ShortPoint2D centerPos, IAttackable movable, short searchRadius, boolean includeTowers);

	/**
	 * Adds an arrow object to the map flying from
	 * 
	 * @param attackedPos
	 *            Attacked position.
	 * @param shooterPos
	 *            Position of the shooter.
	 * @param shooterPlayerId
	 *            The id of the attacking player.
	 * @param hitStrength
	 *            Strength of the hit.
	 */
	public abstract void addArrowObject(ShortPoint2D attackedPos, ShortPoint2D shooterPos, byte shooterPlayerId, float hitStrength);

	public abstract boolean hasNoMovableAt(short x, short y);

	/**
	 * 
	 * @param position
	 *            The position to be checked.
	 * @return true if the position is on the grid, not blocked and free of other movables. <br>
	 *         false otherwise.
	 */
	public abstract boolean isFreePosition(ShortPoint2D position);

}
