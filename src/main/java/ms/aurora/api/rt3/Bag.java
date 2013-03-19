package ms.aurora.api.rt3;

/**
 * @author rvbiljouw
 */
public interface Bag {

    int getSentinelCount();

    Node getLastRetrived();

    Node getCurrent();

    Node[] getSentinels();

    int getCurrentSentinel();

}
