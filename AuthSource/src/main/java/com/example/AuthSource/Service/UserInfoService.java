package com.example.AuthSource.Service;

import com.example.AuthSource.Entity.UserInfo;
import com.example.AuthSource.Repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userInfoRepository.findByName(username);
        System.out.println("check " + userInfo);
        return userInfo.map(UserInfoDetails::new).orElseThrow(
                () -> new UsernameNotFoundException("User not found " + username)
        );
    }

    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);
        return "USER ADDED SUCCESSFULLY";
    }

    public List<UserInfo> getAllUser(){
        System.out.println("check co get ALL User khong");
        return userInfoRepository.findAll();
    }


    public UserInfo getUser(Integer id) {
        return userInfoRepository.findById(id).get();
    }




}
