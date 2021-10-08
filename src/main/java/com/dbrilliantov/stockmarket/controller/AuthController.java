package com.dbrilliantov.stockmarket.controller;

import com.dbrilliantov.stockmarket.domain.Account;
import com.dbrilliantov.stockmarket.repository.AccountRepository;
import com.dbrilliantov.stockmarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping
    public String index() {
        if (userService.isAuthenticated()) {
            return "redirect:/office";
        }

        return "main/index";
    }

    @GetMapping("/login")
    public String login(Model model, String error) {
        if (userService.isAuthenticated()) {
            return "redirect:/office";
        }

        if (error != null) {
            model.addAttribute("error", "Incorrect login or password.");
        }

        return "main/login";
    }

    @GetMapping("/signup")
    public String signup(@ModelAttribute("account") Account account) {
        if (userService.isAuthenticated()) {
            return "redirect:/office";
        }

        return "main/signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("account") @Valid Account account, BindingResult bindingResult) {

        if (!bindingResult.hasFieldErrors("username") && accountRepository.findAccountByUsername(account.getUsername()) != null) {
            bindingResult.rejectValue("username", "error.username", "This username is already taken.");
        }

        if (!bindingResult.hasFieldErrors("password") && !bindingResult.hasFieldErrors("confirm") && !account.getPassword().equals(account.getConfirm())) {
            bindingResult.rejectValue("password", "error.password", "Those passwords didn't match.");
            bindingResult.rejectValue("confirm", "error.confirm", "Those passwords didn't match.");
        }

        if (bindingResult.hasErrors()) {
            return "main/signup";
        }

        userService.addAccount(account);
        userService.getAccount(account.getUsername(), account.getConfirm());

        return "redirect:/office";
    }

}