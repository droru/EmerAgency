package View;

import Model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.NodeOrientation;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import sample.Aview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class reportController extends Aview {
    static int eventID;
    Event event;
    ArrayList<Notification> notifications;
    public Label eventName;
    public Label eventID_Label;
    public Label eventDate_Label;
    public Label eventTime_Label;
    public Label eventStatus_Label;
    public Label eventPublisher_Label;
    String updates="";
    String usersEvent="";
    public Button saveReport;
    public Button cancelReport;


    public ListView updateList;
    public ListView userList;


    public void initialize() {
        event = getController().getEvents().get(eventID);
        eventName.setText(String.valueOf(event.getEventID()));
        eventID_Label.setText((event.getTitle()));
        eventStatus_Label.setText(event.getStatus());
        eventPublisher_Label.setText(getController().getUsers().get(event.getPublisher()).getUserName());
        eventDate_Label.setText(event.getPublishDateTime().toString());

        for (Update u : event.getUpdates().values()) {
            updateList.getItems().add(u.updateForReport(getController().getUsers()));
            updates=updates+u.updateForReport(getController().getUsers())+"\n";
        }

        Map<Integer, User> eventUsers = new HashMap<>();
        eventUsers = getController().getEventUser(eventID);

        for (User u : eventUsers.values()) {
            userList.getItems().add(u.userForReport(getController().getOrganizations()));
            usersEvent=usersEvent+u.userForReport(getController().getOrganizations())+"\n";
        }

    }

    public void cancelReportAct(ActionEvent actionEvent) {
        ((Stage) cancelReport.getScene().getWindow()).close();

    }

    public void saveReportAct(ActionEvent actionEvent) {
        PrinterJob job = PrinterJob.createPrinterJob();
        TextArea textArea = new TextArea();
        textArea.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        textArea.setMinHeight(650);
        String report= "דוח איורע מספר :" +eventID+"\n\n"+
                        event.printString()+
                "עדכונים:"+ "\n\n"+updates +
                "משתתפים:"+"\n\n" +usersEvent;
        textArea.setText(report);
        if (job != null) {
            job.showPrintDialog(((Stage) cancelReport.getScene().getWindow())); // Window must be your main Stage
            job.printPage(textArea);
            job.endJob();
        }
        ((Stage) cancelReport.getScene().getWindow()).close();
    }
}
