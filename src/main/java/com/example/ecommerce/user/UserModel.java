package com.example.ecommerce.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table
@Entity
public class UserModel
{
 @Id
 private String email;
 private String password;
 private String name;
}
