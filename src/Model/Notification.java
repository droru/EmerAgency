package Model;

public class Notification
{
    public int NotificationID;
    public String title;
    public String PublishTime;
    public String Status;


    public Notification(int notificationID, String title, String publishTime, String status) {
        NotificationID = notificationID;
        this.title = title;
        PublishTime = publishTime;
        Status = status;
    }

    public Notification(String title, String status) {
        this.title = title;
        Status = status;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "NotificationID=" + NotificationID +
                ", title='" + title + '\'' +
                ", PublishTime='" + PublishTime + '\'' +
                ", Status='" + Status + '\'' +
                '}';
    }
}

