package ms.aurora.sdn.net.packet;

import ms.aurora.sdn.net.OutgoingPacket;

import java.io.IOException;

/**
 * @author tobiewarburton
 */
public class ScriptRequest extends OutgoingPacket {
    private int idx;

    public ScriptRequest(int idx) {
        this.idx = idx;
    }

    @Override
    public int getOpcode() {
        return 7;
    }

    @Override
    public void prepare() throws IOException {
        getStream().writeInt(idx);
        getStream().flush();
        getStream().close();
    }
}