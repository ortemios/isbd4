package org.isbd.part4.repository;

import org.isbd.part4.entity.Location;
import org.isbd.part4.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {

    Location findLocationById(Integer id);

    @Query(value ="Select near_location_id From location join location_near_location\n" +
            "on location.id = location_near_location.location_id where id= :id",nativeQuery = true)
    ArrayList<Integer> findAllNearLocationByLocationId(@Param("id") int id);


}
