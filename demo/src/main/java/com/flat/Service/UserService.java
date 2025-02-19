package com.flat.Service;

import com.flat.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDetailsService userDetailsService();
    //    Add User
    User createUser(User user);
    //    update User
    User updateUser(User user);
    //    delete User
    void deleteUser(long id);
    //    get One User
    String getSingleUser(long id);
    ////    getAll Users
    List<User> getAllUsers();


    Optional<User> getUserByID(long id);


}
