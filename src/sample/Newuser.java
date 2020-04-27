package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Newuser {
    @FXML
    public TextField name;
    @FXML
    public TextField username;
    @FXML
    public TextField age;
    @FXML
    public ChoiceBox choicebox2;
    @FXML
    public ChoiceBox choicebox1;
    @FXML
    public TextField favplayer;
    @FXML
    public TextField password;
    @FXML
    private void initialize() throws SQLException {
        String teams[]={"Arsenal","Bournemouth","Brighton and Hove","Burnley","Chelsea","Crystal Palace","Everton","Huddersfield","Leicester City","Liverpool","Manchester City","Manchester United","Newcastle United","Southampton","Stoke City","Swansea","Tottenham","Watford","West Brom","West Ham"};

        choicebox1.setItems(FXCollections.observableArrayList(teams));

        String gender[]={"MALE","FEMALE"};
        choicebox2.setItems(FXCollections.observableArrayList(gender));
        age.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    age.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        favplayer.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    favplayer.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

    }
    public void update(MouseEvent mouseEvent) throws SQLException, IOException {
        String teams[]={"Arsenal","Bournemouth","Brighton and Hove","Burnley","Chelsea","Crystal Palace","Everton","Huddersfield","Leicester City","Liverpool","Manchester City","Manchester United","Newcastle United","Southampton","Stoke City","Swansea","Tottenham","Watford","West Brom","West Ham"};
        String gender[]={"MALE","FEMALE"};
        int genind=choicebox2.getSelectionModel().getSelectedIndex();
        int teamind=choicebox1.getSelectionModel().getSelectedIndex();
        Connection conn= DB.getConnection();
        PreparedStatement stmt=conn.prepareStatement("insert into users values(?,?,?,?,?,?,?)");
        if (!name.getText().contentEquals("") && !age.getText().contentEquals("") && !favplayer.getText().contentEquals("") && !username.getText().contentEquals("") && !password.getText().contentEquals("") && genind!=-1 && teamind!=-1){
            stmt.setString(1,username.getText());
            stmt.setString(2,name.getText());
            stmt.setInt(3, Integer.parseInt(age.getText()));
            stmt.setString(4,gender[genind]);
            stmt.setString(5,password.getText());
            stmt.setString(6,teams[teamind]);
            stmt.setInt(7, Integer.parseInt(favplayer.getText()));
            if(!stmt.execute()){
                System.out.println("Added succesfully");
                Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
                Main.window.setTitle("Log In");
                Main.window.setScene(new Scene(root));
            }

        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Wrong credentials");
            alert.show();
        }


    }

    public void back(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Main.window.setTitle("Log In");
        Main.window.setScene(new Scene(root));
    }
}
