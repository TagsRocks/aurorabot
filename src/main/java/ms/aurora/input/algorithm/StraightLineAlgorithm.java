package ms.aurora.input.algorithm;

import ms.aurora.input.VirtualMouse;

import java.awt.*;

/**
 * @author rvbiljouw
 */
public class StraightLineAlgorithm implements VirtualMouse.MousePathAlgorithm {

    @Override
    public Point[] generatePath(Point origin, Point dest) {
        double dist = origin.distance(dest);
        double xStep = (dest.x - origin.x) / dist;
        double yStep = (dest.y - origin.y) / dist;
        Point[] path = new Point[(int) dist];
        if (path.length > 0) {
            path[0] = origin;
            path[(int) dist - 1] = dest;
            for (int i = 1; i < dist - 1; i++) {
                path[i] = new Point((int) (origin.x + (xStep * i)), (int) (origin.y + (yStep * i)));
            }
        }
        return path;
    }

}
