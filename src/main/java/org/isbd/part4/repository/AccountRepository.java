package org.isbd.part4.repository;

import org.isbd.part4.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByEmail(String email);

    Account getByEmail(String email);

    boolean existsByEmail(String email);
}
