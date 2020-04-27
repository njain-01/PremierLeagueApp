package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;


public class Quiz {
    @FXML
    public RadioButton a1;
    public RadioButton a2;
    public  RadioButton a3;
    public void submit(MouseEvent mouseEvent) throws SQLException, IOException {
        if (a1.isSelected() && a2.isSelected() && a3.isSelected()){
            int mid=(new Random().nextInt(11))+20;
            int tierr=2;
            Connection conn=DB.getConnection();
            PreparedStatement stmt=conn.prepareStatement("select max(ticket_id) from ticket");
//        stmt.setString(1,Controller.usern);
            ResultSet rs=stmt.executeQuery();
            int tid=0;
            if (rs.next()){
                tid=rs.getInt(1);
                tid++;
                System.out.println(tid);
            }
            int price=tierr*1000;
            stmt=conn.prepareStatement("select home_stadium from team,schedule where match_id=? and team_home=name");
            stmt.setInt(1,mid);
            rs=stmt.executeQuery();
            String stadium= new String("Falmer Stadium");
            if(rs.next())
            {
                stadium=rs.getString(1);
            }
            stmt=conn.prepareStatement("insert into ticket values(?,?,?,?,?,?)");
            stmt.setInt(1,tid);
            stmt.setString(2,Controller.usern);
            stmt.setInt(3,mid);
            stmt.setString(4,stadium);
            stmt.setInt(5,tierr);
            stmt.setInt(6,price);
            stmt.execute();
            Controller.quiz=false;
            String temp="Congratulations! You won a ticket for Match ID ";
            temp=temp.concat(String.valueOf(mid));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(temp);
            alert.show();
            Parent root = FXMLLoader.load(getClass().getResource("ticketstable.fxml"));
            Main.window.setTitle("Tickets");
            Main.window.setScene(new Scene(root));
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Wrong Answer! Try again ");
            alert.show();
        }
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("User_panel.fxml"));
        Main.window.setTitle("User panel");
        Main.window.setScene(new Scene(root));
    }
}
