package it.polimi.ingsw.view.GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicInteger;

public class ConfirmationBox {

    /**
     * Displays a popup that asks the user to confirm whether
     * they want to close the application.
     * @param exitStatus a code to specify whether the action of closing the application
     *                   has to be taken by this method or instead it will be done outside.
     * @param mainStage reference to the application's main window.
     * @param message the message to display in the popup.
     */
    public static int display(int exitStatus, Stage mainStage, String message){
        Stage window = new Stage();
        AtomicInteger status = new AtomicInteger(exitStatus);

        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle(message);
        window.setMinWidth(350);
        window.setMinHeight(100);

        Button yesButton = new Button("YES");
        Button noButton = new Button("NO");

        window.setOnCloseRequest(e -> status.set(2));

        yesButton.setOnAction(event -> {
            window.close();
            if(exitStatus == 0) {
                mainStage.close();
            }
        });

        noButton.setOnAction(event -> {
            window.close();
            status.set(2);
        });

        HBox layout = new HBox(25);
        layout.getChildren().add(yesButton);
        layout.getChildren().add(noButton);

        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return status.get();
    }

}
