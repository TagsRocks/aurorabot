package ms.aurora.api;

import ms.aurora.api.event.EventBus;
import ms.aurora.api.script.ScriptState;
import ms.aurora.core.Repository;
import ms.aurora.core.Session;
import ms.aurora.rt3.IClient;
import org.apache.log4j.Logger;

import static java.lang.Thread.currentThread;
import static ms.aurora.api.methods.Widgets.getWidget;

/**
 * @author Rick
 */
public class Context {
    private static final Logger logger = Logger.getLogger(Context.class);
    private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
    private static boolean rendering = true;

    public static Session getSession() {
        if (session.get() == null) {
            Session group = Repository.get(currentThread().getThreadGroup());
            if (group != null) {
                session.set(group);
            }
        }
        return session.get();
    }

    public static IClient getClient() {
        if(getSession() == null) {
            throw new RuntimeException("Req from tg " + currentThread().getThreadGroup().getName() + " results in null.");
        }
        return (IClient) getSession().getApplet();
    }

    public static void setProperty(String key, String value) {
        getSession().getProperties().setProperty(key, value);
    }

    public static String getProperty(String key) {
        return getSession().getProperties().getProperty(key);
    }

    public static boolean isActive() {
        return getSession().getScriptManager().getState() != ScriptState.STOP &&
                getSession().getScriptManager().getState() != ScriptState.PAUSED;
    }


    public static boolean isLoggedIn() {
        return getClient().getLoginIndex() == 30 && getWidget(378, 6) == null;
    }

    public static void invokeLater(Runnable runnable) {
        Thread thread = new Thread(getSession().getThreadGroup(), runnable);
        thread.start();
    }

    public static void invokeLater(ThreadGroup group,Runnable runnable) {
        Thread thread = new Thread(group, runnable);
        thread.start();
    }

    public static EventBus getEventBus() {
        return getSession().getEventBus();
    }

    public static boolean isRendering() {
        return rendering;
    }

    public static void setRendering(boolean rendering) {
        Context.rendering = rendering;
    }
}
