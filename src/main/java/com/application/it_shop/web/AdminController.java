package com.application.it_shop.web;


import com.application.it_shop.model.User;
import com.application.it_shop.model.enums.Role;
import com.application.it_shop.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin1")
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOD')")
    public String getAdminPage( Model model) {
        List<User> users = this.userService
                .listAll()
                .stream()
                .filter(i -> !i.getRole().equals(Role.ADMIN))
                .collect(Collectors.toList());
        model.addAttribute("users", users);
        model.addAttribute("roles", List.of(Arrays.stream(Role.values()).toList()));
        model.addAttribute("bodyContent","admin");

        return "master-template";
    }

    @GetMapping("/user/{id}/edit")
    public String editUserProfile(@PathVariable Long id, Model model) {
        if(this.userService.findById(id).isPresent()) {
            User u = this.userService.findById(id).get();
            model.addAttribute("user", u);

            List<Role> roles = List.of(Role.USER, Role.MOD);
            model.addAttribute("roles", roles);
            model.addAttribute("bodyContent","edit-user");
            return "master-template";
        }
        return "redirect:/admin1?error=UserNotFound";
    }

    @PostMapping("/updateProfile")
    public String updateUserProfile(@RequestParam(required = false) Long id,
                                    @RequestParam String name,
                                    @RequestParam String surname,
                                    @RequestParam String username,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bdate,
                                    @RequestParam Role role,
                                    Model model) {

        if(id != null) {
            this.userService.edit(id,name,surname, username, bdate,role);
        }

        return "redirect:/admin1";


    }

    @DeleteMapping("/user/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        this.userService.deleteById(id);
        return "redirect:/admin1?UserDeletedSuccessfully";
    }


}
