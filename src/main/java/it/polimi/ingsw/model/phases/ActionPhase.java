package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.CharacterID;

public abstract class ActionPhase extends Phase {

    protected Player currentPlayer;

    public void playCharacter(Character activatedCharacter) {
        this.activatedCharacter = activatedCharacter;
        activatedCharacter.effect();
        // TODO parameters and iterations
    }
}
