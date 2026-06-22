package com.example.ecommerce.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController()
@RequestMapping("/api/user")
public class UserController
{
    @Autowired
    UserServices userServices;

    @Autowired
    private AuthenticationManager authenticationManager;
    @GetMapping("/health") // springboot rest module
    public ResponseEntity<String> health()
    {
        return new ResponseEntity<>("Hi da na health ah dhan irukan mpla",HttpStatus.OK);
    }

    @PostMapping("/login") // This is the authentication services should be treated by the spring security
    public ResponseEntity<String> loginUser(@RequestBody UserModel userModel)
    {
        Authentication authentication=new UsernamePasswordAuthenticationToken(userModel.getEmail(),userModel.getPassword(), Collections.emptyList());
        authenticationManager.authenticate(authentication);
        if(authentication.isAuthenticated())
        {
            return new ResponseEntity<>("The user is sucessfully logined",HttpStatus.OK);
        }
        return new ResponseEntity<>("The email or password is wrong",HttpStatus.NOT_FOUND);
    }
    @PostMapping("/register")
    public ResponseEntity<String> RegisterUser(@RequestBody UserModel userModel)
    {
        if(userServices.saveUser(userModel))
        {
            return new ResponseEntity<>("The user registered sucessfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("The user already exsists",HttpStatus.CONFLICT);
    }
    @GetMapping("/profile/{email}")
    public ResponseEntity<UserModel> getProfile(@PathVariable String email)
    {
        UserModel userModel=userServices.findByUserEmail(email);
        if(userModel!=null)
        {
            return new ResponseEntity<>(userModel,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
