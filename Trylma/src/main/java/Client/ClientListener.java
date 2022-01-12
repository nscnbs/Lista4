package Client;

/**
 * Client listener
 */
public interface ClientListener {
    public boolean sendMove(String message) throws IllegalArgumentException;
}
