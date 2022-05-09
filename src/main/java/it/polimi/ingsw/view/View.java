package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.enumerations.CharacterID;
import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.model.enumerations.WizardName;
import it.polimi.ingsw.model.gameBoard.Assistant;
import it.polimi.ingsw.model.gameBoard.Board;
import it.polimi.ingsw.model.gameBoard.IslandGroup;
import it.polimi.ingsw.model.phases.Phase;

import it.polimi.ingsw.network.message.clientToserver.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic view that will be extended by 'concrete' views (CLI, GUI).
 * It observes the model.
 * It's observed by the controller.
 */
public class View  {

    //todo: da eliminare?

    //attributo che lega la view ad un client


    //messages are sent to the client

    /**
     * Asks the user to choose a wizard from a list of available wizard.
     * @param wizardsAvailable list of wizards that aren't been chosen from other players.
     */
    public void askWizard(List<WizardName> wizardsAvailable) {
        //client: send(new WizardsAvailableMessage(wizardsAvailable));
    }

    /**
     * Asks the user to choose an assistant from those he has.
     * @param assistantsAvailable list of assistant that the player has not used yet.
     */
    public void askAssistant(ArrayList<Assistant> assistantsAvailable) {
        //client: send(new AssistantsMessage(assistantsAvailable));
    }

    /**
     * Asks the user to choose a student's color.
     * @param colorsAvailable the list of available colors.
     */
    public void askColor(ArrayList<CreatureColor> colorsAvailable) {
        //client: send(new ColorsAvailableMessage(colorsAvailable));
    }

    /**
     * Asks the user to choose the id of the cloud to pick.
     * @param cloudsAvailable list of clouds' id that aren't been chosen from other players.
     */
    public void askCloudId(ArrayList<Integer> cloudsAvailable) {
        //client: send(new CloudsMessage(cloudsAvailable));
    }

    /**
     * Shows the result of login request by a client.
     * @param connectionAccepted whether the connection was accepted.
     * @param nicknameAccepted whether the nickname was accepted.
     */
    public void showLoginResult(boolean connectionAccepted, boolean nicknameAccepted) {
        //client: send(new LoginReplyMessage(connectionAccepted, nicknameAccepted));
    }

    /**
     * Shows a generic message.
     * @param genericMessage the message to show.
     */
    public void showGenericMessage(String genericMessage) {
        //client: send(new GenericMessage(genericMessage));
    }

    /**
     * Shows the nickname of the player that has disconnected.
     * @param nicknameDisconnected nickname of the player that has disconnected.
     */
    public void showDisconnectionMessage(String nicknameDisconnected, String messageDisconnection) {
        //client: send(new DisconnectionMessage(nicknameDisconnected, messageDisconnection));
    }

    /**
     * Shows an error message.
     * @param errorMessage message to show.
     */
    public void showErrorMessage(String errorMessage) {
        //client: send(new ErrorMessage(errorMessage));
    }

    /**
     * Shows the info about a character.
     * @param characterID ID of the character whose information has to be show.
     * @param description description of the character.
     */
    public void showCharacterInfoReply(int characterID, String description) {
        //client: send(new CharacterInfoMessage(characterID, description));
    }

    /**
     * Shows the board (entrance and hall) of a player.
     * @param nickname nickname of the player whose board is to be shown.
     * @param board board to show.
     */
    public void showClientBoard(String nickname, Board board) {
        //client: send(new BoardMessage(nickname, board));
    }

    /**
     * Shows the island groups (index of islandGroup, islands contained, students on islands).
     * @param islandGroups island groups to be show.
     */
    public void showIslandGroups(ArrayList<IslandGroup> islandGroups){
        //client: send(new IslandGroupsMessage(islandGroups));
    }

    /**
     * Shows the characters drawn (if expert mode).
     * @param characters the characters to show.
     */
    public void showCharactersDrawn(ArrayList<Character> characters) {
        //client: send(new CharacterDrawnMessage(characters));
    }

    /**
     * Shows the current player.
     * @param currentPlayer the player to show.
     */
    public void showCurrentPlayer(Player currentPlayer) {
        //client: send(new CurrentPlayerMessage(currentPlayer.getNickname()));
    }

    /**
     * Shows the current phase.
     * @param currentPhase the phase to show.
     */
    public void showCurrentPhase(Phase currentPhase) {
        //client: send(new CurrentPhaseMessage(currentPhase.getPhaseName()));
    }

    /**
     * Shows some info about the game.
     * @param activePlayers players in game.
     * @param drawnCharacters list of characters drawn (if expert mode).
     * @param boards players' board.
     * @param islandGroups island group and their information.
     * @param currentPlayerNickname the current player.
     */
    public void showMatchInfo(List<String> activePlayers, List<CharacterID> drawnCharacters,
                       List<Board> boards, List<IslandGroup> islandGroups, String currentPlayerNickname) {
        //client: send(new ActivePlayersMessage(activePlayers, drawnCharacters, boards, islandGroups, currentPlayerNickname));
    }

    /**
     * Shows the winner of the game.
     * @param winner nickname of the winner.
     */
    public void showWinMessage(String winner) {
        //client: send(new WinnerMessage(winner));
    }

}
