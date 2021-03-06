package ms.aurora.api.plugin.internal;

import ms.aurora.api.event.EventBus;
import ms.aurora.api.event.EventHandler;
import ms.aurora.api.event.PaintEvent;
import ms.aurora.api.methods.Minimap;
import ms.aurora.api.methods.Players;
import ms.aurora.api.wrappers.Player;

import java.awt.*;

import static ms.aurora.api.Context.getClient;

/**
 * @author rvbiljouw
 */
public class PositionPaint {

    @EventHandler
    public void onRepaint(PaintEvent event) {
        Graphics2D graphics = event.getGraphics();
        Player player = null;
        try {
            player = Players.getLocal();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (player != null) {
            Point loc = player.getScreenLocation();
            Color original = graphics.getColor();
            graphics.setColor(Color.CYAN);
            graphics.drawString(player.getLocation().toString(),
                    loc.x, loc.y);
            Point minimapLoc = Minimap.convert(player.getLocalX(), player.getLocalY());
            graphics.drawOval(minimapLoc.x - 1, minimapLoc.y - 1, 3, 3);
            graphics.drawString(getClient().getBaseX() + ", " + getClient().getBaseY(), 10, 100);
            graphics.setColor(original);
        }
    }

}
