package depauw.datle.eshop.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    private String email;
    private String token;

    public LoggedInUserView(String username, String token, String email) {
        this.displayName = username;
        this.token = token;
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public String getToken() {
        return token;
    }

    public String getDisplayName() {
        return displayName;
    }
}