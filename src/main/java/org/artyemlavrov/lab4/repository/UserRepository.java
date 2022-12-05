package org.artyemlavrov.lab4.repository;

import org.artyemlavrov.lab4.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Account, Long> {

    Account findByUsername(String username);

    Account getByUsername(String username);

    boolean existsByUsername(String username);
}
