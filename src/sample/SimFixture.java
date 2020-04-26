package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class SimFixture {
    @FXML
    public ChoiceBox<Integer> matchSelector;
    private static Connection conn = DB.getConnection();
    public ImageView homeTeam;
    public ImageView awayTeam;
    public Button submit;
    public TextArea details;
    public Label awayScore;
    public Label homeScore;
    public Button simulate;
    public ProgressBar progress;
    public static String homeTeamName = null,awayTeamName = null;
    public Result result = null;

    @FXML
    private void initialize() throws SQLException {
        submit.setDisable(false);
        details.setEditable(false);
        PreparedStatement stmt=conn.prepareStatement("select match_id from schedule where match_id not in (select match_id from result)");
        ResultSet rs=stmt.executeQuery();
        ArrayList<Integer> remainingFixtures = new ArrayList<>();
        while (rs.next()){
            remainingFixtures.add(rs.getInt(1));
        }
        matchSelector.setItems(FXCollections.observableArrayList(remainingFixtures));
        matchSelector.setValue(0);

        if(!remainingFixtures.isEmpty()){matchSelector.setValue(remainingFixtures.get(0));}
        matchSelector.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                int match_id = remainingFixtures.get(t1.intValue());
                try {
                    PreparedStatement stmt = conn.prepareStatement("select * from schedule where match_id=?");
                    stmt.setInt(1,match_id);
                    ResultSet rs = stmt.executeQuery();
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columnsNumber = rsmd.getColumnCount();
                    while (rs.next()){
                        for (int i = 1; i <= columnsNumber; i++) {
                            if (i > 1) System.out.print(",  ");
                            String columnValue = rs.getString(i);
                            System.out.print(columnValue + " " + rsmd.getColumnName(i));
                        }
                        System.out.println("");
                        homeTeamName = rs.getString("team_home");
                        awayTeamName = rs.getString("team_other");
                    }

                    homeTeam.setImage(new Image(getClass().getResourceAsStream("/Logos/"+homeTeamName+".png")));
                    awayTeam.setImage(new Image(getClass().getResourceAsStream("/Logos/"+awayTeamName+".png")));
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void runMatch(MouseEvent mouseEvent) throws SQLException, InterruptedException {
        ArrayList<String> queries = new ArrayList<>();
        simulate.setDisable(true);
        ArrayList<String> playerHome = new ArrayList<>();
        ArrayList<String> playerAway = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("select * from player where team=?");
        stmt.setString(1,homeTeamName);
        ResultSet rs = stmt.executeQuery();
        String[] playerPosn = new String[]{"LW","RW","AM","RM","CM","CB","CF","SS"};
        ArrayList<String> posn = new ArrayList<>();
        Collections.addAll(posn,playerPosn);
        while (rs.next()){
            if (posn.contains(rs.getString(5)))
                playerHome.add(rs.getString(2));
        }
        stmt = conn.prepareStatement("select * from player where team=?");
        stmt.setString(1,awayTeamName);
        rs = stmt.executeQuery();
        result = new Result(matchSelector.getValue());
        while (rs.next()){
            if (posn.contains(rs.getString(5)))
                playerAway.add(rs.getString(2));
            result.pom = rs.getInt(1);
        }
        ArrayList<ArrayList<String>> allPlayers = new ArrayList<>();
        allPlayers.add(playerHome);
        allPlayers.add(playerAway);
        details.appendText("KICK OFF\n");
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(progress.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(5), e-> {
                    doSomehing(allPlayers,result);
                    System.out.println("Minute over 1");
                }, new KeyValue(progress.progressProperty(), 0.16)),
                new KeyFrame(Duration.seconds(10), e-> {
                    doSomehing(allPlayers,result);
                    System.out.println("Minute over 2");
                }, new KeyValue(progress.progressProperty(), 0.32)),
                new KeyFrame(Duration.seconds(15), e-> {
                    doSomehing(allPlayers,result);
                    System.out.println("Minute over 3");
                    details.appendText("HALF TIME\n");
                }, new KeyValue(progress.progressProperty(), 0.50)),
                new KeyFrame(Duration.seconds(20), e-> {
                    doSomehing(allPlayers,result);
                    System.out.println("Minute over 4");
                }, new KeyValue(progress.progressProperty(), 0.66)),
                new KeyFrame(Duration.seconds(25), e-> {
                    doSomehing(allPlayers,result);
                    System.out.println("Minute over 5");
                }, new KeyValue(progress.progressProperty(), 0.82)),
                new KeyFrame(Duration.seconds(30), e-> {
                    doSomehing(allPlayers,result);
                    System.out.println("Minute over 6");
                    details.appendText("FULL TIME");
                }, new KeyValue(progress.progressProperty(), 1))
        );
        timeline.setCycleCount(1);
        timeline.play();

    }

    private void addResult(Result result) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO result VALUES(?,?,?,?,?,?,?,?,?,?)");
        stmt.setInt(1,result.match_id);
        stmt.setInt(2,result.pom);
        stmt.setInt(3,result.goal_a);
        stmt.setInt(4,result.goal_b);
        stmt.setInt(5,result.poss_a);
        stmt.setInt(6,result.poss_b);
        stmt.setInt(7,result.ya);
        stmt.setInt(8,result.yb);
        stmt.setInt(9,result.ra);
        stmt.setInt(10,result.rb);
        System.out.println(stmt);
        stmt.executeUpdate();

    }

    private void doSomehing(ArrayList<ArrayList<String>> allPlayers, Result result) {
        int[] opt = new int[]{0,0,0,0,1,1,1,2,3,3};
        int[] teamOpt = new int[]{0,1};
        int teamChosen;
        String chosenPlayer;
        Random random = new Random();
        int option = opt[random.nextInt(10)];
        System.out.println("Option "+option);
        switch (option){
            case 0:
                break;
            case 1:
                teamChosen = teamOpt[random.nextInt(2)];
                System.out.println("TEAM "+teamChosen);
                System.out.println(allPlayers.get(teamChosen).size()-1);
                chosenPlayer = allPlayers.get(teamChosen).get(random.nextInt(allPlayers.get(teamChosen).size()-1));
                details.appendText("YELLOW CARD "+chosenPlayer+" " );
                if (teamChosen == 0) {
                    details.appendText(homeTeamName+"\n");
                    result.ya=result.ya+1;
                } else {
                    details.appendText(awayTeamName+"\n");
                    result.yb=result.yb+1;
                }
                break;
            case 2:
                teamChosen = teamOpt[random.nextInt(2)];
                System.out.println("TEAM "+teamChosen);
                System.out.println(allPlayers.get(teamChosen).size()-1);
                chosenPlayer = allPlayers.get(teamChosen).get(random.nextInt(allPlayers.get(teamChosen).size()-1));
                details.appendText("RED CARD "+chosenPlayer+" " );
                if (teamChosen == 0) {
                    details.appendText(homeTeamName+"\n");
                    result.ra=result.ra+1;
                } else {
                    details.appendText(awayTeamName+"\n");
                    result.rb=result.rb+1;
                }
                break;
            case 3:
                teamChosen = teamOpt[random.nextInt(2)];
                System.out.println("TEAM "+teamChosen);
                System.out.println(allPlayers.get(teamChosen).size()-1);
                chosenPlayer = allPlayers.get(teamChosen).get(random.nextInt(allPlayers.get(teamChosen).size()-1));
                result.goalScorer.add(chosenPlayer);
                details.appendText("GOAL "+chosenPlayer+" " );
                chosenPlayer = allPlayers.get(teamChosen).get(random.nextInt(allPlayers.get(teamChosen).size()-1));
                result.assist.add(chosenPlayer);
                details.appendText("ASSIST "+chosenPlayer+" ");
                if (teamChosen == 0) {
                    details.appendText(homeTeamName+"\n");
                    homeScore.setText(String.valueOf(Integer.parseInt(homeScore.getText())+1));
                    result.goal_a=result.goal_a+1;
                    result.teamScorer.add(homeTeamName);
                } else {
                    details.appendText(awayTeamName+"\n");
                    awayScore.setText(String.valueOf(Integer.parseInt(awayScore.getText())+1));
                    result.goal_b=result.goal_b+1;
                    result.teamScorer.add(awayTeamName);
                }
                break;
        }
    }

    public void addResultToTable(MouseEvent mouseEvent) throws SQLException, IOException {
        addResult(result);
        for (int i = 0; i <result.teamScorer.size() ; i++) {
            PreparedStatement stmt = conn.prepareStatement("select count(goal_id) from goal");
            ResultSet rs = stmt.executeQuery();
            int goalId = 0;
            while (rs.next()){
                goalId = rs.getInt(1);
            }
            stmt = conn.prepareStatement("INSERT into goal values(?,?,?,?,?,?)");
            stmt.setInt(1,goalId+1);
            stmt.setInt(2,result.match_id);
            stmt.setString(3,result.teamScorer.get(i));
            PreparedStatement searchPlayer = conn.prepareStatement("select player_id from player where name=? and team=?");
            searchPlayer.setString(1,result.goalScorer.get(i));
            searchPlayer.setString(2,result.teamScorer.get(i));
            PreparedStatement searchAssist = conn.prepareStatement("select player_id from player where name=? and team=?");
            searchAssist.setString(1,result.assist.get(i));
            searchAssist.setString(2,result.teamScorer.get(i));
            ResultSet rs1 = searchPlayer.executeQuery();
            ResultSet rs2 = searchAssist.executeQuery();
            while (rs1.next()){
                stmt.setInt(4,rs1.getInt(1));
            }
            while (rs2.next()){
                stmt.setInt(5,rs2.getInt(1));
            }
            Random random = new Random();
            int millisInDay = 90*60*1000;
            Time time = new Time((long)random.nextInt(millisInDay));
            stmt.setString(6,String.valueOf(time));
            System.out.println(stmt);
            stmt.executeUpdate();
        }

        // Points alter
        PreparedStatement stmt3=conn.prepareStatement("select * from points_table where team=? or team=?");
        stmt3.setString(1,homeTeamName);
        stmt3.setString(2,awayTeamName);
        ResultSet rs3=stmt3.executeQuery();
        teaminPT homeTeam = new teaminPT();
        teaminPT awayTeam = new teaminPT();
        while (rs3.next()){
            if (rs3.getString("team").equals(homeTeamName)) {
                homeTeam.setName(rs3.getString("team"));
                homeTeam.setPlayed(rs3.getInt("played"));
                homeTeam.setWon(rs3.getInt("won"));
                homeTeam.setDraw(rs3.getInt("draw"));
                homeTeam.setLost(rs3.getInt("lost"));
                homeTeam.setGa(rs3.getInt("ga"));
                homeTeam.setGd(rs3.getInt("gd"));
                homeTeam.setGf(rs3.getInt("gf"));
                homeTeam.setPoints(rs3.getInt("points"));
            }
            else{
                awayTeam.setName(rs3.getString("team"));
                awayTeam.setPlayed(rs3.getInt("played"));
                awayTeam.setWon(rs3.getInt("won"));
                awayTeam.setDraw(rs3.getInt("draw"));
                awayTeam.setLost(rs3.getInt("lost"));
                awayTeam.setGa(rs3.getInt("ga"));
                awayTeam.setGd(rs3.getInt("gd"));
                awayTeam.setGf(rs3.getInt("gf"));
                awayTeam.setPoints(rs3.getInt("points"));
            }

        }
        int goalDif = result.goal_a-result.goal_b;
        if (goalDif>0){
            homeTeam.setGf(homeTeam.getGf()+result.goal_a);
            homeTeam.setGa(homeTeam.getGa()+result.goal_b);
            homeTeam.setGd(homeTeam.getGf()-homeTeam.getGa());
            homeTeam.setPoints(homeTeam.getPoints()+3);
            homeTeam.setWon(homeTeam.getWon()+1);
            homeTeam.setPlayed(homeTeam.getPlayed()+1);

            awayTeam.setGf(awayTeam.getGf()+result.goal_b);
            awayTeam.setGa(awayTeam.getGa()+result.goal_a);
            awayTeam.setGd(awayTeam.getGf()-awayTeam.getGa());
            awayTeam.setLost(awayTeam.getLost()+1);
            awayTeam.setPlayed(awayTeam.getPlayed()+1);

        }
        else if (goalDif<0){
            awayTeam.setGf(awayTeam.getGf()+result.goal_b);
            awayTeam.setGa(awayTeam.getGa()+result.goal_a);
            awayTeam.setGd(awayTeam.getGf()-awayTeam.getGa());
            awayTeam.setPoints(awayTeam.getPoints()+3);
            awayTeam.setWon(awayTeam.getWon()+1);
            awayTeam.setPlayed(awayTeam.getPlayed()+1);

            homeTeam.setGf(homeTeam.getGf()+result.goal_a);
            homeTeam.setGa(homeTeam.getGa()+result.goal_b);
            homeTeam.setGd(homeTeam.getGf()-homeTeam.getGa());
            homeTeam.setLost(homeTeam.getLost()+1);
            homeTeam.setPlayed(homeTeam.getPlayed()+1);

        }
        else {
            homeTeam.setGf(homeTeam.getGf()+result.goal_a);
            homeTeam.setGa(homeTeam.getGa()+result.goal_b);
            homeTeam.setGd(homeTeam.getGf()-homeTeam.getGa());
            homeTeam.setPoints(homeTeam.getPoints()+1);
            homeTeam.setDraw(homeTeam.getDraw()+1);
            homeTeam.setPlayed(homeTeam.getPlayed()+1);

            awayTeam.setGf(awayTeam.getGf()+result.goal_b);
            awayTeam.setGa(awayTeam.getGa()+result.goal_a);
            awayTeam.setGd(awayTeam.getGf()-awayTeam.getGa());
            awayTeam.setDraw(awayTeam.getDraw()+1);
            awayTeam.setDraw(awayTeam.getDraw()+1);
            awayTeam.setPlayed(awayTeam.getPlayed()+1);
        }
        PreparedStatement updateHome = conn.prepareStatement("UPDATE points_table set played=?,won=?,draw=?,lost=?,gf=?,ga=?,gd=?,points=? where team=?");
        updateHome.setInt(1,homeTeam.getPlayed());
        updateHome.setInt(2,homeTeam.getWon());
        updateHome.setInt(3,homeTeam.getDraw());
        updateHome.setInt(4,homeTeam.getLost());
        updateHome.setInt(5,homeTeam.getGf());
        updateHome.setInt(6,homeTeam.getGa());
        updateHome.setInt(7,homeTeam.getGd());
        updateHome.setInt(8,homeTeam.getPoints());
        updateHome.setString(9,homeTeamName);
        PreparedStatement updateAway = conn.prepareStatement("UPDATE points_table set played=?,won=?,draw=?,lost=?,gf=?,ga=?,gd=?,points=? where team=?");
        updateAway.setInt(1,awayTeam.getPlayed());
        updateAway.setInt(2,awayTeam.getWon());
        updateAway.setInt(3,awayTeam.getDraw());
        updateAway.setInt(4,awayTeam.getLost());
        updateAway.setInt(5,awayTeam.getGf());
        updateAway.setInt(6,awayTeam.getGa());
        updateAway.setInt(7,awayTeam.getGd());
        updateAway.setInt(8,awayTeam.getPoints());
        updateAway.setString(9,awayTeamName);
        System.out.println(updateHome);
        System.out.println(updateAway);
        updateHome.executeUpdate();
        updateAway.executeUpdate();
        submit.setDisable(true);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Fixture Created Successfully ");
        alert.show();
        Parent root = FXMLLoader.load(getClass().getResource("admin_panel.fxml"));
        Main.window.setScene(new Scene(root));

    }
}
