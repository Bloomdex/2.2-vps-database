package org.bloomdex.datamcbaseface.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bloomdex.datamcbaseface.config.SecurityConfig;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "USERS")
@JsonIgnoreProperties(value = { "password" }, allowGetters = false, allowSetters = true)
public class User implements Serializable {
    @Id
    @Column(columnDefinition = "VARCHAR_IGNORECASE(50)")
    private String username;

    @NotNull
    @Column(columnDefinition = "VARCHAR_IGNORECASE(500)")
    private String password;

    @NotNull
    private boolean enabled;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "username")
    private Set<Authority> authorities;

    //region Constructors

    /**
     * Default constructor for User
     */
    public User(){
        username = "UNKNOWN";
        password = "UNKNOWN";
        enabled = false;
        authorities = new HashSet<>();
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
        authorities = new HashSet<>();
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
        this.password = SecurityConfig.PasswordEncoder.encode(password);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getAuthorities() {
        List<String> authority_strings = new ArrayList<>();

        for (Authority authority : this.authorities) {
            authority_strings.add(authority.getAuthority());
        }

        return authority_strings;
    }

    public void setAuthorities(Set<Authority> authorities){
        this.authorities = authorities;
    }

    //endregion
}
