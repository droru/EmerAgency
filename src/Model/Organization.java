package Model;

import java.util.ArrayList;
import java.util.List;

public class Organization {

    List<User>users;
    private int id;
    private String name;

    public Organization(int id, String name,List<User>users) {
        this.id = id;
        this.name = name;
        this.users=new ArrayList<>();
        this.users=users;
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

    @Override
    public String toString() {
        return name;
    }

    public List<User> getUsers() {
        return users;
    }
}
