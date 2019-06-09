package Model;

import javafx.util.Pair;
import sample.Main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class Event{

    private List<Update>updates;
    private List<Category> categories;
    private List<Organization>organizations;
    private int eventID;
    private int publisher;
    private String title;
    private Date publishDateTime;
    private String status;

    public Event(int eventID, int publish, String title, Date publish_time, String status,List<Update>updates,List<Category> categories,List<Organization>organizations) {
        this.eventID = eventID;
        this.publisher = publish;
        this.title = title;
        this.publishDateTime = publish_time;
        this.status = status;
        setLists(updates,categories,organizations);
    }

    private void setLists(List<Update> updates, List<Category> categories, List<Organization> organizations) {
        if(this.updates==null)
            this.updates=new ArrayList<>();
        if(categories == null)
            this.categories = new ArrayList<>();
        if(organizations==null)
            this.organizations=new ArrayList<>();

        this.updates=updates;
        this.organizations=organizations;
        this.categories=categories;
    }

    public Event(String title,User user) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        this.publisher=user.getUserId();
        this.publishDateTime=date;
        this.title=title;
        this.title = title;
        if(this.updates==null)
            this.updates=new ArrayList<>();
        if(categories == null)
            categories = new ArrayList<>();
        if(organizations==null)
            organizations=new ArrayList<>();

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

    public Update createUpdate(User user, String desc) {
        Update update=new Update(user.getUserId(),this.eventID,desc);
        this.updates.add(update);
        return update;
    }

    public void addCategory(Category c) {
        categories.add(c);
    }

    public void addOrganization(Organization o) {
        organizations.add(o);
    }

    public Pair<Notification,List<User>> sendNotifications(String Title) {
        List<User>users=null;
        Notification n=null;
        for(Organization o:organizations){
           users=o.getUsers();
             n=new Notification(title);
            for(User u:users){
               u.sendNotification(n);
           }
        }
        Pair <Notification,List<User>> res=new Pair<>(n,users);
        return res;
    }

    public List<Permission> setPremissions() {
       List<Permission>res=new ArrayList<>();
        for (Organization o:organizations) {
            List<User> users =o.getUsers();
            users.add(Main.loggedUser);
            for (User u: users){
                Permission permission =new Permission(u.getUserId(),this.eventID,"R",0);
                u.addPremission(permission);
                res.add(permission);
            }
        }
        return res;
    }
}

