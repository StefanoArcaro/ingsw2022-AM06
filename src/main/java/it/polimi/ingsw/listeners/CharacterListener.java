package it.polimi.ingsw.listeners;

import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.network.message.serverToclient.CharacterPlayedMessage;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeEvent;

public class CharacterListener extends Listener {

    public CharacterListener(VirtualView virtualView) {
        super(virtualView);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        CharacterPlayedMessage message = new CharacterPlayedMessage((Character) evt.getNewValue());
        virtualView.sendAll(message);
    }
}
