package it.polimi.ingsw.view.GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    public static void display(String title, String message){
        Stage window = new Stage();

        // IMPORTANT: THIS METHOD IS USED TO AVOID THE USER TO CLICK OTHER WINDOWS,
        // WHICH MEANS THAT THE USER CANNOT NAVIGATE BETWEEN THEM, IT SIMPLY "BLOCKS"
        // THE CURRENT WINDOW.
        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label(message);
        Button closeButton = new Button("OK");
        closeButton.setOnAction(event -> window.close());

        VBox layout = new VBox(20);
        layout.getChildren().addAll(label,closeButton);

        // TO ALIGN THE LAYOUT !!!
        layout.setAlignment(Pos.CENTER);

        /*Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();*/

    }

}
