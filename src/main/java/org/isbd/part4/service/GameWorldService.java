package org.isbd.part4.service;

import net.bytebuddy.implementation.bytecode.assign.TypeCasting;
import org.isbd.part4.controller.GameWorld;
import org.isbd.part4.entity.Entity;
import org.isbd.part4.entity.Location;
import org.isbd.part4.entity.Person;
import org.isbd.part4.repository.EntityRepository;
import org.isbd.part4.repository.LocationRepository;
import org.isbd.part4.repository.PersonRepository;
import org.isbd.part4.repository.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class GameWorldService {


    @Autowired
    private EntityRepository entityRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private RaceRepository raceRepository;
    @Autowired
    private LocationRepository locationRepository;

    public ResultAttac makeAttac(String attacking, String attacked){
//    System.out.println(attacking+"makeAttac");
//    int attackingId= PersonRepository.findByName(attacking);
//    System.out.println(attackingId);
    return ResultAttac.WINATTACKED;
}


public List<String> getPersonNearForAttack(String personName){
    Person nowPerson;
    Person person=personRepository.findPersonByName(personName);
    Entity entity=entityRepository.findEntityById(person.getEntityId());
    Integer locationId =entity.getId();
    List<Entity> entityList=entityRepository.findEntityByLocation_Id(locationId);
    ArrayList<String> personArrayList =new ArrayList<>();
    for (Entity e:entityList) {
        if(e.getId()!=person.getEntityId()){
            nowPerson=personRepository.findPersonByEntityId(e.getId());
            if(raceRepository.findById(person.getRaceId()).getSide()!=(raceRepository.findById(nowPerson.getRaceId())).getSide()){
                personArrayList.add(nowPerson.getName());
            }
        }
    }
    return personArrayList;

}
    public List<String> getPersonNearForHelp(String personName){
        Person nowPerson;
        Person person=personRepository.findPersonByName(personName);
        Integer locationId =getLocationIdByPersonName(personName);
        List<Entity> entityList=entityRepository.findEntityByLocation_Id(locationId);
        ArrayList<String> personArrayList =new ArrayList<>();
        for (Entity e:entityList) {
            if(e.getId()!=person.getEntityId()){
                nowPerson=personRepository.findPersonByEntityId(e.getId());
                if(raceRepository.findById(person.getRaceId()).getSide()==(raceRepository.findById(nowPerson.getRaceId())).getSide()){
                    personArrayList.add(nowPerson.getName());
                }
            }
        }
        return personArrayList;

    }

    public String getLocationPerson(String personName ){
        return getLocationByPersonName(personName).getName();
    }

    public List<String> getNearLocation(String personName){
        Location personLocation=getLocationByPersonName(personName);
       List<Integer> location=locationRepository.findAllNearLocationByLocationId(personLocation.getId());
        List<String> nearLocation=new ArrayList<>();
        for (Integer i:location) {
            nearLocation.add(locationRepository.findLocationById(i).getName());
        }
        return nearLocation;
    }


    private Integer getLocationIdByPersonName(String personName){
        Person person=personRepository.findPersonByName(personName);
        Entity entity=entityRepository.findEntityById(person.getEntityId());
        Location location=entity.getLocation();
        return location.getId();
    }
    private Location getLocationByPersonName(String personName){
        Integer locationId =getLocationIdByPersonName(personName);
        Location location=locationRepository.findLocationById(locationId);
        return location;
    }

}
