package com.example.controller;

import com.example.service.*;
import com.example.entity.*;
import com.example.exception.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account acc) throws InvalidInputException, AlreadyExistsException {
        return new ResponseEntity<Account>(accountService.insertAccount(acc), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account acc) {
        Account loggedInAccount = accountService.getAccount(acc);
        if (loggedInAccount != null) {
            return new ResponseEntity<Account>(loggedInAccount, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<Account>(HttpStatus.UNAUTHORIZED);
        } 
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> messages(@RequestBody Message msg) {
        return ResponseEntity.status(HttpStatus.OK).body(new Message());
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody String handleAlreadyExists(AlreadyExistsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody String handleInvalidInput(InvalidInputException ex) {
        return ex.getMessage();
    }
    
}
