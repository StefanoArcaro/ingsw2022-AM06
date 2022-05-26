package it.polimi.ingsw.view.GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmationBox {

    public static void display(int exitStatus, Stage mainStage, String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle(message);
        window.setMinWidth(350);
        window.setMinHeight(100);

        Button yesButton = new Button("YES");
        Button noButton = new Button("NO");

        yesButton.setOnAction(event -> {
            window.close();
            if(exitStatus == 0) {
                mainStage.close();
            }
        });
        noButton.setOnAction(event -> window.close());

        HBox layout = new HBox(25);
        layout.getChildren().add(yesButton);
        layout.getChildren().add(noButton);

        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

}
