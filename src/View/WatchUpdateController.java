package View;

import Model.Update;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.Aview;
import sample.Main;

public class WatchUpdateController extends Aview
{
    @FXML
    public TableView<Update> UpdateTable;
    public Label Title;
    public ImageView pic;


    public void initialize() {
        Title.setText("עדכון מספר " + Main.EventId);
        switch(Main.OrganizationName) {
            case "Police":
            {
                pic.setImage(new Image(Main.class.getResource("../img/Police.png").toExternalForm() ));
                break;
            }
            case "MDA":
            {
                pic.setImage(new Image(Main.class.getResource("../img/MDA.png").toExternalForm() ));
                break;
            }
            case "Firefighters":
            {
                pic.setImage(new Image(Main.class.getResource("../img/Firefighters.jpg").toExternalForm() ));
                break;
            }
            case "Moked":
            {
                pic.setImage(new Image(Main.class.getResource("../img/Moked.png").toExternalForm() ));
                break;
            }

        }
        if (UpdateTable.getColumns().size() == 0) {
            ObservableList<Update> updates = getController().getUpdateByEventID(Main.EventId);
            setTableData(updates);
        }
    }



    private void setTableData(ObservableList<Update> updates) {

        TableColumn<Update, Integer> UpdateIDCol = new TableColumn<Update, Integer>("מזהה עדכון");
        TableColumn<Update, Integer> UserIdCol = new TableColumn<Update, Integer>("מפרסם");
        TableColumn<Update, Integer> EventIdCol = new TableColumn<Update, Integer>("מזהה אירוע");
        TableColumn<Update, String> TitleCol = new TableColumn<Update, String>("הודעה");
        TableColumn<Update, String> PublishTimeCol = new TableColumn<Update, String>("תאריך פרסום");

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
        Main.switchScene("../View/MainScreen.fxml", Main.getStage(), Main.mainWidth, Main.mainHeight);

    }
}
