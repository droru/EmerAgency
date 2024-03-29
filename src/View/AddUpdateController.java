package View;

import javafx.event.ActionEvent;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.*;

import sample.Aview;
import sample.Main;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddUpdateController extends Aview {
    public TextField UserId;
    public TextField EventId;
    public TextField Time;
    public TextField date;
    public TextArea body;
    public Label err;


    public void initialize()
    {


        UserId.setText(String.valueOf(Main.loggedUser.getUserId()));
        EventId.setText(String.valueOf(Main.EventId));
        date.setText(new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime()));
        Time.setText(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
    }


    public void send() {
        if (body.getText().isEmpty())
            err.setText("*נא להכניס שדות חוקיים");
        else
        {
            try {
                getController().createUpdate(Integer.valueOf(EventId.getText()), Integer.valueOf(UserId.getText()), body.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("הוספת עדכון לאירוע");
                alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                alert.setContentText("ההוספה בוצעה בהצלחה");
                alert.showAndWait();
                alert.close();
                Main.switchScene("../View/MainScreen.fxml", Main.getStage(), Main.mainWidth, Main.mainHeight);
            }
            catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("הוספת עדכון לאירוע");
                alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                alert.setContentText("ארעה שגיאה בעת הוספת העדכון.");
                alert.showAndWait();
                alert.close();
            }
        }

    }

    public void Back(ActionEvent actionEvent)
    {
        Main.switchScene("../View/MainScreen.fxml", Main.getStage(), Main.mainWidth, Main.mainHeight);
    }
}