package com.fareye.Follow;

import com.fareye.ErrorMessage;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by khushal on 10/8/16.
 */
@RestController
public class FollowController {
    @Autowired
    FollowRepository followRepository;
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    ResponseEntity<?> insert(@RequestBody Follow follow) {
        if (follow.getFname() == null)
            return new ResponseEntity<>(new ErrorMessage("Blank Field!!"), HttpStatus.BAD_REQUEST);
        if (userRepository.findByName(follow.getFname())==null)
            return new ResponseEntity<>(new ErrorMessage("User not present!!"), HttpStatus.BAD_REQUEST);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        follow.setUserName(user);
        if (followRepository.findByUserName(user).size() > 0) {
            for (int i=0;i<followRepository.findByUserName(user).size();i++)
            if (followRepository.findByUserName(user).get(i).getFname().equals(follow.getFname()))
                return new ResponseEntity<>(new ErrorMessage("Already Followed!!"), HttpStatus.BAD_REQUEST);
        }
        followRepository.save(follow);
        return new ResponseEntity<>(new ErrorMessage(follow.getFname()+ " Followed!!"), HttpStatus.OK);

    }

    @RequestMapping(value = "/displayFollowing")
    ResponseEntity<?> displayf() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        if (followRepository.findByUserName(user).size()==0)
            return new ResponseEntity<>(new ErrorMessage("Not Following Anyone!!"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(followRepository.findByUserName(user), HttpStatus.OK);

    }

    @RequestMapping(value = "/displayFollowers")
    ResponseEntity<?> display() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        if(followRepository.findByFname(userRepository.findByEmail(user).getName()).size()==0)
            return new ResponseEntity<>(new ErrorMessage("No Followers!!"), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(followRepository.findByFname(userRepository.findByEmail(user).getName()), HttpStatus.OK);

    }

    @RequestMapping(value = "/unfollow")
    ResponseEntity<?> delete(HttpServletRequest request) {
        String fname = request.getParameter("name");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        Follow follow = followRepository.findByFnameAndUserName(fname,user);
        followRepository.delete(follow);
        return new ResponseEntity<>(new ErrorMessage(fname + " unfollowed!!"), HttpStatus.OK);

    }
}
