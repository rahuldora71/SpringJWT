package com.spring.jwt.service;

import com.spring.jwt.entities.User;
import com.spring.jwt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Autowired
    private PasswordEncoder passwordEncoder;


//    private List<User> store=new ArrayList<>();
//    public UserService() {
//        store.add(new User(UUID.randomUUID().toString(),"Rahul Dora","test@gmail.com"));
//        store.add(new User(UUID.randomUUID().toString(),"Vansh Kumar","vansh@gmail.com"));
//        store.add(new User(UUID.randomUUID().toString(),"Manoj Malik","manoj@gmail.com"));
//        store.add(new User(UUID.randomUUID().toString(),"Vikrant Verma","vikrant@gmail.com"));
//    }
    public List<User> getUsers(){
        List<User> userList = userRepository.findAll();
        System.out.println(userList);
        return userList;
    }

    public User createUser(User user) {
        user.setUserId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

}
