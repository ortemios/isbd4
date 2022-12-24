package org.isbd.part4.controller;

import org.isbd.part4.entity.Account;
import org.isbd.part4.entity.Person;
import org.isbd.part4.repository.AccountRepository;
import org.isbd.part4.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class AdminPageController {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AccountRepository accountRepository;
    public static ArrayList<String> adminList=new ArrayList<String>();
    public AdminPageController(){
        adminList.add("admin@admin.ru");
    }

    @PostMapping("/banAccount")
    public String play(@RequestParam Integer personId, HttpServletRequest request){
        final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(adminList.contains(account.getEmail())){
            final Person person = personRepository.findPersonById(personId);
            final Account account1=accountRepository.findAccountById(person.getAccountId());
                accountRepository.banAccount(account1.getId());
                return "redirect:/admin";

        } else {
            return "redirect:/logout";
        }
    }
}
