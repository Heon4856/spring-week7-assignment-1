package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
    }


    @Test
    void changeWith() {
        User user = User.builder().build();

        user.changeWith(User.builder()
                .name("TEST")
                .password("TEST")
                .build());

        assertThat(user.getName()).isEqualTo("TEST");
        assertThat(user.getPassword()).isEqualTo("TEST");
    }

    @Test
    void changePassword() {
        User user = User.builder().build();

        user.changePassword("TEST", passwordEncoder);

        assertThat(user.getPassword()).isNotEmpty();
        assertThat(user.getPassword()).isNotEqualTo("TEST");    }

    @Test
    void destroy() {
        User user = User.builder().build();

        assertThat(user.isDeleted()).isFalse();

        user.destroy();

        assertThat(user.isDeleted()).isTrue();
    }

    @Test
    void authenticate() {
        User user = User.builder().build();
        user.changePassword("test", passwordEncoder);

        assertThat(user.authenticate("test", passwordEncoder)).isTrue();
        assertThat(user.authenticate("xxx", passwordEncoder)).isFalse();
    }

    @Test
    void authenticateWithDeletedUser() {
        User user = User.builder().build();
        user.changePassword("test", passwordEncoder);

        assertThat(user.authenticate("test", passwordEncoder)).isTrue();
        assertThat(user.authenticate("xxx", passwordEncoder)).isFalse();

        user.destroy();

        assertThat(user.authenticate("test", passwordEncoder)).isFalse();
        assertThat(user.authenticate("xxx", passwordEncoder)).isFalse();
    }
}
