package ms.aurora.api.rt3;

/**
 * @author rvbiljouw
 */
public interface Node {

    Node getPrev();

    Node getNext();

    long getHash();

}
