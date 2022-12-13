package org.isbd.part4.repository;

import org.isbd.part4.entity.Npc;
import org.isbd.part4.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NpcRepository extends JpaRepository<Npc, Long> {

    Npc findNpcByEntityId(Integer EntityId);
}
