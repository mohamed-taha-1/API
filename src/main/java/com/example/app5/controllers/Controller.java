package com.example.app5.controllers;


import com.example.app5.request.response.ResponsePayload;

import com.example.app5.request.response.UserDeatialRequest;

import com.example.app5.service.UserService;
import com.example.app5.shared.UserDataTransferToObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    UserService service;


    @RequestMapping("/hello")
    public String hello(){
        return "hi";
    }


    @PostMapping(value = "/signUp/save",
            consumes = {  MediaType.APPLICATION_JSON_VALUE  ,MediaType.APPLICATION_XML_VALUE } ,
            produces = {  MediaType.APPLICATION_JSON_VALUE  ,MediaType.APPLICATION_XML_VALUE } ) // TO RETURN VALUES IN XML)

    public ResponsePayload saveData(@RequestBody  UserDeatialRequest  userDetails)  throws Exception{


      ResponsePayload returnValues=new ResponsePayload();   // get copy of  specific data


        UserDataTransferToObject  userDto= new UserDataTransferToObject(); // get copy of all data

        BeanUtils.copyProperties(userDetails, userDto);

        UserDataTransferToObject  createdUser=service.createUser(userDto);
        BeanUtils.copyProperties(createdUser, returnValues);


        return returnValues;
    }


    /*
    * --- Get users based on  user ID
    * */

    @GetMapping(value = "/users/{id}",
    produces = {  MediaType.APPLICATION_JSON_VALUE  ,MediaType.APPLICATION_XML_VALUE }  ) // TO RETURN VALUES IN XML

    public ResponsePayload getUsers(@PathVariable String id){

        //  CREATE A COPY OF users data
        ResponsePayload user=new ResponsePayload();

        // find users based on ID
        UserDataTransferToObject userDto=service.getUserById(id);
        BeanUtils.copyProperties(userDto,user);

        return user;

    }
    /*
    *  Update users based on their ID
    *  it behaves like create and get in some methods
    * */

    @PutMapping(value = "/users/update/{id}",
            consumes = {  MediaType.APPLICATION_JSON_VALUE  ,MediaType.APPLICATION_XML_VALUE } ,
            produces = {  MediaType.APPLICATION_JSON_VALUE  ,MediaType.APPLICATION_XML_VALUE } ) // TO RETURN VALUES IN XML)

    public ResponsePayload updateUsers(@RequestBody  UserDeatialRequest  userDetails , @PathVariable String id){

       ResponsePayload returnValues=new ResponsePayload();   // get copy of  specific data

        UserDataTransferToObject  userDto= new UserDataTransferToObject(); // get copy of all data

        BeanUtils.copyProperties(userDetails, userDto);

        UserDataTransferToObject updatedUser =service.updateUser(id,userDto);
        BeanUtils.copyProperties(updatedUser, returnValues);


        return returnValues;

    }

    /*
    * DELETE USER BASED ON their ID
    * */

    @DeleteMapping("/users/delete/{id}")
    public String deleteUsers(@PathVariable String id){
        service.deleteUser(id);
        return "success deletion";

    }


    /*
    * Return list of users
    * */

    @GetMapping(value = "/users",
            produces = {  MediaType.APPLICATION_JSON_VALUE  ,MediaType.APPLICATION_XML_VALUE }  )

    public List<ResponsePayload> getListOfusers(@RequestParam(value = "page" , defaultValue="0") int page,
                                                @RequestParam(value = "limit" , defaultValue="25") int limit){

        List<ResponsePayload> returnValues=new ArrayList<>();
        List<UserDataTransferToObject> users=service.getUsers(page,limit);

        // - loop  to all users : for each user object in users , and
        // - we create model response , then we copy property from source object to user model

        for(UserDataTransferToObject userDto :users){

            ResponsePayload userModel= new ResponsePayload();
            BeanUtils.copyProperties(userDto,userModel);

            returnValues.add(userModel);
        }
        return  returnValues;
    }





}
