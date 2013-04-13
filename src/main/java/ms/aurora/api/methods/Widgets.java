package ms.aurora.api.methods;

import ms.aurora.api.Context;
import ms.aurora.api.wrappers.RSWidget;
import ms.aurora.api.wrappers.RSWidgetGroup;
import ms.aurora.rt3.Client;
import ms.aurora.rt3.Widget;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Widget related functions
 *
 * @author tobiewarburton
 * @author Rick
 */
public final class Widgets {

    /**
     * Gets an array of all the NON-NULL widget groups in the client.
     *
     * @return widget groups
     */
    public static RSWidgetGroup[] getAll() {
        Widget[][] cache = getClient().getWidgetCache();
        if (cache == null) return new RSWidgetGroup[0];

        List<RSWidgetGroup> groups = newArrayList();
        for (int i = 0; i < cache.length; i++) {
            RSWidgetGroup group = getWidgets(i);
            if (group != null) {
                groups.add(group);
            }
        }
        return groups.toArray(new RSWidgetGroup[groups.size()]);
    }

    /**
     * Gets a specific widget from the widget cache, which may be null.
     *
     * @param parent parent index
     * @param child  child index
     * @return widget or null
     */
    public static RSWidget getWidget(int parent, int child) {
        RSWidgetGroup group = getWidgets(parent);
        if (group != null && group.getWidgets()[child] != null) {
            return group.getWidgets()[child];
        }
        return null;
    }

    /**
     * Retrieves a widget group by it's index
     *
     * @param parent widget group index
     * @return widget group or null
     */
    public static RSWidgetGroup getWidgets(int parent) {
        if (getClient().getWidgetCache()[parent] != null) {
            return new RSWidgetGroup(Context.get(), getClient().getWidgetCache()[parent], parent);
        }
        return null;
    }

    /**
     * Gets all widgets containing the specified text
     *
     * @param predicate the string to search for
     * @return a list of all the RSWidget that contain the specified text
     */
    public static RSWidget[] getWidgetsWithText(String predicate) {
        final Rectangle rect = new Rectangle(5, 350, 510, 130);
        List<RSWidget> satisfied = new ArrayList<RSWidget>();
        for (RSWidgetGroup groups : getAll()) {
            for (RSWidget child : groups.getWidgets()) {
                if (child != null && child.getText().contains(predicate) &&
                        rect.contains(child.getCenterPoint())) {
                    System.out.println("Valid click here interface: " + child.getParent()+","+ child.getId());
                    satisfied.add(child);
                }
            }
        }
        return satisfied.toArray(new RSWidget[satisfied.size()]);
    }

    /**
     * Checks whether the "Click here to continue" interface is up.
     *
     * @return true if it's up
     */
    public static boolean canContinue() {
        RSWidget[] possible = getWidgetsWithText("Click here to continue");
        return possible.length > 0;
    }

    /**
     * Clicks the "Click here to continue" interface.
     */
    public static void clickContinue() {
        for (RSWidget widget : getWidgetsWithText("Click here to continue")) {
            widget.click(true);
        }
    }

    /**
     * INTERNAL: Retrieves the client for the current context.
     *
     * @return client
     */
    private static Client getClient() {
        return Context.getClient();
    }
}
