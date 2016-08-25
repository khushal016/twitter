package com.fareye.Follow;

import com.fareye.TwitterUser.TwitterUser;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by khushal on 10/8/16.
 */
@Entity
public class Follow {
    @Id
    @GeneratedValue
    private long id;
    private String fname;
    private String userName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
