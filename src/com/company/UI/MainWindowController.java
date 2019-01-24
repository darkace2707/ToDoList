package com.company.UI;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import com.company.BL.TaskBL;
import com.company.BL.TaskManager;
import com.company.BL.TaskManagerConcrete;
import com.company.DB.TaskMySQLRepository;
import com.company.obs.ProgressListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainWindowController implements ProgressListener {

    @FXML
    private Integer id = 0;

    @FXML
    private TaskManagerConcrete tdl = null;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private List<TaskUI> list = new ArrayList<>();

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


        ObservableList<String> statuses = FXCollections.observableArrayList("Planned", "In progress", "Ended");
        changedTaskStatus.setItems(statuses);

    }

    void initTable(){
        table.getItems().clear();
        table.getItems().addAll(list);
    }

    @FXML
    void curRow() {

        this.id = table.getSelectionModel().getSelectedItem().getMasterID();

        changedTaskName.setText(table.getSelectionModel().getSelectedItem().getName());

        List<Integer> date = new ArrayList<>(Mapper.map(table.getSelectionModel().getSelectedItem().getDeadlineDate()));
        changedTaskDate.setValue(LocalDate.of(date.get(0), date.get(1), date.get(2)));

        changedTaskStatus.setValue(table.getSelectionModel().getSelectedItem().getStatus().getStatus());

        changedTaskUsers.setText(table.getSelectionModel().getSelectedItem().getUsers());
    }

    @FXML
    void create() {
        String name = newTaskName.getText();

        Date date = Date.from(newTaskDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

        TaskUI task = new TaskUI(0, name, date, Status.PLANNED, tdl.getUserID());

        tdl.add(Mapper.map(task));

        List<String> users = Arrays.asList(newTaskUsers.getText().split(" "));
        users.forEach(System.out::println);
        tdl.updateInvolvedUsers(task.getId(), users);

        refresh();
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

        List<String> users = Arrays.asList(changedTaskUsers.getText().split(" "));
        users.forEach(System.out::println);
        tdl.updateInvolvedUsers(task.getId(), users);

        refresh();
    }

    @FXML
    void refresh() {
        //initData(list.get(0).getMasterID());
        this.loadTasks();
    }

    private void loadTasks() {
        final ProgressListener listener = this;
        Task task = new Task<List<TaskUI>>() {
            @Override
            public List<TaskUI> call() {
                tdl.subscribe(listener);
                list.clear();
                for (TaskBL task : tdl.getTasks() ) {
                    list.add(Mapper.map(task));
                }
                return list;
            }
        };

        task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (EventHandler<WorkerStateEvent>) t -> {
            this.table.setPlaceholder(new Label("No available tasks"));
            this.list = ((Task<List<TaskUI>>) task).getValue();
            this.table.setDisable(false);
            this.initTable();
        });

        task.addEventHandler(WorkerStateEvent.WORKER_STATE_RUNNING, (EventHandler<WorkerStateEvent>) t -> {
            this.table.setPlaceholder(new Label("Data is loading..."));
            this.table.setDisable(true);
            this.progressBar.setProgress(0);
        });

        new Thread(task).start();
    }

    void initData(int id) {
        tdl = new TaskManagerConcrete(new TaskMySQLRepository(id));
        this.loadTasks();
        /*list.clear();
        for (TaskBL task : tdl.getTasks() ) {
            list.add(Mapper.map(task));
        }*/
    }

    @Override
    public void update(int progress) {
        progressBar.setProgress(((double) progress) / 100);
    }
}
