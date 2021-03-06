package jsettlers.algorithms.path.astar.normal;

import java.util.BitSet;

import jsettlers.algorithms.AlgorithmConstants;
import jsettlers.algorithms.path.IPathCalculatable;
import jsettlers.algorithms.path.InvalidStartPositionException;
import jsettlers.algorithms.path.Path;
import jsettlers.algorithms.path.astar.AbstractAStar;
import jsettlers.common.movable.EDirection;
import jsettlers.common.position.ShortPoint2D;

/**
 * AStar algorithm to find paths from A to B on a hex grid
 * 
 * @author Andreas Eberle
 * 
 */
public final class HexAStar extends AbstractAStar implements IAStarHeapable {
	private static final byte[] xDeltaArray = EDirection.getXDeltaArray();
	private static final byte[] yDeltaArray = EDirection.getYDeltaArray();

	private final IAStarPathMap map;

	private final short height;
	private final short width;

	private final BitSet openList;
	private final BitSet closedList;

	final float[] costsAndHeuristics;

	final int[] depthParentHeap;

	private final AbstractMinPriorityQueue open;

	float xFactor = 1.01f, yFactor = 1.02f;

	public HexAStar(IAStarPathMap map, short width, short height) {
		this.map = map;
		this.width = width;
		this.height = height;
		this.open = new AStarMinHeap(this, AlgorithmConstants.MINHEAP_INIT_NUMBER_OF_ELEMENTS);

		this.openList = new BitSet(width * height);
		this.closedList = new BitSet(width * height);
		this.costsAndHeuristics = new float[width * height * 2];

		this.depthParentHeap = new int[width * height * 3];
	}

	@Override
	public final Path findPath(IPathCalculatable requester, ShortPoint2D target) {
		ShortPoint2D pos = requester.getPos();
		return findPath(requester, pos.x, pos.y, target.x, target.y);
	}

	@Override
	public final Path findPath(IPathCalculatable requester, final short sx, final short sy, final short tx, final short ty) {
		final boolean blockedAtStart;
		if (!isInBounds(sx, sy)) {
			throw new InvalidStartPositionException("Start position is out of bounds!", sx, sy);
		} else if (!isInBounds(tx, ty) || isBlocked(requester, tx, ty) || map.getBlockedPartition(sx, sy) != map.getBlockedPartition(tx, ty)) {
			return null; // target can not be reached
		} else if (sx == tx && sy == ty) {
			return null;
		} else if (isBlocked(requester, sx, sy)) {
			blockedAtStart = true;
		} else {
			blockedAtStart = false;
		}

		float temp = xFactor; // swap the heuristic factors
		xFactor = yFactor;
		yFactor = temp;

		final int targetFlatIdx = getFlatIdx(tx, ty);

		closedList.clear();
		openList.clear();

		open.clear();
		boolean found = false;
		initStartNode(sx, sy, tx, ty);

		while (!open.isEmpty()) {
			int currFlatIdx = open.deleteMin();

			short x = getX(currFlatIdx);
			short y = getY(currFlatIdx);

			setClosed(x, y);

			if (targetFlatIdx == currFlatIdx) {
				found = true;
				break;
			}

			for (int i = 0; i < EDirection.NUMBER_OF_DIRECTIONS; i++) {
				short neighborX = (short) (x + xDeltaArray[i]);
				short neighborY = (short) (y + yDeltaArray[i]);

				if (isValidPosition(requester, neighborX, neighborY, blockedAtStart)) {
					int flatNeighborIdx = getFlatIdx(neighborX, neighborY);

					if (!closedList.get(flatNeighborIdx)) {
						float newCosts = costsAndHeuristics[getCostsIdx(currFlatIdx)] + map.getCost(x, y, neighborX, neighborY);
						if (openList.get(flatNeighborIdx)) {
							if (costsAndHeuristics[getCostsIdx(flatNeighborIdx)] > newCosts) {
								float oldRank = getRank(flatNeighborIdx);

								costsAndHeuristics[getCostsIdx(flatNeighborIdx)] = newCosts;
								// costsAndHeuristics[getHeuristicIdx(flatNeighborIdx)] = getHeuristicCost(neighborX, neighborY, tx, ty);
								depthParentHeap[getDepthIdx(flatNeighborIdx)] = depthParentHeap[getDepthIdx(currFlatIdx)] + 1;
								depthParentHeap[getParentIdx(flatNeighborIdx)] = currFlatIdx;
								open.increasedPriority(flatNeighborIdx, oldRank);
							}
						} else {
							costsAndHeuristics[getCostsIdx(flatNeighborIdx)] = newCosts;
							costsAndHeuristics[getHeuristicIdx(flatNeighborIdx)] = getHeuristicCost(neighborX, neighborY, tx, ty);
							depthParentHeap[getDepthIdx(flatNeighborIdx)] = depthParentHeap[getDepthIdx(currFlatIdx)] + 1;
							depthParentHeap[getParentIdx(flatNeighborIdx)] = currFlatIdx;
							openList.set(flatNeighborIdx);
							open.insert(flatNeighborIdx);

							map.markAsOpen(neighborX, neighborY);
						}
					}
				}
			}
		}

		if (found) {
			int pathlength = depthParentHeap[getDepthIdx(getFlatIdx(tx, ty))];
			Path path = new Path(pathlength);

			int idx = pathlength;
			int parentFlatIdx = targetFlatIdx;

			while (idx > 0) {
				idx--;
				path.insertAt(idx, getX(parentFlatIdx), getY(parentFlatIdx));
				parentFlatIdx = depthParentHeap[getParentIdx(parentFlatIdx)];
			}

			path.initPath();

			return path;
		}

		return null;
	}

	private static final int getHeuristicIdx(int flatIdx) {
		return flatIdx * 2 + 1;
	}

	private static final int getCostsIdx(int flatIdx) {
		return flatIdx * 2;
	}

	private static final int getDepthIdx(int flatIdx) {
		return 3 * flatIdx;
	}

	private static final int getParentIdx(int flatIdx) {
		return 3 * flatIdx + 1;
	}

	static final int getHeapArrayIdx(int flatIdx) {
		return 3 * flatIdx + 2;
	}

	private final void setClosed(short x, short y) {
		closedList.set(getFlatIdx(x, y));
		map.markAsClosed(x, y);
	}

	private final void initStartNode(short sx, short sy, short tx, short ty) {
		int flatIdx = getFlatIdx(sx, sy);
		open.insert(flatIdx);
		openList.set(flatIdx);
		depthParentHeap[getDepthIdx(flatIdx)] = 0;
		depthParentHeap[getParentIdx(flatIdx)] = -1;
		costsAndHeuristics[getCostsIdx(flatIdx)] = 0;
		costsAndHeuristics[getHeuristicIdx(flatIdx)] = getHeuristicCost(sx, sy, tx, ty);
	}

	private final boolean isValidPosition(IPathCalculatable requester, short x, short y, boolean blockedAtStart) {
		return isInBounds(x, y) && (!isBlocked(requester, x, y) || blockedAtStart);
	}

	private final boolean isInBounds(short x, short y) {
		return 0 <= x && x < width && 0 <= y && y < height;
	}

	private final boolean isBlocked(IPathCalculatable requester, short x, short y) {
		return map.isBlocked(requester, x, y);
	}

	private final int getFlatIdx(short x, short y) {
		return y * width + x;
	}

	private final short getX(int flatIdx) {
		return (short) (flatIdx % width);
	}

	private final short getY(int flatIdx) {
		return (short) (flatIdx / width);
	}

	@Override
	public final float getRank(int identifier) {
		return costsAndHeuristics[getCostsIdx(identifier)] + costsAndHeuristics[getHeuristicIdx(identifier)];
	}

	@Override
	public final int getHeapIdx(int identifier) {
		return depthParentHeap[getHeapArrayIdx(identifier)];
	}

	@Override
	public final void setHeapIdx(int identifier, int idx) {
		depthParentHeap[getHeapArrayIdx(identifier)] = idx;
	}

	private final float getHeuristicCost(final short sx, final short sy, final short tx, final short ty) {
		final float dx = (tx - sx) * xFactor;
		final float dy = (ty - sy) * yFactor;
		final float absDx = Math.abs(dx);
		final float absDy = Math.abs(dy);

		if (dx * dy > 0) { // dx and dy go in the same direction
			if (absDx > absDy) {
				return absDx;
			} else {
				return absDy;
			}
		} else {
			return absDx + absDy;
		}
	}
}
