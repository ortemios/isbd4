//package org.artyemlavrov.lab4.controller;
//
//import org.artyemlavrov.lab4.entity.HistoryItem;
//import org.artyemlavrov.lab4.entity.Account;
//import org.artyemlavrov.lab4.repository.HistoryRepository;
//import org.artyemlavrov.lab4.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.ConstraintViolation;
//import javax.validation.Valid;
//import javax.validation.Validation;
//import javax.validation.Validator;
//import java.util.List;
//import java.util.Set;
//
//@Controller
//public class HomePageController {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private HistoryRepository historyRepository;
//
//    @RequestMapping(value = "/get_history", method = RequestMethod.GET, headers = "Accept=application/json")
//    @ResponseBody
//    public List<HistoryItem> getHistory() {
//        final Account user = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return user.getHistory();
//    }
//
//    @RequestMapping(value = "/add_history_item", method = RequestMethod.POST, headers = "Accept=application/json")
//    @ResponseBody
//    public void addHistoryItem(@Valid HistoryItem item) {
//        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
//        Set<ConstraintViolation<HistoryItem>> violations = validator.validate(item);
//        if (violations.isEmpty()) {
//            historyRepository.save(item);
//            final String username = SecurityContextHolder.getContext().getAuthentication().getName();
//            final Account user = userRepository.getByUsername(username);
//            user.getHistory().add(item);
//            userRepository.save(user);
//        }
//    }
//}
