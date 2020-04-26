package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Controller {
    @FXML
    public TextField field1;
    public PasswordField field2;
    public RadioButton user;
    public RadioButton organiser;
    public static String usern;
    public void Doitt(MouseEvent e) throws SQLException, IOException {
        Connection conn= DB.getConnection();
        String id = field1.getText();
        String pw = field2.getText();
        if (((RadioButton)user).isSelected()){
            PreparedStatement stmt=conn.prepareStatement("select * from users where user_name=? and password=?");
            stmt.setString(1,id);
            stmt.setString(2,pw);
            ResultSet rs=stmt.executeQuery();
            if (rs.next())
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                usern=rs.getString("user_name");
                alert.setContentText("Logged In Successfully "+usern);
                alert.show();
                Parent root = FXMLLoader.load(getClass().getResource("User_panel.fxml"));
                Main.window.setTitle("User Panel");
                Main.window.setScene(new Scene(root));
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Wrong username or password");
                alert.show();
            }

        }
        if (((RadioButton)organiser).isSelected()){
            PreparedStatement stmt=conn.prepareStatement("select * from organiser where emp_name=? and password=?");
            stmt.setString(1,id);
            stmt.setString(2,pw);
            ResultSet rs=stmt.executeQuery();
            if (rs.next())
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                usern=rs.getString("emp_name");
                alert.setContentText("Logged In Successfully "+usern);
                alert.show();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_panel.fxml"));
                Parent root = loader.load();
                AdminPanel ap = loader.getController();
                ap.showDetails(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4));
                Main.window.setTitle("Admin panel");
                Main.window.setScene(new Scene(root));
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Wrong username or password");
                alert.show();
            }

        }

//        PreparedStatement stmt=conn.prepareStatement("select * from player");
//        ResultSet rs= stmt.executeQuery();
//        if (rs.next())
//            System.out.println(rs.getString(2));
    }
}
