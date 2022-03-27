package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CharacterMover extends Character{

    public CharacterMover(int characterID) {
        this.characterID = characterID;
        this.used = false;
        this.numberOfBanCards = 0;
        this.students = new ArrayList<Student>();
        this.moreSteps = 0;
        switch (characterID){
            case 1:
                this.cost = 1;
                this.numberOfStudents = 4;
                break;
            case 7:
                this.cost = 1;
                this.numberOfStudents = 6;
                break;
            case 10:
                this.cost = 1;
                this.numberOfStudents = 0;
                break;
            case 11:
                this.cost = 2;
                this.numberOfStudents = 4;
                break;
            case 12:
                this.cost = 3;
                this.numberOfStudents = 0;
                break;
        }
    }

    public ArrayList<Student> getStudents() {
        return new ArrayList<>(students);
    }

    @Override
    public void initialPreparation() {
        Bag bag = Bag.getBag();

        for (int i=0; i<numberOfStudents; i++){
            students.add(bag.drawStudent());
        }
    }

    @Override
    public void effect() {
        // TODO
    }


    private boolean effect1 (CreatureColor studentColor, int islandID){
        if(this.getStudents().stream().map(Creature::getColor).collect(Collectors.toList()).contains(studentColor)){
            Island island = Game.getGame().getIslandByID(islandID);
            island.addStudent(new Student(studentColor));
            students.remove(getStudentByColor(students, studentColor));
            students.add(Bag.getBag().drawStudent());
            return true;
        }
        return false;
    }


    private boolean effect7 (CreatureColor cardColor, CreatureColor entranceColor){
       //fino a 3 volte: specificato prima di chiamare l'effetto? determinato da quante volte chiamo l'effetto?
        ArrayList<Student> entranceOfPlayer = Game.getCurrentRound().getCurrentPlayer().getBoard().getEntrance();
        Student studentEntrance = getStudentByColor(entranceOfPlayer, entranceColor);
        Student studentCard = getStudentByColor(students, cardColor);

        if(studentEntrance != null && studentCard != null){
                students.remove(studentCard);
                entranceOfPlayer.add(studentCard);
                entranceOfPlayer.remove(studentEntrance);
                students.add(studentEntrance);

                return true;
        }

        return false;
    }

    //fino a 2 studenti
    private boolean effect10 (CreatureColor hallColor, CreatureColor entranceColor){
        Board board = Game.getCurrentRound().getCurrentPlayer().getBoard();
        ArrayList<Student> entranceOfPlayer = Game.getCurrentRound().getCurrentPlayer().getBoard().getEntrance();
        Student studentEntrance = getStudentByColor(entranceOfPlayer, entranceColor);

        if(studentEntrance != null && board.studentInTable(hallColor)){
            board.removeStudentFromHall(hallColor);
            board.removeStudentFromEntrance(entranceColor);
            board.addStudentToHall(entranceColor);
            board.addStudentToEntrance(hallColor);

            return true;
        }
        return false;
    }

    private boolean effect11(CreatureColor cardColor){
        Board boardPlayer = Game.getCurrentRound().getCurrentPlayer().getBoard();
        Student cardStudent = getStudentByColor(students, cardColor);

        if(cardStudent != null){
            students.remove(cardStudent);
            boardPlayer.addStudentToHall(cardColor);
            students.add(Bag.getBag().drawStudent());
            return true;
        }
        return false;
    }

    private void effect12(CreatureColor colorToRemove){
        ArrayList<Player> players = Game.getGame().getPlayers();

        for(Player player : players){
            int removed = 0;
            boolean haveStudent = true;

            while(removed<3 && haveStudent) {
                haveStudent = player.getBoard().removeStudentFromHall(colorToRemove);
                removed++;
            }
        }
    }

    private Student getStudentByColor(ArrayList<Student> students, CreatureColor studentColor){
        for (Student student : students){
            if (student.getColor().equals(studentColor)){
                return student;
            }
        }
        return null;
    }

}
