package org.isbd.part4.service;

import org.isbd.part4.entity.*;
import org.isbd.part4.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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


public boolean changeLocation(Integer personId, String locationName){
    Integer locationId=locationRepository.findLocationByName(locationName).getId();
    try {
        personRepository.movePerson(personId,locationId);
    }catch (Exception e){
    }
    return true;
}

public boolean makeInteract(int oneEntityId, int twoEntityId, String[] things){
    String useThings=convertArrayThingsToString(things);
    System.out.println(useThings);
    try {
        personRepository.interact(oneEntityId,twoEntityId,useThings);
    }catch (Exception e){

    }
    return true;
}

private String convertArrayThingsToString(String[] strArray){
    String resultString="'{";
    if(strArray==null){
        resultString="{}";
        return resultString;
    }

    resultString+=strArray[0];
    for(int i=1;i<strArray.length;i++){
        resultString+=","+strArray[i];
    }
    resultString+="}'";

    return resultString;
}
//public boolean atackPerson(String personNameOne,String personNameTwo, Integer idThings){
//    Integer personidOne=personRepository.findPersonByName(personNameOne).getId();
//    Integer personidTwo=personRepository.findPersonByName(personNameTwo).getId();
//    try {
//        if(idThings==-1){
//            buisnessProcessRepository.makeAttack(personidOne,personidTwo,idThings);
//        }else{
//            buisnessProcessRepository.makeAttack(personidOne,personidTwo);
//        }
//
//    }catch (Exception e){
//    }
//    return true;
//}
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


    //TODO
    public ArrayList<Item> getThingsPerson(Person person){
        Person person= personRepository.findPersonByName(personName);
        ArrayList<Integer> listIdItem=personRepository.findItemByPersonId(person.getId());
        ArrayList<Item> itemList=new ArrayList();
        for (int i:listIdItem) {
            itemList.add(itemRepository.findItemById(i));

        }

        return itemList;
    }
}
