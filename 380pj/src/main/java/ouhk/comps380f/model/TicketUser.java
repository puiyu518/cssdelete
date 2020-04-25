package ouhk.comps380f.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class TicketUser implements Serializable {

    @Id
    private String username;
    private String password;
    private boolean disabled;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> roles = new ArrayList<>();

    public TicketUser() {
    }

    public TicketUser(String username, String password, String[] roles) {
        this.username = username;
        this.password = "{noop}" + password;
        for (String role : roles) {
            this.roles.add(new UserRole(this, role));
        }
    }

    public TicketUser(String username, String password, String role) {
        this.username = username;
        this.password = "{noop}" + password;
        this.roles.add(new UserRole(this, role));

    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public boolean hasRole(String hasRole) {

        for (UserRole role : roles) {
            if (role.getRole().equals(hasRole)) {
                return true;
            }
        }
        return false;
    }

    public void updateRoles(String[] roles) {
        this.roles = new ArrayList<>();
        for (String role : roles) {
            this.roles.add(new UserRole(this, role));
        }

    }

    public String getNoHashPassword() {
        String pw = password.replace("{noop}", "");
        return pw;
    }

}
