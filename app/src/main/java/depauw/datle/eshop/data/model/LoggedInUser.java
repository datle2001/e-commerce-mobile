package depauw.datle.eshop.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Data class that captures user information for logged in users retrieved from UserRepository
 */
public class LoggedInUser {
    @SerializedName("id")
    private int userId;

    public int getUserId() {
        return userId;
    }

    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("email")
    private String email;

    @SerializedName("card")
    private Card card;
    @SerializedName("sex")
    private String sex;
    @SerializedName("dob")
    private String dob;
    public LoggedInUser(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public LoggedInUser(String username, String dob, String sex, boolean a) {
        this.username = username;
        this.sex = sex;
        this.dob = dob;
    }

    public LoggedInUser(String email, String password) {
        this.password = password;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setCard(Card card) {
        this.card = card;
    }
    public String getUsername() {
        return username;
    }

    public boolean hasCard() {
        return card != null;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
    @Override
    public String toString() {
        return "LoggedInUser{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public void setUsername(String username) {
        this.username = username;
    }
}