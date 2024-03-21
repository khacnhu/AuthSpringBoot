package com.example.AuthSource.Controller;


import com.example.AuthSource.Entity.AuthRequest;
import com.example.AuthSource.Entity.UserInfo;
import com.example.AuthSource.Service.JwtService;
import com.example.AuthSource.Service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/welcome")
    public String welcome () {
        return "Welcome to Spring Security";
    }

    @PostMapping("/adduser")
    public String addUser(@RequestBody UserInfo userInfo) {
        return userInfoService.addUser(userInfo);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authentication.getName());
        } else {
            throw new RuntimeException("PASSWORD OR USER NAME IS NOT VALID");
        }
    }

    @GetMapping("/getUsers")
    public List<UserInfo> getAllUsers() {
        System.out.println("check get ALl duoc khong");
        return userInfoService.getAllUser();
    }

    @GetMapping("/getUsers/{id}")
    public UserInfo getUser(@PathVariable Integer id) {
        return userInfoService.getUser(id);
    }



}
