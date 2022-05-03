package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.gameBoard.Student;
import it.polimi.ingsw.network.message.MessageType;

public class CharacterPlayedMessage extends Answer {

    private final Character character;

    public CharacterPlayedMessage(Character character) {
        super(MessageType.CHARACTER_PLAYED_MESSAGE);
        this.character = character;
    }

    @Override
    public String getMessage() {
        String played = "Character played: " + character.getCharacterID();

        String banCard = "";
        if(character.getNumberOfBanCards() > 0) {
            banCard = "Number of ban cards: " + character.getNumberOfBanCards();
        }

        String students = "";
        if(character.getStudents() != null) {
            students = "Students: ";
            for(Student student : character.getStudents()) {
                students = students + student.getColor().getColorName() + " ";
            }
        }

        return played + students + banCard;
    }
}
