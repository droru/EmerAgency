package Model;

import java.sql.Date;
import java.util.Map;

public class Update {
    private  int updateID;
    private  int userID;
    private  int evnentID;
    private  String desc;
    private Date publishDate;

    public Update(int updateID, int userID, int evnentID, String desc, Date publishDate) {
        this.updateID = updateID;
        this.userID = userID;
        this.evnentID = evnentID;
        this.desc = desc;
        this.publishDate = publishDate;
    }

    public Update(int userID, int evnentID, String desc) {
        //DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        this.publishDate=date;
        this.userID = userID;
        this.evnentID = evnentID;
        this.desc = desc;
    }


    public int getUpdateID() {
        return updateID;
    }

    public void setUpdateID(int updateID) {
        this.updateID = updateID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getEvnentID() {
        return evnentID;
    }

    public void setEvnentID(int evnentID) {
        this.evnentID = evnentID;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public String toString() {
        return "Update{" +
                "updateID=" + updateID +
                ", userID=" + userID +
                ", evnentID=" + evnentID +
                ", desc='" + desc + '\'' +
                ", publishDate=" + publishDate +
                '}';
    }

    public String updateForReport(Map<Integer,User> userMap){
        return "מספר עדכון :"+updateID + "   " +
                "מפרסם העדכון :" +userMap.get(userID).getUserName()+"\n"+
                "תיאור העדכון: " +desc +"\n"+
                "תאריך פירסום: " +publishDate;


    }
}
