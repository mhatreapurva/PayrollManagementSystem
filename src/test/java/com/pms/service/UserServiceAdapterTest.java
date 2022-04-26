package com.pms.service;

import com.pms.model.Role;
import com.pms.model.User;
import com.pms.repository.DepartmentRepository;
import com.pms.repository.RoleRepository;
import com.pms.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class UserServiceAdapterTest {

    @Mock private UserRepository userRepository;
    @Mock private RoleRepository roleRepository;
    @Mock private DepartmentRepository departmentRepository;
    @Mock private PasswordEncoder passwordEncoder;

    private UserServiceAdapter underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new UserServiceAdapter(
                userRepository,
                roleRepository,
                departmentRepository,
                passwordEncoder
        );
    }

    @Test
    void canGetUsers() {
        /*
        This is the result test pattern.
         */
        /*
        Given
         */
        List<User> users = List.of(
                new User(
                        "apurva",
                        "appu",
                        "1234",
                        "amhatre@syr.edu",
                        new ArrayList<>()
                ),
                new User(
                        "professor",
                        "prof",
                        "1234",
                        "prof@syr.edu",
                        new ArrayList<>()
                )

        );
        given(userRepository.findAll()).willReturn(users);
        /*
        When
         */
        List<User> actual = underTest.getUsers();
        /*
        Then
         */
        List<User> expected = new ArrayList<>();
        expected.add(new User(
                "apurva",
                "appu",
                "1234",
                "amhatre@syr.edu",
                new ArrayList<>()
        ));
        expected.add( new User(
                "professor",
                "prof",
                "1234",
                "prof@syr.edu",
                new ArrayList<>()
        ));
        /*
        Pass/Fail condition
         */
        assertThat((int)actual.size()).isEqualTo((int)expected.size());
        for (int i = 0 ; i < 2 ; i++){
            assertThat(actual.get(i).getName()).isEqualTo(expected.get(i).getName());
            assertThat(actual.get(i).getUsername()).isEqualTo(expected.get(i).getUsername());
            assertThat(actual.get(i).getEmail()).isEqualTo(expected.get(i).getEmail());
            assertThat(actual.get(i).getPassword()).isEqualTo(expected.get(i).getPassword());
        }
    }
}