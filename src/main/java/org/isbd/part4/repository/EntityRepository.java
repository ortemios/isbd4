package org.isbd.part4.repository;

import org.isbd.part4.entity.Entity;
import org.isbd.part4.entity.Person;
import org.isbd.part4.entity.Race;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntityRepository extends JpaRepository<Entity, Integer> {

    List<Entity> findEntityByLocation_Id(Integer locationId);
    Entity findEntityById(Integer id);

}
