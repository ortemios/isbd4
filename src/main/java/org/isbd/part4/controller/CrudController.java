package org.isbd.part4.controller;

import org.isbd.part4.entity.*;
import org.isbd.part4.repository.PersonClassRepository;
import org.isbd.part4.repository.PersonRepository;
import org.isbd.part4.repository.RaceRepository;
import org.isbd.part4.repository.SideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    @RequestMapping(
            path = "/person",
            method = POST,
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
            produces = "application/json;charset=UTF-8"
    )
    public List<Person> addPerson(
            @RequestParam String name,
            @RequestParam Integer raceId,
            @RequestParam Integer personClassId
    ) {
        final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        personRepository.createPerson(account.getId(), name, raceId, personClassId);
        return personRepository.findAllByAccountId(account.getId());
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
}
