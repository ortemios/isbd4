package org.isbd.part4.controller;

import org.isbd.part4.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class HomePageController {

    @Autowired
    private AccountRepository accountRepository;
}
