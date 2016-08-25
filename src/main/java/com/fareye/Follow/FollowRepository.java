package com.fareye.Follow;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by khushal on 10/8/16.
 */
public interface FollowRepository extends JpaRepository<Follow,Long> {
    List<Follow> findByFname(String fname);
    List<Follow> findByUserName(String userName);
    Follow findByFnameAndUserName(String fname,String userName);


}
