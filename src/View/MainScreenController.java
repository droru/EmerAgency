package View;

import Model.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import sample.Aview;
import sample.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainScreenController extends Aview
{

    @FXML
    public TableView<Event> EventTable;
    public Label Title;
    public ImageView pic;
    public Button addEventbtn;


    public void initialize() {
        getController().getUsers();
        getController().getEvents();
        getController().getCategories();
        getController().getOrganizations();
        //getController().getReports();


        if(Main.loggedUser.getOrganizationId()!=4)
            addEventbtn.setVisible(false);

        if(Main.OrganizationName == "")
            Main.OrganizationName = getController().getOrganizationName(Main.loggedUser.getOrganizationId());
        Title.setText(Main.OrganizationName + " Events");
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

        if (EventTable.getColumns().size() == 0&&Main.loggedUser.getOrganizationId()!=4) {
            ObservableList<Event> events = getController().getEventsByUserId(Main.loggedUser.getUserId());
            setTableData(events);
        }
        else
        {
            ObservableList<Event> events = getController().getAllEvent();
            setTableData(events);
        }
    }



    private void setTableData(ObservableList<Event> events) {

        TableColumn actionCol1 = new TableColumn("עידכון");
        TableColumn actionCol2 = new TableColumn("עידכונים");
        TableColumn actionCol3=new TableColumn("דוח");
        TableColumn<Event, Integer> eventIDCol = new TableColumn<Event, Integer>("מספר מזהה אירוע");
        TableColumn<Event, Integer> eventPublishCol = new TableColumn<Event, Integer>("מפרסם אירוע");
        TableColumn<Event, String> eventTitleCol = new TableColumn<Event, String>("כותרת אירוע");
       // TableColumn<Event, String> eventPublishTimeCol = new TableColumn<Event, String>("תאריך פירסום");
        TableColumn<Event, String> eventStatusCol = new TableColumn<Event, String>("סטאטוס אירוע");

        actionCol1.setCellFactory(new PropertyValueFactory<>("DUMMY"));
        Callback<TableColumn<Event, String>, TableCell<Event, String>> cellFactory
                = new Callback<TableColumn<Event, String>, TableCell<Event, String>>() {
            @Override
            public TableCell call(final TableColumn<Event, String> param) {
                final TableCell<Event, String> cell = new TableCell<Event, String>() {
                    final Button btn = new Button("הוסף עדכון");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            if(getTableView().getItems().get(getIndex()).getStatus().equals("Finish"))
                            {
                                btn.setVisible(false);
                                //System.out.println("yes");
                            }
                            btn.setOnAction(event -> {
                                Main.EventId = getTableView().getItems().get(getIndex()).getEventID();
                                Main.switchScene("../View/AddUpdate.fxml", Main.getStage(), Main.mainWidth, Main.mainHeight);
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };


        actionCol2.setCellFactory(new PropertyValueFactory<>("DUMMY"));
        Callback<TableColumn<Event, String>, TableCell<Event, String>> cellFactory1
                = new Callback<TableColumn<Event, String>, TableCell<Event, String>>() {
            @Override
            public TableCell call(final TableColumn<Event, String> param) {
                final TableCell<Event, String> cell = new TableCell<Event, String>() {
                    final Button btn = new Button("צפייה בעדכונים");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                Main.EventId = getTableView().getItems().get(getIndex()).getEventID();
                                Main.switchScene("../View/WatchUpdate.fxml", Main.getStage(), Main.mainWidth, Main.mainHeight);
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };


        actionCol3.setCellFactory(new PropertyValueFactory<>("DUMMY"));
        Callback<TableColumn<Event, String>, TableCell<Event, String>> cellFactory2
                = new Callback<TableColumn<Event, String>, TableCell<Event, String>>() {
            @Override
            public TableCell call(final TableColumn<Event, String> param) {
                    final TableCell<Event, String> cell = new TableCell<Event, String>() {
                    final Button btn = new Button("יצירת דוח");

                    @Override
                    public void updateItem(String item, boolean empty) {

                            super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            if(getTableView().getItems().get(getIndex()).getStatus().equals("Active")||Main.loggedUser.getOrganizationId()!=4)
                                setVisible(false);
                            btn.setOnAction(event -> {
                                System.out.println("btn pressed");
                                int eventId=( this.getTableView().getItems().get(getIndex()).getEventID());
                                reportController.eventID=eventId;
                                getController().createReport(eventId);
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/eventReport.fxml"));
                                try {
                                    Parent root = (Parent)fxmlLoader.load();
                                    Main.newStage(root,"דוח אירוע",450,632,Title.getScene().getWindow());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };



        actionCol1.setCellFactory(cellFactory);
        actionCol2.setCellFactory(cellFactory1);
        actionCol3.setCellFactory(cellFactory2);
        eventIDCol.setCellValueFactory(new PropertyValueFactory<>("EventID"));
        eventPublishCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        eventTitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
       // eventPublishTimeCol.setCellValueFactory(new PropertyValueFactory<>("publish_time"));
        eventStatusCol.setCellValueFactory(new PropertyValueFactory<>("Status"));
        EventTable.setItems(events);
        EventTable.getColumns().addAll(eventIDCol,eventTitleCol,eventPublishCol,actionCol2,actionCol1,actionCol3,eventStatusCol );

    }

    public void addEvent(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/AddEvent.fxml"));
        try {
            Parent root = (Parent)fxmlLoader.load();
            AddEventController addEventController = fxmlLoader.getController();
            addEventController.setData(this);
            Main.newStage(root, "AddEvent", 400, 600, Title.getScene().getWindow());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addEventToTable(Event newEvent) {
        List<Event> events = new ArrayList<>(EventTable.getItems());
        events.add(newEvent);
        ObservableList<Event> observableList = FXCollections.observableList(events);
        EventTable.setItems(observableList);
    }
}
