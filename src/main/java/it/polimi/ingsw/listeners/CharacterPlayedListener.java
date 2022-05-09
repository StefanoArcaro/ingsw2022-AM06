package it.polimi.ingsw.listeners;

import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.network.message.serverToclient.CharacterPlayedMessage;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeEvent;

public class CharacterPlayedListener extends Listener {

    public CharacterPlayedListener(VirtualView virtualView) {
        super(virtualView);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Character character = (Character) evt.getNewValue();
        CharacterPlayedMessage message = new CharacterPlayedMessage(character.getCharacterID(), character.getCost(), character.isUsed(), character.getStudents(), character.getNumberOfBanCards());
        virtualView.sendAll(message);
    }
}
