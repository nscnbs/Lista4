package Server;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Player handler (Przewodnik gracza)
 */

public class PlayerHandler implements Runnable {
    private String name;
    private PlayerSocket player;
    private BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private ServerListener serverListener;

    private SharedData data;

    public PlayerHandler(PlayerSocket player, SharedData data, ServerListener serverListener) {
        this.player = player;
        this.data = data;
        this.serverListener = serverListener;
    }

    private synchronized String receiveName() {
        String temp = null;
            Collection<String> names = data.getNames();
            player.sendStr("NAME_GET");
            while(temp == null) {
                temp = player.receive();
            }
            System.out.println(temp);
            // Name recived: NAME_SET userName
            temp = temp.substring(9);
            if(!temp.isBlank() && !names.contains(temp)) {
                data.addName(temp);
        }

        player.sendStr("NAME_ACCEPTED");

        return temp;
    }

    private synchronized void sendPlayerAmount() {
        player.sendStr("PLAYERS_NUMBER " + data.getNumberOfPlayers());
    }

    public void sendStr(String string) {
        queue.add(string);
    }

    public void run() {
        try {
            name = receiveName();
            sendPlayerAmount();
            UserInterface.print(name + " joined. "+player.getSocket().getLocalSocketAddress());
            boolean connected = true;
            while(connected) {
                String input = null;
                while(input == null) {
                    input = player.receive();
                    if(!queue.isEmpty()) {
                        UserInterface.print("Reading from collection: " + queue.peek());
                        player.sendStr(queue.take());
                    }
                }
                serverListener.notifyServer(input);

                /*String[] res = input.split("\\s");
                switch (res[0]) {
                    case "DISCONNECT": {connected = false;} break;
                    case "MOVE": {
                        if(!data.game.interpretMove(input.substring(5)))
                            player.sendStr("MOVE_BAD");
                        else
                            this.notify();
                    } break;
                }*/
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            if (player != null) {
                player.sendStr("DISCONNECTED");
                data.deletePlayerHandlers(this);
                player.close();
            }
            if (name != null) {
                UserInterface.print(name + " left" );
                data.deleteName(name);
            }
        }
    }

    public void close() {
        player.close();
    }
}
