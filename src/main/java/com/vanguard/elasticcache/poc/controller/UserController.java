package com.vanguard.elasticcache.poc.controller;

import com.vanguard.elasticcache.poc.domain.User;
import com.vanguard.elasticcache.poc.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(
            value = "Get User by Id")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @ApiOperation(
            value = "Resets the cache for a key")
    @RequestMapping(value = "/reset/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> reset(@PathVariable long id) {
        userService.resetGetUserByIdResult(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
