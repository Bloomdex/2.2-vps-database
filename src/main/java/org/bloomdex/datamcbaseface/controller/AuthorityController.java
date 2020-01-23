package org.bloomdex.datamcbaseface.controller;

import org.bloomdex.datamcbaseface.model.Authority;
import org.bloomdex.datamcbaseface.repository.AuthorityRepository;
import org.bloomdex.datamcbaseface.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import java.net.URI;

@RolesAllowed("ADMIN")
@RestController
public class AuthorityController extends AbstractController {

    @Autowired
    AuthorityRepository repo;

    @Autowired
    UserRepository userRepository;

    /**
     * Delete a user's authority
     * @param username the username of the user that should be deleted.
     * @param role the role that should be removed from this user.
     */
    @DeleteMapping(api_prefix + "users/authority/{username}")
    public void deleteAuthority(
            @PathVariable String username,
            @RequestParam(value = "role", defaultValue = "") String role)
            throws InvalidRequestException
    {
        if(username.equals("") || role.equals(""))
            throw new InvalidRequestException();

        repo.deleteAuthoritiesByUsername_UsernameAndAuthority(username, role);
    }

    /**
     * Add an authority to a user
     * @param username the username of the user the authority should be added to.
     * @param role the role that should be given to the user.
     * @return Location of retrieving information about this user.
     */
    @PostMapping(path = api_prefix + "users/authority/{username}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createAuthority(
            @PathVariable String username,
            @RequestParam(value = "role", defaultValue = "") String role) {

        var user = userRepository.getOne(username);
        repo.save(new Authority(user, role));

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path(api_prefix + "/users/{username}/")
                .buildAndExpand(username).toUri();

        return ResponseEntity.created(location).build();
    }
}
