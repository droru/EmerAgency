package Model;


import java.util.HashMap;
import java.util.Map;

public class Organization {

    Map<Integer,Report>reports;
    Map<Integer,User>users;
    private int id;
    private String name;

    public Organization(int id, String name,Map<Integer,User>users){//},Map<Integer,Report>reports) {
        this.id = id;
        this.name = name;
        this.users=new HashMap<>();
        this.users=users;
        this.reports=new HashMap<>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<Integer,User> getUsers() {
        return users;
    }

    public void addReport(Report report) {
        reports.put(report.getReportID(),report);
    }

    @Override
    public String toString() {
        return name;
    }
}

