package ms.aurora.api.methods;

import ms.aurora.api.Context;

/**
 * Setting related functions
 * @author tobiewarburton
 */
public final class Settings {

    /**
     * Retrieves a client setting
     * @param index index of the setting
     * @return setting value
     */
    public static int getSetting(int index) {
        return Context.get().getClient().getWidgetSettings()[index];
    }

}
