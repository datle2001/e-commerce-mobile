package depauw.datle.eshop.data.model;

import com.google.gson.annotations.SerializedName;

public class Session {
    @SerializedName("user")
    private LoggedInUser user;
    @SerializedName("token")
    private String token = "";

    public Session(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return user.getUsername();
    }

    public String getEmail() {
        return user.getEmail();
    }
    public boolean hasCard() {
        return user.hasCard();
    }
    public void setUserCard(Card card) {
        user.setCard(card);
    }
    public String getSex() {return user.getSex();}

    public String getDob() {
        return user.getDob();
    }

    public int getUserId() {
        return user.getUserId();
    }

    public LoggedInUser getUser() {
        return user;
    }
}
