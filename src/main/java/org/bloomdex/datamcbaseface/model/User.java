package org.bloomdex.datamcbaseface.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User implements Serializable {
    @Id
    @Column(columnDefinition = "VARCHAR_IGNORECASE(50)")
    private String username;

    @NotNull
    @Column(columnDefinition = "VARCHAR_IGNORECASE(500)")
    private String password;

    @NotNull
    boolean enabled;

    @OneToMany
    @JoinColumn(name = "username")
    private Set<Authority> authorities;

    //region Constructors

    /**
     * Default constructor for User
     */
    public User(){
        username = "UNKNOWN";
        password = "UNKNOWN";
        enabled = false;
    }

    /**
     * Constructor for creating a User
     * @param username The unique username idenitifier (max characters is 50)
     * @param password The password of this user (max characters is 500)
     * @param enabled
     */
    public User(String username, String password, boolean enabled){
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    //endregion

    //region Getters and Setter

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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    //endregion
}
