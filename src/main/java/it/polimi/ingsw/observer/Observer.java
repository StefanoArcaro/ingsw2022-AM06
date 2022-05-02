package it.polimi.ingsw.observer;

import it.polimi.ingsw.network.message.clientToserver.Message;

/**
 * Generic observer interface.
 * It will be implemented by controllers to observer the view.
 * It will be implemented by the view to observer the model.
 */
public interface Observer {
    void update(Message message);
}
