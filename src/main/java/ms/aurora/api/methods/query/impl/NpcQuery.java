package ms.aurora.api.methods.query.impl;

import ms.aurora.api.methods.query.Sort;
import ms.aurora.api.wrappers.RSNPC;
import ms.aurora.rt3.Npc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ms.aurora.api.Context.getClient;

/**
 * Date: 23/05/13
 * Time: 10:46
 *
 * @author A_C/Cov
 */
public final class NPCQuery extends CharacterQuery<RSNPC, NPCQuery> {

    @Override
    public RSNPC[] result() {
        List<RSNPC> rsnpcList = new ArrayList<RSNPC>();
        for (Npc npc : getClient().getAllNpcs()) {
            if (npc != null) {
                rsnpcList.add(new RSNPC(npc));
            }
        }
        if (this.comparator == null) {
            switch (sortType) {
                case DISTANCE:
                    comparator = Sort.DISTANCE;
                    break;
                default:
                    comparator = Sort.DEFAULT;
            }
        }
        Collections.sort(filterResults(rsnpcList), comparator);
        return rsnpcList.toArray(new RSNPC[rsnpcList.size()]);
    }

    public NPCQuery named(final String... names) {
        this.addConditional(new Conditional() {
            @Override
            protected boolean accept(RSNPC type) {
                for (String name : names) {
                    if (type.getName().equals(name)) {
                        return true;
                    }
                }
                return false;
            }
        });
        return this;
    }

    public NPCQuery id(final int... ids) {
        this.addConditional(new Conditional() {
            @Override
            protected boolean accept(RSNPC type) {
                for (int id : ids) {
                    if (type.getId() == id) {
                        return true;
                    }
                }
                return false;
            }
        });
        return this;
    }
}