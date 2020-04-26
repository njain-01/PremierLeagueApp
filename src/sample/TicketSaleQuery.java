package sample;

import javafx.beans.property.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class TicketSaleQuery {

    public IntegerProperty match_id;
    public IntegerProperty ticket_sold;
    public StringProperty team_home;
    public IntegerProperty match_views;
    public StringProperty username;
    public StringProperty fav_team;
    public IntegerProperty tickets_buy;
    public StringProperty home_stadium;
    public IntegerProperty capacity;
    public IntegerProperty remaining_tickets;

    public TicketSaleQuery(Integer match_id, String home_stadium,Integer capacity,Integer ticket_sold, Integer remaining_tickets) {
        this.match_id = new SimpleIntegerProperty();
        this.ticket_sold = new SimpleIntegerProperty();
        this.capacity = new SimpleIntegerProperty();
        this.remaining_tickets = new SimpleIntegerProperty();
        this.home_stadium = new SimpleStringProperty();
        setMatch_id(match_id);
        setTicket_sold(ticket_sold);
        setHome_stadium(home_stadium);
        setCapacity(capacity);
        setRemaining_tickets(remaining_tickets);
    }

    public TicketSaleQuery(Integer match_id, Integer ticket_sold) {

        this.match_id = new SimpleIntegerProperty();
        this.ticket_sold = new SimpleIntegerProperty();
        setMatch_id(match_id);
        setTicket_sold(ticket_sold);
    }

    public TicketSaleQuery(String username, String fav_team, Integer tickets_buy) {
        this.username = (new SimpleStringProperty(username));
        this.fav_team = new SimpleStringProperty(fav_team);
        this.tickets_buy = new SimpleIntegerProperty(tickets_buy);
    }

    public TicketSaleQuery(String team_home, Integer match_views) {
        this.team_home = new SimpleStringProperty(team_home);
        this.match_views = new SimpleIntegerProperty(match_views);
    }

    public int getMatch_id() {
        return match_id.get();
    }

    public IntegerProperty match_idProperty() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id.set(match_id);
    }

    public int getTicket_sold() {
        return ticket_sold.get();
    }

    public IntegerProperty ticket_soldProperty() {
        return ticket_sold;
    }

    public void setTicket_sold(int ticket_sold) {
        this.ticket_sold.set(ticket_sold);
    }

    public String getTeam_home() {
        return team_home.get();
    }

    public StringProperty team_homeProperty() {
        return team_home;
    }

    public void setTeam_home(String team_home) {
        this.team_home.set(team_home);
    }

    public int getMatch_views() {
        return match_views.get();
    }

    public IntegerProperty match_viewsProperty() {
        return match_views;
    }

    public void setMatch_views(int match_views) {
        this.match_views.set(match_views);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getFav_team() {
        return fav_team.get();
    }

    public StringProperty fav_teamProperty() {
        return fav_team;
    }

    public void setFav_team(String fav_team) {
        this.fav_team.set(fav_team);
    }

    public int getTickets_buy() {
        return tickets_buy.get();
    }

    public IntegerProperty tickets_buyProperty() {
        return tickets_buy;
    }

    public void setTickets_buy(int tickets_buy) {
        this.tickets_buy.set(tickets_buy);
    }

    public String getHome_stadium() {
        return home_stadium.get();
    }

    public StringProperty home_stadiumProperty() {
        return home_stadium;
    }

    public void setHome_stadium(String home_stadium) {
        this.home_stadium.set(home_stadium);
    }

    public int getCapacity() {
        return capacity.get();
    }

    public IntegerProperty capacityProperty() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity.set(capacity);
    }

    public int getRemaining_tickets() {
        return remaining_tickets.get();
    }

    public IntegerProperty remaining_ticketsProperty() {
        return remaining_tickets;
    }

    public void setRemaining_tickets(int remaining_tickets) {
        this.remaining_tickets.set(remaining_tickets);
    }
}
