package ms.aurora.core.plugin;

import ms.aurora.api.plugin.Plugin;
import ms.aurora.api.plugin.PluginState;
import ms.aurora.core.Session;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Rick
 */
public final class PluginManager {
    private static final Logger logger = Logger.getLogger(PluginManager.class);
    private final Map<String, Plugin> pluginMap = new HashMap<String, Plugin>();
    private final Session session;

    public PluginManager(Session session) {
        this.session = session;
    }

    public void start(Class<? extends Plugin> pluginClass) {
        try {
            if (!pluginMap.containsKey(pluginClass.getName())) {
                final Plugin plugin = pluginClass.newInstance();
                plugin.setSession(session);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        plugin.setState(PluginState.INIT);
                    }
                });
                pluginMap.put(pluginClass.getName(), plugin);
            }
        } catch (Exception e) {
            logger.error("Failed to initialize plugin", e);
        }
    }

    public void stop(Class<? extends Plugin> pluginClass) {
        if (pluginMap.containsKey(pluginClass.getName())) {
            Plugin plugin = pluginMap.get(pluginClass.getName());
            plugin.setState(PluginState.STOPPED);
            pluginMap.remove(pluginClass.getName());
        }
    }

}
