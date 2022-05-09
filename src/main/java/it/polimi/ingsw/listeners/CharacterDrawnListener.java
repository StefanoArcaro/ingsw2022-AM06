package it.polimi.ingsw.listeners;

import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.network.message.serverToclient.CharacterDrawnMessage;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeEvent;

public class CharacterDrawnListener extends Listener {

    /**
     * Default constructor.
     *
     * @param virtualView virtual representation of the clients' view.
     */
    public CharacterDrawnListener(VirtualView virtualView) {
        super(virtualView);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Character character = (Character) evt.getNewValue();
        CharacterDrawnMessage message = new CharacterDrawnMessage(character.getCharacterID(), character.getCost(), character.isUsed(), character.getStudents(), character.getNumberOfBanCards());
        virtualView.sendAll(message);
    }
}
