package com.company.UI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.company.BL.TaskBL;
import com.company.BL.TaskManager;
import com.company.BL.TaskManagerConcrete;
import com.company.DB.TaskMySQLRepository;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;

public class MainWindowController {

    private int id;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label idLabel;

    @FXML
    private Slider slider = new Slider(0, 100, 0);

    @FXML
    private ProgressBar progressBar;

    @FXML
    void initialize() {
        System.out.println(id);
        System.out.println(idLabel.getText());
        System.out.println(idLabel.getAccessibleText());
        //int id = Integer.parseInt(idLabel.getText());
        /*System.out.println("FIRST");
        TaskManager tdl = new TaskManagerConcrete(new TaskMySQLRepository(id));
        List<TaskBL> list = new ArrayList<>(tdl.getTasks());

        progressBar.setProgress(0.5f);

        System.out.println("SECOND");
        double i=0;
        for (TaskBL task : list) {
            System.out.println(task);
            progressBar.setProgress(0.5f+i/list.size());
            i++;
        }
        System.out.println("THIRD");*/
    }

    @FXML
    void go() {
        int id = Integer.parseInt(idLabel.getText());
        System.out.println("FIRST");
        TaskManager tdl = new TaskManagerConcrete(new TaskMySQLRepository(id));
        List<TaskBL> list = new ArrayList<>(tdl.getTasks());

        progressBar.setProgress(0.5f);

        System.out.println("SECOND");
        double i=0;
        for (TaskBL task : list) {
            System.out.println(task);
            progressBar.setProgress(0.5f+i/list.size());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
        System.out.println("THIRD");
    }

    void initData(int id) {
        this.id = id;
        idLabel.setText(Integer.toString(id));
        System.out.println(id);
    }
}
