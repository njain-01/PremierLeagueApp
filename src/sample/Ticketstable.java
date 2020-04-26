package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Ticketstable {
    @FXML
    public TableView table;
    @FXML
    public AnchorPane buypanel;
    @FXML
    public TextField matchid;
    @FXML
    public TextField tier;
    @FXML
    private void initialize() throws SQLException{

        Connection conn=DB.getConnection();
        PreparedStatement stmt=conn.prepareStatement("select * from ticket where user_name=?");
        stmt.setString(1,Controller.usern);
        ResultSet rs=stmt.executeQuery();
        ArrayList<ticketobj> arr= new ArrayList<>();
        while (rs.next()){
            ticketobj ob= new ticketobj();
            ob.setTicket_id(rs.getInt(1));
            ob.setUser_name(rs.getString(2));
            ob.setMatch_id(rs.getInt(3));
            ob.setStadium(rs.getString(4));
            ob.setTicket_tier(rs.getInt(5));
            ob.setPrice(rs.getInt(6));
            arr.add(ob);
        }
        rs.first();
        ArrayList<TableColumn> list= new ArrayList<>();
        for (int i=1; i<=rs.getMetaData().getColumnCount(); i++)
        {
            if (i==1 || i==3 || i==5 || i==6)
            {
                TableColumn<Integer,ticketobj> tc= new TableColumn<>(rs.getMetaData().getColumnName(i));
                tc.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i)));
                list.add(tc);
            }
            else{
                TableColumn<ticketobj,String> tc= new TableColumn<>(rs.getMetaData().getColumnName(i));
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

    public void back(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("User_panel.fxml"));
        Main.window.setTitle("User panel");
        Main.window.setScene(new Scene(root));
    }

    public void buy(MouseEvent mouseEvent) {
        buypanel.setDisable(false);
        buypanel.setVisible(true);
        table.setVisible(false);

    }

    public void buyinpanel(MouseEvent mouseEvent) throws SQLException, IOException {
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

        stmt=conn.prepareStatement("select count(*) from schedule");
        rs=stmt.executeQuery();
        int maxmid=0;
        if (rs.next()){
            maxmid=rs.getInt(1);
        }
        int mid=0;
        int tierr=0;
        try{
            mid= Integer.parseInt(matchid.getText());
            tierr=Integer.parseInt(tier.getText());
        }catch (Exception e){
            buypanel.setDisable(true);
            buypanel.setVisible(false);
            table.setVisible(true);
        }

        if (mid<=maxmid && tierr<=4 && tierr>=1)
        {
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
            Parent root = FXMLLoader.load(getClass().getResource("ticketstable.fxml"));
            Main.window.setTitle("Tickets");
            Main.window.setScene(new Scene(root));

        }
        else {
            buypanel.setDisable(true);
            buypanel.setVisible(false);
            table.setVisible(true);
        }

    }

    public void cancel(MouseEvent mouseEvent) {
        buypanel.setDisable(true);
        buypanel.setVisible(false);
        table.setVisible(true);
    }
}
