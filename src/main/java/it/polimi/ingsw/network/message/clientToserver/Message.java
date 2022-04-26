package it.polimi.ingsw.network.message.clientToserver;

import it.polimi.ingsw.network.message.MessageType;

/**
 * Abstract message class which must be extended by message sent by the client.
 * The client communicates with the server using this generic type of message.
 */

public abstract class Message {
    private final String nickname;
    private final MessageType messageType;

    public Message(String nickname, MessageType messageType) {
        this.nickname = nickname;
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
