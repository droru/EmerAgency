package Model;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Query
{
    private static Connection connect() {
        // SQLite connection string
        //DriverManager.getConnection("jdbc:sqlite:D:\\db\\my-db.sqlite");
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
                + "FROM USERS WHERE UserName =  '" +st + "'" ;

        try (Connection conn = connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            ResultSet rs  = pstmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("UserId"),rs.getString("UserName"),rs.getInt("OrganizationId"),rs.getString("Password"),rs.getInt("Rank"),rs.getString("Email"),rs.getString("Status")) ;
            }

            else {
                return null ;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null ;
        }
    }



    public Event getEventbyID(int id) {
        String sql = "SELECT * "
                + "FROM Event WHERE EventID =  '" +id + "'" ;

        try (Connection conn = connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            ResultSet rs  = pstmt.executeQuery();
            if (rs.next()) {
                return new Event(rs.getInt("EventID"),rs.getInt("Publisher"),rs.getString("title"),rs.getDate("publishDateTime"),rs.getString("Status"));
            }

            else {
                return null ;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null ;
        }
    }

    public ObservableList<Event> getEventsByUserName(String userName)
    {
        String sql = "SELECT * FROM Event  INNER JOIN UserEvent ON UserEvent.EventId=Event.EventId AND UserEvent.UserId="+userName+" group BY Event.EventId;";

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
            Event e= new Event(rs.getInt("EventID"),rs.getInt("Publisher"),rs.getString("title"),rs.getDate("publishDateTime"),rs.getString("Status"));
            events.add(e);
        }
        if(events != null) {
            ObservableList<Event> observableEvents = FXCollections.observableArrayList(events);
            return observableEvents;
        }
        return null;
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
                orgs.add(new Organization(rs.getInt("OrganizationId"), rs.getString("OrganizationName")));
            }
            ObservableList<Organization> observablOrgs = FXCollections.observableArrayList(orgs);
            return observablOrgs;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /*
    public int insert(Model.Notification noti)
    {
            String sql = "INSERT INTO Notification (NotificationID, UserId, EventId , Title, PublishTime ,PublishDate ,Status ) VALUES(?,?,?,?,?,?,?)";
            try (
                    Connection conn = connect();
                    PreparedStatement pstmt = conn.prepareStatement(sql))
            {
                pstmt.setInt(1,noti.getNotificationID());
                pstmt.setInt(2, noti.getUserId());
                pstmt.setInt(3,noti.getEventId());
                pstmt.setString(4,noti.getTitle() );
                pstmt.setString(5, noti.getPublishTime());
                pstmt.setString(6,noti.getPublishDate());
                pstmt.setString(7,noti.getStatus());

                return pstmt.executeUpdate();
                //return 0 ;
            } catch (SQLException e) {
                return 1 ;
            }
    }

     */

    /*
    public ObservableList<Model.Notification> getNotificationByUserName()
    {
        String sql = "SELECT * FROM Notification  where EventId="+ Main.EventId;
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

     */
    /*

    private ObservableList<Model.Notification> notificationResultSetToObservable(ResultSet rs) throws SQLException {
        List< Model.Notification> notifications = new ArrayList<>();
        while (rs.next()) {
            Model.Notification e = new  Model.Notification(rs.getInt("NotificationID"),rs.getInt("UserId"), rs.getInt("EventId"), rs.getString("Title"), rs.getString("PublishTime"), rs.getString("PublishDate"), rs.getString("Status"));
            notifications.add(e);
        }
        if(notifications != null) {
            ObservableList< Model.Notification> observablenotifications = FXCollections.observableArrayList(notifications);
            return observablenotifications;
        }
        return null;
    }


     */
    public List<User> getallUsers() {
        String sql = "SELECT * FROM Users";
        System.out.println(sql);
        List< User> users = new ArrayList<>();
        try(Connection conn = connect();
            PreparedStatement psmt = conn.prepareStatement(sql);){
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                Model.User user = new  Model.User(rs.getInt("UserId"),rs.getString("UserName"), rs.getInt("OrganizationId"), rs.getString("Password"), rs.getInt("Rank"), rs.getString("Email"), rs.getString("Status"));
                users.add(user);
            }
            return users;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int insertEvent(Event event, ObservableList<Category> addedCategories, ObservableList<Organization> addedOrganozations, Model.Update initUpdate){
        String sql = "INSERT INTO Event (Publisher, Title, publishDateTime ,Status ) VALUES(?,?,?,?)";
        try (
                Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1, event.getPublisher());
            pstmt.setString(2,event.getTitle());
            pstmt.setDate(3, (Date) event.getPublishDateTime());
            pstmt.setString(4, event.getStatus());

            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int newId = rs.getInt(1);
                event.setEventID(newId);
                insertCategories(newId, addedCategories);
                insertOrganization(newId, addedOrganozations);
                initUpdate.setEvnentID(newId);
                //insert(initUpdate);
               // insertUsersWithPermissions(newId, addedOrganozations);
            }
            return 0 ;
        } catch (SQLException e) {
            e.printStackTrace();
            return 1 ;
        }
    }

    public void insertCategories(int eID, ObservableList<Category> list) throws SQLException {
        for (Category c:list) {
            String sql = "INSERT INTO EventCategory(EventID, CategoryId) VALUES (?,?)";
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, eID);
            pstmt.setInt(2, c.id);
            pstmt.executeUpdate();
        }
    }
    public void insertOrganization(int eID, ObservableList<Organization> list) throws SQLException {
        for(Organization o:list){
            String sql = "INSERT INTO EventOrganization(EventId, OrganizationID) VAlUES(?,?)";
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, eID);
            pstmt.setInt(2, o.getId());
            pstmt.executeUpdate();
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
            pstmt.setDate(4, (Date) update.getPublishDate());
            pstmt.setString(3, update.getDesc());
            pstmt.executeUpdate();
            return 0 ;
        } catch (SQLException e) {
            e.printStackTrace();
            return 1 ;
        }
    }
   /*
    public void insertUsersWithPermissions(int eID, ObservableList<Organization> list) throws SQLException {
        for(Organization o:list){
            Connection conn = connect();
            ResultSet rs = getAllUsersInOrganization(conn, o.getId());
            while (rs.next()) {
                String sql = "INSERT INTO UserEvent(UserId, EventId, Permission) VALUES(?,?,?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, rs.getInt("UserId"));
                pstmt.setInt(2, eID);
                pstmt.setString(3, "R");
                pstmt.executeUpdate();
            }
            String sql = "INSERT INTO UserEvent(UserId, EventId, Permission) VALUES('1', "+eID+", 'R')";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();

        }
    }

    */
}
