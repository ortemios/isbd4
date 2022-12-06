package org.isbd.part4.controller;

import org.isbd.part4.entity.Account;
import org.isbd.part4.entity.Person;
import org.isbd.part4.repository.AccountRepository;
import org.isbd.part4.repository.PersonRepository;
import org.isbd.part4.service.UserExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class HomePageController {

    @Autowired
    private AccountRepository accountRepository;


}
