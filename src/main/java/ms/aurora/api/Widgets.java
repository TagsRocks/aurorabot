package ms.aurora.api;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import ms.aurora.api.rt3.Widget;
import ms.aurora.api.wrappers.RSWidget;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Lists.newArrayList;

/**
 * @author tobiewarburton
 * @author rvbiljouw
 */
public final class Widgets {
    private final ClientContext ctx;

    public Widgets(ClientContext ctx) {
        this.ctx = ctx;
    }

    public RSWidget[][] getAll() {
        Widget[][] cache = ctx.getClient().getWidgetCache();
        if (cache == null) return new RSWidget[0][];
        List<RSWidget[]> widgets = new ArrayList<RSWidget[]>();
        for (Widget[] tmp : cache) {
            if (tmp == null) continue;
            widgets.add(filter(transform(newArrayList(tmp), transform), Predicates.notNull()).toArray(new RSWidget[]{}));
        }
        return widgets.toArray(new RSWidget[][]{});
    }

    public RSWidget getWidget(int parent, int child) {
        Widget[][] cache = ctx.getClient().getWidgetCache();
        Widget[] p = cache[parent];
        if (p == null) return null;
        Widget c = p[child];
        if (c == null) return null;
        return new RSWidget(ctx, c);
    }

    public RSWidget[] getWidgets(int parent) {
        Widget[][] cache = ctx.getClient().getWidgetCache();
        Widget[] p = cache[parent];
        if (p == null) return null;
        return transform(newArrayList(p), transform).toArray(new RSWidget[0]);
    }

    /**
     * @param predicate the string to search for
     * @return a list of all the RSWidget that contain the specified text
     */
    public RSWidget[] getWidgetsWithText(String predicate) {
        List<RSWidget> satisfied = new ArrayList<RSWidget>();
        for (Widget[] parents : ctx.getClient().getWidgetCache()) {
            for (RSWidget child : filter(transform(newArrayList(parents), transform), Predicates.notNull()).toArray(new RSWidget[0])) {
                if (child.getText() != null && child.getText().contains(predicate)) {
                    satisfied.add(child);
                }
            }
        }
        return satisfied.toArray(new RSWidget[0]);
    }

    public boolean canContinue() {
        RSWidget[] possible = getWidgetsWithText("Click here to continue");
        return possible.length > 0;
    }

    public void clickContinue() {
        for (RSWidget widget : getWidgetsWithText("Click here to continue")) {
            widget.click(true);
        }
    }

    private final Function<Widget, RSWidget> transform = new Function<Widget, RSWidget>() {
        @Override
        public RSWidget apply(Widget widget) {
            if (widget != null) {
                return new RSWidget(ctx, widget);
            }
            return null;
        }
    };

}
