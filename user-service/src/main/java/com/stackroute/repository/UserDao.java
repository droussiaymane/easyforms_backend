package com.stackroute.repository;

import com.stackroute.dao.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserDao extends CrudRepository<User, Integer> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_role WHERE user_id=?1", nativeQuery = true)
    void deleteUsersFromUserRole(Integer id);

     User findByMail(String email);

}
