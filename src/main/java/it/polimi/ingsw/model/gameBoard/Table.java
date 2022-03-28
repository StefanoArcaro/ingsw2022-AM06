package it.polimi.ingsw.model.gameBoard;

public class Table {
    private CreatureColor color;
    private int index;

    public Table(CreatureColor color) {
        this.color = color;
        this.index = 0;
    }

    public CreatureColor getColor() {
        return color;
    }

    public boolean addStudent(){
        if(index < 10){
            index = index+1;
            return true;
        }
        return false;
    }

    public boolean removeStudent(){
        if(index > 0){
            index = index-1;
            return true;
        }
        return false;
    }


    public int getLenght(){
        return index;
    }
}
