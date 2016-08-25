package com.fareye.Tweets;

import com.fareye.ErrorMessage;
import com.fareye.Follow.Follow;
import com.fareye.Follow.FollowRepository;
import com.fareye.TwitterUser.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by khushal on 10/8/16.
 */
@RestController
public class TweetController {
    @Autowired
    TweetRepository tweetRepository;
    @Autowired
    FollowRepository followRepository;
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/createTweet", method = RequestMethod.POST)
    ResponseEntity<?> create(@RequestBody Tweet tweet) {
        if (tweet.getMsg() == null)
            return new ResponseEntity<>(new ErrorMessage("Blank Field!!"), HttpStatus.BAD_REQUEST);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        tweet.setEmail(user);
        tweetRepository.save(tweet);
        return new ResponseEntity<>(new ErrorMessage("Tweeted!"), HttpStatus.OK);
    }

    @RequestMapping(value = "/displayTweet")
    ResponseEntity<?> display() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        List<Tweet> list = tweetRepository.findByEmail(user);
        List<Follow> list2 = followRepository.findByUserName(user);
        for (int i = 0; i < list2.size(); i++) {
            list.addAll(tweetRepository.findByEmail(userRepository.findByName(list2.get(i).getFname()).getEmail()));
        }
        System.out.print(list);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/count")
    ResponseEntity<?> count() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        if (tweetRepository.findByEmail(user).size() == 0)
            return new ResponseEntity<>(new ErrorMessage("No tweets!!"), HttpStatus.BAD_REQUEST);
        List<Tweet> list = tweetRepository.findByEmail(user);
        return new ResponseEntity<>(list, HttpStatus.OK);

    }
}
