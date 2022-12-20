package org.isbd.part4.controller;

import org.isbd.part4.entity.Entity;
import org.isbd.part4.entity.Item;
import org.isbd.part4.service.GameWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Controller
public class GameWorld {
    @Autowired
    private GameWorldService gameWorldService;


    @PostMapping("/main")
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
    public String changeLocation(HttpServletRequest request, Model model){
        String namePerson=request.getParameter("personName");
        String location=request.getParameter("nearLocation");
        gameWorldService.chengeLocation(namePerson,location);
        return main(model);
    }
    @PostMapping("/characterInteraction")
    public String characterInteraction(HttpServletRequest request,Model model){
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
        String aa=request.getParameter("ners");

        System.out.println(aa);



        System.out.println(personTwo);
        return main(model);
    }

    @PostMapping("/attack")
    public String atackPerson(HttpServletRequest request, Model model){
        gameWorldService.atackPerson(request.getParameter("PersonOne"),request.getParameter("personTwo"),Integer.valueOf(request.getParameter("thingsForAttck")));
        return main(model);
    }

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
