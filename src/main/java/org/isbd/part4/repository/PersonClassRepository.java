package org.isbd.part4.repository;

import org.isbd.part4.entity.PersonClass;
import org.isbd.part4.entity.Side;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonClassRepository extends JpaRepository<PersonClass, Long> {

    List<PersonClass> findAll();
}
