package com.application.it_shop.service.impl;

import com.application.it_shop.model.User;
import com.application.it_shop.model.exceptions.*;
import com.application.it_shop.repository.jpa.UserRepository;
import com.application.it_shop.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.password.PasswordEncoder;
import com.application.it_shop.model.enums.Role;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User login(String username, String password) {
        if (username == null || username.isEmpty() ||
                password == null || password.isEmpty()) throw new InvalidArgumentException(username, password);

        return this.userRepository
                .findByUsernameAndPassword(username, password)
                .orElseThrow(InvalidUserCredentialsException::new);
    }

    @Override
    public User register(String name, String surname, LocalDate birthday, String email, String username, String password, String passwordRepeat, Role role) {
        if (name == null || name.isEmpty() || surname == null || surname.isEmpty())
            throw new InvalidUserCredentialsException();
        if (!password.equals(passwordRepeat))
            throw new PasswordNotMatchingException();

        if (this.userRepository.findByUsername(username).isPresent())
            throw new UserAlreadyExistsException(username);

        String encryptedPassword = this.passwordEncoder.encode(password);

        User user = new User(
                name,
                surname,
                birthday,
                username,
                email,
                encryptedPassword,
                role);

        return this.userRepository.save(user);
    }

    @Override
    public List<User> listAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return this.userRepository
                .findById(id);
    }

    @Override
    public void deleteById(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public User edit(Long id, String name, String surname, String username, LocalDate bdate,  Role role) {
        User u = this.findById(id).get();
        u.setName(name);
        u.setSurname(surname);
        u.setUsername(username);
        u.setDateOfBirth(bdate);
        u.setRole(role);
        return this.userRepository.save(u);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = this.userRepository.
                findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException(username));

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                u.getUsername(),
                u.getPassword(),
                Stream.of(new SimpleGrantedAuthority(u.getRole().toString())).collect(Collectors.toList()));
        return userDetails;
    }
}
