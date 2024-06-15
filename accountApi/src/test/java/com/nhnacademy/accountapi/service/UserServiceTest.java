package com.nhnacademy.accountapi.service;

import com.nhnacademy.accountapi.entity.User;
import com.nhnacademy.accountapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertEquals(user.getEmail(), savedUser.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testFindById() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findById(1L);

        assertTrue(foundUser.isPresent());
        assertEquals(1L, foundUser.get().getId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindByEmail() {
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findByEmail("test@example.com");

        assertTrue(foundUser.isPresent());
        assertEquals("test@example.com", foundUser.get().getEmail());
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setEmail("test@example.com");

        doNothing().when(userRepository).delete(user);

        userService.deleteUser(user);

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void testFindUserIdsByEmails() {
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("test1@example.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("test2@example.com");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<Long> userIds = userService.findUserIdsByEmails(Arrays.asList("test1@example.com", "test2@example.com"));

        assertEquals(2, userIds.size());
        assertTrue(userIds.contains(1L));
        assertTrue(userIds.contains(2L));
    }

    @Test
    public void testUpdateUserRoles() {
        User user1 = new User();
        user1.setEmail("test1@example.com");

        User user2 = new User();
        user2.setEmail("test2@example.com");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
        when(userRepository.save(any(User.class))).thenReturn(user1).thenReturn(user2);

        userService.updateUserRoles(Arrays.asList("test1@example.com", "test2@example.com"), "PROJECT_ADMIN");

        verify(userRepository, times(2)).save(any(User.class));
        assertEquals(User.Role.PROJECT_ADMIN, user1.getRole());
        assertEquals(User.Role.PROJECT_ADMIN, user2.getRole());
    }
}
