package ms.aurora.core.script;

import ms.aurora.api.script.Script;
import ms.aurora.api.script.ScriptState;
import ms.aurora.core.Session;
import ms.aurora.gui.Main;

public final class ScriptManager {
    private final Session session;
    private Script currentScript;
    private Thread currentThread;

    public ScriptManager(Session session) {
        this.session = session;
    }

    public void start(Class<? extends Script> scriptClass) {
        if (!hasScript()) {
            try {
                Script script = scriptClass.newInstance();
                currentThread = new Thread(session.getThreadGroup(), script);
                currentScript = script;
                currentScript.setState(ScriptState.START);
                currentThread.start();

                Main.setInputEnabled(false);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void pause() {
        currentScript.setState(ScriptState.PAUSED);
        Main.setInputEnabled(true);
    }

    public void resume() {
        currentScript.setState(ScriptState.RUNNING);
        Main.setInputEnabled(false);
    }

    public void stop() {
        try {
            currentScript.setState(ScriptState.STOP);
            currentThread.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        currentThread = null;
        currentScript = null;

        Main.setInputEnabled(true);
    }

    private boolean hasScript() {
        return currentScript != null && currentThread != null;
    }

    public ScriptState getState() {
        return currentScript != null ? currentScript.getState() : ScriptState.STOP;
    }
}
