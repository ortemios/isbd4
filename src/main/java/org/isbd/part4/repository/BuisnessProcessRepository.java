package org.isbd.part4.repository;

import org.isbd.part4.entity.Location;
import org.isbd.part4.entity.Person;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
public interface BuisnessProcessRepository extends CrudRepository<Person, Long> {
    @ExceptionHandler
    @Query(value ="call move_person(:idPerson, :idLocation)",
            nativeQuery = true)
    void moveLocation(@Param("idPerson") int idPerson,@Param("idLocation") int idLocation);


    @Procedure
    void move_person(int idPerson,int idLocation);
}
