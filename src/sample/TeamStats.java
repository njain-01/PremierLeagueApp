package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class TeamStats {
    public Label manager;
    public Label stadium;
    public Label money;
    public Label fans;
    public Label captain;
    public Label p1;
    public Label p2;
    public Label p3;
    public Label p4;
    public Label p5;
    public Label p6;
    public Label sp1;
    public Label sp2;
    public Label sp3;
    public ImageView teamLogo;
    public ImageView sp1Logo;
    public ImageView sp2Logo;
    public ImageView sp3Logo;
    public Connection conn = DB.getConnection();
    public ChoiceBox<String> teamChoser;
    public Button back;

    @FXML
    public void initialize() throws SQLException {
        manager.setText("-");
        stadium.setText("-");
        money.setText("-");
        fans.setText("-");
        captain.setText("-");
        PreparedStatement stmt1 = conn.prepareStatement("select  team from points_table where points in (select max(points) from points_table)");
        PreparedStatement stmt2 = conn.prepareStatement("select  team from points_table where gf in (select max(gf) from points_table)");
        PreparedStatement stmt3 = conn.prepareStatement("select name from player,(select player as C,max(total) from (select player, count(player) as total from goal group by player) as A) as X where player.player_id=X.C");
        PreparedStatement stmt4 = conn.prepareStatement("select name,max(capacity) from stadium");
        PreparedStatement stmt5 = conn.prepareStatement("select fav_team,max(total) from (select fav_team, count(fav_team) as total from users group by fav_team) as A");
        PreparedStatement stmt6 = conn.prepareStatement("select team,max(sponsor_rev+ticket_revenue-cost) from cash_flows");
        PreparedStatement[] ps = new PreparedStatement[]{stmt1,stmt2,stmt3,stmt4,stmt5,stmt6};
        String[] output = new String[6];
        for (int i = 0; i <6 ; i++) {
            String ans = "";
            ResultSet rs = ps[i].executeQuery();
            while (rs.next()){
                ans=ans.concat(rs.getString(1));
            }
            output[i]=ans;
        }
        System.out.println(Arrays.toString(output));
        p1.setText(output[0]);
        p2.setText(output[1]);
        p3.setText(output[2]);
        p4.setText(output[3]);
        p5.setText(output[4]);
        p6.setText(output[5]);

        String[] teams ={"Arsenal","Bournemouth","Brighton and Hove","Burnley","Chelsea","Crystal Palace","Everton","Huddersfield","Leicester City","Liverpool","Manchester City","Manchester United","Newcastle United","Southampton","Stoke City","Swansea","Tottenham","Watford","West Brom","West Ham"};
        teamChoser.setItems(FXCollections.observableArrayList(teams));
        teamChoser.setValue(teams[0]);
        PreparedStatement stmt7 = conn.prepareStatement("select * from team_sponsor where team=?");
        stmt7.setString(1,teams[0]);
        ResultSet rs = stmt7.executeQuery();
        Sponsor[] sponsors = new Sponsor[3];
        int i=0;
        while (rs.next()){
            sponsors[i]=new Sponsor(rs.getString(2),rs.getInt(3),rs.getString(5));
            i++;
        }
        teamLogo.setImage(new Image(getClass().getResourceAsStream("/Logos/"+teams[0]+".png")));
        System.out.println(sponsors[0].path);
        System.out.println("/Logos/"+teams[0]+".png");
        sp1Logo.setImage(new Image(getClass().getResourceAsStream(sponsors[0].path)));
        sp2Logo.setImage(new Image(getClass().getResourceAsStream(sponsors[1].path)));
        sp3Logo.setImage(new Image(getClass().getResourceAsStream(sponsors[2].path)));
        sp1.setText(sponsors[0].name);
        sp2.setText(sponsors[2].name);
        sp3.setText(sponsors[1].name);
        PreparedStatement stmt8 = conn.prepareStatement("select * from team where name=?");
        stmt8.setString(1,teams[0]);
        rs=stmt8.executeQuery();
        while (rs.next()){
            manager.setText(rs.getString(3));
            captain.setText(rs.getString(2));
            stadium.setText(rs.getString(4));
        }
        PreparedStatement stmt9 = conn.prepareStatement("select ticket_revenue+sponsor_rev-cost from cash_flows where team=?");
        stmt9.setString(1,teams[0]);
        rs=stmt9.executeQuery();
        while (rs.next()){
            money.setText("$".concat(String.valueOf(rs.getInt(1))));
        }
        PreparedStatement stmt10 = conn.prepareStatement("select count(user_name) from users where fav_team=?");
        stmt10.setString(1,teams[0]);
        rs=stmt10.executeQuery();
        while (rs.next()){
            fans.setText(String.valueOf(rs.getInt(1)));
        }

        teamChoser.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                PreparedStatement stmt7 = null;
                try {
                    stmt7 = conn.prepareStatement("select * from team_sponsor where team=?");
                    stmt7.setString(1,teams[(int)t1]);
                    ResultSet rs = stmt7.executeQuery();
                    Sponsor[] sponsors = new Sponsor[3];
                    int i=0;
                    while (rs.next()){
                        sponsors[i]=new Sponsor(rs.getString(2),rs.getInt(3),rs.getString(5));
                        i++;
                    }
                    teamLogo.setImage(new Image(getClass().getResourceAsStream("/Logos/"+teams[(int)t1]+".png")));
                    System.out.println(sponsors[0].path);
                    System.out.println("/Logos/"+teams[(int)t1]+".png");
                    sp1Logo.setImage(new Image(getClass().getResourceAsStream(sponsors[0].path)));
                    sp2Logo.setImage(new Image(getClass().getResourceAsStream(sponsors[1].path)));
                    sp3Logo.setImage(new Image(getClass().getResourceAsStream(sponsors[2].path)));
                    sp1.setText(sponsors[0].name);
                    sp2.setText(sponsors[2].name);
                    sp3.setText(sponsors[1].name);

                    PreparedStatement stmt8 = conn.prepareStatement("select * from team where name=?");
                    stmt8.setString(1,teams[(int)t1]);
                    rs=stmt8.executeQuery();
                    while (rs.next()){
                        manager.setText(rs.getString(3));
                        captain.setText(rs.getString(2));
                        stadium.setText(rs.getString(4));
                    }
                    PreparedStatement stmt9 = conn.prepareStatement("select ticket_revenue+sponsor_rev-cost from cash_flows where team=?");
                    stmt9.setString(1,teams[(int)t1]);
                    rs=stmt9.executeQuery();
                    while (rs.next()){
                        money.setText("$".concat(String.valueOf(rs.getInt(1))));
                    }
                    PreparedStatement stmt10 = conn.prepareStatement("select count(user_name) from users where fav_team=?");
                    stmt10.setString(1,teams[(int)t1]);
                    rs=stmt10.executeQuery();
                    while (rs.next()){
                        fans.setText(String.valueOf(rs.getInt(1)));
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });



    }

    public void goBack(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("admin_panel.fxml"));
        Main.window.setTitle("Admin Panel");
        Main.window.setScene(new Scene(root));
    }
}
