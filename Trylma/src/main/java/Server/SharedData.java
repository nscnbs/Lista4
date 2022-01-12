package Server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Data
 */

public class SharedData {

    private static SharedData instance;
    private static Object mutex = new Object();

    private CopyOnWriteArrayList<String> names;
    private CopyOnWriteArrayList<PlayerHandler> players;
    public Game game;

    private int amount;

    public SharedData() {
        names = new CopyOnWriteArrayList<>();
        players = new CopyOnWriteArrayList<>();
    }

    public static SharedData getInstance() {
        SharedData result = instance;

        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null)
                    instance = result = new SharedData();
            }
        }

        return result;
    }

    public void setNumberOfPlayers(int amount) {
        synchronized (mutex) {
            this.amount = amount;
        }
    }

    public int getNumberOfPlayers() {
        synchronized (mutex) {
            return this.amount;
        }
    }

    public Collection<String> getNames() {
        synchronized (mutex) {
            return names;
        }
    }

    public Collection<PlayerHandler> getPlayerHandlers() {
        synchronized (mutex) {
            return players;
        }
    }

    public void addName(String name) {
        synchronized (mutex)  {
            if(names.contains(name)) {
                throw new IllegalArgumentException("Name is taken");
            } else {
                names.add(name);
            }
        }
    }

    public void addPlayerHandlers(PlayerHandler player) {
        synchronized (mutex) {
            players.add(player);
        }
    }

    public void deleteName(String name) {
        synchronized (mutex) {
            names.remove(name);
        }
    }

    public void deletePlayerHandlers(PlayerHandler player) {
        synchronized (mutex) {
            players.remove(player);
        }
    }

    public void clearAll() {
        synchronized (mutex) {
            names.clear();
            players.clear();
        }
    }
}

