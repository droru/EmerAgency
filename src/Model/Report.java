package Model;
import java.util.Map;

public class Report {
    private Event event;

    private int reportID;
    private int eventID;
    private Map<Integer,Organization> organization;

    public Report(int reportID, int eventID,Event event, Map<Integer,Organization>  organizations) {
        this.event = event;
        this.reportID = reportID;
        this.eventID = eventID;
        this.organization = organizations;
    }

    public Report(Event event, int eventID,  Map<Integer,Organization>  organization) {
        this.event = event;
        this.eventID = eventID;
        this.organization=organization;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public  Map<Integer,Organization>  getOrganizations() {
        return organization;
    }

    public void setOrganizations( Map<Integer,Organization>  organizations) {
        this.organization = organizations;
    }

    @Override
    public String toString() {
        return "Report{" +
                "event=" + event +
                ", reportID=" + reportID +
                ", eventID=" + eventID +
                ", organizations=" + organization +
                '}';
    }
}
