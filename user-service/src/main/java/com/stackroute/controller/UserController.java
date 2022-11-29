package com.stackroute.controller;



import com.stackroute.dao.Role;
import com.stackroute.dao.User;
import com.stackroute.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    @PostMapping({"/registerNewUser"})
    public User registerNewUser(@RequestBody User user) {

        return userService.registerNewUser(user);
    }

    @PutMapping({"/editUserRoles/{userName}"})
    public User editUserRoles(@PathVariable(value="userName") Integer userName, @RequestBody List<Role> newRoles) {
        return userService.editUserRoles(userName, newRoles);
    }

    @GetMapping({"/getUsers"})
    public List<User> getUsers() { return userService.getUsers(); }

    @GetMapping({"/getUser"})
    public User getUserByEmail(@RequestParam String email) { return userService.getUserByEmail(email); }

    @DeleteMapping({"/deleteUser/{userName}"})
    public void deleteUser(@PathVariable(value="userName") Integer userName) { userService.deleteUser(userName); }

    // Update User Details
    @PutMapping({"/updateUser/{userName}"})
    public User updateUser(@PathVariable(value="userName") Integer userName, @RequestBody User userDetails) { return userService.updateUser(userName, userDetails); }

    @PutMapping({"/updateUserByEmail/{email}"})
    public User updateUserById(@PathVariable(value="email") String email, @RequestBody User userDetails) { return userService.updateUserByEmail(email, userDetails); }

    @GetMapping({"/getUserRole/{userName}"})
    public Set<Role> getUserRoleByUsername(@PathVariable(value="userName") Integer userName) {
        return userService.getUserRoleByUsername(userName);
    }
    @GetMapping({"/blockUser/{userName}"})
    public User blockuser(@PathVariable(value="userName") Integer userName) {
        return userService.blockUser(userName);
    }

}
