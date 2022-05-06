package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;

/**
 * Message used by the server to display the available colors.
 */
public class ColorsAvailableMessage extends Answer {

    private final ArrayList<CreatureColor> colors;

    public ColorsAvailableMessage(ArrayList<CreatureColor> colors) {
        super(MessageType.COLORS_AVAILABLE_MESSAGE);
        this.colors = colors;
    }

    public String getMessage() {
        String message = "Available colors:\n";

        for(CreatureColor c : colors) {
            message = message + "\t-" + c.getColorName() + "\n";
        }

        return message;
    }
}
