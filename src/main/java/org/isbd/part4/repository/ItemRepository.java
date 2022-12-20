package org.isbd.part4.repository;

import org.isbd.part4.entity.Item;
import org.isbd.part4.entity.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findItemById(int idItem);
}
