package org.isbd.part4.service;

import org.isbd.part4.entity.Account;
import org.isbd.part4.entity.Person;
import org.isbd.part4.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GameService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final Map<Integer, String> messages = new HashMap<>();

    public String getMessageFor(Person person) {
        String message = messages.getOrDefault(person.getEntityId(), "");
        messages.remove(person.getEntityId());
        return message;
    }

    public List<Map<String, Object>> getItemsFor(Person person) {
        return jdbcTemplate.queryForList(
                "select * from person_item join item on " +
                        "person_item.item_id = item.id and person_item.person_id = ?",
                person.getId()
        );
    }

    public List<Map<String, Object>> getNearLocationsFor(Person person) {
        return jdbcTemplate.queryForList(
                "select * from location join location_near_location " +
                        "on location_near_location.near_location_id = location.id " +
                        "and location_near_location.location_id = " +
                        "(select location_id from entity where entity.id = ?)",
                person.getEntityId()
        );
    }

    public List<Map<String, Object>> getEntitiesAtLocationFor(Person person) {
        return jdbcTemplate.queryForList(
                "select entity.id as id, entity.health as health, npc.name as npcName, person.name as personName, are_friends(entity.id, ?) from entity " +
                "left join npc on npc.entity_id = entity.id " +
                "left join person on person.entity_id = entity.id " +
                "where entity.location_id = (select location_id from entity where entity.id = ?) " +
                "and entity.id <> ?",
                person.getEntityId(),
                person.getEntityId(),
                person.getEntityId()
        );
    }

    public Map<String, Object> getPersonInfoFor(Person person) {

        return jdbcTemplate.queryForMap(
                "select person.name as name, entity.health as health, location.name as locationName, race.name as raceName, person_class.name as className " +
                "from person join entity on " +
                "person.entity_id = entity.id and person.id = ? " +
                "join race on race.id = person.race_id " +
                "join person_class on person_class.id = person.person_class_id " +
                "join location on location.id = entity.location_id",
                person.getId()
        );
    }

    public void interact(Person person, int victimId, List<Integer> personItemIds) {
        String query = String.format(
                "select interact(%d, %d, '{%s}')",
                person.getEntityId(),
                victimId,
                personItemIds.stream().map(Object::toString).collect(Collectors.joining(","))
        );
        int result = jdbcTemplate.queryForObject(query, Integer.class);

        final String[] msgTexts = {
                "Вы проиграли",
                "Ничья",
                "Вы победили"
        };
        this.messages.put(person.getEntityId(), msgTexts[1 + result]);
        this.messages.put(victimId, msgTexts[1 - result]);
    }
}
