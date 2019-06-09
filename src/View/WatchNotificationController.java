package View;

import Model.Notification;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Aview;
import sample.Main;

public class WatchNotificationController extends Aview
{
    @FXML
    public TableView<Notification> NotificationTable;
    public Label Title;


    public void initialize() {
        /*Title.setText(Main.EventId +" Notification : ");
        if (NotificationTable.getColumns().size() == 0) {
            ObservableList<Notification> notifications = getController().getNotificationByUserName();
            setTableData(notifications);
        }

         */
    }



    private void setTableData(ObservableList<Notification> notifications) {

        TableColumn<Notification, Integer> NotificationIDCol = new TableColumn<Notification, Integer>("NotificationID");
        TableColumn<Notification, Integer> UserIdCol = new TableColumn<Notification, Integer>("UserId");
        TableColumn<Notification, Integer> EventIdCol = new TableColumn<Notification, Integer>("EventId");
        TableColumn<Notification, String> TitleCol = new TableColumn<Notification, String>("Title");
        TableColumn<Notification, String> PublishTimeCol = new TableColumn<Notification, String>("PublishTime");
        TableColumn<Notification, String> PublishDateCol = new TableColumn<Notification, String>("PublishDate");
        TableColumn<Notification, String> StatusCol = new TableColumn<Notification, String>("Status");




        NotificationIDCol.setCellValueFactory(new PropertyValueFactory<>("NotificationID"));
        UserIdCol.setCellValueFactory(new PropertyValueFactory<>("UserId"));
        EventIdCol.setCellValueFactory(new PropertyValueFactory<>("EventId"));
        TitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        PublishTimeCol.setCellValueFactory(new PropertyValueFactory<>("PublishTime"));
        PublishDateCol.setCellValueFactory(new PropertyValueFactory<>("PublishDate"));
        StatusCol.setCellValueFactory(new PropertyValueFactory<>("Status"));


        NotificationTable.setItems(notifications);
        NotificationTable.getColumns().addAll(StatusCol ,PublishDateCol, PublishTimeCol , TitleCol ,EventIdCol , UserIdCol , NotificationIDCol );

    }

    public void Back(ActionEvent actionEvent)
    {
        Main.switchScene("../View/MainScreen.fxml", Main.getStage(), 620, 400);

    }
}
