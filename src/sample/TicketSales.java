/*
Tickets Remaining
select A.match_id,home_stadium,capacity,ticket_sold, capacity-ticket_sold as 'Remaining Tickets' from
    (select match_id, home_stadium,capacity from
        schedule,team,stadium where
            team.name=schedule.team_home and team.home_stadium=stadium.name
    order by match_id)
    as A,
    (select match_id,count(ticket_id) as ticket_sold from
        ticket
    group by match_id)
    as B
    where A.match_id = B.match_id;
 */
/*
Top 5 matches all-time
select match_id,count(ticket_id) as tickets_sold from ticket group by match_id  order by tickets_sold desc limit 5;
 */
/*
Top 5 upcoming matches
select match_id,count(ticket_id) as tickets_sold from ticket where match_id not in (select match_id from result) group by match_id  order by tickets_sold desc limit 5;
 */

/*
Top Supporters
select ticket.user_name,users.fav_team, count(ticket_id) as ticket_buy from ticket,users where ticket.user_name=users.user_name group by user_name order by ticket_buy desc;
 */

/*
Top Viewership
select team_home, sum(ticket_sold) as match_views from (
select A.match_id,team_home,home_stadium,capacity,ticket_sold, capacity-ticket_sold as 'Remaining Tickets' from
    (select match_id,team_home,home_stadium,capacity from schedule,team,stadium where team.name=schedule.team_home and team.home_stadium=stadium.name order by match_id) as A,
    (select match_id,count(ticket_id) as ticket_sold from ticket group by match_id) as B
where A.match_id = B.match_id
UNION
select A.match_id,team_home,home_stadium,capacity,ticket_sold, capacity-ticket_sold as 'Remaining Tickets' from
    (select match_id,team_other as team_home,home_stadium,capacity from schedule,team,stadium where team.name=schedule.team_home and team.home_stadium=stadium.name order by match_id) as A,
    (select match_id,count(ticket_id) as ticket_sold from ticket group by match_id) as B
where A.match_id = B.match_id ) as C
group by team_home order by match_views desc limit 5

 */

package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class TicketSales {

    public ChoiceBox<String> criteria;
    public ChoiceBox<String> filter;
    public Connection conn = DB.getConnection();

    public String[] queries = new String[]{"select A.match_id,home_stadium,capacity,ticket_sold, capacity-ticket_sold as 'Remaining_Tickets' from (select match_id, home_stadium,capacity from schedule,team,stadium where team.name=schedule.team_home and team.home_stadium=stadium.name order by match_id) as A, (select match_id,count(ticket_id) as ticket_sold from ticket group by match_id) as B where A.match_id = B.match_id order by remaining_tickets" ,
            "select match_id,count(ticket_id) as tickets_sold from ticket group by match_id  order by tickets_sold",
            "select match_id,count(ticket_id) as tickets_sold from ticket where match_id not in (select match_id from result) group by match_id  order by tickets_sold",
            "select ticket.user_name,users.fav_team, count(ticket_id) as ticket_buy from ticket,users where ticket.user_name=users.user_name group by user_name order by ticket_buy",
            "select team_home, sum(ticket_sold) as match_views from (select A.match_id,team_home,home_stadium,capacity,ticket_sold, capacity-ticket_sold as 'Remaining Tickets' from (select match_id,team_home,home_stadium,capacity from schedule,team,stadium where team.name=schedule.team_home and team.home_stadium=stadium.name order by match_id) as A, (select match_id,count(ticket_id) as ticket_sold from ticket group by match_id) as B where A.match_id = B.match_id UNION select A.match_id,team_home,home_stadium,capacity,ticket_sold, capacity-ticket_sold as 'Remaining Tickets' from (select match_id,team_other as team_home,home_stadium,capacity from schedule,team,stadium where team.name=schedule.team_home and team.home_stadium=stadium.name order by match_id) as A, (select match_id,count(ticket_id) as ticket_sold from ticket group by match_id) as B where A.match_id = B.match_id ) as C group by team_home order by match_views"};
    public String qty = " LIMIT 5";
    public static String desc = "DESC";
    public TableView<TicketSaleQuery> table;

    @FXML
    TableColumn<TicketSaleQuery,Integer> Match_Id = new TableColumn<>("Match Id");
    @FXML
    TableColumn<TicketSaleQuery,Integer> tickets_sold = new TableColumn<>("Tickets Sold");
    @FXML
    TableColumn<TicketSaleQuery,Integer> Match_Views = new TableColumn<>("Match Views");
    @FXML
    TableColumn<TicketSaleQuery,Integer> Tickets_Bought = new TableColumn<>("Tickets Bought");
    @FXML
    TableColumn<TicketSaleQuery,Integer> Capacity = new TableColumn<>("Capacity");
    @FXML
    TableColumn<TicketSaleQuery,Integer> Remaining_Tickets= new TableColumn<>("Remaining Tickets Id");
    @FXML
    TableColumn<TicketSaleQuery,String> Home_Stadium= new TableColumn<>("Home Stadium");
    @FXML
    TableColumn<TicketSaleQuery,String> Fav_Team= new TableColumn<>("Favourite Team");
    @FXML
    TableColumn<TicketSaleQuery,String> Username= new TableColumn<>("Username");
    @FXML
    TableColumn<TicketSaleQuery,String> Home_Team= new TableColumn<>("Home Team");

    @FXML
    public void initialize(){
        String[] diffCriteria = new String[]{"Unsold Tickets","Match All Time Tickets Sold","Upcoming Matches Tickets Sold","Supporters","Team Viewership"};
        criteria.setValue(diffCriteria[0]);
        criteria.setItems(FXCollections.observableArrayList(diffCriteria));
        String[] filterCriteria = new String[]{"Bottom 5","Top 5"};
        filter.setValue(filterCriteria[0]);
        filter.setItems(FXCollections.observableArrayList(filterCriteria));

        Match_Id.setCellValueFactory(new PropertyValueFactory<>("match_id"));
        Match_Id.setCellValueFactory(cellData -> cellData.getValue().match_idProperty().asObject());
        tickets_sold.setCellValueFactory(new PropertyValueFactory<>("tickets_sold"));
        tickets_sold.setCellValueFactory(cellData -> cellData.getValue().ticket_soldProperty().asObject());
        Match_Views.setCellValueFactory(new PropertyValueFactory<>("match_views"));
        Match_Views.setCellValueFactory(cellData -> cellData.getValue().match_viewsProperty().asObject());
        Tickets_Bought.setCellValueFactory(new PropertyValueFactory<>("tickets_buy"));
        Tickets_Bought.setCellValueFactory(cellData -> cellData.getValue().tickets_buyProperty().asObject());
        Capacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        Capacity.setCellValueFactory(cellData -> cellData.getValue().capacityProperty().asObject());
        Remaining_Tickets.setCellValueFactory(new PropertyValueFactory<>("remaining_tickets"));
        Remaining_Tickets.setCellValueFactory(cellData -> cellData.getValue().remaining_ticketsProperty().asObject());
        Home_Stadium.setCellValueFactory(new PropertyValueFactory<>("home_stadium"));
        Home_Stadium.setCellValueFactory(cellData -> cellData.getValue().home_stadiumProperty());
        Fav_Team.setCellValueFactory(new PropertyValueFactory<>("fav_team"));
        Fav_Team.setCellValueFactory(cellData -> cellData.getValue().fav_teamProperty());
        Home_Team.setCellValueFactory(new PropertyValueFactory<>("home_team"));
        Home_Team.setCellValueFactory(cellData -> cellData.getValue().team_homeProperty());
        Username.setCellValueFactory(new PropertyValueFactory<>("username"));
        Username.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());






        criteria.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                try {
                    table.getItems().clear();
                    table.getColumns().clear();
                    String query = queries[(int) t1];
                    desc = filter.getSelectionModel().getSelectedIndex()==0?" ":" DESC";
                    query=query.concat(desc+qty);
                    PreparedStatement stmt = conn.prepareStatement(query);
                    ResultSet rs = stmt.executeQuery();
                    ArrayList<TableColumn<TicketSaleQuery,?>> tableCoumns = new ArrayList<>();
                    ObservableList<TicketSaleQuery> data = FXCollections.observableArrayList();
                    switch ((int)t1){
                        case 0:
                            tableCoumns.add(Match_Id);
                            tableCoumns.add(Home_Stadium);
                            tableCoumns.add(Capacity);
                            tableCoumns.add(tickets_sold);
                            tableCoumns.add(Remaining_Tickets);
                            while (rs.next()){
                                data.add(new TicketSaleQuery(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getInt(5)));
                            }
                            break;
                        case 1:
                        case 2:
                            tableCoumns.add(Match_Id);
                            tableCoumns.add(tickets_sold);
                            while (rs.next()){
                                data.add(new TicketSaleQuery(rs.getInt(1),rs.getInt(2)));
                            }
                            break;
                        case 3:
                            tableCoumns.add(Username);
                            tableCoumns.add(Fav_Team);
                            tableCoumns.add(Tickets_Bought);
                            while (rs.next()){
                                data.add(new TicketSaleQuery(rs.getString(1),rs.getString(2),rs.getInt(3)));
                            }
                            break;
                        case 4:
                            tableCoumns.add(Home_Team);
                            tableCoumns.add(Match_Views);
                            while (rs.next()){
                                data.add(new TicketSaleQuery(rs.getString(1),rs.getInt(2)));
                            }
                            break;
                    }
                    table.getColumns().addAll(tableCoumns);
                    table.setItems(data);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        filter.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                try {
                    table.getItems().clear();
                    table.getColumns().clear();
                    String query = queries[criteria.getSelectionModel().getSelectedIndex()];
                    desc = filter.getSelectionModel().getSelectedIndex()==0?" ":" DESC";
                    query=query.concat(desc+qty);
                    PreparedStatement stmt = conn.prepareStatement(query);
                    ResultSet rs = stmt.executeQuery();
                    ArrayList<TableColumn<TicketSaleQuery,?>> tableCoumns = new ArrayList<>();
                    ObservableList<TicketSaleQuery> data = FXCollections.observableArrayList();
                    switch (criteria.getSelectionModel().getSelectedIndex()){
                        case 0:
                            tableCoumns.add(Match_Id);
                            tableCoumns.add(Home_Stadium);
                            tableCoumns.add(Capacity);
                            tableCoumns.add(tickets_sold);
                            tableCoumns.add(Remaining_Tickets);
                            while (rs.next()){
                                data.add(new TicketSaleQuery(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getInt(5)));
                            }
                            break;
                        case 1:
                        case 2:
                            tableCoumns.add(Match_Id);
                            tableCoumns.add(tickets_sold);
                            while (rs.next()){
                                data.add(new TicketSaleQuery(rs.getInt(1),rs.getInt(2)));
                            }
                            break;
                        case 3:
                            tableCoumns.add(Username);
                            tableCoumns.add(Fav_Team);
                            tableCoumns.add(Tickets_Bought);
                            while (rs.next()){
                                data.add(new TicketSaleQuery(rs.getString(1),rs.getString(2),rs.getInt(3)));
                            }
                            break;
                        case 4:
                            tableCoumns.add(Home_Team);
                            tableCoumns.add(Match_Views);
                            while (rs.next()){
                                data.add(new TicketSaleQuery(rs.getString(1),rs.getInt(2)));
                            }
                            break;
                    }
                    table.getColumns().addAll(tableCoumns);
                    table.setItems(data);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("admin_panel.fxml"));
        Main.window.setTitle("Admin panel");
        Main.window.setScene(new Scene(root));
    }
}
