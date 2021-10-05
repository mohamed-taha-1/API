package com.example.app5.service;

import com.example.app5.shared.UserDataTransferToObject;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService  extends UserDetailsService {
     UserDataTransferToObject   createUser(UserDataTransferToObject user);
     UserDataTransferToObject  getUser(String email);
     UserDataTransferToObject  getUserById(String id);
     UserDataTransferToObject  updateUser(String id ,UserDataTransferToObject userDto);
     void deleteUser(String userId);
     List<UserDataTransferToObject> getUsers(int page , int limit);

}
