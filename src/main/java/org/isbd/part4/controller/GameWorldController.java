package org.isbd.part4.controller;

import org.isbd.part4.entity.Person;
import org.isbd.part4.service.GameWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class GameWorldController {
    @Autowired
    private GameWorldService gameWorldService;


    @GetMapping("/main")
    public String main(Model model,HttpServletRequest request){
        try {
            final Person person= (Person) request.getSession().getAttribute("person");

            model.addAttribute("thingsForAttck",gameWorldService.getThingsPerson(person.getId()));
            model.addAttribute("thingsForHelp",gameWorldService.getThingsPerson(person.getId()));
            model.addAttribute("nearPersonForAttack",gameWorldService.getPersonNearForAttack(person.getId()));
            model.addAttribute("nearPersonForHelp",gameWorldService.getPersonNearForHelp(person.getId()));
            model.addAttribute("personLocation",gameWorldService.getLocationPerson(person.getId()));
            model.addAttribute("nearLocation",gameWorldService.getNearLocation(person.getId()));
            model.addAttribute("nearNpc",gameWorldService.getNPCNearForAttck(person.getId()));

        }
        catch (NullPointerException e){

        }

        return "GameWorld";
    }

    @PostMapping("/changeLocation")
    public String changeLocation(HttpServletRequest request){
        int idPerson=Integer.valueOf(request.getParameter("personId"));
        String location=request.getParameter("nearLocation");
        gameWorldService.changeLocation(idPerson,location);
        return "redirect:/main";
    }
    @PostMapping("/characterInteraction")
    public String characterInteraction(HttpServletRequest request){
        String personTwo="";
        String typeInteraction=request.getParameter("characterInteraction");
        String personOne=request.getParameter("PersonOneId");
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
