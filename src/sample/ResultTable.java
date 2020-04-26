package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResultTable {
    @FXML
    public TableView table;
    @FXML
    private void initialize() throws SQLException{
        Connection conn=DB.getConnection();
        PreparedStatement stmt=conn.prepareStatement("select result. match_id,team_home,goalA,team_other,goalB from result,schedule where result.match_id=schedule.match_id");
        ResultSet rs=stmt.executeQuery();
        ArrayList<resultobj> arr= new ArrayList<>();
        while (rs.next()){
            resultobj ob= new resultobj();
            ob.setMatch_id(rs.getInt(1));
            ob.setTeam_home(rs.getString(2));
            ob.setGoalA(rs.getInt(3));
            ob.setTeam_other(rs.getString(4));
            ob.setGoalB(rs.getInt(5));
            arr.add(ob);
        }
        rs.first();
        ArrayList<TableColumn> list= new ArrayList<>();
        for (int i=1; i<=rs.getMetaData().getColumnCount(); i++)
        {
            if (i==1 || i==3 || i==5)
            {
                TableColumn<Integer,resultobj> tc= new TableColumn<>(rs.getMetaData().getColumnName(i));
                tc.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i)));
                list.add(tc);
            }
            else{
                TableColumn<resultobj,String> tc= new TableColumn<>(rs.getMetaData().getColumnName(i));
                tc.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i)));
                list.add(tc);
            }

        }
//        int j=0;
        while (!list.isEmpty()){
            table.getColumns().add(list.remove(0));
        }
        while (!arr.isEmpty()){
            table.getItems().add(arr.remove(0));
        }

//        System.out.println(arr.get(1));
        conn.close();

    }
    public void black(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("User_panel.fxml"));
        Main.window.setTitle("User panel");
        Main.window.setScene(new Scene(root));
    }
}
