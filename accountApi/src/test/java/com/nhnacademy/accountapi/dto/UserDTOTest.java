package com.nhnacademy.accountapi.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class UserDTOTest {

    @Test
    void testUserDTOConstructor() {
        // given
        String email = "test@example.com";
        String password = "password123";

        // when
        UserDTO userDTO = new UserDTO(email, password);

        // then
        assertThat(userDTO.email()).isEqualTo(email);
        assertThat(userDTO.password()).isEqualTo(password);
    }

    @Test
    void testUserDTOToString() {
        // given
        String email = "test@example.com";
        String password = "password123";

        // when
        UserDTO userDTO = new UserDTO(email, password);
        String expectedString = "UserDTO[email=test@example.com, password=password123]";

        // then
        assertThat(userDTO.toString()).isEqualTo(expectedString);
    }

    @Test
    void testUserDTOEqualsAndHashCode() {
        // given
        String email = "test@example.com";
        String password = "password123";
        UserDTO userDTO1 = new UserDTO(email, password);
        UserDTO userDTO2 = new UserDTO(email, password);

        // then
        assertThat(userDTO1).isEqualTo(userDTO2);
        assertThat(userDTO1.hashCode()).isEqualTo(userDTO2.hashCode());
    }
}
