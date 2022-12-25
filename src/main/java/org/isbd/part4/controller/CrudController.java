package org.isbd.part4.controller;

import org.isbd.part4.entity.*;
import org.isbd.part4.repository.PersonClassRepository;
import org.isbd.part4.repository.PersonRepository;
import org.isbd.part4.repository.RaceRepository;
import org.isbd.part4.repository.SideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class CrudController {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private SideRepository sideRepository;
    @Autowired
    private PersonClassRepository personClassRepository;
    @Autowired
    private RaceRepository raceRepository;

    @GetMapping(value="/person", produces = "application/json;charset=UTF-8")
    public List<Person> getPersons() {

        final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return personRepository.findAllByAccountId(account.getId());
    }

    @GetMapping(value="/allPerson", produces = "application/json;charset=UTF-8")
    public List<Person> getAllPersons() {

        final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(AdminPageController.adminList.contains(account.getEmail())){

            return personRepository.getAllPerson();
        }
        return personRepository.findAllByAccountId(account.getId());
    }

    @GetMapping(value="/TypeAccount", produces = "application/json;charset=UTF-8")
    public String getTypeAccount() {
        final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (AdminPageController.adminList.contains(account.getEmail())){
            return "admin";
        }else
            return "";
    }
    @RequestMapping(
            path = "/person",
            method = POST,
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
            produces = "application/json;charset=UTF-8"
    )
    public ResponseEntity<List<Person>> addPerson(
            @RequestParam String name,
            @RequestParam Integer raceId,
            @RequestParam Integer personClassId
    ) {
        final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            personRepository.createPerson(account.getId(), name, raceId, personClassId);
            final List<Person> personList = personRepository.findAllByAccountId(account.getId());
            return new ResponseEntity<>(personList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value="/side", produces = "application/json;charset=UTF-8")
    public List<Side> getSides() {
        return sideRepository.findAll();
    }

    @GetMapping(value="/race", produces = "application/json;charset=UTF-8")
    public List<Race> getRaces(@RequestParam Integer sideId) {
        return raceRepository.findAllBySideId(sideId);
    }

    @GetMapping(value="/person_class", produces = "application/json;charset=UTF-8")
    public List<PersonClass> getPersonClasses() {
        return personClassRepository.findAll();
    }
    @GetMapping(value="/havePerson", produces = "application/json;charset=UTF-8")
    public String havePerson() {
        final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return String.valueOf(personRepository.countDistinctByAccountId(account.getId()));

    }
}
