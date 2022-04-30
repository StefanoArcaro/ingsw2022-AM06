package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.message.clientToserver.Message;
import it.polimi.ingsw.network.message.serverToclient.Answer;

public abstract class Client {

    public abstract void readMessage();

    public abstract void sendMessage(Message message);

    public abstract void disconnect();

    public abstract void enablePinger(boolean enabled);
}
