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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlayerTable {
    @FXML
    public TableView table;
    @FXML
    public TextField textfield;
    @FXML
    public ChoiceBox choiceBox;
    @FXML
    private void initialize() throws SQLException {
        Connection conn=DB.getConnection();
        PreparedStatement stmt=conn.prepareStatement("select * from player");
        ResultSet rs=stmt.executeQuery();
        ArrayList<player> arr= new ArrayList<>();
        while (rs.next()){
            player ob= new player();
            ob.setPlayer_id(rs.getInt(1));
            ob.setName(rs.getString(2));
            ob.setTeam(rs.getString(3));
            ob.setAge(rs.getInt(4));
            ob.setPosition(rs.getString(5));
            ob.setNationality(rs.getString(6));
            ob.setRating(rs.getInt(7));
            ob.setSpeciality(rs.getString(8));
            ob.setJersey(rs.getInt(9));
            arr.add(ob);
        }
        rs.first();
        ArrayList<TableColumn> list= new ArrayList<>();
        for (int i=1; i<=rs.getMetaData().getColumnCount(); i++)
        {
            if (i==1 || i==4 || i==7 || i==9)
            {
                TableColumn<Integer,teaminPT> tc= new TableColumn<>(rs.getMetaData().getColumnName(i));
                tc.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i)));
                list.add(tc);
            }
            else{
                TableColumn<player,String> tc= new TableColumn<>(rs.getMetaData().getColumnName(i));
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
                    stmt = conn.prepareStatement("select * from player where team=?");
                    stmt.setString(1,tname);
//                    stmt.setString(2,tname);
                    ResultSet rs=stmt.executeQuery();
                    ArrayList<player> arr= new ArrayList<>();
                    while (rs.next()){
                        player ob= new player();
                        ob.setPlayer_id(rs.getInt(1));
                        ob.setName(rs.getString(2));
                        ob.setTeam(rs.getString(3));
                        ob.setAge(rs.getInt(4));
                        ob.setPosition(rs.getString(5));
                        ob.setNationality(rs.getString(6));
                        ob.setRating(rs.getInt(7));
                        ob.setSpeciality(rs.getString(8));
                        ob.setJersey(rs.getInt(9));
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
    public void back(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("User_panel.fxml"));
        Main.window.setTitle("User panel");
        Main.window.setScene(new Scene(root));
    }

    public void go(MouseEvent mouseEvent) {
        String txt= textfield.getText();
        txt=txt.concat("%");
        txt="%".concat(txt);
//        System.out.println(txt);
        table.getItems().removeAll(table.getItems());
        Connection conn=DB.getConnection();
        PreparedStatement stmt= null;
        try {
            stmt = conn.prepareStatement("select * from player where name like ?");
            stmt.setString(1,txt);
            ResultSet rs=stmt.executeQuery();
            ArrayList<player> arr= new ArrayList<>();
            while (rs.next()){
                player ob= new player();
                ob.setPlayer_id(rs.getInt(1));
                ob.setName(rs.getString(2));
                ob.setTeam(rs.getString(3));
                ob.setAge(rs.getInt(4));
                ob.setPosition(rs.getString(5));
                ob.setNationality(rs.getString(6));
                ob.setRating(rs.getInt(7));
                ob.setSpeciality(rs.getString(8));
                ob.setJersey(rs.getInt(9));
                arr.add(ob);
            }
            while (!arr.isEmpty()){
                table.getItems().add(arr.remove(0));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(MouseEvent mouseEvent) {
    }

    public void update(MouseEvent mouseEvent) {
    }
}
