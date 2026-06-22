package com.example.ecommerce.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServices implements UserDetailsService
{
    @Autowired
    UserRepo userRepo;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    public boolean  saveUser(UserModel userModel)
    {
        if(findByUserEmail(userModel.getEmail())!=null) // here i delegate the functionality to the other method
        {
            return false;
        }
        userModel.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));
        UserModel userModel1=userRepo.save(userModel);
        return true;
    }

    public UserModel findByUserEmail(String email)
    {
       UserModel userModel=userRepo.findByEmail(email);
        return userModel;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
    {
        UserModel userModel=userRepo.findByEmail(email);
        return new User(userModel.getEmail(), userModel.getPassword(), Collections.emptyList());
    }
}
