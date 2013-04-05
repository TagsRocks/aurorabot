import ms.aurora.api.methods.*;
import ms.aurora.api.methods.tabs.Bank;
import ms.aurora.api.methods.tabs.Inventory;
import ms.aurora.api.script.Script;
import ms.aurora.api.script.ScriptManifest;
import ms.aurora.api.util.Predicate;
import ms.aurora.api.wrappers.RSNPC;
import ms.aurora.api.wrappers.RSTile;
import ms.aurora.event.listeners.PaintListener;

import java.awt.*;
import java.util.LinkedList;

import static ms.aurora.api.util.Utilities.random;
import static ms.aurora.api.util.Utilities.sleepNoException;

/**
 * @author tobiewarburton
 */
@ScriptManifest(name = "Catherby Fisher",
        author = "warb0",
        version = 0.1,
        shortDescription = "fishes lobsters")
public class CatherbyFisher extends Script implements PaintListener {
    private RSNPC spot;

    private RSTile FISH_SPOT = new RSTile(2847, 3431);
    private RSTile BANK_SPOT = new RSTile(2809, 3440);

    private enum Action {
        BANK,
        WALK,
        FISH,
        WAIT
    }

    private Predicate<RSNPC> SPOT_PREDICATE = new Predicate<RSNPC>() {
        @Override
        public boolean apply(RSNPC object) {
            return object.getId() == 321;
        }
    };

    private Predicate<Inventory.InventoryItem> INV_PREDICATE = new Predicate<Inventory.InventoryItem>() {
        @Override
        public boolean apply(Inventory.InventoryItem object) {
            return object.getId() != 301;
        }
    };

    private int fishingStartXp;
    private long startTime;

    @Override
    public void onStart() {
        fishingStartXp = Skills.getExperience(Skills.Skill.FISHING);
        startTime = System.currentTimeMillis();
    }

    @Override
    public int tick() {
        if (spot == null) {
            spot = Npcs.get(SPOT_PREDICATE);
        }
        switch (getAction()) {
            case WALK:
                if (Inventory.isFull()) {
                    Walking.walkTo(BANK_SPOT);
                } else {
                    Walking.walkTo(FISH_SPOT);
                }
                break;
            case BANK:
                while (!Bank.isOpen()) {
                    Bank.open();
                    sleepNoException(300, 600);
                }
                while (Inventory.getCount() > 1) {
                    Inventory.InventoryItem item = Inventory.get(INV_PREDICATE);
                    if (item != null) {
                        item.applyAction("Store All");
                        sleepNoException(300, 600);
                    }
                }
                break;
            case FISH:
                if (spot != null) {
                    spot.applyAction("Cage");
                }
                break;
            case WAIT:
                break;
        }
        return random(200, 400);
    }

    private Action getAction() {
        if (Players.getLocal().isMoving() || Players.getLocal().getAnimation() != -1) {
            return Action.WAIT;
        } else {
            if (Inventory.isFull()) {
                if (Calculations.distance(Players.getLocal().getLocation(), BANK_SPOT) > 5) {
                    return Action.WALK;
                } else {
                    return Action.BANK;
                }
            } else {
                if (Calculations.distance(Players.getLocal().getLocation(), FISH_SPOT) > 15) {
                    return Action.WALK;
                } else {
                    return Action.FISH;
                }
            }
        }
    }

    @Override
    public void onRepaint(Graphics graphics) {
        long runtime = System.currentTimeMillis() - startTime;
        int secs = ((int) ((runtime / 1000) % 60));
        int mins = ((int) (((runtime / 1000) / 60) % 60));
        int hours = ((int) ((((runtime / 1000) / 60) / 60) % 60));
        int gained = Skills.getExperience(Skills.Skill.FISHING) - fishingStartXp;
        float xpSecond = 0;
        if ((mins > 0 || hours > 0 || secs > 0) && gained > 0) {
            xpSecond = ((float) gained / (float) (secs + (mins * 60) + (hours * 60 * 60)));
        }
        float xpMinute = xpSecond * 60;
        int xpHour = (int) xpMinute * 60;

        int x = 20, y = 20;
        java.util.List<String> itemList = new LinkedList<String>(); // order preserved
        itemList.add(String.format("Runtime: %02d:%02d:%02d", hours, mins, secs));
        itemList.add(String.format("XP Gain: %d", gained));
        itemList.add(String.format("XP Hour: %d", xpHour));
        graphics.setColor(new Color(61, 61, 61, 100));
        graphics.fillRoundRect(x - 10, y - 10, 200, itemList.size() * 20 + 10, 10, 10);
        graphics.setColor(Color.WHITE);
        y += 10;
        for (String item : itemList) {
            graphics.drawString(item, x, y);
            y += 20;
        }
    }
}
