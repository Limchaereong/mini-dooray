package com.nhnacademy.accountapi.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

class UserIdsGetDtoTest {

    @Test
    void testUserIdsGetDtoConstructor() {
        // given
        List<String> emails = Arrays.asList("test1@example.com", "test2@example.com");

        // when
        UserIdsGetDto userIdsGetDto = new UserIdsGetDto(emails);

        // then
        assertThat(userIdsGetDto.emails()).isEqualTo(emails);
    }

    @Test
    void testUserIdsGetDtoBuilder() {
        // given
        List<String> emails = Arrays.asList("test1@example.com", "test2@example.com");

        // when
        UserIdsGetDto userIdsGetDto = UserIdsGetDto.builder()
                .emails(emails)
                .build();

        // then
        assertThat(userIdsGetDto.emails()).isEqualTo(emails);
    }

    @Test
    void testUserIdsGetDtoToString() {
        // given
        List<String> emails = Arrays.asList("test1@example.com", "test2@example.com");

        // when
        UserIdsGetDto userIdsGetDto = new UserIdsGetDto(emails);
        String expectedString = "UserIdsGetDto[emails=[test1@example.com, test2@example.com]]";

        // then
        assertThat(userIdsGetDto.toString()).isEqualTo(expectedString);
    }

    @Test
    void testUserIdsGetDtoEqualsAndHashCode() {
        // given
        List<String> emails1 = Arrays.asList("test1@example.com", "test2@example.com");
        List<String> emails2 = Arrays.asList("test1@example.com", "test2@example.com");
        UserIdsGetDto userIdsGetDto1 = new UserIdsGetDto(emails1);
        UserIdsGetDto userIdsGetDto2 = new UserIdsGetDto(emails2);

        // then
        assertThat(userIdsGetDto1).isEqualTo(userIdsGetDto2);
        assertThat(userIdsGetDto1.hashCode()).isEqualTo(userIdsGetDto2.hashCode());
    }
}
