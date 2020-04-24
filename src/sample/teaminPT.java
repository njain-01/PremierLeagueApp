package sample;

public class teaminPT {
    private String name;
    private int played;
    private int won;
    private int draw;
    private int lost;
    private int gf;
    private int gd;
    private int ga;
    private int points;

    public int getDraw() {
        return draw;
    }

    public int getGa() {
        return ga;
    }

    public int getGd() {
        return gd;
    }

    public int getGf() {
        return gf;
    }

    public int getLost() {
        return lost;
    }

    public int getPoints() {
        return points;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public void setGa(int ga) {
        this.ga = ga;
    }

    public void setGd(int gd) {
        this.gd = gd;
    }

    public void setGf(int gf) {
        this.gf = gf;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "teaminPT{" +
                "name='" + name + '\'' +
                ", played=" + played +
                ", won=" + won +
                ", draw=" + draw +
                ", lost=" + lost +
                ", gf=" + gf +
                ", gd=" + gd +
                ", ga=" + ga +
                ", points=" + points +
                '}';
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
