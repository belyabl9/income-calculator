package com.belyabl9.incomecalc.controller;

import com.belyabl9.incomecalc.domain.BankAccount;
import com.belyabl9.incomecalc.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public String accountsPage(Model model) {
        model.addAttribute("accounts", bankAccountService.findAll());
        
        return "accounts";
    }

    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public String addAccount(BankAccount bankAccount) {
        bankAccountService.add(bankAccount);

        return "redirect:/accounts";
    }

    @RequestMapping(value = "/account/{id}/del", method = RequestMethod.GET)
    public String addAccount(@PathVariable Long id) {
        bankAccountService.remove(id);

        return "redirect:/accounts";
    }
}
