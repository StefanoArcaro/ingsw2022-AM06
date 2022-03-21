package it.polimi.ingsw;

import java.util.ArrayList;

public class Island {
    private int islandID;
    private ArrayList<Student> students;
    private Tower tower = null;

    public int calculateInfluence(Player player){}
    public void addStudent(Student student){}
    public void addTower(Tower tower){}
    public void replaceTower(Tower tower){}

    public Island(int islandID, ArrayList<Student> students) {
        this.islandID = islandID;
        this.students = students;
    }

    public int getIslandID() {
        return islandID;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public Tower getTower() {
        return tower;
    }
}
