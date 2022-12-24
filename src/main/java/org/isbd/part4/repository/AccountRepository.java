package org.isbd.part4.repository;

import org.isbd.part4.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByEmail(String email);
    Account findAccountById(int id);
    boolean existsByEmail(String email);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value ="DELETE FROM account where id=:accountId",
            nativeQuery = true)
    void banAccount(@Param("accountId") int accountId);
}
