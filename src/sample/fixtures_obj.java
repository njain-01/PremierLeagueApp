package sample;

public class fixtures_obj {
    private int match_id;
    private String team_home;
    private String team_other;
    private String match_date;
    private int refree;

    public int getMatch_id() {
        return match_id;
    }

    public String getMatch_date() {
        return match_date;
    }

    public String getTeam_home() {
        return team_home;
    }

    public String getTeam_other() {
        return team_other;
    }

    public int getRefree() {
        return refree;
    }

    @Override
    public String toString() {
        return "fixtures_obj{" +
                "match_id=" + match_id +
                ", team_home='" + team_home + '\'' +
                ", team_other='" + team_other + '\'' +
                ", match_date='" + match_date + '\'' +
                ", refree=" + refree +
                '}';
    }

    public void setMatch_date(String match_date) {
        this.match_date = match_date;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public void setRefree(int refree) {
        this.refree = refree;
    }

    public void setTeam_home(String team_home) {
        this.team_home = team_home;
    }

    public void setTeam_other(String team_other) {
        this.team_other = team_other;
    }
}
