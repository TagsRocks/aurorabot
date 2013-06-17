package ms.aurora.api.plugin.internal;

import ms.aurora.api.methods.Calculations;
import ms.aurora.api.methods.GroundItems;
import ms.aurora.api.methods.Players;
import ms.aurora.api.methods.Viewport;
import ms.aurora.api.util.Predicate;
import ms.aurora.api.wrappers.GroundItem;
import ms.aurora.event.listeners.PaintListener;

import java.awt.*;

/**
 * Date: 25/04/13
 * Time: 08:59
 *
 * @author A_C/Cov
 */
public class GroundItemPaint implements PaintListener {
    @Override
    public void onRepaint(Graphics graphics) {
        GroundItem[] items = GroundItems.getAll(ITEMS);
        for (GroundItem item: items) {
            Point screenLocation = Viewport.convert(item.getLocation());
            graphics.drawString(String.format("Id: %s | Stack: %s", item.getId(), item.getStackSize())
                    , screenLocation.x, screenLocation.y);
        }
    }


    private static final Predicate<GroundItem> ITEMS = new Predicate<GroundItem>() {
        @Override
        public boolean apply(GroundItem object) {
            return Calculations.distance(object.getLocation(), Players.getLocal().getLocation()) < 7;
        }
    };

}
