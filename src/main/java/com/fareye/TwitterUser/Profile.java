package com.fareye.TwitterUser;

/**
 * Created by khushal on 11/8/16.
 */
public class Profile {
    private String bio;
    private int following;
    private int followers;
    private int count;


    public Profile(String bio, int following, int followers, int count) {
        this.bio = bio;
        this.following = following;
        this.followers = followers;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }
}
