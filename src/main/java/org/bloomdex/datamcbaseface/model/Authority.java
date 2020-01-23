package org.bloomdex.datamcbaseface.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "AUTHORITIES")
public class Authority implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name="username", nullable=false)
    private User username;

    @Id
    @Column(columnDefinition = "VARCHAR_IGNORECASE(50)")
    private String authority;

    //region Constructors

    /**
     * Default empty constructor for Authority
     */
    public Authority() {
        username = null;
        authority = "UNKNOWN";
    }

    /**
     * Create an Authority for a user
     * @param user The user the authority should be given to
     * @param authority The authority the user should be given, A user can only have one of the same type authority
     */
    public Authority(User user, String authority) {
        this.username = user;
        this.authority = authority;
    }

    //endregion

    //region Getters and Setters

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    //endregion
}
