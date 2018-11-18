package com.talisman.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "UserAccounts")
public class UserAccount implements Serializable {

    private String userName;
    private String password;
    private String userRole;
    private boolean active;

    @Id
    @Column(name = "User_Name", length = 20, nullable = false)
    public String getUserName() {
        return this.userName;
    }

    @Column(name = "Password", length = 20, nullable = false)
    public String getPassword() {
        return this.password;
    }

    @Column(name = "User_Role", length = 20, nullable = false)
    public String getUserRole() {
        return this.userRole;
    }

    @Column(name = "Active", length = 1, nullable = false)
    public Boolean getActive() {
        return this.active;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public void setUserRole(final String userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return this.userName + " " + this.password + " " + this.userRole;
    }
}
