package ru.atavrel.restclientdemo.controller;

import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.atavrel.restclientdemo.dto.AuthenticationRequestDTO;
import ru.atavrel.restclientdemo.dto.AuthenticationResponseDTO;
import ru.atavrel.restclientdemo.dto.RoleDTO;
import ru.atavrel.restclientdemo.dto.UserDTO;
import ru.atavrel.restclientdemo.util.PasswordGenerator;

import java.util.HashSet;
import java.util.Set;

@Controller
public class Controllers {

    private AuthenticationResponseDTO responseDTO;
    private RestTemplate restTemplate;
    private PasswordGenerator passwordGenerator;

    @Autowired
    public void setPasswordGenerator(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

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
        if (responseDTO.getRoleList() != null) {
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

    @GetMapping(value = "/login/{registrationId}")
    public String socialLogin(@AuthenticationPrincipal OAuth2User principal, Model model, @PathVariable String registrationId) {

        String name = principal.getAttribute("name");
        String email = principal.getAttribute("email");
        String[] fullName = name.split(" ");
        String firstName = fullName[0];
        String lastName = fullName[1];
        //String password = passwordGenerator.generateRandomSpecialCharacters(10);
        String password = "123";
        Set<RoleDTO> roles = new HashSet<>();
        roles.add(new RoleDTO(1L, "USER"));

        Boolean result = restTemplate.getForObject("http://localhost:8075/api/registration/users/email/" + email, Boolean.class);
        if (result==null) {
            UserDTO userDTO = new UserDTO(firstName, lastName, email, password, roles);
            restTemplate.postForObject("http://localhost:8075/api/registration/users/", userDTO, UserDTO.class);
        }
        return login(new AuthenticationRequestDTO(email, password), model);
    }

    @PostMapping
    public String login(@ModelAttribute AuthenticationRequestDTO requestDTO, Model model) {
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
