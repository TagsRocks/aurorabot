package ms.aurora.api.pathfinding.impl;

import ms.aurora.api.methods.Players;
import ms.aurora.api.pathfinding.AStarPathFinder;
import ms.aurora.api.pathfinding.ClosestHeuristic;
import ms.aurora.api.pathfinding.Path;

import java.awt.*;

import static ms.aurora.api.Context.getClient;

/**
 * @author rvbiljouw
 */
public class RSMapPathFinder {

    public static final int LAZY = 0;
    public static final int FULL = 1;
    private AStarPathFinder pathFinder;

    public RSMapPathFinder() {
        reload();
    }

    private void reload() {
        pathFinder = new AStarPathFinder(new RSMap(), 600, true, new ClosestHeuristic());
    }

    public Path getPath(int destX, int destY, int full) {
        reload();
        Point destPoint = new Point(destX, destY);
        return getPath(getClient().getPlane(), Players.getLocal().getX(),
                Players.getLocal().getY(),
                destPoint.x,
                destPoint.y, full);
    }

    public Path getPath(int plane, int startX, int startY, int destX, int destY, int full) {
        return pathFinder.findPath(plane, startX, startY, destX, destY, full);
    }
}
