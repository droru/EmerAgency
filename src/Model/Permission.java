package Model;

public class Permission {

    private int userID;
    private int eventID;
    private String premission;
    private int isManger;

    public Permission(int userID, int eventID, String premission, int isManger) {
        this.userID = userID;
        this.eventID = eventID;
        this.premission = premission;
        this.isManger = isManger;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getPremission() {
        return premission;
    }

    public void setPremission(String premission) {
        this.premission = premission;
    }

    public int getIsManger() {
        return isManger;
    }

    public void setIsManger(int isManger) {
        this.isManger = isManger;
    }
}
