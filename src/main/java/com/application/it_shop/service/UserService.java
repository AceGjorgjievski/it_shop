package com.application.it_shop.service;

import com.application.it_shop.model.User;
import com.application.it_shop.model.enums.Role;
import net.bytebuddy.asm.Advice;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    User login(String username, String password);
    User register(String name,
                  String surname,
                  LocalDate birthday, String email, String username,
                  String password,
                  String passwordRepeat,
                  Role role);

    List<User> listAll();
    Optional<User> findById(Long id);

    void deleteById(Long id);

    User edit(Long id, String name, String surname, String username, LocalDate bdate,  Role role);
}
