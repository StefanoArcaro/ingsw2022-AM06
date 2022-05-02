package it.polimi.ingsw.listeners;

import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.network.message.serverToclient.CharacterInfoMessage;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeEvent;

public class CharacterListener extends Listener {

    public CharacterListener(VirtualView virtualView) {
        super(virtualView);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        CharacterInfoMessage message = new CharacterInfoMessage((Character) evt.getNewValue());
        virtualView.sendAll(message);
    }
}
