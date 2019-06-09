package View;

import Model.Event;
import Model.Notification;
import Model.User;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import sample.Aview;

import java.util.ArrayList;
import java.util.List;


public class reportController extends Aview {
    static int eventID;
    Event event;
    List<User> users;
    ArrayList<Notification> notifications;
    public Label eventName;
    public Label eventID_Label;
    public Label eventManger_Label;
    public Label eventDate_Label;
    public Label eventTime_Label;
    public Label eventStatus_Label;
    public Label eventPublisher_Label;

    public ListView notificationList;

    public void initialize() {
        event = getController().getEventbyID(eventID);
        //notifications = getController().getNotificationByEventID(eventID);
        users = getController().getUsers();
        setReportDetails();
        editNotificationtoReport();
        //notificationList.setItems(notifications);

        //eventID_label.setText(String.valueOf(event.getEventID()));

    }

    private ObservableList<String> editNotificationtoReport() {
        String res;
        for (Notification n: notifications) {

        }
        return null;
    }

    private void setReportDetails() {
        eventName.setText(event.getTitle());
        eventID_Label.setText(String.valueOf(event.getEventID()));
        //eventDate_Label.setText(event.getPublish_time());
        //eventManger_Label.setText(getUserNamebyID(event.getManage()));
        //eventPublisher_Label.setText(getUserNamebyID(event.getPublish()));
        eventStatus_Label.setText(event.getStatus());
    }

    private String getUserNamebyID(int id) {
        for (User u:users) {
            if(u.getUserId()==id)
                return u.getUserName();
        }
        return null;
    }
}
