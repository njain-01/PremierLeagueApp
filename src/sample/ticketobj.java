package sample;

public class ticketobj {
    private int ticket_id;
    private String user_name;
    private int match_id;
    private String stadium;
    private int ticket_tier;
    private int price;

    @Override
    public String toString() {
        return "ticketobj{" +
                "ticket_id=" + ticket_id +
                ", user_name='" + user_name + '\'' +
                ", match_id=" + match_id +
                ", stadium='" + stadium + '\'' +
                ", ticket_tier=" + ticket_tier +
                ", price=" + price +
                '}';
    }

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public int getTicket_tier() {
        return ticket_tier;
    }

    public void setTicket_tier(int ticket_tier) {
        this.ticket_tier = ticket_tier;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
