package org.isbd.part4.controller;

import org.isbd.part4.entity.Entity;
import org.isbd.part4.service.GameWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class GameWorld {
    @Autowired
    private GameWorldService gameWorldService;


    @PostMapping("/main")
    public String main(Model model){
        String personName="Bob";
        model.addAttribute("nearPersonForAttack",gameWorldService.getPersonNearForAttack(personName));
        model.addAttribute("nearPersonForHelp",gameWorldService.getPersonNearForHelp(personName));
        return "GameWorld";
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
