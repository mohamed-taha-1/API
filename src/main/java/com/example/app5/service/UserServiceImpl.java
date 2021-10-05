package com.example.app5.service;

import com.example.app5.models.SignUPEntitiy;
import com.example.app5.repositories.SignUpRepo;
import com.example.app5.shared.UserDataTransferToObject;
import com.example.app5.shared.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements  UserService{

    @Autowired
    SignUpRepo  repo;


    @Autowired
    Utils utils;


    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


   /*  ---   Implement User sign up  and create user
   *
   */

    @Override
    public UserDataTransferToObject createUser(UserDataTransferToObject user) {


        // check if there are duplicates in sign up data

        if(repo.findAllByEmail(user.getEmail())!=null){
            throw new RuntimeException("Record already exists");
        }

        // else continue store data


        SignUPEntitiy data=new SignUPEntitiy();

        BeanUtils.copyProperties(user, data);

        data.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        String publishUserId=utils.generateUserId(30);

        data.setUserId(publishUserId);

        SignUPEntitiy storedDetails=repo.save(data);

        UserDataTransferToObject returnValues= new UserDataTransferToObject();

        BeanUtils.copyProperties(storedDetails, returnValues);


        return returnValues;
    }



  // ----->  for login
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

     SignUPEntitiy   user =   repo.findAllByEmail(email);
     if(user == null){

         throw new UsernameNotFoundException(email);

     }

     return  new User(user.getEmail(),user.getEncryptedPassword(), new ArrayList<>());

    }



    // ---->  To get user data like userID and other data
    @Override
    public UserDataTransferToObject getUser(String email) {

        SignUPEntitiy   user =   repo.findAllByEmail(email); // find and get
        if(user == null){
            throw new UsernameNotFoundException(email);

        }

        UserDataTransferToObject userDto=new UserDataTransferToObject(); // all details

        BeanUtils.copyProperties(user, userDto);  // get all details of that user

        return userDto;

    }


    //  get user by Id
    @Override
    public UserDataTransferToObject getUserById(String id) {

        UserDataTransferToObject userDto = new UserDataTransferToObject();
        // get user id  from database
       SignUPEntitiy   userEntity =repo.findAllByUserId(id);

       if(userEntity==null) throw  new UsernameNotFoundException(id);

       BeanUtils.copyProperties(userEntity, userDto);

        return userDto;

    }


    // to update users


    @Override
    public UserDataTransferToObject updateUser(String id , UserDataTransferToObject userDto) {

        UserDataTransferToObject returnValues =new UserDataTransferToObject();
        SignUPEntitiy userEntity= repo.findAllByUserId(id);

        if(userEntity == null ) throw new UsernameNotFoundException(id);

        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());

        SignUPEntitiy updatedEntity=repo.save(userEntity);

        BeanUtils.copyProperties(updatedEntity,returnValues);

        return returnValues;

    }


    /*
    * Delete user
    * */

    @Override
    public void deleteUser(String userId) {

        SignUPEntitiy userEnitiy=repo.findAllByUserId(userId);
        if(userEnitiy == null) throw new UsernameNotFoundException(userId);

        repo.delete(userEnitiy);
    }

    /*
    * return a List of user
    * */

    @Override
    public List<UserDataTransferToObject> getUsers(int page, int limit) {

        List<UserDataTransferToObject> returnValues= new ArrayList<>();

        if(page>0) page = page-1;

        Pageable pageableRequest = PageRequest.of(page, limit);

       Page<SignUPEntitiy> usersPage =repo.findAll(pageableRequest);

       List<SignUPEntitiy> users=usersPage.getContent();

        for (SignUPEntitiy userEntity : users) {

            UserDataTransferToObject userDto = new UserDataTransferToObject();
            BeanUtils.copyProperties(userEntity, userDto);

            returnValues.add(userDto);

        }

       return  returnValues;

    }
}
