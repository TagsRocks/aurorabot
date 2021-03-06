package ms.aurora.api.plugin.internal;

import ms.aurora.api.event.EventHandler;
import ms.aurora.api.event.PaintEvent;
import ms.aurora.api.methods.Objects;
import ms.aurora.api.methods.Players;
import ms.aurora.api.util.Predicate;
import ms.aurora.api.wrappers.GameObject;

import java.awt.*;

/**
 * @author rvbiljouw
 */
public class GroundObjectPaint {

    @EventHandler
    public void onRepaint(PaintEvent event) {
        Graphics2D graphics = event.getGraphics();
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
            return object.distance(Players.getLocal()) < 10
                    && object.getObjectType().equals(GameObject.ObjectType.GROUND_DECORATION);
        }
    };
}
