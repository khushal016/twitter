package com.fareye.TwitterUser;

import com.fareye.ErrorMessage;
import com.fareye.Follow.FollowRepository;
import com.fareye.Tweets.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by khushal on 10/8/16.
 */
@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    FollowRepository followRepository;
    @Autowired
    TweetRepository tweetRepository;


    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    ResponseEntity<?> create(@RequestBody TwitterUser user) {
        System.out.print(user);
        if (userRepository.findByEmail(user.getEmail())!=null) {
            return new ResponseEntity<>(new ErrorMessage("Already Exist!!"), HttpStatus.BAD_REQUEST);
        }
        if (user.getBio()==null)
            user.setBio("Hi I am using Twitter");

        userRepository.save(user);
        return new ResponseEntity<>(new ErrorMessage("User Created!!"), HttpStatus.OK);
    }

    @RequestMapping(value = "/displayUsers")
    List<TwitterUser> display() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        List<TwitterUser> list = userRepository.findAll();
        list.remove(userRepository.findByEmail(user));
        return list;

    }
    @RequestMapping(value = "/bio")
    ResponseEntity<?> addBio(HttpServletRequest request) {
        String msg = request.getParameter("msg");
        System.out.print(msg);
        if (msg.equals("undefined"))
            return new ResponseEntity<>(new ErrorMessage("Blank Field!!"), HttpStatus.BAD_REQUEST);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        TwitterUser twitterUser = userRepository.findByEmail(user);
        System.out.print(twitterUser);
        twitterUser.setBio(msg);
        userRepository.save(twitterUser);
        return new ResponseEntity<>(new ErrorMessage("Bio updated!!"), HttpStatus.OK);
    }
    @RequestMapping(value = "/displayProfile")
    Profile displayP() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        int following = followRepository.findByUserName(user).size();
        int followers = followRepository.findByFname(userRepository.findByEmail(user).getName()).size();
        String bio = userRepository.findByEmail(user).getBio();
        int count = tweetRepository.findByEmail(user).size();
        return new Profile(bio,following,followers,count);


    }
}
