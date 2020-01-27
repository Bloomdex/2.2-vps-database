package org.bloomdex.datamcbaseface.controller;

import org.bloomdex.datamcbaseface.model.User;
import org.bloomdex.datamcbaseface.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import java.net.URI;
import java.util.Optional;

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

        Optional<User> user = repo.findById(username);

        if(!user.isPresent())
            throw new NoEntriesFoundException();

        return user.get();
    }

    /**
     * @return Information about the current user.
     * EXAMPLE:
     * {
     *     "username": "USERNAME",
     *     "enabled": true,
     *     "authorities": [
     *         "ROLE_USER"
     *     ]
     * }
     *
     * @throws NoEntriesFoundException If the current user somehow does not exist.
     */
    @RequestMapping(api_prefix + "me")
    @RolesAllowed({"USER", "ADMIN"})
    public User getCurrentUser() throws NoEntriesFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = repo.findById(authentication.getName());

        if(!user.isPresent())
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

    /**
     * Modify a user using a PUT with the following .json as a body:
     *
     * {
     *     "password": "{password}",
     *     "enabled": {enabled}
     * }
     *
     * If you want to modify this users authorities take a look at AuthorityController instead.
     *
     * @param user The new information that should be added to this user.
     * @param username the username of the user the data should be modified from.
     * @return Query page for user information.
     */
    @PutMapping(api_prefix + "users/{username}/")
    public ResponseEntity<Object> updateStudent(@RequestBody User user, @PathVariable String username)
            throws InvalidRequestException
    {
        Optional<User> userOptional = repo.findById(username);

        //Check if the PUT contains a username; Because it should not.
        if(!user.getUsername().equals("UNKNOWN"))
            throw new InvalidRequestException();

        if (!userOptional.isPresent())
            return ResponseEntity.notFound().build();

        user.setUsername(username);

        repo.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(user.getUsername()).toUri();

        return ResponseEntity.created(location).build();
    }
}
