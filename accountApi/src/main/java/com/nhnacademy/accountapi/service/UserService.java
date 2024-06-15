package com.nhnacademy.accountapi.service;

import com.nhnacademy.accountapi.entity.User;
import com.nhnacademy.accountapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public List<Long> findUserIdsByEmails(List<String> emails) {
        return userRepository.findAll().stream()
                .filter(user -> emails.contains(user.getEmail()))
                .map(User::getId)
                .collect(Collectors.toList());
    }

    public void updateUserRoles(List<String> emails, String role) {
        List<User> users = userRepository.findAll().stream()
                .filter(user -> emails.contains(user.getEmail()))
                .collect(Collectors.toList());

        for (User user : users) {
            user.setRole(User.Role.valueOf(role));
            userRepository.save(user);
        }
    }


//    public List<String> findUserEmailsByIds(List<Long> ids) {
//        return userRepository.findAllByIdIn(ids).stream()
//                .map(User::getEmail)
//                .collect(Collectors.toList());
//    }

    public Map<Long, String> findEmailsByIds(List<Long> ids) {
        Map<Long, String> emailMap = new HashMap<>();
        List<User> users = userRepository.findAllByIdIn(ids);
        for (User user : users) {
            emailMap.put(user.getId(), user.getEmail());
        }

        for (Long id : ids) {
            emailMap.putIfAbsent(id, null);
        }

        return emailMap;
    }
}
