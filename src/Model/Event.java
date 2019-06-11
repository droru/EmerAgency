package Model;

import javafx.util.Pair;
import sample.Main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Date;

public class Event{

    private Map<Integer,Update>updates;
    private Map<Integer,Category> categories;
    private Map<Integer,Organization>organizations;

    private int eventID;
    private int publisher;
    private String title;
    private Date publishDateTime;
    private String status;

    public Event(int eventID, int publish, String title, Date publish_time, String status,Map<Integer,Update>updates,Map<Integer,Category> categories,Map<Integer,Organization>organizations) {
        this.eventID = eventID;
        this.publisher = publish;
        this.title = title;
        this.publishDateTime = publish_time;
        this.status = status;
        setLists(updates,categories,organizations);
    }

    private void setLists(Map<Integer,Update> updates, Map<Integer,Category> categories, Map<Integer,Organization> organizations) {
        if(this.updates==null)
            this.updates=new HashMap<>();
        if(categories == null)
            this.categories = new HashMap<>();
        if(organizations==null)
            this.organizations=new HashMap<>();

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
            this.updates=new HashMap<>();
        if(categories == null)
            categories = new HashMap<>();
        if(organizations==null)
            organizations=new HashMap<>();

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

    public String printString() {
        return "סוג האיורע " +title + "    "  + "מפרסם האירוע :" +publisher+"\n"+
                "תאריך האירוע : " +publishDateTime.toString() +"    " +"סטאטוס האירוע: " +status+"\n";
    }

    public Update createUpdate(User user, String desc) {
        Update update=new Update(user.getUserId(),this.eventID,desc);
        this.updates.put(update.getUpdateID(),update);
        return update;
    }

    public void addCategory(Category c) {
        categories.put(c.getId(),c);
    }

    public void addOrganization(Organization o) {
            organizations.put(o.getId(),o);
    }

    public Pair<Notification,Map<Integer,User>> sendNotifications(String Title) {
        Map<Integer,User>users=null;
        Notification n=null;
        for(Organization o:organizations.values()){
           users=o.getUsers();
             n=new Notification(title);
            for(User u:users.values()){
               u.sendNotification(n);
           }
        }
        Pair <Notification,Map<Integer,User>> res=new Pair<>(n,users);
        return res;
    }

    public Map<Integer,Permission> setPremissions() {
       Map<Integer,Permission>res=new HashMap<>();
        for (Organization o:organizations.values()) {
            Map<Integer,User> users =o.getUsers();
            users.put(Main.loggedUser.getUserId(),Main.loggedUser);
            for (User u: users.values()){
                Permission permission =new Permission(u.getUserId(),this.eventID,"R",0);
                u.addPremission(permission);
                res.put(permission.getUserID(),permission);
            }
        }
        return res;
    }

    public Map<Integer, Update> getUpdates() {
        return updates;
    }

    public void setUpdates(Map<Integer, Update> updates) {
        this.updates = updates;
    }

    public Map<Integer, Category> getCategories() {
        return categories;
    }

    public void setCategories(Map<Integer, Category> categories) {
        this.categories = categories;
    }

    public Map<Integer, Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(Map<Integer, Organization> organizations) {
        this.organizations = organizations;
    }

    public Report createReport() {
        return (new Report(this.eventID+123,this.eventID,this,organizations));
    }

    }


