package com.fareye.TwitterUser;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by khushal on 10/8/16.
 */
public interface UserRepository extends JpaRepository<TwitterUser,Long> {
    TwitterUser findByName(String name);
    TwitterUser findByEmail(String email);
}
