package org.isbd.part4.controller;

import org.isbd.part4.entity.Account;
import org.isbd.part4.entity.Person;
import org.isbd.part4.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomePageController {

    @Autowired
    private PersonRepository personRepository;

    @PostMapping("/play")
    public String play(@RequestParam Integer personId, HttpServletRequest request){
        final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Person person = personRepository.findPersonById(personId);
        if(person.getAccountId()==account.getId()){
            request.getSession().setAttribute("person", person);
            return "redirect:/game";
        } else {
            return "redirect:/logout";
        }
    }
}
