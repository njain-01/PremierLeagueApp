package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
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
import java.util.Arrays;

public class Fixtures {
    @FXML
    public TableView table;
    @FXML
    public ChoiceBox choiceBox;
    @FXML
    private void initialize() throws SQLException {
        Connection conn=DB.getConnection();
        PreparedStatement stmt=conn.prepareStatement("select * from schedule where match_id not in (select match_id from result);");
        ResultSet rs=stmt.executeQuery();
        ArrayList<fixtures_obj> arr= new ArrayList<>();
        while (rs.next()){
            fixtures_obj ob= new fixtures_obj();
            ob.setMatch_id(rs.getInt(1));
            ob.setTeam_home(rs.getString(2));
            ob.setTeam_other(rs.getString(3));
            ob.setMatch_date(rs.getString(4));
            ob.setRefree(rs.getInt(5));
            arr.add(ob);
        }
        rs.first();
        ArrayList<TableColumn> list= new ArrayList<>();
        for (int i=1; i<=rs.getMetaData().getColumnCount(); i++)
        {
            if (rs.getMetaData().getColumnName(i).contentEquals("match_id")) {
                TableColumn<Integer,fixtures_obj> tc= new TableColumn<>("Match ID");
                tc.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i)));
                list.add(tc);
            }
            else if (rs.getMetaData().getColumnName(i).contentEquals("refree"))
            {
                TableColumn<Integer,fixtures_obj> tc= new TableColumn<>("Refree ID");
                tc.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i)));
                list.add(tc);
            }
            else if(rs.getMetaData().getColumnName(i).contentEquals("team_home")){
                TableColumn<fixtures_obj,String> tc= new TableColumn<>("Home Team");
                tc.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i)));
                list.add(tc);
            }
            else if(rs.getMetaData().getColumnName(i).contentEquals("team_other")){
                TableColumn<fixtures_obj,String> tc= new TableColumn<>("Away Team");
                tc.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i)));
                list.add(tc);
            }
            else
            {
                TableColumn<fixtures_obj,String> tc= new TableColumn<>("Match Date&Time");
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


        String teams[]={"Arsenal","Bournemouth","Brighton and Hove","Burnley","Chelsea","Crystal Palace","Everton","Huddersfield","Leicester City","Liverpool","Manchester City","Manchester United","Newcastle United","Southampton","Stoke City","Swansea","Tottenham","Watford","West Brom","West Ham"};
//        ArrayList<String> arr2= new ArrayList<String>(Arrays.asList(teams));
//        choiceBox.getItems().addAll(FXCollections.observableArrayList(teams));
        choiceBox.setItems(FXCollections.observableArrayList(teams));


        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value)
            {
                String tname=teams[new_value.intValue()];
                table.getItems().removeAll(table.getItems());
                Connection conn=DB.getConnection();
                PreparedStatement stmt= null;
                try {
                    stmt = conn.prepareStatement("select * from (select * from schedule where match_id not in (select match_id from result)) T where team_home=? or team_other=?");
                    stmt.setString(1,tname);
                    stmt.setString(2,tname);
                    ResultSet rs=stmt.executeQuery();
                    ArrayList<fixtures_obj> arr= new ArrayList<>();
                    while (rs.next()){
                        fixtures_obj ob= new fixtures_obj();
                        ob.setMatch_id(rs.getInt(1));
                        ob.setTeam_home(rs.getString(2));
                        ob.setTeam_other(rs.getString(3));
                        ob.setMatch_date(rs.getString(4));
                        ob.setRefree(rs.getInt(5));
                        arr.add(ob);
                    }
                    while (!arr.isEmpty()){
                        table.getItems().add(arr.remove(0));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }


            }
        });

    }
    public void back(MouseEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("User_panel.fxml"));
        Main.window.setTitle("User Panel");
        Main.window.setScene(new Scene(root));
    }
}
