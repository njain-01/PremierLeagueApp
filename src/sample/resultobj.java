package sample;

public class resultobj {
    private int match_id;
    private String team_home;
    private int goalA;
    private String team_other;
    private int goalB;

    @Override
    public String toString() {
        return "resultobj{" +
                "match_id=" + match_id +
                ", team_home='" + team_home + '\'' +
                ", goalA=" + goalA +
                ", team_other='" + team_other + '\'' +
                ", goalB=" + goalB +
                '}';
    }

    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public String getTeam_home() {
        return team_home;
    }

    public void setTeam_home(String team_home) {
        this.team_home = team_home;
    }

    public int getGoalA() {
        return goalA;
    }

    public void setGoalA(int goalA) {
        this.goalA = goalA;
    }

    public String getTeam_other() {
        return team_other;
    }

    public void setTeam_other(String team_other) {
        this.team_other = team_other;
    }

    public int getGoalB() {
        return goalB;
    }

    public void setGoalB(int goalB) {
        this.goalB = goalB;
    }
}
