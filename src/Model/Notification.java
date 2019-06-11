package Model;

import java.util.Date;

public class Notification
{
    private int NotificationID;
    private String title;
    private String PublishTime;


    public Notification(int notificationID, String title, String publishTime) {
        NotificationID = notificationID;
        this.title = title;
        PublishTime = publishTime;
    }

    public Notification(String title) {
        this.title = title;
        this.PublishTime=new Date().toString();

    }

    public int getNotificationID() {
        return NotificationID;
    }

    public void setNotificationID(int notificationID) {
        NotificationID = notificationID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishTime() {
        return PublishTime;
    }

    public void setPublishTime(String publishTime) {
        PublishTime = publishTime;
    }

}

