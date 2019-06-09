package Model;

public class User
{
    private int UserId;
    private String UserName;
    private int OrganizationId;
    private String Password;
    private int Rank;
    private String Email;
    private String Status;

    public User(int userId, String userName, int organizationId, String password, int rank, String email, String status) {
        UserId = userId;
        UserName = userName;
        OrganizationId = organizationId;
        Password = password;
        Rank = rank;
        Email = email;
        Status = status;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getOrganizationId() {
        return OrganizationId;
    }

    public void setOrganizationId(int organizationId) {
        OrganizationId = organizationId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getRank() {
        return Rank;
    }

    public void setRank(int rank) {
        Rank = rank;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
