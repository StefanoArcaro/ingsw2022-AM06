package it.polimi.ingsw.view.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    /**
     * Displays a popup with the specified title and message.
     * @param title the popup's title.
     * @param message the popup's message.
     */
    public static void display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label(message);
        label.setPadding(new Insets(20));
        Button closeButton = new Button("OK");
        closeButton.setOnAction(event -> window.close());

        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, closeButton);

        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

}
