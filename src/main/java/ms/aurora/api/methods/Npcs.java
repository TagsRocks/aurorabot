package ms.aurora.api.methods;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import ms.aurora.api.ClientContext;
import ms.aurora.rt3.Npc;
import ms.aurora.api.util.Predicate;
import ms.aurora.api.wrappers.RSNPC;

import java.util.Collection;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Lists.newArrayList;

/**
 * @author tobiewarburton
 * @author rvbiljouw
 */
public final class Npcs {
    private final ClientContext ctx;

    public Npcs(ClientContext ctx) {
        this.ctx = ctx;
    }


    /**
     * returns the closest {@link RSNPC} which matches the given predicates
     *
     * @param predicates the {@link Predicate} in which are required to be satisfied
     * @return the closest {@link RSNPC} which satisfies the predicates if there is one or null
     * @see RSNPC#distance(ms.aurora.api.wrappers.Locatable)
     * @see Predicate
     */
    public RSNPC get(final Predicate<RSNPC>... predicates) {
        return getClosest(Collections2.filter(_getAll(),
                new com.google.common.base.Predicate<RSNPC>() {
                    @Override
                    public boolean apply(RSNPC object) {
                        for (Predicate<RSNPC> predicate : predicates) {
                            if (!predicate.apply(object)) return false;
                        }
                        return true;
                    }
                }).toArray(new RSNPC[0]));
    }

    /**
     * return an array of all the {@link RSNPC} which match the given predicate
     *
     * @param predicate the {@link Predicate} in which is required to be satisfied
     * @return an array of the {@link RSNPC} which satisfy the given predicate
     * @see Predicate
     */
    public RSNPC[] getAll(final Predicate<RSNPC> predicate) {
        return Collections2.filter(_getAll(),
                new com.google.common.base.Predicate<RSNPC>() {
                    @Override
                    public boolean apply(RSNPC object) {
                        return predicate.apply(object);
                    }
                }).toArray(new RSNPC[0]);
    }

    /**
     * returns a list of all the {@link RSNPC} that are loaded in the client which aren't null
     *
     * @return a list of all the {@link RSNPC} that aren't null which are loaded into the client
     */
    public RSNPC[] getAll() {
        return _getAll().toArray(new RSNPC[0]);
    }

    private RSNPC getClosest(RSNPC[] npcs) {
        RSNPC closest = null;
        int closestDistance = 9999;
        for (RSNPC npc : npcs) {
            int distance = npc.distance(ctx.players.getLocal());
            if (distance < closestDistance) {
                closestDistance = distance;
                closest = npc;
            }
        }
        return closest;
    }

    private Collection<RSNPC> _getAll() {
        return filter(transform(newArrayList(ctx.getClient()
                .getAllNpcs()), transform), Predicates.notNull());
    }

    private final Function<Npc, RSNPC> transform = new Function<Npc, RSNPC>() {
        @Override
        public RSNPC apply(Npc npc) {
            if (npc != null) {
                return new RSNPC(ctx, npc);
            }
            return null;
        }
    };
}