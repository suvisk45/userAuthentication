package com.example.ecommerce.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServices
{
    @Autowired
    UserRepo userRepo;
    public boolean  saveUser(UserModel userModel)
    {
        if(findByUserEmail(userModel.getEmail())!=null) // here i delegate the functionality to the other method
        {
            return false;
        }
        UserModel userModel1=userRepo.save(userModel);
        return true;
    }

    public UserModel findByUserEmail(String email)
    {
       UserModel userModel=userRepo.findByEmail(email);
        return userModel;
    }
}
