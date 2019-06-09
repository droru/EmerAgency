package sample;

import Model.*;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private  static Query query=new Query();

    //region User
    public User search_username(String Username){ return query.search_username(Username); }

    public Event getEventbyID(int id){return  query.getEventbyID(id);}

    public ObservableList<Event> getEventsByUserName(String userName) { return query.getEventsByUserName(userName);}

    public String getOrganizationName(int orgId) { return query.getOrganizationName(orgId);}

    //public int AddNotification(Notification n) { return query.insert(n);}

    //public ObservableList<Notification> getNotificationByUserName() { return query.getNotificationByUserName();}

    public ObservableList<Category> getAllCategories(){return query.getAllCategories();}
    public ObservableList<Organization> getAllOrganizations(){return query.getAllOrganizations();}

    //public ArrayList<Notification> getNotificationByEventID(int eventID){return query.getNotificationByEventID(eventID);}

    public List<User> getUsers() {return query.getallUsers() ;}

    public int insertEvent(Event event, ObservableList<Category> categories, ObservableList<Organization> organizations, Update initUpdate){
        return query.insertEvent(event, categories, organizations, initUpdate);
    }

    public int insertUpdate(Update update) {return query.inserUpdate(update); }

    /*
    public void test(int i, ObservableList selectedItems) throws SQLException {
        query.insertUsersWithPermissions(i, selectedItems);
    }

     */
}

