package it.polimi.ingsw.network.client;

public abstract class Client {

    public abstract void sendMessage(String message);

    public abstract void readMessage();

    public abstract void disconnect();

    public abstract void enablePinger(boolean enabled);
}
