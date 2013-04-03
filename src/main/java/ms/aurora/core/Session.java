package ms.aurora.core;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import ms.aurora.api.plugin.Plugin;
import ms.aurora.core.model.PluginConfig;
import ms.aurora.core.plugin.PluginLoader;
import ms.aurora.core.plugin.PluginManager;
import ms.aurora.core.script.ScriptManager;
import ms.aurora.event.PaintManager;
import ms.aurora.gui.widget.AppletWidget;
import ms.aurora.loader.AppletLoader;

import java.applet.Applet;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.Thread.currentThread;
import static ms.aurora.core.SessionRepository.set;

/**
 * @author Rick
 */
public final class Session implements Runnable {
    private final CopyOnWriteArrayList<MenuItem> pluginMenu = new CopyOnWriteArrayList<MenuItem>();
    private final PaintManager paintManager = new PaintManager(this);
    private ScriptManager scriptManager;
    private PluginManager pluginManager;
    private AppletWidget container;
    private Applet applet;

    public Session(AppletWidget container) {
        this.container = container;
    }

    @Override
    public void run() {
        AppletLoader loader = new AppletLoader();
        loader.run();

        if(loader.getApplet() != null) {
            applet = loader.getApplet();
            container.setApplet(applet);
            set(applet.hashCode(), this);
            initComponents();
            refreshPlugins();
        }
    }

    private void initComponents() {
        scriptManager = new ScriptManager(this);
        pluginManager = new PluginManager(this);
    }

    public void refreshPlugins() {
        for (Plugin plugin : PluginLoader.getPlugins()) {
            PluginConfig config = PluginConfig.getByName(
                    plugin.getClass().getName());

            pluginManager.stop(plugin.getClass());
            if (config.isEnabled()) {
                pluginManager.start(plugin.getClass());
            } else {
                pluginManager.stop(plugin.getClass());
            }
        }
        System.out.println(currentThread().getThreadGroup().getName() + " refreshplugins");
    }

    public void registerMenu(final Menu menu) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                pluginMenu.add(menu);
            }
        });
    }

    public void deregisterMenu(final Menu menu) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                pluginMenu.remove(menu);
            }
        });
    }

    public synchronized CopyOnWriteArrayList<MenuItem> getPluginMenu() {
        return pluginMenu;
    }

    public ScriptManager getScriptManager() {
        return scriptManager;
    }

    public PluginManager getPluginManager() {
        return pluginManager;
    }

    public PaintManager getPaintManager() {
        return paintManager;
    }

    public Applet getApplet() {
        return applet;
    }

}
