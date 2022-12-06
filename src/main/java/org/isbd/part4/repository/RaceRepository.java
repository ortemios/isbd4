package org.isbd.part4.repository;

import org.isbd.part4.entity.Race;
import org.isbd.part4.entity.Side;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {

    List<Race> findAllBySideId(Integer sideId);
}
