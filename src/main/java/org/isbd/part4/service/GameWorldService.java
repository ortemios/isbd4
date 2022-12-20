package org.isbd.part4.service;

import net.bytebuddy.implementation.bytecode.assign.TypeCasting;
import org.isbd.part4.controller.GameWorld;
import org.isbd.part4.entity.*;
import org.isbd.part4.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
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
    @Autowired
    private NpcRepository npcRepository;
    @Autowired
    private BuisnessProcessRepository buisnessProcessRepository;
    @Autowired
    private ItemRepository itemRepository;

    public ResultAttac makeAttac(String attacking, String attacked){
//    System.out.println(attacking+"makeAttac");
//    int attackingId= PersonRepository.findByName(attacking);
//    System.out.println(attackingId);
    return ResultAttac.WINATTACKED;
}

public boolean chengeLocation(String personName,String locationName){
    Integer personid=personRepository.findPersonByName(personName).getId();
    Integer locationId=locationRepository.findLocationByName(locationName).getId();
    try {
        buisnessProcessRepository.moveLocation(personid,locationId);
    }catch (Exception e){
    }
    return true;
}

public boolean atackPerson(String personNameOne,String personNameTwo, Integer idThings){
    Integer personidOne=personRepository.findPersonByName(personNameOne).getId();
    Integer personidTwo=personRepository.findPersonByName(personNameTwo).getId();
    try {
        if(idThings==-1){
            buisnessProcessRepository.makeAttack(personidOne,personidTwo,idThings);
        }else{
            buisnessProcessRepository.makeAttack(personidOne,personidTwo);
        }

    }catch (Exception e){
    }
    return true;
}
public List<String> getPersonNearForAttack(String personName){
    Person nowPerson;
    Person person=personRepository.findPersonByName(personName);
    Entity entity=entityRepository.findEntityById(person.getEntityId());
    Integer locationId =entity.getLocation().getId();
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
public List<String> getNPCNearForAttck(String personName){
    Person person=personRepository.findPersonByName(personName);
    Entity entity=entityRepository.findEntityById(person.getEntityId());
    Integer locationId =entity.getLocation().getId();
    List<Entity> entityList=entityRepository.findEntityByLocation_Id(locationId);
    ArrayList<String> npcArrayList =new ArrayList<>();
    for (Entity e:entityList) {
        npcArrayList.add(String.valueOf(npcRepository.findNpcByEntityId(e.getId()).getId()));
    }
    return npcArrayList;
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

    public ArrayList<Item> getThingsPerson(String personName){
        Person person= personRepository.findPersonByName(personName);
        ArrayList<Integer> listIdItem=personRepository.findItemByPersonId(person.getId());
        ArrayList<Item> itemList=new ArrayList();
        for (int i:listIdItem) {
            itemList.add(itemRepository.findItemById(i));

        }

        return itemList;
    }
}
