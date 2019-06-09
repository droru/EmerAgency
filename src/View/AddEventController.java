package View;
import Model.Category;
import Model.Event;
import Model.Organization;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Aview;
import sample.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddEventController extends Aview {

    @FXML
    public TextField txt_eventTitle;
    public TextArea txt_initUpdate;
    public ListView list_organizations;
    public ListView list_categories;
    private MainScreenController main;

    ObservableList<Organization> organizations;
    ObservableList<Category> categories;
    List<Category> selectedCategories= new ArrayList<>();
    List<Organization> selectedOrganizations= new ArrayList<>();
    public void initialize() {
        organizations = FXCollections.observableArrayList(getController().getOrganizations().values());
        categories = FXCollections.observableArrayList(getController().getCategories().values());
        list_organizations.setItems(organizations);
        list_organizations.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        list_organizations.getFocusModel().focus(0);
        list_organizations.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> {
            handleMultipleSelection(evt, list_organizations);
        });
        /*list_organizations.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("ListView selection changed from oldValue = "
                        + oldValue + " to newValue = " + newValue);
            }
        });*/

        list_categories.setItems(categories);
        list_categories.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        list_categories.getFocusModel().focus(0);
        list_categories.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> {
            handleMultipleSelection(evt, list_categories);
        });
    }

    public void setData(MainScreenController main){
        this.main = main;
    }

    private void handleMultipleSelection(MouseEvent evt, ListView list_categories) {
        Node node = evt.getPickResult().getIntersectedNode();
        while (node != null && node != list_categories && !(node instanceof ListCell)) {
            node = node.getParent();
        }
        if (node instanceof ListCell) {
            evt.consume();
            ListCell cell = (ListCell) node;
            ListView lv = cell.getListView();
            lv.requestFocus();
            if (!cell.isEmpty()) {
                int index = cell.getIndex();
                if (cell.isSelected()) {
                    lv.getSelectionModel().clearSelection(index);
                } else {
                    lv.getSelectionModel().select(index);
                }
            }
        }
    }

    public void saveclicked(MouseEvent mouseEvent) {
        boolean isAllValid = true;
        if (txt_eventTitle.getText() != "" && txt_eventTitle.getLength() != 0 )
            txt_eventTitle.getStyleClass().remove("error");
        else {
            txt_eventTitle.getStyleClass().add("error");
            isAllValid = false;
        }
        if(list_categories.getSelectionModel().getSelectedItems().size() != 0)
            list_categories.getStyleClass().remove("error");
        else {
            list_categories.getStyleClass().add("error");
            isAllValid = false;
        }
        if(list_organizations.getSelectionModel().getSelectedItems().size() != 0)
            list_organizations.getStyleClass().remove("error");
        else {
            list_organizations.getStyleClass().add("error");
            isAllValid = false;
        }
        if(txt_initUpdate.getText() != "" && txt_initUpdate.getText().length() != 0)
            txt_initUpdate.getStyleClass().remove("error");
        else {
            txt_initUpdate.getStyleClass().add("error");
            isAllValid = false;
        }

        if(isAllValid){
          try {
              Event newEvent = getController().createEvent(Main.loggedUser.getUserId(), txt_eventTitle.getText());
              int newID = newEvent.getEventID();
              getController().setInitialUpdate(newID, Main.loggedUser.getUserId(), txt_initUpdate.getText());
              for (Category c : (ObservableList<Category>) list_categories.getSelectionModel().getSelectedItems()) {
                  getController().selectCategory(newID, c.getId());
              }
              for (Organization o : (ObservableList<Organization>) list_organizations.getSelectionModel().getSelectedItems()) {
                  getController().selectOrganization(newID, o.getId());
              }
              getController().sendNotification(newID, "אירוע חדש נוסף בשם: " + txt_eventTitle);
              getController().setReadPremissions(newID);


              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("הוספת אירוע");
              alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
              alert.setContentText("השמירה בוצעה בהצלחה");
              alert.showAndWait();
              alert.close();
              ((Stage) list_organizations.getScene().getWindow()).close();
              newEvent.setStatus("Active");
              main.addEventToTable(newEvent);
          }catch (Exception e){
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("הוספת אירוע");
              alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
              alert.setContentText("ארעה שגיאה בעת הוספת האירוע.");
              alert.showAndWait();
              alert.close();
          }
        }

    }

    public void cancelclicked(MouseEvent mouseEvent) {
        if(txt_eventTitle.getText().length() != 0 || list_categories.getSelectionModel().getSelectedItems().size() != 0 || list_organizations.getSelectionModel().getSelectedItems().size() != 0 || txt_initUpdate.getText().length() != 0){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("הוספת אירוע");
            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            alert.setContentText("השינויים לא ישמרו,\n האם אתה בטוח שאתה רוצה לצאת ללא שמירה?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == ButtonType.OK) {
                alert.close();
                ((Stage) list_organizations.getScene().getWindow()).close();
            } else {
                alert.close();
            }
        }
    }
}
