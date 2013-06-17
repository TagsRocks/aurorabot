package ms.aurora.api.plugin.internal;

import ms.aurora.api.methods.tabs.Inventory;
import ms.aurora.api.wrappers.WidgetItem;
import ms.aurora.event.listeners.PaintListener;

import java.awt.*;

/**
 * @author rvbiljouw
 */
public class InventoryPaint implements PaintListener {

    @Override
    public void onRepaint(Graphics graphics) {
        WidgetItem[] items = Inventory.getAll();
        for (WidgetItem item : items) {
            Rectangle loc = item.getArea();
            graphics.drawString(String.valueOf(item.getId()),
                    loc.x + 3, loc.y + 3);
            graphics.drawRect(loc.x, loc.y, loc.width, loc.height);
        }
    }

}
