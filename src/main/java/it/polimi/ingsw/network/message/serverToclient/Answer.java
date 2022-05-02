package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;

/**
 * Abstract answer class which must be extended by message sent by the server.
 * The server communicates with the client using this generic type of message.
 */
// TODO check if ok (removed abstract)
public class Answer {

    private final static String SERVER_NICKNAME = "server";
    private final String nickname;
    private final MessageType messageType;

    public Answer(MessageType messageType) {
        this.nickname = SERVER_NICKNAME;
        this.messageType = messageType;
    }

    public String getNickname() {
        return nickname;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String toString() {
        return "Message{" +
        "nickname=" + nickname +
        ", messageType=" + messageType +
        '}';
    }

}
