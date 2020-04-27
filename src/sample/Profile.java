package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Profile {
    @FXML
    public TextField name;
    @FXML
    public TextField username;
    @FXML
    public TextField age;
    @FXML
    public TextField gender;
    @FXML
    public TextField favteam;
    @FXML
    public TextField favplayer;
    @FXML
    private void initialize() throws SQLException {
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
        Connection conn= DB.getConnection();
        PreparedStatement stmt=conn.prepareStatement("select * from users where user_name=?");
        stmt.setString(1,Controller.usern);
//        stmt.setString(2,pw);
        ResultSet rs=stmt.executeQuery();
        if(rs.next())
        {
            name.setText(rs.getString("name"));
            username.setText(rs.getString("user_name"));
            age.setText(String.valueOf(rs.getInt("age")));
            gender.setText(rs.getString("gender"));
            favteam.setText(rs.getString("fav_team"));
            favplayer.setText(rs.getString("fav_player"));
        }
        username.setDisable(true);
        conn.close();


    }
    public void back(MouseEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("User_panel.fxml"));
        Main.window.setTitle("User Panel");
        Main.window.setScene(new Scene(root));
    }

    public void update(MouseEvent e) throws SQLException {
        Connection conn=DB.getConnection();
        String query = "update users set name = ?,age=?,gender=?,fav_team=?,fav_player=? where user_name = ?";
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString   (1, name.getText());
        preparedStmt.setInt(2, Integer.parseInt(age.getText()));
        preparedStmt.setString(3,gender.getText());
        preparedStmt.setString(4,favteam.getText());
        preparedStmt.setString(5,favplayer.getText());
        preparedStmt.setString(6,Controller.usern);
        // execute the java preparedstatement
        if (preparedStmt.executeUpdate()==1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Updated Successfully");
            alert.show();
        }
        PreparedStatement stmt=conn.prepareStatement("select * from users where user_name=?");
        stmt.setString(1,Controller.usern);
//        stmt.setString(2,pw);

        ResultSet rs=stmt.executeQuery();
        if(rs.next())
        {
            name.setText(rs.getString("name"));
            username.setText(rs.getString("user_name"));
            age.setText(String.valueOf(rs.getInt("age")));
            gender.setText(rs.getString("gender"));
            favteam.setText(rs.getString("fav_team"));
            favplayer.setText(rs.getString("fav_player"));
        }
        conn.close();

    }
}
