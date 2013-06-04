package ms.aurora.core;

import ms.aurora.api.event.RegionUpdateEvent;
import ms.aurora.api.methods.web.model.World;
import ms.aurora.api.script.task.EventBus;
import ms.aurora.core.model.Account;
import ms.aurora.core.script.PluginManager;
import ms.aurora.core.script.ScriptManager;
import ms.aurora.event.PaintManager;
import ms.aurora.gui.widget.AppletWidget;
import ms.aurora.loader.ClientWrapper;
import ms.aurora.sdn.SDNConnection;
import ms.aurora.sdn.net.packet.RegionDataCheck;
import org.apache.log4j.Logger;

import java.applet.Applet;

import static ms.aurora.api.Context.getClient;

/**
 * @author Rick
 */
public final class Session implements Runnable {
    private final SessionProperties properties = new SessionProperties();
    private final PaintManager paintManager = new PaintManager();
    private final ClientWrapper wrapper = new ClientWrapper();
    private final EventBus eventBus = new EventBus();
    private final ThreadGroup threadGroup;
    private final SessionUI ui;
    private ScriptManager scriptManager;
    private PluginManager pluginManager;
    private Account account;

    public Session(ThreadGroup threadGroup, AppletWidget container) {
        this.ui = new SessionUI(this, container);
        this.threadGroup = threadGroup;
    }

    @Override
    public void run() {
        wrapper.start();
        if (wrapper.getApplet() != null) {
            scriptManager = new ScriptManager(this);
            pluginManager = new PluginManager(this);
            update();
        }

        eventBus.register(this);
    }

    @EventBus.EventHandler
    public void onRegionUpdate(RegionUpdateEvent event) {
        int baseX = getClient().getBaseX();
        int baseY = getClient().getBaseY();
        int plane = getClient().getPlane();
        int[][] masks = getClient().getRegions()[plane].getClippingMasks();
        Logger.getLogger(Session.class).info("Entering new region " + baseX + ", " + baseY);
        SDNConnection.getInstance().writePacket(new RegionDataCheck(baseX, baseY, plane, masks));
    }

    public void destroy() {
        try {
            scriptManager.stop();
            pluginManager.stop();
            wrapper.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SessionUI getUI() {
        return ui;
    }

    public Applet getApplet() {
        return wrapper.getApplet();
    }

    public void update() {
        ui.getContainer().setApplet(wrapper.getApplet());
        Repository.set(wrapper.getApplet().hashCode(), this);
        pluginManager.refresh();
    }

    public SessionProperties getProperties() {
        return properties;
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

    public ThreadGroup getThreadGroup() {
        return threadGroup;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
        this.ui.update();
    }

    public void setWorld(World world) {
        if(wrapper.setWorld(this, world)) {
            ui.getContainer().setApplet(wrapper.getApplet());
        }
    }

    public EventBus getEventBus() {
        return eventBus;
    }
}
