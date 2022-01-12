package Server;


/**
 * Server listener
 */
public interface ServerListener {
    public void notifyServer(String message);

    public void notifyNameSet();
}
