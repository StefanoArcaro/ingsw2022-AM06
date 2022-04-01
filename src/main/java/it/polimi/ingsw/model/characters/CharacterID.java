package it.polimi.ingsw.model.characters;

public enum CharacterID {

    CHARACTER_NONE(0),
    CHARACTER_ONE(1),
    CHARACTER_TWO(2),
    CHARACTER_THREE(3),
    CHARACTER_FOUR(4),
    CHARACTER_FIVE(5),
    CHARACTER_SIX(6),
    CHARACTER_SEVEN(7),
    CHARACTER_EIGHT(8),
    CHARACTER_NINE(9),
    CHARACTER_TEN(10),
    CHARACTER_ELEVEN(11),
    CHARACTER_TWELVE(12);

    private final int id;

    CharacterID(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

}
