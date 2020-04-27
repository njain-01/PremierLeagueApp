package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class AdminPanel {

    @FXML
    public Button schedule;
    @FXML
    public Button result;
    @FXML
    public Button sponsor;
    @FXML
    public Button ticket;
    @FXML
    public Label id;
    @FXML
    public Label name;
    @FXML
    public Label dept;
    @FXML
    public Label contact;
    @FXML
    public Button logOut;



    public void scheduleFixture(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("newFixture.fxml"));
        Main.window.setTitle("Fixtures");
        Main.window.setScene(new Scene(root));
    }

    public void simulateMatch(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("simFixture.fxml"));
        Main.window.setTitle("Match Results");
        Main.window.setScene(new Scene(root));
    }

    public void ticketSales(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ticketSales.fxml"));
        Main.window.setTitle("Ticket Sales");
        Main.window.setScene(new Scene(root));
    }

    public void showDetails(int anInt, String string, String string1, int anInt1) {
        id.setText(String.valueOf(anInt));
        contact.setText(String.valueOf(anInt1));
        name.setText(string);
        dept.setText(string1);
        if (string1.equals("stats")){
            schedule.setDisable(true);
            result.setDisable(true);
        }
        else if (string1.equals("finance")){
            schedule.setDisable(true);
            result.setDisable(true);
            ticket.setDisable(true);
        }
        else if (string1.equals("fixtures")){
            ticket.setDisable(true);
        }
    }

    public void goBack(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Main.window.setTitle("Sign in");
        Main.window.setScene(new Scene(root));
    }

    public void teamDetails(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("teamStats.fxml"));
        Main.window.setTitle("Team Stats");
        Main.window.setScene(new Scene(root));
        
    }
}
