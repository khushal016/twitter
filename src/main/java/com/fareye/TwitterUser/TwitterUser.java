package com.fareye.TwitterUser;

import com.fareye.Follow.Follow;
import com.fareye.Tweets.Tweet;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by khushal on 10/8/16.
 */
@Entity
public class TwitterUser {
    @Id
    private String email;
    private String name;
    private String password;
    private String bio;



    public TwitterUser() {
    }

    public TwitterUser(String email, String name, String password, String bio) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.bio = bio;

    }

//    public void setTweetSet(Set<Tweet> tweetSet) {
//        this.tweetSet = tweetSet;
//    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "TwitterUser{" +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
}
