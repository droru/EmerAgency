package Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event {

    private int eventID;
    private int publisher;
    private String title;
    private Date publishDateTime;
    private String status;

    public Event(int eventID, int publish, String title, Date publish_time, String status) {
        this.eventID = eventID;
        this.publisher = publish;
        this.title = title;
        this.publishDateTime = publish_time;
        this.status = status;
    }

    public Event(String title,User user) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        this.publisher=user.getUserId();
        this.publishDateTime=date;
        this.title=title;
        this.title = title;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public int getPublisher() {
        return publisher;
    }

    public void setPublisher(int publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublishDateTime() {
        return publishDateTime;
    }

    public void setPublishDateTime(Date publishDateTime) {
        this.publishDateTime = publishDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventID=" + eventID +
                ", publisher=" + publisher +
                ", title='" + title + '\'' +
                ", publishDateTime=" + publishDateTime +
                ", status='" + status + '\'' +
                '}';
    }
}

