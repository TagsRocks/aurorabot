package ms.aurora.input.algorithm.impl;

import ms.aurora.input.algorithm.MousePathGenerator;

import java.awt.*;

/**
 * Date: 01/05/13
 * Time: 16:55
 *
 * @author A_C/Cov
 */
public class ZetaMouseGenerator implements MousePathGenerator {
    @Override
    public Point[] generate(Point a, Point b) {
        //DEF VARS
        int steps = (int) (Math.sqrt(a.distance(b)) * 3);

        int multiplier = 1;

        double xOffset = (b.x - a.x) / (Math.sqrt(a.distance(b)) * 3);
        double yOffset = (b.y - a.y) / (Math.sqrt(a.distance(b)) * 3);

        double x = Math.toRadians((180 / ((double) steps)));
        double y = Math.toRadians((180 / ((double) steps)));

        Point[] path = new Point[steps + 2];

        //RANDOMISE PATH VARS
        double waviness = 2.8;
        if (a.distance(b) < 120) { // less than 120px
            waviness = 0.9;
        }
        if ((Math.random()) >= 0.5) {
            x *= (int) Math.floor((Math.random() * waviness) + 1);
        }
        if ((Math.random()) >= 0.5) {
            y *= (int) Math.floor(Math.random() * waviness + 1);
        }
        if (Math.random() >= 0.5) {
            multiplier *= -1;
        }

        double offset = (Math.random() * (1.6 + Math.sqrt(steps)) + 6) + 2;

        // GEN PATH
        for (int i = 0; i < steps + 1; i++) {
            path[i] = new Point((a.x + ((int) (xOffset * i) + (multiplier * (int) (offset * Math.sin(x * i))))),
                    (a.y + ((int) (yOffset * i) + (multiplier * (int) (offset * Math.sin(y * i))))));
        }

        // RETURN PATH
        path[0] = a;
        path[path.length - 1] = b;
        return path;
    }
}
