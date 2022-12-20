package org.isbd.part4.controller;

import org.isbd.part4.entity.Person;
import org.isbd.part4.repository.PersonRepository;
import org.isbd.part4.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Controller
public class GamePageController {

    @Autowired
    private GameService gameService;
    @Autowired
    private PersonRepository personRepository;

    private String forPerson(HttpServletRequest request, Function<Person, String> handler) {
        final Person person = (Person) request.getSession().getAttribute("person");
        if (person == null) {
            return "redirect:/";
        } else {
            return handler.apply(person);
        }
    }

    @GetMapping("/game")
    public String game(Model model, HttpServletRequest request) {
        return forPerson(request, person -> {
            model.addAttribute("message", gameService.getMessageFor(person));
            model.addAttribute("items", gameService.getItemsFor(person));
            model.addAttribute("entities", gameService.getEntitiesAtLocationFor(person));
            model.addAttribute("locations", gameService.getNearLocationsFor(person));
            model.addAttribute("person", gameService.getPersonInfoFor(person));
            return "game_view";
        });
    }

    @PostMapping("/move_person")
    public String movePerson(@RequestParam Integer locationId, HttpServletRequest request) {
        return forPerson(request, person -> {
            personRepository.movePerson(person.getId(), locationId);
            return "redirect:/game";
        });
    }

    @PostMapping("/interact")
    public String interact(@RequestParam Integer victimId, @RequestParam(defaultValue = "") List<Integer> personItemIds, HttpServletRequest request) {
        return forPerson(request, person -> {
            gameService.interact(person, victimId, personItemIds);
            return "redirect:/game";
        });
    }
}
