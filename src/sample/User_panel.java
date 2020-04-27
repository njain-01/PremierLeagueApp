package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class User_panel {
    @FXML
    public Button lquiz;
    @FXML
    private void initialize()
    {
        if (!Controller.quiz){
            lquiz.setDisable(true);
        }
    }
    public void profile(MouseEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("profile.fxml"));
        Main.window.setTitle("My Profile");
        Main.window.setScene(new Scene(root));
        System.out.println(Controller.usern);
    }
    public void pt( MouseEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Points_table.fxml"));
        Main.window.setTitle("Points Table");
        Main.window.setScene(new Scene(root));
    }
    public void fixture(MouseEvent e) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("fixtures.fxml"));
        Main.window.setTitle("Fixtures");
        Main.window.setScene(new Scene(root));
    }
    public void leagueinfo(MouseEvent e) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("leagueinfo.fxml"));
        Main.window.setTitle("League Info");
        Main.window.setScene(new Scene(root));
    }

    public void ticket(MouseEvent mouseEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("ticketstable.fxml"));
        Main.window.setTitle("Tickets");
        Main.window.setScene(new Scene(root));
    }

    public void stats(MouseEvent mouseEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("stats.fxml"));
        Main.window.setTitle("League Stats");
        Main.window.setScene(new Scene(root));
    }

    public void result(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("resultTable.fxml"));
        Main.window.setTitle("Results");
        Main.window.setScene(new Scene(root));
    }

    public void logout(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Main.window.setTitle("Log in");
        Main.window.setScene(new Scene(root));
    }

    public void fantasy(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("quiz.fxml"));
        Main.window.setTitle("League Quiz");
        Main.window.setScene(new Scene(root));
    }
}
