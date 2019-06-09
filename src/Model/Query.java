package Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Query
{
    private static Connection connect() throws SQLException {
        //SQLite connection string
        //DriverManager.getConnection("jdbc:sqlite:D:\\db\\Emer-Agency-DB.sqlite");
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        String url = "jdbc:sqlite:src/DataBase/Emer-Agency-DB";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    public User search_username(String st) {
        String sql = "SELECT * "
                + "FROM Users WHERE UserName =  '" +st + "'" ;

        try (Connection conn = connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            ResultSet rs  = pstmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("UserId"),rs.getString("UserName"),rs.getInt("OrganizationId"),rs.getString("Password"),rs.getInt("Rank"),rs.getString("Email"),rs.getString("Status"), getNotificationUserID(rs.getInt("UserId")),getPermissionByuserID(rs.getInt("UserId")));

            }

            else {
                return null ;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null ;
        }
    }

    private List<Permission> getPermissionByuserID(int userId) {
        String sql = "SELECT * FROM UserEvent  where UserId="+userId;
        try(Connection conn = connect();
            PreparedStatement psmt = conn.prepareStatement(sql);){
            ResultSet rs = psmt.executeQuery();
            List<Permission> permissions = new ArrayList<>();
            while (rs.next()) {
                Permission p = new Permission(rs.getInt("UserId"), rs.getInt("EventId"), rs.getString("Permission"),rs.getInt("isManger"));
                permissions.add(p);
            }
            return permissions;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public Event getEventbyID(int id) {
        String sql = "SELECT * "
                + "FROM Event WHERE EventID =  '" +id + "'" ;

        try (Connection conn = connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            ResultSet rs  = pstmt.executeQuery();
            if (rs.next()) {
                return new Event(rs.getInt("EventID"),rs.getInt("Publisher"),rs.getString("title"),rs.getDate("publishDateTime"),rs.getString("Status"), getUpdatesByEventID(rs.getInt("EventID")), getCategoriesByEventID(rs.getInt("EventID")), getOrganizationByEventID(rs.getInt("EventID")));
            }

            else {
                return null ;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null ;
        }
    }

    public ObservableList<Event> getEventsByUserName(int userID)
    {
        String sql = "SELECT * FROM Event INNER JOIN UserEvent ON UserEvent.EventId=Event.EventId AND UserEvent.UserId="+userID+" group BY Event.EventId;";

        try(Connection conn = connect();
            PreparedStatement psmt = conn.prepareStatement(sql);){
            //psmt.setString(1, userName);
            ResultSet rs = psmt.executeQuery();

            return eventtResultSetToObservable(rs);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private ObservableList<Event> eventtResultSetToObservable(ResultSet rs) throws SQLException {
        List<Event> events = new ArrayList<>();
        while (rs.next()) {


            //System.out.println(d.toString());
            Event e= new Event(rs.getInt("EventID"),rs.getInt("Publisher"),rs.getString("title"),rs.getDate("publishDateTime"),rs.getString("Status"), getUpdatesByEventID(rs.getInt("EventID")), getCategoriesByEventID(rs.getInt("EventID")), getOrganizationByEventID(rs.getInt("EventID")));
            events.add(e);
        }
        if(events != null) {
            ObservableList<Event> observableEvents = FXCollections.observableArrayList(events);
            return observableEvents;
        }
        return null;
    }

    private List<Organization> getOrganizationByEventID(int eventID) {
        String sql = "SELECT * FROM Organization inner join EventOrganization where EventId="+eventID;
        try(Connection conn = connect();
            PreparedStatement psmt = conn.prepareStatement(sql);){
            ResultSet rs = psmt.executeQuery();

            List<Organization> organizations = new ArrayList<>();
            while (rs.next()) {
                Organization org = new Organization(rs.getInt("OrganizationId"), rs.getString("OrganizationName"), getUserByOrganizationID(rs.getInt("OrganizationID")));
                organizations.add(org);
            }
            return organizations;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private List<Category> getCategoriesByEventID(int eventID) {
        String sql = "SELECT * FROM Category inner join EventCategory where EventID="+eventID;
        try(Connection conn = connect();
            PreparedStatement psmt = conn.prepareStatement(sql);){
            ResultSet rs = psmt.executeQuery();

            List<Category> categories = new ArrayList<>();
            while (rs.next()) {
                Category category = new Category(rs.getInt("CategoryID"), rs.getString("NameCategory"));
                categories.add(category);
            }
            return categories;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private List<Update> getUpdatesByEventID(int eventID) {
        String sql = "SELECT * FROM  EventUpdates where EventId="+eventID;
        try(Connection conn = connect();
            PreparedStatement psmt = conn.prepareStatement(sql);){
            ResultSet rs = psmt.executeQuery();

            List<Update> updates = new ArrayList<>();
            while (rs.next()) {
                Update u = new Update(rs.getInt("updateID"),rs.getInt("UserId"),rs.getInt("EventId"),rs.getString("description"),rs.getDate("publishDateTime"));
                updates.add(u);
            }
            return updates;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public ObservableList<Update> getObserverUpdatesByEventID(int eventID){
        List<Update> updates = getUpdatesByEventID(eventID);
        return FXCollections.observableArrayList(updates);
    }

    public ObservableList<Category> getAllCategories()
    {
        String sql = "SELECT * FROM Category";
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            List<Category> categories = new ArrayList<>();
            while (rs.next()) {
                categories.add(new Category(rs.getInt("CategoryID"), rs.getString("NameCategory")));
            }
            ObservableList<Category> observablCategories = FXCollections.observableArrayList(categories);
            return observablCategories;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public String getOrganizationName(int orgId)
    {
        String sql = "SELECT OrganizationName FROM Organization  where OrganizationId="+orgId;
        try(Connection conn = connect();
            PreparedStatement psmt = conn.prepareStatement(sql);){
            //psmt.setString(1, userName);
            ResultSet rs = psmt.executeQuery();

            return rs.getString("OrganizationName");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public ObservableList<Organization> getAllOrganizations()
    {
        String sql = "SELECT * FROM Organization limit 3";
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            List<Organization> orgs = new ArrayList<>();
            while (rs.next()) {
                List<User> users = getUserByOrganizationID(rs.getInt("OrganizationID"));
                orgs.add(new Organization(rs.getInt("OrganizationId"), rs.getString("OrganizationName"), users));
            }
            ObservableList<Organization> observablOrgs = FXCollections.observableArrayList(orgs);
            return observablOrgs;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private List<User> getUserByOrganizationID(int organizationID) {
        String sql = "SELECT * FROM Users where OrganizationId="+organizationID;
        System.out.println(sql);

        try(Connection conn = connect();
            PreparedStatement psmt = conn.prepareStatement(sql);){
            ResultSet rs = psmt.executeQuery();

            List<User> users = new ArrayList<>();
            while (rs.next()) {
                User u =new User(rs.getInt("UserId"),rs.getString("UserName"),rs.getInt("OrganizationId"),rs.getString("Password"),rs.getInt("Rank"),rs.getString("Email"),rs.getString("Status"), getNotificationUserID(rs.getInt("UserId")),getPermissionByuserID(rs.getInt("UserId")));

                users.add(u);
            }
            return users;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    private ObservableList<Model.Notification> getNotificationUserID(int userID)
    {
        String sql = "SELECT * FROM Notification inner join UserNotification where userID="+userID;
        System.out.println(sql);

        try(Connection conn = connect();
            PreparedStatement psmt = conn.prepareStatement(sql);){
            ResultSet rs = psmt.executeQuery();

            return notificationResultSetToObservable(rs);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private ObservableList<Model.Notification> notificationResultSetToObservable(ResultSet rs) throws SQLException {
        List< Model.Notification> notifications = new ArrayList<>();
        while (rs.next()) {
            Model.Notification e = new  Model.Notification(rs.getInt("NotificationID"), rs.getString("Title"), rs.getString("PublishTime"));
            notifications.add(e);
        }
        if(notifications != null) {
            ObservableList< Model.Notification> observablenotifications = FXCollections.observableArrayList(notifications);
            return observablenotifications;
        }
        return null;
    }

    public List<User> getallUsers() {
        String sql = "SELECT * FROM Users";
        System.out.println(sql);
        List< User> users = new ArrayList<>();
        try(Connection conn = connect();
            PreparedStatement psmt = conn.prepareStatement(sql);){
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                List<Notification> not=getNotificationUserID(rs.getInt("UserId"));
                Model.User user = new User(rs.getInt("UserId"),rs.getString("UserName"),rs.getInt("OrganizationId"),rs.getString("Password"),rs.getInt("Rank"),rs.getString("Email"),rs.getString("Status"), getNotificationUserID(rs.getInt("UserId")),getPermissionByuserID(rs.getInt("UserId")));

                users.add(user);
            }
            return users;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int insertEvent(Event event){
        String sql = "INSERT INTO Event (Publisher, Title, publishDateTime  ) VALUES(?,?,?)";
        try (
                Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1, event.getPublisher());
            pstmt.setString(2,event.getTitle());
            pstmt.setDate(3, event.getPublishDateTime());
            //pstmt.setString(4, event.getStatus());
            pstmt.executeUpdate();
            ResultSet rs=pstmt.getGeneratedKeys();
           return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1 ;
        }
    }
    public int insertCategoryForEvent(int eventID, Category c){
        String sql = "INSERT INTO EventCategory(EventID, CategoryId) VALUES (?,?)";
        try(Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, eventID);
            pstmt.setInt(2, c.getId());
            pstmt.executeUpdate();
            return 0;
        }
        catch (SQLException e){
            e.printStackTrace();
            return 1;
        }
    }


    public int inserUpdate(Update update) {
        String sql = "INSERT INTO EventUpdates (userid, eventid, description, publishdatetime) VALUES(?,?,?,?)";
        try (
                Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1, update.getUserID());
            pstmt.setInt(2,update.getEvnentID());
            pstmt.setDate(4, update.getPublishDate());
            pstmt.setString(3, update.getDesc());
            pstmt.executeUpdate();
            return 0 ;
        } catch (SQLException e) {
            e.printStackTrace();
            return 1 ;
        }
    }

    public int insertOrganizationForEvent(int eventID, Organization o) {
        String sql = "INSERT INTO EventOrganization(EventId, OrganizationID) VAlUES(?,?)";
        try(Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, eventID);
            pstmt.setInt(2, o.getId());
            pstmt.executeUpdate();
            return 0;
        }
        catch (SQLException e){
            e.printStackTrace();
            return 1;
        }
    }

    public int insertNotificationToUser(int notID, User u) {
        String sql = "INSERT INTO UserNotification(notificationID, userID) VAlUES(?,?)";
        try(Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, notID);
            pstmt.setInt(2, u.getUserId());
            pstmt.executeUpdate();
            return 0;
        }
        catch (SQLException e){
            e.printStackTrace();
            return 1;
        }
    }

    public int insertNotification(Notification n) {
        String sql = "INSERT INTO Notification(Title, PublishTime) VAlUES(?,?)";
        try(Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, n.getTitle());
            pstmt.setString(2, n.getPublishTime());
            pstmt.executeUpdate();
            ResultSet rs=pstmt.getGeneratedKeys();
            return rs.getInt(1);
        }
        catch (SQLException e){
            e.printStackTrace();
            return 1;
        }
    }

    public int insertPremission(Permission p) {
        String sql = "INSERT INTO UserEvent(UserId,EventId,Permission,isManger) VAlUES(?,?,?,?)";
        try(Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, p.getUserID());
            pstmt.setInt(2, p.getEventID());
            pstmt.setString(3, p.getPremission());
            pstmt.setInt(4, p.getIsManger());
            pstmt.executeUpdate();
            return 0;
        }
        catch (SQLException e){
            e.printStackTrace();
            return 1;
        }
    }
}
