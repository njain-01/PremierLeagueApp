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

public class PointsTable {
    @FXML
    public TableView table;
    @FXML
    public ChoiceBox choiceBox;
    @FXML
    private void initialize() throws SQLException {
        Connection conn=DB.getConnection();
        PreparedStatement stmt=conn.prepareStatement("select * from points_table");
        ResultSet rs=stmt.executeQuery();
        ArrayList<teaminPT> arr= new ArrayList<>();
        while (rs.next()){
            teaminPT ob= new teaminPT();
            ob.setName(rs.getString("team"));
            ob.setPlayed(rs.getInt("played"));
            ob.setWon(rs.getInt("won"));
            ob.setDraw(rs.getInt("draw"));
            ob.setLost(rs.getInt("lost"));
            ob.setGa(rs.getInt("ga"));
            ob.setGd(rs.getInt("gd"));
            ob.setGf(rs.getInt("gf"));
            ob.setPoints(rs.getInt("points"));
            arr.add(ob);
        }
        rs.first();
        ArrayList<TableColumn> list= new ArrayList<>();
        for (int i=1; i<=rs.getMetaData().getColumnCount(); i++)
        {
            if (rs.getMetaData().getColumnName(i).contentEquals("team")) {
                TableColumn<teaminPT,String> tc= new TableColumn<>("Team");
                tc.setCellValueFactory(new PropertyValueFactory<>("name"));
                list.add(tc);
            }
            else
            {
                TableColumn<Integer,teaminPT> tc= new TableColumn<>(rs.getMetaData().getColumnName(i));
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

        String[] options={"won","draw","lost","gf","ga","gd","points"};
        choiceBox.setItems(FXCollections.observableArrayList(options));


        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value)
            {

                int cname=new_value.intValue()+3;
//                System.out.println(cname);
                table.getItems().removeAll(table.getItems());
                Connection conn=DB.getConnection();
                PreparedStatement stmt= null;
                try {
                    stmt = conn.prepareStatement("select * from points_table order by ? desc");
//                    stmt.setString(1,cname);
                    stmt.setInt(1,cname);
                    ResultSet rs=stmt.executeQuery();
                    ArrayList<teaminPT> arr= new ArrayList<>();
                    while (rs.next()){
//                        System.out.println("yy");
                        teaminPT ob= new teaminPT();
                        ob.setName(rs.getString("team"));
                        ob.setPlayed(rs.getInt("played"));
                        ob.setWon(rs.getInt("won"));
                        ob.setDraw(rs.getInt("draw"));
                        ob.setLost(rs.getInt("lost"));
                        ob.setGa(rs.getInt("ga"));
                        ob.setGd(rs.getInt("gd"));
                        ob.setGf(rs.getInt("gf"));
                        ob.setPoints(rs.getInt("points"));
//                        System.out.println(ob);
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
        Main.window.setTitle("User panel");
        Main.window.setScene(new Scene(root));
    }

}
