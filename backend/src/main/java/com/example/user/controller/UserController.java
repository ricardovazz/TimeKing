package com.example.user.controller;

import com.example.task.dto.TaskDto;
import com.example.user.dto.UserDto;
import com.example.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {


    /*private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }*/
    @Autowired
    private UserService userService;

    @GetMapping
    public String hello(){
        return "Hello World";
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Integer id){
        try {
            return new ResponseEntity<UserDto>(userService.getUser(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<UserDto>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/all")
    public List<UserDto> getUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable Integer id){
        try {
            userService.updateUser(id, userDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<UserDto>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping(path = "/getusers")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/add")
    public String add(@RequestBody UserDto userDto){
        userService.saveUser(userDto);
        return "New user is added";
    }
}

