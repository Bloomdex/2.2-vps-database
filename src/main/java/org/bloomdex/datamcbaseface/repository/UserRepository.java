package org.bloomdex.datamcbaseface.repository;

import org.bloomdex.datamcbaseface.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> { }
