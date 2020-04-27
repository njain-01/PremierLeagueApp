package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Stats {
    @FXML
    public Text ansbox;
    @FXML
    public ChoiceBox choicebox;
    @FXML
    private void initialize() throws SQLException{
        String opts[]={"Player with most goals","Player with most assists","Max number of goals by any player","Team with most goals in a single match","Player with most goals in a single match"};
        choicebox.setItems(FXCollections.observableArrayList(opts));


        choicebox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value)
            {
                int opt=new_value.intValue();
                Connection conn=DB.getConnection();
                PreparedStatement stmt= null;

                if (opt==0)
                {
                    ResultSet rs=null;
                    try {
                        stmt=conn.prepareStatement("SELECT name from player where player_id in (SELECT player from(SELECT player, Count(*) myCount FROM goal GROUP BY player) as T where myCount in (SELECT MAX(myCount) from(SELECT player, Count(*) myCount FROM goal GROUP BY player) as T))");
                        rs=stmt.executeQuery();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (rs.next())
                        {
                            ansbox.setText(rs.getString(1));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }else if(opt==1){

                    ResultSet rs=null;
                    try {
                        stmt=conn.prepareStatement("SELECT name from player where player_id in (SELECT assist from(SELECT assist, Count(*) myCount FROM goal GROUP BY assist) as T where myCount in (SELECT MAX(myCount) from(SELECT assist, Count(*) myCount FROM goal GROUP BY assist) as T))");
                        rs=stmt.executeQuery();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (rs.next())
                        {
                            ansbox.setText(rs.getString(1));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else if(opt==2){

                    ResultSet rs=null;
                    try {
                        stmt=conn.prepareStatement("SELECT MAX(myCount) from(SELECT player, Count(*) myCount FROM goal GROUP BY player)as T");
                        rs=stmt.executeQuery();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (rs.next())
                        {
                            ansbox.setText(rs.getString(1));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else if(opt==3){

                    ResultSet rs=null;
                    try {
                        stmt=conn.prepareStatement("SELECT team from(SELECT team, Count(*) myCount FROM goal GROUP BY team,match_id) as T where myCount in (SELECT MAX(myCount) from(SELECT team, Count(*) myCount FROM goal GROUP BY team,match_id) as T)");
                        rs=stmt.executeQuery();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (rs.next())
                        {
                            ansbox.setText(rs.getString(1));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else if(opt==4){

                    ResultSet rs=null;
                    try {
                        stmt=conn.prepareStatement("SELECT name from player where player_id in (SELECT player from(SELECT player, Count(*) myCount FROM goal GROUP BY player, match_id) as T where myCount in (SELECT MAX(myCount) from(SELECT player, Count(*) myCount FROM goal GROUP BY player, match_id) as T))");
                        rs=stmt.executeQuery();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (rs.next())
                        {
                            ansbox.setText(rs.getString(1));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    public void back(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("User_panel.fxml"));
        Main.window.setTitle("User panel");
        Main.window.setScene(new Scene(root));
    }
}
