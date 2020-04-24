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

public class Teams {
    @FXML
    public TableView table;
    @FXML
    private void initialize() throws SQLException{
        Connection conn=DB.getConnection();
        PreparedStatement stmt=conn.prepareStatement("select * from team");
        ResultSet rs=stmt.executeQuery();
        ArrayList<teamobj> arr= new ArrayList<>();
        while (rs.next()){
            teamobj ob= new teamobj();
            ob.setName(rs.getString(1));
            ob.setCaptain(rs.getString(2));
            ob.setManager(rs.getString(3));
            ob.setHome_stadium(rs.getString(4));
            arr.add(ob);
        }
        rs.first();
        ArrayList<TableColumn> list= new ArrayList<>();
        for (int i=1; i<=rs.getMetaData().getColumnCount(); i++)
        {

                TableColumn<player,String> tc= new TableColumn<>(rs.getMetaData().getColumnName(i));
                tc.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i)));
                list.add(tc);

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
    public void back(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("User_panel.fxml"));
        Main.window.setTitle("User panel");
        Main.window.setScene(new Scene(root));
    }
}
