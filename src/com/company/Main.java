package com.company;

import com.company.BL.TaskManager;
import com.company.BL.TaskManagerConcrete;
import com.company.DB.TaskMySQLRepository;
import com.company.UI.DateParser;
import com.company.UI.Mapper;
import com.company.UI.TaskUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("UI/Authorization.fxml"));
        primaryStage.setTitle("Authorization");
        primaryStage.setScene(new Scene(root, 225, 245));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);

        /*Authorization.signIn();

        int userID = Authorization.logIn();
        if (userID == -1) {
            System.out.println("incorrect");
            System.exit(0);
        }
*/
        /*String stringDate1 = "22:00, 30 December 2018";
        String stringDate2 = "14:00, 08 July 2019";

        String dateFormat = "H:m, d MMMMM y";
        DateParser dateParser = new DateParser(dateFormat);

        int userID = 1707;

        TaskManager tdl = new TaskManagerConcrete(new TaskMySQLRepository(userID));


        tdl.getTasks().forEach(System.out::println);
        //tdl.getByName("Task3").getDeadlineDate().setDate(1234);
        System.out.println(tdl.getByName("Task3"));

        TaskUI task = new TaskUI(0, "Task65489", tdl.getByName("Task1").getDeadlineDate(), tdl.getByName("Task1").getStatus(), userID);
        tdl.add(Mapper.map(task));


        //tdl.getInIterval(dateParser.parsingDate(stringDate1), dateParser.parsingDate(stringDate2)).forEach(System.out::println);


        //tdl.removeTask(task);
        //System.out.println(tdl.getTaskByName("Task01").toString());

        //TaskUI task = new TaskUI("Task1", tdl.getTaskByName("Task01").getDeadlineDate(), tdl.getTaskByName("Task01").getStatus());
        //tdl.updateTask(tdl.getTaskByName("Task01"), task);

        /*TaskUI task = new TaskUI("Task01", tdl.getTaskByName("Task1").getDeadlineDate(), tdl.getTaskByName("Task1").getStatus());
        tdl.updateTask(tdl.getTaskByName("Task1"), task);*/
    }
}
