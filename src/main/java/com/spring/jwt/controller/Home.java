package com.spring.jwt.controller;

import com.spring.jwt.entities.RefreshToken;
import com.spring.jwt.helper.JwtHelper;
import com.spring.jwt.models.JwtRequest;
//import com.spring.jwt.models.JwtResponse;
import com.spring.jwt.models.JwtResponse;
import com.spring.jwt.entities.User;
//import com.spring.jwt.service.UserService;
import com.spring.jwt.models.RefreshTokenRequest;
import com.spring.jwt.service.RefreshTokenService;
import com.spring.jwt.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class Home {

//    @Bean
//    private UserService userService(){
//        return new UserService();
//    };
    @Autowired
    private UserService userService;


    @GetMapping("/user")
    public List<User> welcome(){
        String text="This is private page ";
        text+="this page is not allowed to unauthenticated user";
        return this.userService.getUsers();
    }
//    @GetMapping("/current-user")
//    public  String getLogInUser(Principal principal){
//        return principal.getName();
//    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper helper;

    private Logger logger = LoggerFactory.getLogger(Home.class);


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

        this.doAuthenticate(request.getEmail(), request.getPassword());


        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);

        RefreshToken refreshToken = this.refreshTokenService.createRefreshToken(userDetails.getUsername());

        JwtResponse response = JwtResponse.builder().token(token).refreshToken(refreshToken.getRefreshToken()).username(userDetails.getUsername()).build();
                // Depricated
//                JwtResponse.builder()
//                .jwtToken(token)
//                .username(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }


    @PostMapping("/refresh")
    public JwtResponse refreshJwtToken(@RequestBody RefreshTokenRequest request){

        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(request.getRefreshToken());

        User user=refreshToken.getUser();
        String token = this.helper.generateToken(user);
        return JwtResponse.builder().refreshToken(refreshToken.getRefreshToken())
                .token(token)
                .username(user.getEmail())
                .build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }

    @PostMapping("/signup")
    public  User  createUser(@RequestBody User user){
        return this.userService.createUser(user);
    }


}
