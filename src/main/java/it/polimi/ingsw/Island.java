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
        if(islandID > 0 && islandID <13) {
            this.islandID = islandID;
            this.students = new ArrayList<>();
            this.tower = null;
        }
    }

    /**
     * @return students on the island
     */
    public ArrayList<Student> getStudents() {

        ArrayList<Student> result = new ArrayList<Student>(students);

        return result;
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
    public boolean addTower(PlayerColor tower){
        if(this.tower == null) {
            this.tower = tower;
            return true;
        }
        return false;
    }

    // REPLACE TOWER HO TOLTO IL PARAMETRO INT TOWER E CAMBIATO NOME
    /**
     * Removes the tower from the island: it has been conquered
     */
    public boolean removeTower(){
        if(this.tower != null){
            this.tower = null;
            return true;
        }
        return false;
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
