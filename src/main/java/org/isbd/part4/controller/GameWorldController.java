package org.isbd.part4.controller;

import org.isbd.part4.service.GameWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Null;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;

@Controller
public class GameWorldController {
    @Autowired
    private GameWorldService gameWorldService;


    @GetMapping("/main")
    public String main(Model model){
        try {
            String personName="Bob";
            model.addAttribute("thingsForAttck",gameWorldService.getThingsPerson(personName));
            model.addAttribute("thingsForHelp",gameWorldService.getThingsPerson(personName));
            model.addAttribute("nearPersonForAttack",gameWorldService.getPersonNearForAttack(personName));
            model.addAttribute("nearPersonForHelp",gameWorldService.getPersonNearForHelp(personName));
            model.addAttribute("personLocation",gameWorldService.getLocationPerson(personName));
            model.addAttribute("nearLocation",gameWorldService.getNearLocation(personName));
            model.addAttribute("nearNpc",gameWorldService.getNPCNearForAttck(personName));

        }
        catch (NullPointerException e){

        }

        return "GameWorld";
    }

    @PostMapping("/changeLocation")
    public String changeLocation(HttpServletRequest request){
        String namePerson=request.getParameter("personName");
        String location=request.getParameter("nearLocation");
        gameWorldService.chengeLocation(namePerson,location);
        return "redirect:/main";
    }
    @PostMapping("/characterInteraction")
    public String characterInteraction(HttpServletRequest request){
        String personTwo="";
        String typeInteraction=request.getParameter("characterInteraction");
        String personOne=request.getParameter("PersonOne");
        if(typeInteraction.equals("Атаковать")){
            personTwo=request.getParameter("personTwoForAttck");
        }else{
            if(typeInteraction.equals("Лечить")){
                personTwo=request.getParameter("personNameForHelp");
            }else {
                personTwo=request.getParameter("NameNpc");
            }

        }

        gameWorldService.makeInteract(personOne,personTwo,request.getParameterValues("useThings"));
        return "redirect:/main";
    }

//    @PostMapping("/attack")
//    public String atackPerson(HttpServletRequest request, Model model){
//        gameWorldService.atackPerson(request.getParameter("PersonOne"),request.getParameter("personTwo"),Integer.valueOf(request.getParameter("thingsForAttck")));
//        return main(model);
//    }

//    @PostMapping("/attack")
//    public String attack(HttpServletRequest request){
//        String attacking=request.getParameter("attacking");
//        String attacked=request.getParameter("attacked");
//        gameWorldService.makeAttac(attacking,attacked);
//        return main();
//    }
//    @PostMapping("/move")
//    public String move(){
//        return main();
//    }
}
