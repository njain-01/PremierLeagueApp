package sample;

import java.util.ArrayList;
import java.util.Random;

public class Result {

    public int match_id;
    public int pom;
    public int goal_a;
    public int goal_b;
    public int poss_a;
    public int poss_b;
    public int ya;
    public int yb;
    public int ra;
    public int rb;
    public ArrayList<String> goalScorer;
    public ArrayList<String> assist;
    public ArrayList<String> teamScorer;

    public Result(int match_id){
        this.ya=0;
        this.yb=0;
        this.ra=0;
        this.rb=0;
        this.goal_a=0;
        this.goal_b=0;
        this.match_id=match_id;
        this.poss_a = new Random().nextInt(45)+1;
        this.poss_b=100-poss_a;
        goalScorer = new ArrayList<>();
        assist = new ArrayList<>();
        teamScorer = new ArrayList<>();
    }

}
