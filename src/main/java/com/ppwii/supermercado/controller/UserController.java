package com.ppwii.supermercado.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ppwii.supermercado.model.User;
import com.ppwii.supermercado.service.IUserService;

@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/usuario")
    public String list(Model model) {
        model.addAttribute("usuarios", userService.getAllUsers());
        return "user/list";
    }

    @GetMapping("/usuario/create")
    public String register() {
        return "user/registerUser";
    }

    @PostMapping("/usuario/save")
    public String saveUser(
            @ModelAttribute User user,
            Model model) {
        userService.saveUser(user);
        return "redirect:/usuario";
    }

    @GetMapping("/usuario/delete/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.deleteUserById(id);
        return "redirect:/usuario";
    }

    @GetMapping("/accessDenied")
	public String getAccessDeniedPage() {
		return "user/accessDeniedPage";
	}
}
