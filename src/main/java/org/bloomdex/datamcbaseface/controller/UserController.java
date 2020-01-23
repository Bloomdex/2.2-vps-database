package org.bloomdex.datamcbaseface.controller;

import org.bloomdex.datamcbaseface.model.User;
import org.bloomdex.datamcbaseface.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import java.net.URI;

@RolesAllowed("ADMIN")
@RestController
public class UserController extends AbstractController {

    @Autowired
    UserRepository repo;

    /**
     * Request user-information by username
     * @param username the username of the user that should be deleted.
     * @return user-information about the user requested.
     */
    @RequestMapping(api_prefix + "users/{username}/")
    public User getUserById(
            @PathVariable String username)
            throws InvalidRequestException, NoEntriesFoundException
    {
        if(username.equals(""))
            throw new InvalidRequestException();

        var user = repo.findById(username);

        if(user.isEmpty())
            throw new NoEntriesFoundException();

        return user.get();
    }

    /**
     * Retrieves all users that exist.
     * @return All users that exist.
     */
    @GetMapping(api_prefix + "users")
    public Iterable<User> retrieveAllUsers() {
        return repo.findAll();
    }

    /**
     * Retrieves all users in a paged format.
     * @return Returns all users in a paged format
     */
    @GetMapping(api_prefix + "paged/users")
    public Page<User> retrieveAllUsers(Pageable pageable) {
        return repo.findAll(pageable);
    }

    /**
     * Delete a user using it's username
     * @param username the username of the user that should be deleted.
     */
    @DeleteMapping(api_prefix + "users/{username}/")
    public void deleteUser(@PathVariable String username) {
        repo.deleteById(username);
    }

    /**
     * Create a user using a POST with the following .json as a body:
     *
     * {
     *     "username": "{username}",
     *     "password": "{password}",
     *     "enabled": {enabled}
     * }
     *
     * @param user the user that should be added to the database.
     * @return returns the created user as a json
     */
    @PostMapping(path = api_prefix + "users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        User savedUser = repo.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}/")
                .buildAndExpand(savedUser.getUsername()).toUri();

        return ResponseEntity.created(location).build();
    }

}
