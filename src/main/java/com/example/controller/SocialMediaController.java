package com.example.controller;

import com.example.service.*;
import com.example.entity.*;
import com.example.exception.*;
import java.util.List;

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
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(AccountService accService, MessageService msgService) {
        accountService = accService;
        messageService = msgService;
    }    

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account acc) throws InvalidInputException, AlreadyExistsException {
        return new ResponseEntity<>(accountService.insertAccount(acc), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account acc) {
        Account loggedInAccount = accountService.getAccount(acc);
        if (loggedInAccount != null) {
            return new ResponseEntity<>(loggedInAccount, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> messagePost(@RequestBody Message msg) throws InvalidInputException {
        return new ResponseEntity<>(messageService.insertMessage(msg), HttpStatus.OK);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> messageGet() {
        return new ResponseEntity<>(messageService.getAllMessages(), HttpStatus.OK);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> messageGetById(@PathVariable int messageId) {
        return new ResponseEntity<>(messageService.getMessageById(messageId), HttpStatus.OK);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> messageDeleteById(@PathVariable int messageId) {
        if (messageService.deleteMessageById(messageId)) {
            return new ResponseEntity<>(1, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> messageUpdateById(@PathVariable int messageId, @RequestBody Message msg) throws InvalidInputException {
        messageService.updateMessageById(msg.getMessageText(), messageId);
        return new ResponseEntity<>(1, HttpStatus.OK);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> accountGetAllMessages(@PathVariable int accountId) {
        return new ResponseEntity<>(messageService.getAllMessagesByAccountId(accountId), HttpStatus.OK);
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
