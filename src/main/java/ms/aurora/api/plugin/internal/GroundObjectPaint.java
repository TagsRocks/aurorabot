package ms.aurora.api.plugin.internal;

import ms.aurora.api.methods.Objects;
import ms.aurora.api.methods.Players;
import ms.aurora.api.util.Predicate;
import ms.aurora.api.wrappers.GameObject;
import ms.aurora.event.listeners.PaintListener;

import java.awt.*;

/**
 * @author rvbiljouw
 */
public class GroundObjectPaint implements PaintListener {

    @Override
    public void onRepaint(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        GameObject[] objects = Objects.getAll(RSOBJECT_PREDICATE);
        for (GameObject object : objects) {
            Point loc = object.getScreenLocation();
            graphics.setColor(Color.GREEN);
            graphics.drawString(String.valueOf(object.getId()), loc.x, loc.y);
            try {
                /*graphics.setColor(Color.RED);
                graphics.draw(RSModel.scaleHull(hull, 0.5));*/
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
    }

    private final static Predicate<GameObject> RSOBJECT_PREDICATE = new Predicate<GameObject>() {
        @Override
        public boolean apply(GameObject object) {
            return object.distance(Players.getLocal()) < 7
                    && object.getObjectType().equals(GameObject.ObjectType.GROUND_DECORATION);
        }
    };
}
