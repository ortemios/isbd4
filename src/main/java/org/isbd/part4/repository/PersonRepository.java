package org.isbd.part4.repository;

import org.isbd.part4.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value ="call move_person(:personId, :locationId)",
            nativeQuery = true)
    void movePerson(@Param("personId") int personId, @Param("locationId") int locationId);

    Person findPersonById(int id);
    List<Person> findAllByAccountId(Integer accountId);
    @Query(value ="SELECT * FROM person",
            nativeQuery = true)
    List<Person> getAllPerson();
    Integer countDistinctByAccountId(Integer accountId);
}
