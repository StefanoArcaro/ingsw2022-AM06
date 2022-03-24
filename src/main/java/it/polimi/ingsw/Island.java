package it.polimi.ingsw;

import java.util.ArrayList;

public class Island {
    private int islandID;
    private ArrayList<Student> students;
    private PlayerColor tower;

    /**
     * Default constructor
     * @param islandID ID of the island
     */
    public Island(int islandID) {
        this.islandID = islandID;
        this.students = new ArrayList<>();
        this.tower = null;
    }

    /**
     * @return students on the island
     */
    public ArrayList<Student> getStudents() {
        // TO DO
        return null;
    }

    /**
     * Add student on the island
     * @param student to add
     */
    public void addStudent(Student student){
        // TO DO
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
     * Set the color of the player who conquered the island
     * @param tower color of the player who conquered the island
     */
    public void setTower(PlayerColor tower) {
        this.tower = tower;
    }

    /**
     * add a tower on the island
     * @param tower represents the player who conquered the island
     */
    public void addTower(PlayerColor tower){
        // TO DO
    }

    // REPLACE TOWER QUI O IN ISLAND GROUP
    /**
     * Removes the towers from the island: it has been conquered
     * @param tower number of towers to be removed
     */
    public void replaceTower(int tower){
        // TO DO
    }

    /**
     * Calculate the influence of a player (identified by its nickname) on the island
     * @param nickname of the player
     * @return the influence of the player on the island
     */
    public int calculateInfluence(String nickname){
        // TO DO
        return 0;
    }






}
