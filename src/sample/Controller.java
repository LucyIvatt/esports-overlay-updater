package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private ArrayList<TextField> inputs;
    @FXML
    private TextField teamOneName;

    @FXML
    private TextField teamOneScore;

    @FXML
    private TextField teamTwoName;

    @FXML
    private TextField teamTwoScore;

    @FXML
    private TextField eventStage;

    @FXML
    private Button updateButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inputs = new ArrayList<>(Arrays.asList(teamOneName, teamOneScore, teamTwoName, teamTwoScore, eventStage));

        teamOneScore.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([.]\\d{0,4})?")) {
                    teamOneScore.setText(oldValue);
                }
            }
        });

        teamTwoScore.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([.]\\d{0,4})?")) {
                    teamOneScore.setText(oldValue);
                }
            }
        });

        updateButton.disableProperty().bind(
                Bindings.isEmpty(teamOneName.textProperty())
                        .or(Bindings.isEmpty(teamOneScore.textProperty()))
                        .or(Bindings.isEmpty(teamTwoName.textProperty()))
                        .or(Bindings.isEmpty(teamTwoName.textProperty()))
                        .or(Bindings.isEmpty(eventStage.textProperty()))
        );
    }

    @FXML
    void onUpdateClicked(MouseEvent event) throws IOException {
        for(TextField input : inputs) {
            File file = new File(input.getId() + ".txt");
            if(!file.exists()) {
                file.createNewFile();
            }
            if (input == teamOneScore || input == teamTwoScore) {
                FileWriter fileWriter = new FileWriter(input.getId() + ".txt",false);
                fileWriter.write(repeatString("n", Integer.parseInt(input.getText())));
                fileWriter.close();
            }
            else {
                FileWriter fileWriter = new FileWriter(input.getId() + ".txt",false);
                fileWriter.write(input.getText());
                fileWriter.close();
            }
        }

    }

    private String repeatString(String s,int count){
        StringBuilder r = new StringBuilder();
        for (int i = 0; i < count; i++) {
            r.append(s);
        }
        return r.toString();
    }
}