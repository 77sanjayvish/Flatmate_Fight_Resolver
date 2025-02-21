package com.flat.controller;

import com.flat.entity.User;
import com.flat.enums.Role;
import com.flat.Service.JwtService;
import com.flat.Service.UserService;
import com.flat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;

    @PutMapping("/update")
    public User updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id)
    {
        userService.deleteUser(id);
        return "User Deleted SuccessFully";
    }
    //    get All user
    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAll(@RequestHeader("Authorization") String token) throws Exception {

        String userName = jwtService.extractUserName(token.substring(7));
        User user =(User)  userRepository.findByEmail(userName);
        if(user != null && user.getRole().equals(Role.ADMIN)|| user.getRole().equals(Role.USER)){

            return ResponseEntity.ok(userService.getAllUsers());
        }
        else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    @GetMapping("/getById/{id}")
    public ResponseEntity<User> getAll( @PathVariable int id,@RequestHeader("Authorization") String token) throws Exception {

        String userName = jwtService.extractUserName(token.substring(7));
        User user =(User)  userRepository.findByEmail(userName);
        if(user != null && (user.getRole().equals(Role.USER)|| user.getRole().equals(Role.ADMIN))){

            return ResponseEntity.ok(userService.getUserByID(id).get());
        }
        else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    @GetMapping("/{id}")
    public String getSingleUser(@PathVariable int id){
        return userService.getSingleUser(id);
    }

}