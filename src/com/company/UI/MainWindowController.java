package com.company.UI;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.company.BL.TaskBL;
import com.company.BL.TaskManager;
import com.company.BL.TaskManagerConcrete;
import com.company.DB.TaskMySQLRepository;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private ObservableList<TaskUI> list = FXCollections.observableArrayList();

    @FXML
    private Label idLabel = new Label();

    @FXML
    private TableView<TaskUI> table = new TableView<>();

    @FXML
    private TableColumn<TaskUI, String> name = new TableColumn<>();

    @FXML
    private TableColumn<TaskUI, String> date = new TableColumn<>();

    @FXML
    private TableColumn<TaskUI, String> status = new TableColumn<>();

    @FXML
    private TableColumn<TaskUI, String> users = new TableColumn<>();

    @FXML
    private Button refreshButton;

    @FXML
    private TextField newTaskName;

    @FXML
    private TextField newTaskUsers;

    @FXML
    private Button createTaskBtn;

    @FXML
    private TextField changedTaskName;

    @FXML
    private TextField changedTaskUsers;


    @FXML
    private DatePicker newTaskDate = new DatePicker();

    @FXML
    private DatePicker changedTaskDate;

    @FXML
    private ChoiceBox<String> changedTaskStatus = new ChoiceBox<>();

    @FXML
    void initialize() {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        date.setCellValueFactory(new PropertyValueFactory<>("deadlineDate"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        users.setCellValueFactory(new PropertyValueFactory<>("users"));
        table.setItems(list);


        ObservableList<String> statuses = FXCollections.observableArrayList("Planned", "In progress", "Ended");
        changedTaskStatus.setItems(statuses);

    }

    @FXML
    void curRow() {

        changedTaskName.setText(table.getSelectionModel().getSelectedItem().getName());

        List<Integer> date = new ArrayList<>(Mapper.map(table.getSelectionModel().getSelectedItem().getDeadlineDate()));
        changedTaskDate.setValue(LocalDate.of(date.get(0), date.get(1), date.get(2)));

        changedTaskStatus.setValue(table.getSelectionModel().getSelectedItem().getStatus().getStatus());

        changedTaskUsers.setText(table.getSelectionModel().getSelectedItem().getUsers());
    }

    @FXML
    void change() {
        TaskUI task = table.getSelectionModel().getSelectedItem();

        String name = changedTaskName.getText();

        Date date = Date.from(changedTaskDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

        Status status;
        switch (changedTaskStatus.getValue()) {
            case "Planned":
                status = Status.PLANNED;
                break;
            case "In progress":
                status = Status.INPROGRESS;
                break;
            case "Ended":
                status = Status.ENDED;
                break;

                default:
                    status = null;
                    break;
        }

        TaskUI changedTask = new TaskUI(task.getId(), name, date, status, task.getMasterID());

        TaskManager tdl = new TaskManagerConcrete(new TaskMySQLRepository(task.getMasterID()));
        tdl.update(Mapper.map(task), Mapper.map(changedTask));

        refresh();
    }

    @FXML
    void refresh() {
        //table.getItems().clear();
        initData(list.get(0).getMasterID());
        initialize();
    }

    void initData(int id) {
        TaskManager tdl = new TaskManagerConcrete(new TaskMySQLRepository(id));
        list.clear();
        for (TaskBL task : tdl.getTasks() ) {
            list.add(Mapper.map(task));
        }


    }
}
