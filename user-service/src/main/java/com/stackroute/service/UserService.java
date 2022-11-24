package com.stackroute.service;


import com.stackroute.dao.Role;
import com.stackroute.dao.User;
import com.stackroute.repository.RoleDao;
import com.stackroute.repository.UserDao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getUsers() {
        return (List<User>) userDao.findAll();
    }

    public void deleteUser(Integer id) {
        userDao.deleteUsersFromUserRole(id);
        User user = userDao.findById(id).get();
        userDao.delete(user);
    }

    public User updateUser(Integer id, User userDetails) {
        User user = userDao.findById(id).get();
        user.setUsername(userDetails.getUsername());
        user.setMail(userDetails.getMail());
        user.setAddress(userDetails.getAddress());
        user.setPassword(getEncodedPassword(userDetails.getPassword()));
        user.setLatestUpdate(String.valueOf(new Date()));
        return userDao.save(user);
    }

    public User registerNewUser(User user) {

        User myuser = userDao.findByMail(user.getMail());
        if(myuser!=null){
            throw new RuntimeException("User exist already");
        }

        ModelMapper modelMapper = new ModelMapper();
        User userCreated = modelMapper.map(user, User.class);
        userCreated.setPassword(passwordEncoder.encode(user.getPassword()));
        userCreated.setAccountNonLocked(true);
        userCreated.setRegistrationTime(String.valueOf(new Date()));
        userCreated.setActive(true);
        List<String> rolesget=user.getRolesName();
        Set<Role> roles=new HashSet<>();
        rolesget.stream().forEach(s -> {
            Role role=roleDao.findById(s).get();
            roles.add(role);
        });
        userCreated.setRole(roles);
        User userRegistred=userDao.save(userCreated);
        // check the type of the user
        return userRegistred;
    }

    public User editUserRoles(Integer id, List<Role> newRoles){
        User user = userDao.findById(id).get();
        Set<Role> userNewRoles = new HashSet<>();
        for (Role newRole : newRoles){
            String newRoleName = newRole.getRoleName();
            Role role = roleDao.findById(newRoleName).get();
            userNewRoles.add(role);
        }
        user.setRole(userNewRoles);
        user.setLatestUpdate(String.valueOf(new Date()));
        return userDao.save(user);
    }

    public Set<Role> getUserRoleByUsername(Integer id) {
        User user = userDao.findById(id).get();
        return user.getRole();
    }
    public User blockUser(Integer id) {
        User user = userDao.findById(id).get();
        user.setActive(!user.isActive());
        userDao.save(user);
        return user;
    }


    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleName("ROLE_ADMIN");
        adminRole.setRoleDescription("Admin role");

        Role userReadRole = new Role();
        userReadRole.setRoleName("ROLE_UserRead");
        userReadRole.setRoleDescription("Read Role for User");
        roleDao.save(userReadRole);

        Role userEditRole = new Role();
        userEditRole.setRoleName("ROLE_UserEdit");
        userEditRole.setRoleDescription("Edit Role for User");
        roleDao.save(userEditRole);

        Role userDeleteRole = new Role();
        userDeleteRole.setRoleName("ROLE_UserDelete");
        userDeleteRole.setRoleDescription("Delete Role for User");
        roleDao.save(userDeleteRole);

        User user=userDao.findByMail("test@gmail.com");
        if(user==null){
            User adminUser = new User();
            adminUser.setUsername("admin123");
            adminUser.setPassword(getEncodedPassword("admin@pass"));
            adminUser.setMail("test@gmail.com");
            adminUser.setName("admin");
            adminUser.setAddress("india");
            adminUser.setAccountNonLocked(true);
            adminUser.setActive(true);
            //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //String date = dateFormat.format(new Date());
            adminUser.setRegistrationTime(String.valueOf(new Date()));
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);
            adminUser.setRole(adminRoles);
            userDao.save(adminUser);

        }


    }



    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
