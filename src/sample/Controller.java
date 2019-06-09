package sample;

import Model.*;
import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {

    private Controller() { }
    private static Controller controller;

    public static Controller getInstance(){
        if(controller==null)
             controller=new Controller();
        return controller;
    }

    private  static Query query=new Query();

    private Map<Integer,User> users ;
    private Map<Integer,Event> events;
    private Map<Integer,Organization> organizations;
    private Map<Integer, Category> categories;

    public Event createEvent(int userID,String Title) {
        User u=users.get(userID);
        Event e=new Event(Title,u);
        int newID=query.insertEvent(e);
        e.setEventID(newID);
        events.put(newID,e);
        return e;
    }
    public void setInitialUpdate(int eventID,int userID,String desc){
        createUpdate(eventID,userID,desc);

    }
    private void createUpdate(int eventID, int userID, String desc) {
        Event event=events.get(eventID);
        User user=users.get(userID);
        Update update=event.createUpdate(user,desc);
        query.inserUpdate(update); }
    public void selectCategory(int eventID, int intCategoryID){
        Event e = events.get(eventID);
        Category c = categories.get(intCategoryID);
        e.addCategory(c);
        query.insertCategoryForEvent(eventID, c);
    }
    public void selectOrganization(int eventID,int OrganizationID) {
        Event e=events.get(eventID);
        Organization o=organizations.get(OrganizationID);
        e.addOrganization(o);
        query.insertOrganizationForEvent(eventID,o);
    }
    public void sendNotification(int eventID,String title){
        Event e=events.get(eventID);
        Pair<Notification,List<User>> res=e.sendNotifications(title);
        int notId=query.insertNotification(res.getKey());
        for(User u: res.getValue())
            query.insertNotificationToUser(notId,u);

    }

    public void setReadPremissions(int eventID)
    {
        Event e=events.get(eventID);
        List<Permission> permissions =e.setPremissions();
        for(Permission p: permissions)
            query.insertPremission(p);

    }

    public Map<Integer,Event> getEvents() {
        if (events==null){
            List<Event> tmpEvents=new ArrayList<>();
            tmpEvents=query.getEventsByUserName(Main.loggedUser.getUserId());
            events=new HashMap<>();
            for (Event e:tmpEvents)
            {
                events.put(e.getEventID(),e);
            }
        }
        return events;
    }
    public Map<Integer,User> getUsers(){
        if (users==null){
            List<User> tmpUsers=new ArrayList<>();
            tmpUsers=query.getallUsers();
            users=new HashMap<>();
            for (User u: tmpUsers)
            {
                users.put(u.getUserId(),u);
            }
        }
        return users;
    }
    public Map<Integer, Category> getCategories() {
        if(categories == null){
            List<Category> tmpCateroty = new ArrayList<>();
            tmpCateroty = query.getAllCategories();
            categories = new HashMap<>();
            for(Category c : tmpCateroty){
                categories.put(c.getId(), c);
            }
        }
        return categories;
    }
    public Map<Integer,Organization> getOrganizations() {
        if(organizations == null){
            List<Organization> tmpOrg = new ArrayList<>();
            tmpOrg = query.getAllOrganizations();
            organizations = new HashMap<>();
            for(Organization organization : tmpOrg){
                organizations.put(organization.getId(),organization);
            }
        }
        return organizations;
    }




    //region User
    public User search_username(String Username){ return query.search_username(Username); }

    public Event getEventbyID(int id){return  query.getEventbyID(id);}

    public ObservableList<Event> getEventsByUserId(int ID) { return query.getEventsByUserName(ID);}

    public String getOrganizationName(int orgId) { return query.getOrganizationName(orgId);}

    //public int AddNotification(Notification n) { return query.insert(n);}

    //public ObservableList<Notification> getNotificationByUserName() { return query.getNotificationByUserName();}

    public ObservableList<Organization> getAllOrganizations(){return query.getAllOrganizations();}

    //public ArrayList<Notification> getNotificationByEventID(int eventID){return query.getNotificationByEventID(eventID);}

    public List<User> getUsersDB() {return query.getallUsers() ;}

    public int insertUpdate(Update update) {return query.inserUpdate(update); }

    /*
    public void test(int i, ObservableList selectedItems) throws SQLException {
        query.insertUsersWithPermissions(i, selectedItems);
    }

     */
}

