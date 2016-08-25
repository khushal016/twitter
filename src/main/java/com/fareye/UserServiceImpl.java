package com.fareye;

import com.fareye.TwitterUser.TwitterUser;
import com.fareye.TwitterUser.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Created by khushal on 1/8/16.
 */
@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional

    /*method to check the role of a user*/
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException, DataAccessException {
        // Declare a null Spring User


        // Search database for a user that matches the specified username
        // You can provide a custom DAO to access your persistence layer
        // Or use JDBC to access your database
        // DbUser is our custom domain user. This is not the same as Spring's User
        TwitterUser twitterUser = userRepository.findByEmail(email);

        if (twitterUser == null) {
            throw new UsernameNotFoundException("HiMVC Security:: Error in retrieving user(username=" + email + ")");
        }
        System.out.print(twitterUser.getName());
        System.out.print(twitterUser.getPassword());

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                twitterUser.getEmail(),
                twitterUser.getPassword(),//here you can put a clear text password
                true,
                true,
                true,
                true,


                loadUserAuthorities("none")

        );

        return userDetails;
    }

    /*method to get role*/
    public Collection<? extends GrantedAuthority> loadUserAuthorities(String username) {
        List<SimpleGrantedAuthority> auths = new java.util.ArrayList<SimpleGrantedAuthority>();
        auths.add(new SimpleGrantedAuthority(username));
        return auths;
    }
}


