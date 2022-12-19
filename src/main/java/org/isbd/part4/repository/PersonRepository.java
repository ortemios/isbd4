package org.isbd.part4.repository;

import org.isbd.part4.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(
            value ="call create_person(:accountId, :name, :raceId, :personClassId)",
            nativeQuery = true
    )
    void createPerson(
            @Param("accountId") int accountId,
            @Param("name") String name,
            @Param("raceId") int raceId,
            @Param("personClassId") int personClassId
    );

    List<Person> findAllByAccountId(Integer accountId);
}
