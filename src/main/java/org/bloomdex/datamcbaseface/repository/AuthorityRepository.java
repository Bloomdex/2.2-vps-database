package org.bloomdex.datamcbaseface.repository;

import org.bloomdex.datamcbaseface.model.Authority;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {
    @Transactional
    void deleteAuthoritiesByUsername_UsernameAndAuthority(String username_username, String authority);
}
