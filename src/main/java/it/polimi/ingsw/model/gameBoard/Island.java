package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.characters.Character;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Island {

    private static final int ISLAND_ID_MIN = 1;
    private static final int ISLAND_ID_MAX = 12;

    private int islandID;
    private ArrayList<Student> students;
    private PlayerColor tower;

    /**
     * Default constructor
     * @param islandID ID of the island
     */
    public Island(int islandID) {
        if(islandID >= ISLAND_ID_MIN && islandID <= ISLAND_ID_MAX) {
            this.islandID = islandID;
            this.students = new ArrayList<>();
            this.tower = null;
        }
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    /**
     * Add student on the island
     * @param student to add
     */
    public void addStudent(Student student){
        students.add(student);
    }

    /**
     * @return the island ID
     */
    public int getIslandID() {
        return islandID;
    }

    /**
     * @return the color of the player who conquered the island
     */
    public PlayerColor getTower() {
        return tower;
    }

    /**
     * Add a tower on the island
     * @param tower represents the player who conquered the island
     */
    public boolean addTower(PlayerColor tower) {
        Player player = Game.getGame().getPlayerByColor(tower);
        int numberOfTowers = player != null ? player.getBoard().getTowers() : 0;
        if(this.tower == null && numberOfTowers>0 && player!=null) {
            if(player.getBoard().removeTower()) {
                this.tower = tower;
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the tower from the island: it has been conquered
     */
    public boolean removeTower() {
        Player player = Game.getGame().getPlayerByColor(tower);
        if(this.tower != null && player!=null) {
            player.getBoard().addTowers(1);
            this.tower = null;
            return true;
        }
        return false;
    }


    /**
     * Calculates the player's influence on the island due to the towers
     * @param player whose influence is being calculated
     * @return the player's influence on the island due to the towers
     */
    private int towerInfluence(Player player) {
        PlayerColor playerColor = player.getColor();

        if(playerColor == this.tower) {
            return 1;
        }

        return 0;
    }

    /**
     * Calculates the player's influence on the island due to the students on the island
     * @param player whose influence is being calculated
     * @return the player's influence on the island due to the students
     */
    private int studentInfluence(Player player) {
        int influenceByStudent = 0;
        ArrayList<Professor> playerProfessors = player.getBoard().getProfessors();
        ArrayList<CreatureColor> playerProfessorsColor;
        List<CreatureColor> playerProfessorsColorList;

        playerProfessorsColorList = playerProfessors.stream().map(Creature::getColor).collect(Collectors.toList());
        playerProfessorsColor = new ArrayList<>(playerProfessorsColorList);

        for (Student student : students) {
            if(playerProfessorsColor.contains(student.getColor())) {
                influenceByStudent++;
            }
        }

        return influenceByStudent;
    }

    /**
     * Calculate the influence of a player on the island
     * @param player whose influence is being calculated
     * @return the influence of the player on the island
     */
    public int calculateInfluence(Player player) {
        int influence = 0;

        influence = influence + towerInfluence(player) + studentInfluence(player);

        return influence;
    }



    /**
     * Calculates the player's influence on the island due to the towers
     * Overloading
     * @param player whose influence is being calculated
     * @param activatedCharacter active character
     * @return the player's influence on the island due to the towers
     */
    private int towerInfluence(Player player, Character activatedCharacter) {
        PlayerColor playerColor = player.getColor();

        //Automatically applied character effect 6
        if(activatedCharacter!= null && !activatedCharacter.getTowerCounter()) {
            return 0;
        }

        if(playerColor == this.tower) {
            return 1;
        }

        return 0;
    }

    /**
     * Calculates the player's influence on the island due to the students on the island
     * Overloading
     * @param player whose influence is being calculated
     * @param activatedCharacter active character
     * @return the player's influence on the island due to the students
     */
    private int studentInfluence(Player player, Character activatedCharacter) {
        int influenceByStudent = 0;
        ArrayList<Professor> playerProfessors = player.getBoard().getProfessors();
        ArrayList<CreatureColor> playerProfessorsColor;
        List<CreatureColor> playerProfessorsColorList;
        CreatureColor colorNoPoints = (activatedCharacter != null) ? activatedCharacter.getColorNoPoints() : null;

        // Automatically applied character effect 9
        playerProfessorsColorList = playerProfessors.stream().map(Creature::getColor)
                                        .filter(x->!x.equals(colorNoPoints)).collect(Collectors.toList());
        playerProfessorsColor = new ArrayList<>(playerProfessorsColorList);

        for (Student student : students){
            if(playerProfessorsColor.contains(student.getColor())){
                influenceByStudent++;
            }
        }

        return influenceByStudent;
    }


    /**
     * Calculate the influence of a player on the island
     * Overloading
     * @param player whose influence is being calculated
     * @param activatedCharacter active character
     * @return the influence of the player on the island
     */
    public int calculateInfluence(Player player, Character activatedCharacter) {
        int influence = 0;

        influence = influence + towerInfluence(player, activatedCharacter)
                              + studentInfluence(player, activatedCharacter);
        return influence;
    }

}
