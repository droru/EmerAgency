package View;

import Model.Update;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Aview;
import sample.Main;

public class WatchUpdateController extends Aview
{
    @FXML
    public TableView<Update> UpdateTable;
    public Label Title;


    public void initialize() {
        Title.setText(Main.EventId +" עדכון : ");
        if (UpdateTable.getColumns().size() == 0) {
            ObservableList<Update> updates = getController().getUpdateByEventID(Main.EventId);
            setTableData(updates);
        }
    }



    private void setTableData(ObservableList<Update> updates) {

        TableColumn<Update, Integer> UpdateIDCol = new TableColumn<Update, Integer>("updateID");
        TableColumn<Update, Integer> UserIdCol = new TableColumn<Update, Integer>("userID");
        TableColumn<Update, Integer> EventIdCol = new TableColumn<Update, Integer>("evnentID");
        TableColumn<Update, String> TitleCol = new TableColumn<Update, String>("desc");
        TableColumn<Update, String> PublishTimeCol = new TableColumn<Update, String>("publishDate");

        UpdateIDCol.setCellValueFactory(new PropertyValueFactory<>("updateID"));
        UserIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        EventIdCol.setCellValueFactory(new PropertyValueFactory<>("evnentID"));
        TitleCol.setCellValueFactory(new PropertyValueFactory<>("desc"));
        PublishTimeCol.setCellValueFactory(new PropertyValueFactory<>("publishDate"));

        UpdateTable.setItems(updates);
        UpdateTable.getColumns().addAll(PublishTimeCol, TitleCol ,EventIdCol , UserIdCol , UpdateIDCol );

    }

    public void Back(ActionEvent actionEvent)
    {
        Main.switchScene("../View/MainScreen.fxml", Main.getStage(), 620, 400);

    }
}
