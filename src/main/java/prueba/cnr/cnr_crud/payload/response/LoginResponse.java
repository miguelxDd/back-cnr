package prueba.cnr.cnr_crud.payload.response;

import java.util.List;

public class LoginResponse {

    private String token;
    private String type = "Bearer";
    private String username;
    private List<String> roles;
    private boolean authenticated;

    public LoginResponse() {
    }

    public LoginResponse(String token, String username, List<String> roles, boolean authenticated) {
        this.token = token;
        this.username = username;
        this.roles = roles;
        this.authenticated = authenticated;
    }

    // Getters y Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
