package it.polimi.ingsw.listeners;

import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.network.message.serverToclient.FXBanCardMessage;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeEvent;

public class FXBanCardListener extends Listener {

    public FXBanCardListener(VirtualView virtualView) {
        super(virtualView);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Character character = (Character) evt.getNewValue();
        FXBanCardMessage message = new FXBanCardMessage(character.getCharacterID(), character.getCost(), character.isUsed(), character.getStudents(), character.getNumberOfBanCards());
        virtualView.sendAll(message);
    }
}
