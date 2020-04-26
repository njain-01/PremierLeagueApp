package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

public class NewFixture {

    public TextField idTextField;
    public ChoiceBox<String> home;
    public ChoiceBox<String> away;
    public TextField homeStadium;
    public DatePicker datePicker;
    public ChoiceBox<String> time;
    public ChoiceBox<Integer> refree;
    private static Connection conn = DB.getConnection();


    @FXML
    private void initialize() throws SQLException {
        PreparedStatement stmt=conn.prepareStatement("select count(match_id) from schedule");
        ResultSet rs=stmt.executeQuery();
        idTextField.setDisable(true);
        while (rs.next()){
            idTextField.setText(String.valueOf(Integer.parseInt(rs.getString(1))+1));
        }
        String[] teams ={"Arsenal","Bournemouth","Brighton and Hove","Burnley","Chelsea","Crystal Palace","Everton","Huddersfield","Leicester City","Liverpool","Manchester City","Manchester United","Newcastle United","Southampton","Stoke City","Swansea","Tottenham","Watford","West Brom","West Ham"};
        home.setItems(FXCollections.observableArrayList(teams));
        home.setValue(teams[0]);
        homeStadium.setText("Emirates Stadium");
        String homeTeam = home.getValue();
        ArrayList<String> awayTeam = new ArrayList<>();
        for (String S : teams){
            if (!S.equals(homeTeam)){
                awayTeam.add(S);
            }
        }
        away.setValue(awayTeam.get(0));
        away.setItems(FXCollections.observableArrayList(awayTeam));

        home.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                ArrayList<String> awayTeam = new ArrayList<>();
                for (String S : teams){
                    if (!S.equals(teams[t1.intValue()])){
                        awayTeam.add(S);
                    }
                }
                away.setValue(awayTeam.get(0));
                away.setItems(FXCollections.observableArrayList(awayTeam));
                PreparedStatement stmt;
                try {
                    stmt = conn.prepareStatement("select home_stadium from team where name=?");
                    stmt.setString(1,teams[t1.intValue()]);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()){
                        homeStadium.setText(String.valueOf(rs.getString(1)));
                    }

                } catch (SQLException e) {
                    System.out.println(" error");
                    e.printStackTrace();
                }


            }
        });
        datePicker.setValue(java.time.LocalDate.now());
        String[] timeOpt = new String[]{"16:30:00","18:30:00","20:30:00","22:30:00"};
        time.setValue(timeOpt[0]);
        time.setItems(FXCollections.observableArrayList(timeOpt));

        datePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate localDate, LocalDate t1) {
                PreparedStatement stmt;
                try {
                    stmt = conn.prepareStatement("select refree from schedule where match_date>=? and match_date<?");
                    System.out.println((datePicker.getValue()));
                    stmt.setDate(1, Date.valueOf((datePicker.getValue())));
                    stmt.setDate(2, Date.valueOf((datePicker.getValue().plusDays(1))));
                    System.out.println(stmt.toString());

                    ArrayList<Integer> existingRefree = new ArrayList<>();
                    ResultSet workingRefree = stmt.executeQuery();
                    while (workingRefree.next()){
                        existingRefree.add(workingRefree.getInt(1));
                    }

                    stmt = conn.prepareStatement("select refree_id from refree");
                    ResultSet rsRefree = stmt.executeQuery();
                    ArrayList<Integer> refreeList = new ArrayList<>();
                    while (rsRefree.next()){
                        if (!existingRefree.contains(rsRefree.getInt(1))) {
                            refreeList.add(rsRefree.getInt(1));
                        }
                    }
                    refree.setItems(FXCollections.observableArrayList(refreeList));
                    refree.setValue(refreeList.get(0));

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    public void scheduleMatch(MouseEvent mouseEvent) throws SQLException, ParseException, IOException {
        PreparedStatement stmt;
        stmt = conn.prepareStatement("INSERT INTO SCHEDULE VALUE(?,?,?,?,?)");
        stmt.setInt(1, Integer.parseInt(idTextField.getText()));
        stmt.setString(2,home.getValue());
        stmt.setString(3,away.getValue());
        String combined = ((datePicker.getValue())+" "+time.getValue());
        stmt.setString(4, combined);
        stmt.setInt(5,refree.getValue());
        System.out.println(stmt.toString());
        stmt.executeUpdate();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Fixture Created Successfully ");
        alert.show();
        Parent root = FXMLLoader.load(getClass().getResource("admin_panel.fxml"));
        Main.window.setScene(new Scene(root));

    }
}
