package ru.atavrel.restclientdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.atavrel.restclientdemo.dto.AuthenticationRequestDTO;
import ru.atavrel.restclientdemo.dto.AuthenticationResponseDTO;

@Controller
public class Controllers {

    private AuthenticationResponseDTO responseDTO;

    @Autowired
    public void setResponseDTO(AuthenticationResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    @GetMapping
    public String loginPage() {
        return "login";
    }

    @GetMapping(value = "/logout")
    public String logout() {
        responseDTO.setJwtToken(null);
        responseDTO.setRoleList(null);
        return "redirect:/?logout";
    }

    @GetMapping(value = "/registration")
    public String registrationPage() {
        if(responseDTO.getRoleList() != null) {
            return "redirect:/";
        }
        return "registration";
    }

    @GetMapping(value = "/home")
    public String homePage(Model model) {
        if (responseDTO.getRoleList() == null || !responseDTO.getRoleList().contains("USER")) {
            return "redirect:/";
        }
        model.addAttribute("roles", responseDTO.getRoleList());
        return "home";
    }

    @GetMapping(value = "/admin")
    public String adminPage(Model model) {
        if (responseDTO.getRoleList() == null || !responseDTO.getRoleList().contains("ADMIN")) {
            return "redirect:/";
        }
        model.addAttribute("roles", responseDTO.getRoleList());
        return "admin";
    }

    @GetMapping(value = "/manager")
    public String managerPage(Model model) {
        if (responseDTO.getRoleList() == null || !responseDTO.getRoleList().contains("MANAGER")) {
            return "redirect:/";
        }
        model.addAttribute("roles", responseDTO.getRoleList());
        return "manager";
    }

    @PostMapping
    public String login(@ModelAttribute AuthenticationRequestDTO requestDTO, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            responseDTO = restTemplate.postForObject("http://localhost:8075/api/login", requestDTO, AuthenticationResponseDTO.class);
            model.addAttribute("token", responseDTO.getJwtToken());
            model.addAttribute("email", requestDTO.getUsername());
            model.addAttribute("roles", responseDTO.getRoleList());
            return "login";
        } catch (HttpStatusCodeException e) {
            System.err.println(e.getMessage());
        }
        return "redirect:/?error";
    }
}
