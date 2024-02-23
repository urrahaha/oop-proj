package login;

import forum.Forum;

public class User {
    private final int id;
    private String username;
    private String password;
    private String contactNumber;

    private User(String username, String password ,String contactNumber) {
        this.id = Forum.getUsers().size();
        this.username = username;
        this.password = password;
        this.contactNumber = contactNumber;
        Forum.getUsers().add(this);
    }

    public static User createUser(String name, String password, String contactNumber) {
        return new User(name, password, contactNumber);
    }

    public int getID() { return id; }

    public String getUsername() { return username; }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
