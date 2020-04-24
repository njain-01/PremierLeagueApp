package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Leagueinfo {

    public void players(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("playerTable.fxml"));
        Main.window.setTitle("Players");
        Main.window.setScene(new Scene(root));
    }

    public void teams(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("teams.fxml"));
        Main.window.setTitle("Teams");
        Main.window.setScene(new Scene(root));
    }
}
