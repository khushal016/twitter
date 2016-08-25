package com.fareye.Tweets;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by khushal on 10/8/16.
 */
public interface TweetRepository extends JpaRepository<Tweet,Long> {
    List<Tweet> findByEmail(String email);
    @Query(value = "select * from tweet where email=?1 AND email in (select email from twitter_user where twitter_user.name in (SELECT fname FROM twitter_user INNER JOIN follow ON (follow.user_name = twitter_user.email) where twitter_user.email=?1)) ",nativeQuery = true)
    List<Tweet> findFollowingTweets(String email);
}
