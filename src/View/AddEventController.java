package View;
import Model.Category;
import Model.Organization;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import sample.Aview;

import java.util.ArrayList;
import java.util.List;

public class AddEventController extends Aview {

    @FXML
    public TextField txt_eventTitle;
    public TextArea txt_initUpdate;
    public ListView list_organizations;
    public ListView list_categories;

    ObservableList<Organization> organizations;
    ObservableList<Category> categories;
    List<Category> selectedCategories= new ArrayList<>();
    List<Organization> selectedOrganizations= new ArrayList<>();
    public void initialize() {
        organizations = getController().getAllOrganizations();
        categories = getController().getAllCategories();
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

        }
    }

    public void cancelclicked(MouseEvent mouseEvent) {
    }
}
